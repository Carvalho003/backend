package school.sptech.EncantoPersonalizados.exceptions;

import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeConflitoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class EntidadeConflitoExceptionTest {

    @Test
    @DisplayName("Exceptions - EntidadeConflitoException - preserva mensagem e possui ResponseStatus CONFLICT")
    void messageAndAnnotationAndType() {
        String msg = "Conflito de entidade";
        EntidadeConflitoException ex = new EntidadeConflitoException(msg);

        // mensagem
        assertEquals(msg, ex.getMessage());
        // tipo
        assertInstanceOf(RuntimeException.class, ex);
        // anotação
        ResponseStatus ann = ex.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(ann);
        assertEquals(HttpStatus.CONFLICT, ann.value());
    }
}
