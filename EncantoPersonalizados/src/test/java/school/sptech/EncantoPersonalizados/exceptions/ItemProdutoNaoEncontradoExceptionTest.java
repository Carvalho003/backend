package school.sptech.EncantoPersonalizados.exceptions;

import school.sptech.EncantoPersonalizados.core.domain.exception.ItemProdutoNaoEncontradoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class ItemProdutoNaoEncontradoExceptionTest {

    @Test
    @DisplayName("Exceptions - ItemProdutoNaoEncontradoException - preserva mensagem e possui ResponseStatus NOT_FOUND")
    void messageAndAnnotation() {
        String msg = "Item não encontrado";
        ItemProdutoNaoEncontradoException ex = new ItemProdutoNaoEncontradoException(msg);

        assertEquals(msg, ex.getMessage());
        ResponseStatus ann = ex.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(ann);
        assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, ann.value());
    }
}

