package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.AutenticacaoGateway;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.UsuarioRepository;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UserDetailDTO;

import java.util.Optional;

@Component
public class AutenticacaoAdapter implements AutenticacaoGateway, UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoAdapter(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByEmail(username);

        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuário: %s não encontrado", username));
        }

        return new UserDetailDTO(usuarioOpt.get());
    }
}
