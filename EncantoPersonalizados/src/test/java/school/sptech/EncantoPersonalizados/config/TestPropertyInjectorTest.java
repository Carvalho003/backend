package school.sptech.EncantoPersonalizados.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPropertyInjectorTest {

    private static class Dummy {
        private String privateField = "initial";
    }

    @Test
    @DisplayName("Config - TestPropertyInjector - deve setar campo privado via reflection")
    void setField_shouldSetPrivateField() {
        Dummy dummy = new Dummy();

        TestPropertyInjector.setField(dummy, "privateField", "changed");

        try {
            var field = Dummy.class.getDeclaredField("privateField");
            field.setAccessible(true);
            Object value = field.get(dummy);
            assertEquals("changed", value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Falha ao acessar campo após injeção: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Config - TestPropertyInjector - deve lançar RuntimeException quando campo inexistente")
    void setField_shouldThrowWhenFieldNotFound() {
        Dummy dummy = new Dummy();

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                TestPropertyInjector.setField(dummy, "nonExistent", "value")
        );

        assertNotNull(ex.getMessage());
    }
}

