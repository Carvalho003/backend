package school.sptech.EncantoPersonalizados.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.messageDto;
import school.sptech.EncantoPersonalizados.core.application.usecase.producer.ProducerUseCase;

@RestController
@RequestMapping("/messages")
public class BrokerController {

    private final ProducerUseCase producerUseCase;

    public BrokerController(ProducerUseCase producerUseCase) {
        this.producerUseCase = producerUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> enviarMensagem(@RequestBody messageDto message) {
        producerUseCase.send(message);
        return ResponseEntity.status(202).build();
    }
}
