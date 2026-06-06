package school.sptech.EncantoPersonalizados.core.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MovimentacaoInvalidaException extends RuntimeException{
    public MovimentacaoInvalidaException(String message) {
        super(message);
    }
}
