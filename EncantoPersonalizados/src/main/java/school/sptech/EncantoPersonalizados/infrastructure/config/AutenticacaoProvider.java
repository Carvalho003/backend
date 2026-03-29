package school.sptech.EncantoPersonalizados.infrastructure.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UserDetailDTO;
import school.sptech.EncantoPersonalizados.infrastructure.adapter.AutenticacaoAdapter;

public class AutenticacaoProvider implements AuthenticationProvider {

    private final AutenticacaoAdapter usuarioAutorizacaoService;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoProvider(AutenticacaoAdapter usuarioAutorizacaoService, PasswordEncoder passwordEncoder) {
        this.usuarioAutorizacaoService = usuarioAutorizacaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserDetails userDetails = this.usuarioAutorizacaoService.loadUserByUsername(username);

        if(this.passwordEncoder.matches(password, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        else{
            throw new BadCredentialsException("Usuario ou senha invalidos.");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
