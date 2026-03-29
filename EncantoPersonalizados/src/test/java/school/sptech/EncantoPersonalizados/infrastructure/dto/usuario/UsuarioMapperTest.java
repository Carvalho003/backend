package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void toEntity_null_returnsNull() {
        assertNull(UsuarioMapper.toEntity(null));
    }

    @Test
    void toEntity_mapsFields() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Nome", "email@test.com", "senha123", "foto", "12345678901", LocalDate.now(), "Dev");
        Usuario entity = UsuarioMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Nome", entity.getName());
        assertEquals("email@test.com", entity.getEmail());
    }

    @Test
    void toResponseDTO_null_returnsNull() {
        assertNull(UsuarioMapper.toResponseDTO((Usuario) null));
    }

    @Test
    void toResponseDTO_mapsFields() {
        Usuario u = new Usuario();
        u.setId(9);
        u.setName("N");
        u.setEmail("a@b");
        u.setFoto("f");
        u.setCpf("cpf");
        u.setDataNasc(LocalDate.now());
        u.setCargo("c");
        u.setCreatedAt(LocalDateTime.now());

        UsuarioResponseDTO dto = UsuarioMapper.toResponseDTO(u);
        assertNotNull(dto);
        assertEquals(9, dto.id());
        assertEquals("N", dto.name());
    }

    @Test
    void toResponseDTO_list_null_returnsNull() {
        assertNull(UsuarioMapper.toResponseDTO((List<Usuario>) null));
    }
}
