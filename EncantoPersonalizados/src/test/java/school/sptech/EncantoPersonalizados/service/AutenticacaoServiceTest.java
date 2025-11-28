package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import school.sptech.EncantoPersonalizados.dto.usuario.UserDetailDTO;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService service;

    @Mock
    private UsuarioRepository repository;

    @Test
    @DisplayName("Deve retornar UserDetails quando usuário existe")
    void deveRetornarUserDetailsQuandoUsuarioExiste() {

        Usuario user = new Usuario();
        String email = "userTeste@gmail.com";
        user.setEmail(email);
        user.setPassword("Senha123@");

        Mockito.when(repository.findUsuarioByEmail(email)).thenReturn(Optional.of(user));

        UserDetails resultado = service.loadUserByUsername(email);

        assertNotNull(resultado);
        assertEquals(email, resultado.getUsername());

    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {

        String email = "userTest@exemplo.com";

        Mockito.when(repository.findUsuarioByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException excecao = assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername(email)
        );

        assertEquals("Usuário: userTest@exemplo.com não encontrado", excecao.getMessage());
    }

}