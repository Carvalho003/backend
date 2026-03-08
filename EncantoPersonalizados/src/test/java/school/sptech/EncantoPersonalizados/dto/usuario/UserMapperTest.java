package school.sptech.EncantoPersonalizados.dto.usuario;

import org.junit.jupiter.api.Test;
import school.sptech.EncantoPersonalizados.entities.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void of_createUserDTO_toUsuario() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setName("Joao");
        dto.setCpf("12345678901");
        dto.setCargo("Vendedor");
        dto.setEmail("joao@test.com");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setPassword("senha123");

        Usuario usuario = UserMapper.of(dto);

        assertNotNull(usuario);
        assertEquals("Joao", usuario.getName());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("Vendedor", usuario.getCargo());
        assertEquals("joao@test.com", usuario.getEmail());
        assertEquals("senha123", usuario.getPassword());
        assertNotNull(usuario.getCreatedAt());
    }

    @Test
    void of_usuario_and_token_to_userTokenDTO() {
        Usuario u = new Usuario();
        u.setId(77);
        u.setEmail("a@b.c");
        u.setName("Ana");

        UserTokenDTO tokenDto = UserMapper.of(u, "tok123");
        assertNotNull(tokenDto);
        assertEquals(77, tokenDto.getUserId());
        assertEquals("a@b.c", tokenDto.getEmail());
        assertEquals("Ana", tokenDto.getNome());
        assertEquals("tok123", tokenDto.getToken());
    }

    @Test
    void of_usuario_to_listUserDTO() {
        Usuario u = new Usuario();
        u.setId(55);
        u.setName("Carlos");
        u.setEmail("c@d");
        u.setCargo("Admin");
        u.setDataNasc(LocalDate.of(1990,1,1));

        ListUserDTO dto = UserMapper.of(u);
        assertNotNull(dto);
        assertEquals(55, dto.getId());
        assertEquals("Carlos", dto.getNome());
        assertEquals("Admin", dto.getCargo());
        assertEquals(LocalDate.of(1990,1,1), dto.getDataNasc());
    }
}

