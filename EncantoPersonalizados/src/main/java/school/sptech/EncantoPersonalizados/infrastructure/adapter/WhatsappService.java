package school.sptech.EncantoPersonalizados.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;
import school.sptech.EncantoPersonalizados.repository.WhatsappRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappService {

    private final RestTemplate restTemplate;
    private final WhatsappRepository whatsappRepository;
    private final String whatsappApiBaseUrl;
    private final String schedulerPhone;
    private final String schedulerMessage;

    public WhatsappService(
            RestTemplateBuilder restTemplateBuilder,
            WhatsappRepository whatsappRepository,
            @Value("${whatsapp.api.base-url:http://localhost:3001}") String whatsappApiBaseUrl,
            @Value("${whatsapp.scheduler.phone:5511989734403}") String schedulerPhone,
            @Value("${whatsapp.scheduler.message:Mensagem automatica do servidor}") String schedulerMessage
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.whatsappRepository = whatsappRepository;
        this.whatsappApiBaseUrl = whatsappApiBaseUrl;
        this.schedulerPhone = schedulerPhone;
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
            enviarMensagem(telefone, mensagem);
            enviados++;
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
            enviarMensagem(telefone, mensagem);
            enviados++;
        }

        return enviados;
    }

    //@EventListener(ApplicationReadyEvent.class)
    public void enviarMensagemAoSubirAplicacao() {
        enviarMensagemParaTelefonesPendentes("teste");
    }

    @Scheduled(fixedRateString = "${whatsapp.scheduler.fixed-rate-ms:200000}")
    public void enviarMensagemAutomatica() {
        if (schedulerMessage == null || schedulerMessage.isBlank()) {
            return;
        }

        enviarMensagemParaTelefonesPendentes(schedulerMessage);
    }

    private void enviarMensagem(String phone, String message) {
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

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
