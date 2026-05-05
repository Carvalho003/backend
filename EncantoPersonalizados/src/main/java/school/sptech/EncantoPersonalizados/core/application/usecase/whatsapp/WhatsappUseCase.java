package school.sptech.EncantoPersonalizados.core.application.usecase.whatsapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import school.sptech.EncantoPersonalizados.core.application.usecase.producer.ProducerUseCase;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.messageDto;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.WhatsappRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappUseCase {

    private static final Logger log = LoggerFactory.getLogger(WhatsappUseCase.class);
    private static final String FIXED_RECIPIENT_PHONE = "5511989734403";

    private final RestTemplate restTemplate;
    private final WhatsappRepository whatsappRepository;
    private final ProducerUseCase producerUseCase;
    private final String whatsappApiBaseUrl;
    private final String schedulerMessage;

    public WhatsappUseCase(
            RestTemplateBuilder restTemplateBuilder,
            WhatsappRepository whatsappRepository,
            ProducerUseCase producerUseCase,
            @Value("${whatsapp.api.base-url:http://localhost:3001}") String whatsappApiBaseUrl,
            @Value("${whatsapp.scheduler.message:teste scheduler message}") String schedulerMessage
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.whatsappRepository = whatsappRepository;
        this.producerUseCase = producerUseCase;
        this.whatsappApiBaseUrl = whatsappApiBaseUrl;
        this.schedulerMessage = schedulerMessage;
    }

    public List<String> listarTelefonesEntregasPendentes() {
        return whatsappRepository.listarTelefonesEntregasPendentes();
    }

    public int enviarMensagemParaTelefonesPendentes(String mensagem) {
        return enviarMensagemParaTelefonesPendentes(mensagem, false);
    }

    public int enviarMensagemParaTelefonesPendentes(String mensagem, boolean lembreteAutomatico) {
        List<String> telefonesPendentes = listarTelefonesEntregasPendentes();

        int enviados = 0;
        for (String telefone : telefonesPendentes) {
            if (telefone == null || telefone.isBlank()) {
                continue;
            }
            if (enviarMensagem(telefone, mensagem, lembreteAutomatico)) {
                enviados++;
            }
        }

        return enviados;
    }

    // para testes, enviar mensagens para numeros conhecidos, sem depender do banco de dados

    public int enviarMensagemTesteParaTelefonesConhecidos(List<String> telefonesConhecidos, String mensagem) {
        List<String> telefonesDeTeste = telefonesConhecidos == null
                ? Collections.emptyList()
                : telefonesConhecidos;

        int enviados = 0;
        for (String telefone : telefonesDeTeste) {
            if (telefone == null || telefone.isBlank()) {
                continue;
            }
            if (enviarMensagem(telefone, mensagem, false)) {
                enviados++;
            }
        }

        return enviados;
    }

    // Fluxo antigo de envio automatico por agendamento. Mantido apenas como referencia futura.
    // private final boolean schedulerEnabled;
    //
    // public WhatsappUseCase(
    //         RestTemplateBuilder restTemplateBuilder,
    //         WhatsappRepository whatsappRepository,
    //         ProducerUseCase producerUseCase,
    //         @Value("${whatsapp.api.base-url:http://localhost:3001}") String whatsappApiBaseUrl,
    //         @Value("${whatsapp.scheduler.enabled:true}") boolean schedulerEnabled,
    //         @Value("${whatsapp.scheduler.message:teste scheduler message}") String schedulerMessage
    // ) {
    //     this.restTemplate = restTemplateBuilder.build();
    //     this.whatsappRepository = whatsappRepository;
    //     this.producerUseCase = producerUseCase;
    //     this.whatsappApiBaseUrl = whatsappApiBaseUrl;
    //     this.schedulerEnabled = schedulerEnabled;
    //     this.schedulerMessage = schedulerMessage;
    // }
    //
    // Envia mensagem agendada automaticamente para pedidos pendentes
    // Configure o horario em application.properties:
    // whatsapp.scheduler.fixed-rate-ms=TEMPO_EM_MILISSEGUNDOS
    // Exemplos:
    //   - 3600000 = 1 hora
    //   - 86400000 = 1 dia
    //   - 300000 = 5 minutos (para testes)
    // @Scheduled(fixedRateString = "${whatsapp.scheduler.fixed-rate-ms:300000}")
    // public void enviarMensagemAutomaticaParaPendentes() {
    //     if (!schedulerEnabled) {
    //         log.info("Scheduler do WhatsApp desabilitado (whatsapp.scheduler.enabled=false)");
    //         return;
    //     }
    //
    //     if (schedulerMessage == null || schedulerMessage.isBlank()) {
    //         log.warn("Mensagem do scheduler vazia. Defina whatsapp.scheduler.message para habilitar envio automatico.");
    //         return;
    //     }
    //
    //     int enviados = enviarMensagemParaTelefonesPendentes(schedulerMessage, true);
    //     log.info("Scheduler WhatsApp executado. Mensagens enviadas: {}", enviados);
    // }

    public boolean enviarMensagemQuandoPedidoConcluido() {
        if (schedulerMessage == null || schedulerMessage.isBlank()) {
            log.warn("Mensagem de conclusao vazia. Defina whatsapp.scheduler.message para habilitar o envio automatico.");
            return false;
        }

        return enviarMensagemParaNumeroFixo(schedulerMessage);
    }

    public boolean enviarMensagemParaNumeroFixo(String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            log.warn("Mensagem vazia. O envio para o numero fixo nao foi executado.");
            return false;
        }

        return enviarMensagem(FIXED_RECIPIENT_PHONE, mensagem, false);
    }

    private String obterDeviceId() {
        // Busca automaticamente o primeiro device disponível na API do WhatsApp
        // Se nenhum device estiver disponível, retorna null
        try {
            String url = whatsappApiBaseUrl + "/devices";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            // Parse simples: extrai o primeiro "id" encontrado
            String body = response.getBody();
            if (body != null && body.contains("\"id\"")) {
                // Extrai o valor entre aspas do primeiro "id"
                int idIndex = body.indexOf("\"id\"");
                int valorInicio = body.indexOf("\"", idIndex + 5);
                int valorFim = body.indexOf("\"", valorInicio + 1);
                if (valorInicio != -1 && valorFim != -1) {
                    String deviceId = body.substring(valorInicio + 1, valorFim);
                    log.debug("Device ID obtido automaticamente: {}", deviceId);
                    return deviceId;
                }
            }
        } catch (Exception ex) {
            log.error("Erro ao obter lista de devices: {}", ex.getMessage());
        }
        return null;
    }

    private boolean enviarMensagem(String phone, String message, boolean lembreteAutomatico) {
        String url = whatsappApiBaseUrl + "/send/message";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Device-Id", obterDeviceId());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(
                Map.of(
                        "phone", phone,
                        "message", message
                ),
                headers
        );

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info("WhatsApp API response | phone={} | status={} | body={}",
                    phone,
                    response.getStatusCode().value(),
                    response.getBody());

            try {
                producerUseCase.send(new messageDto("WhatsApp enviado para " + phone + ": " + message));
            } catch (Exception ex) {
                log.warn("Falha ao publicar evento no RabbitMQ | phone={} | erro={}", phone, ex.getMessage());
            }

            if (lembreteAutomatico) {
                try {
                    producerUseCase.sendReminderSent(new messageDto("Lembrete automatico enviado para " + phone + ": " + message));
                } catch (Exception ex) {
                    log.warn("Falha ao publicar evento de lembrete enviado | phone={} | erro={}", phone, ex.getMessage());
                }
            }

            return true;
        } catch (RestClientResponseException ex) {
            log.error("Erro ao enviar WhatsApp | phone={} | status={} | body={}",
                    phone,
                    ex.getStatusCode().value(),
                    ex.getResponseBodyAsString());
            return false;
        } catch (ResourceAccessException ex) {
            log.error("Falha de conexao com API do WhatsApp | phone={} | url={} | erro={}",
                    phone,
                    url,
                    ex.getMessage());
            return false;
        }
    }
}