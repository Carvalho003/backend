package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.sptech.EncantoPersonalizados.core.application.gateway.UsuarioGateway;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioGateway {

    private final UsuarioRepository repository;

    public UsuarioRepositoryAdapter(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return repository.findUsuarioByEmail(email);
    }

    @Override
    public Page<Usuario> filtrar(String search, String cargo, Boolean status, Pageable pageable) {
        return repository.filtrar(search, cargo, status, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }
}
