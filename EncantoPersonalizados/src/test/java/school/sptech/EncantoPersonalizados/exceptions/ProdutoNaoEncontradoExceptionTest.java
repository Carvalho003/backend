package school.sptech.EncantoPersonalizados.exceptions;

import school.sptech.EncantoPersonalizados.core.domain.exception.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoNaoEncontradoExceptionTest {

    @Test
    @DisplayName("Exceptions - ProdutoNaoEncontradoException - preserva mensagem e possui ResponseStatus NOT_FOUND")
    void messageAndAnnotation() {
        String msg = "Produto não encontrado";
        ProdutoNaoEncontradoException ex = new ProdutoNaoEncontradoException(msg);

        assertEquals(msg, ex.getMessage());
        ResponseStatus ann = ex.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(ann);
        assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, ann.value());
    }
}

