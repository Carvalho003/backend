package school.sptech.EncantoPersonalizados.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.EncantoPersonalizados.service.AutenticacaoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoProviderTest {

    @Test
    @DisplayName("Config - AutenticacaoProvider - autentica quando senha confere")
    void authenticate_shouldReturnAuthenticationWhenPasswordMatches() {
        AutenticacaoService service = mock(AutenticacaoService.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AutenticacaoProvider provider = new AutenticacaoProvider(service, encoder);

        UserDetails userDetails = User.withUsername("user").password("encoded").roles("USER").build();
        when(service.loadUserByUsername("user")).thenReturn(userDetails);
        when(encoder.matches("raw", "encoded")).thenReturn(true);

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "raw");

        Authentication result = provider.authenticate(auth);

        assertNotNull(result);
        assertEquals(userDetails, result.getPrincipal());
    }

    @Test
    @DisplayName("Config - AutenticacaoProvider - lança BadCredentials quando senha inválida")
    void authenticate_shouldThrowBadCredentialsWhenPasswordDoesNotMatch() {
        AutenticacaoService service = mock(AutenticacaoService.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AutenticacaoProvider provider = new AutenticacaoProvider(service, encoder);

        UserDetails userDetails = User.withUsername("user").password("encoded").roles("USER").build();
        when(service.loadUserByUsername("user")).thenReturn(userDetails);
        when(encoder.matches("raw", "encoded")).thenReturn(false);

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "raw");

        assertThrows(BadCredentialsException.class, () -> provider.authenticate(auth));
    }

    @Test
    @DisplayName("Config - AutenticacaoProvider - suporta UsernamePasswordAuthenticationToken")
    void supports_shouldReturnTrueForUsernamePasswordAuthenticationToken() {
        AutenticacaoService service = mock(AutenticacaoService.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        AutenticacaoProvider provider = new AutenticacaoProvider(service, encoder);

        assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
    }
}
