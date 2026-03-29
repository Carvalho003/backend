package school.sptech.EncantoPersonalizados.core.application.usecase.usuario;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioResponseDTO;

import java.io.IOException;
import java.util.List;

public interface UsuarioUseCase {
    boolean destroy(Integer id);
    Page<Usuario> get(String search, String cargo, Boolean status, int page);
    UsuarioResponseDTO getById(Integer id);
    Usuario getEntityById(Integer id);
    UsuarioResponseDTO store(Usuario usuario);
    Usuario storeFoto(Integer id, MultipartFile file) throws IOException;
    void deleteFoto(Integer id) throws IOException;
    UsuarioResponseDTO update(Usuario usuario, Integer id);
    UsuarioResponseDTO updatePassword(String oldPassword, String newPassword, Integer id);
    List<Usuario> getUsuariosComMergeSortPorNome();
}
