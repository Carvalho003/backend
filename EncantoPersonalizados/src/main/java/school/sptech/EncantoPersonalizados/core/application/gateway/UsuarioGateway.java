package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findUsuarioByEmail(String email);
    Page<Usuario> filtrar(String search, String cargo, Boolean status, Pageable pageable);
    void deleteById(Integer id);
    boolean existsById(Integer id);
    List<Usuario> findAll();
}
