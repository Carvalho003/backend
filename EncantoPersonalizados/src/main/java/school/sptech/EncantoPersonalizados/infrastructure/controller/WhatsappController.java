package school.sptech.EncantoPersonalizados.infrastructure.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.EncantoPersonalizados.core.application.usecase.whatsapp.WhatsappUseCase;

import java.util.List;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {

    private final WhatsappUseCase whatsappUseCase;

    public WhatsappController(WhatsappUseCase whatsappUseCase) {
        this.whatsappUseCase = whatsappUseCase;
    }

    @GetMapping("/entregas-pendentes")
    public ResponseEntity<List<String>> listarEntregasPendentes() {
        List<String> telefonesPendentes = whatsappUseCase.listarTelefonesEntregasPendentes();
        return ResponseEntity.ok(telefonesPendentes);
    }

    @PostMapping("/enviar-pendentes")
    public ResponseEntity<String> enviarParaPendentes(@RequestBody EnvioPendentesRequest request) {
        int enviados = whatsappUseCase.enviarMensagemParaTelefonesPendentes(request.mensagem());
        return ResponseEntity.ok("Envio de producao realizado para " + enviados + " numero(s) pendente(s).");
    }


    // testes para verificar se as mensagens estão sendo mandadas corretamente
    @PostMapping("/enviar-teste")
    public ResponseEntity<String> enviarTesteParaNumerosConhecidos(@RequestBody EnvioTesteRequest request) {
        int enviados = whatsappUseCase.enviarMensagemTesteParaTelefonesConhecidos(
                request.telefones(),
                request.mensagem()
        );

        return ResponseEntity.ok("Envio de teste realizado para " + enviados + " numero(s).");
    }

    public record EnvioTesteRequest(
            List<String> telefones,
            String mensagem
    ) {
    }

    public record EnvioPendentesRequest(
            @NotBlank(message = "Mensagem nao pode estar vazia") String mensagem
    ) {
    }
}
