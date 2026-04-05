package school.sptech.EncantoPersonalizados.infrastructure.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.WhatsappRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappService {

    private static final Logger log = LoggerFactory.getLogger(WhatsappService.class);

    private final RestTemplate restTemplate;
    private final WhatsappRepository whatsappRepository;
    private final String whatsappApiBaseUrl;
    private final String schedulerMessage;
    private final boolean schedulerEnabled;

    public WhatsappService(
            RestTemplateBuilder restTemplateBuilder,
            WhatsappRepository whatsappRepository,
            @Value("${whatsapp.api.base-url:http://localhost:3001}") String whatsappApiBaseUrl,
            @Value("${whatsapp.scheduler.enabled:true}") boolean schedulerEnabled,
            @Value("${whatsapp.scheduler.message:teste scheduler message}") String schedulerMessage
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.whatsappRepository = whatsappRepository;
        this.whatsappApiBaseUrl = whatsappApiBaseUrl;
        this.schedulerEnabled = schedulerEnabled;
        this.schedulerMessage = schedulerMessage;
    }

    public List<String> listarTelefonesEntregasPendentes() {
        return whatsappRepository.listarTelefonesEntregasPendentes();
    }

    public int enviarMensagemParaTelefonesPendentes(String mensagem) {
        List<String> telefonesPendentes = listarTelefonesEntregasPendentes();

        int enviados = 0;
        for (String telefone : telefonesPendentes) {
            if (telefone == null || telefone.isBlank()) {
                continue;
            }
            if (enviarMensagem(telefone, mensagem)) {
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
            if (enviarMensagem(telefone, mensagem)) {
                enviados++;
            }
        }

        return enviados;
    }

    // Envia mensagem agendada automaticamente para pedidos pendentes
    // Configure o horario em application.properties:
    // whatsapp.scheduler.fixed-rate-ms=TEMPO_EM_MILISSEGUNDOS
    // Exemplos:
    //   - 3600000 = 1 hora
    //   - 86400000 = 1 dia
    //   - 300000 = 5 minutos (para testes)
    @Scheduled(fixedRateString = "${whatsapp.scheduler.fixed-rate-ms:300000}")
    public void enviarMensagemAutomaticaParaPendentes() {
        if (!schedulerEnabled) {
            log.info("Scheduler do WhatsApp desabilitado (whatsapp.scheduler.enabled=false)");
            return;
        }

        if (schedulerMessage == null || schedulerMessage.isBlank()) {
            log.warn("Mensagem do scheduler vazia. Defina whatsapp.scheduler.message para habilitar envio automatico.");
            return;
        }

        // Envia a mensagem configurada para todos os telefones com pedidos pendentes
        int enviados = enviarMensagemParaTelefonesPendentes(schedulerMessage);
        log.info("Scheduler WhatsApp executado. Mensagens enviadas: {}", enviados);
    }

    private boolean enviarMensagem(String phone, String message) {
        String url = whatsappApiBaseUrl + "/send/message";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

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