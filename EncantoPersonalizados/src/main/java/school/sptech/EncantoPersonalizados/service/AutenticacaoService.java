package school.sptech.EncantoPersonalizados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.usuario.UserDetailDTO;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
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
