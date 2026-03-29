package school.sptech.EncantoPersonalizados.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.EncantoPersonalizados.infrastructure.adapter.AutenticacaoAdapter;
import school.sptech.EncantoPersonalizados.infrastructure.config.AutenticacaoFilter;
import school.sptech.EncantoPersonalizados.infrastructure.config.GerenciadorTokenJwt;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoFilterTest {

    private AutenticacaoAdapter autenticacaoService;
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    private AutenticacaoFilter filter;

    @BeforeEach
    void setUp() {
        autenticacaoService = mock(AutenticacaoAdapter.class);
        gerenciadorTokenJwt = mock(GerenciadorTokenJwt.class);
        filter = new AutenticacaoFilter(autenticacaoService, gerenciadorTokenJwt);
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Config - AutenticacaoFilter - doFilterInternal seta authentication quando token válido")
    void doFilterInternal_shouldSetAuthenticationWhenTokenIsValid() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(gerenciadorTokenJwt.getUsernameFromToken("valid-token")).thenReturn("user");

        UserDetails userDetails = new User("user", "password", Collections.emptyList());
        when(autenticacaoService.loadUserByUsername("user")).thenReturn(userDetails);
        when(gerenciadorTokenJwt.validateToken("valid-token", userDetails)).thenReturn(true);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    @DisplayName("Config - AutenticacaoFilter - doFilterInternal retorna 401 quando token expirado")
    void doFilterInternal_shouldReturnUnauthorizedWhenTokenExpired() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer expired-token");

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("user-expired");

        ExpiredJwtException expiredEx = mock(ExpiredJwtException.class);
        when(expiredEx.getClaims()).thenReturn(claims);

        when(gerenciadorTokenJwt.getUsernameFromToken("expired-token")).thenThrow(expiredEx);

        filter.doFilterInternal(request, response, chain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain).doFilter(request, response);
    }
}
