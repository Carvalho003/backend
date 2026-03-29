package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.EncantoPersonalizados.core.application.usecase.login.LoginUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.config.GerenciadorTokenJwt;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UserTokenDTO;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.core.application.gateway.UsuarioGateway;
import org.springframework.security.core.Authentication;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @InjectMocks
    private LoginUseCaseImpl service;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioGateway repository;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Deve validar login com credenciais corretas e gerar token")
    void deveValidarLoginComCredenciaisCorretas() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail("teste@email.com");
        usuario.setPassword("senha");

        when(repository.findUsuarioByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        Authentication authenticationMock = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenAnswer(invocation -> authenticationMock);

        when(gerenciadorTokenJwt.generateToken(authenticationMock)).thenReturn("token123");

        UserTokenDTO resultado = service.validateLogin("teste@email.com", "senha");

        assertNotNull(resultado);
        assertEquals("token123", resultado.getToken());
        assertEquals("teste@email.com", resultado.getEmail());

    }

    @Test
    @DisplayName("Deve lançar exceção quando email não estiver cadastrado")
    void deveLancarExcecaoQuandoEmailNaoCadastrado() {
        when(repository.findUsuarioByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> service.validateLogin("naoexiste@email.com", "senha")
        );

        assertEquals(404, excecao.getStatusCode().value());
        assertEquals("Email do usuário não cadastrado", excecao.getReason());

    }

    @Test
    @DisplayName("Deve atualizar senha de usuário existente")
    void deveAtualizarSenhaUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setPassword("novaSenha");

        Usuario usuarioBanco = new Usuario();
        usuarioBanco.setId(1);
        usuarioBanco.setPassword("senhaAntiga");

        when(repository.findById(1)).thenReturn(Optional.of(usuarioBanco));
        when(passwordEncoder.encode("novaSenha")).thenReturn("novaSenhaCodificada");
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        boolean resultado = service.updatePassword(usuario, 1);

        assertTrue(resultado);
        assertEquals("novaSenhaCodificada", usuarioBanco.getPassword());
    }

    @Test
    @DisplayName("Deve retornar false ao tentar atualizar senha de usuário inexistente")
    void deveRetornarFalseAoAtualizarSenhaUsuarioInexistente() {
        Usuario usuario = new Usuario();
        usuario.setId(99);
        usuario.setPassword("novaSenha");

        when(repository.findById(99)).thenReturn(Optional.empty());

        boolean resultado = service.updatePassword(usuario, 99);

        assertFalse(resultado);
    }

}