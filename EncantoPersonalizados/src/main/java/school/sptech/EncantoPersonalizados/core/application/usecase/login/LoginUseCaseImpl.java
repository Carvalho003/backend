package school.sptech.EncantoPersonalizados.core.application.usecase.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.EncantoPersonalizados.core.application.gateway.UsuarioGateway;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.infrastructure.config.GerenciadorTokenJwt;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UserTokenDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioMapper;

@Component
public class LoginUseCaseImpl implements LoginUseCase {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioGateway gateway;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public LoginUseCaseImpl(
            PasswordEncoder passwordEncoder,
            UsuarioGateway gateway,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            AuthenticationManager authenticationManager
    ) {
        this.passwordEncoder = passwordEncoder;
        this.gateway = gateway;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserTokenDTO validateLogin(String email, String password, boolean rememberMe) {
        Usuario usuario = gateway.findUsuarioByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        final Authentication authentication = this.authenticationManager.authenticate(credentials);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication, rememberMe);

        return UsuarioMapper.of(usuario, token);
    }

    @Override
    public boolean updatePassword(Usuario usuario, Integer id) {
        var usuarioAtualizar = gateway.findById(id);
        if (usuarioAtualizar.isPresent()) {
            var usuarioAtual = usuarioAtualizar.get();
            String encryptedPassword = passwordEncoder.encode(usuario.getPassword());
            usuarioAtual.setPassword(encryptedPassword);
            gateway.save(usuarioAtual);
            return true;
        }
        return false;
    }
}
