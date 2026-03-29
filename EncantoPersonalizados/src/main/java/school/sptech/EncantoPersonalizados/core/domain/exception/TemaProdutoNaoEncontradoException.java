package school.sptech.EncantoPersonalizados.core.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TemaProdutoNaoEncontradoException extends RuntimeException {
    public TemaProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
