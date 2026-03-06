package school.sptech.EncantoPersonalizados.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTemaNaoEncontradaExceptionTest {

    @Test
    @DisplayName("Exceptions - CategoriaTemaNaoEncontradaException - preserva mensagem e possui ResponseStatus NOT_FOUND")
    void messageAndAnnotation() {
        String msg = "Categoria tema não encontrada";
        CategoriaTemaNaoEncontradaException ex = new CategoriaTemaNaoEncontradaException(msg);

        assertEquals(msg, ex.getMessage());
        ResponseStatus ann = ex.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(ann);
        assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, ann.value());
    }
}

