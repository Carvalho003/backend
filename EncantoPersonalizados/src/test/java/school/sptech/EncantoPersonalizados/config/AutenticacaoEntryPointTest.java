package school.sptech.EncantoPersonalizados.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import school.sptech.EncantoPersonalizados.infrastructure.config.AutenticacaoEntryPoint;

import static org.mockito.Mockito.*;

class AutenticacaoEntryPointTest {

    @Test
    @DisplayName("Config - AutenticacaoEntryPoint - commence retorna 401 para BadCredentials")
    void commence_shouldReturnUnauthorizedForBadCredentialsOrInsufficientAuth() throws IOException, ServletException {
        AutenticacaoEntryPoint entryPoint = new AutenticacaoEntryPoint();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException badCredentials = new BadCredentialsException("bad");

        entryPoint.commence(request, response, badCredentials);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Config - AutenticacaoEntryPoint - commence retorna 401 para InsufficientAuthentication")
    void commence_shouldReturnForbiddenForOtherErrors() throws IOException, ServletException {
        AutenticacaoEntryPoint entryPoint = new AutenticacaoEntryPoint();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException other = new InsufficientAuthenticationException("other");

        entryPoint.commence(request, response, other);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
