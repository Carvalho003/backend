package school.sptech.EncantoPersonalizados.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaTemaNaoEncontradaException extends RuntimeException{
    public CategoriaTemaNaoEncontradaException(String message) {
        super(message);
    }
}
