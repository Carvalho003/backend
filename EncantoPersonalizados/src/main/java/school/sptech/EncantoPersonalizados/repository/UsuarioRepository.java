package school.sptech.EncantoPersonalizados.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findUsuarioByEmail(String email);

    @Query("""
        SELECT u FROM Usuario u
        WHERE (:search IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:cargo IS NULL OR LOWER(u.cargo) LIKE LOWER(CONCAT('%', :cargo, '%')))
        AND (u.status = COALESCE(:status, TRUE))
    """)
    Page<Usuario> filtrar(
        @Param("search") String search,
        @Param("cargo") String cargo,
        @Param("status") Boolean status,
        Pageable pageable
    );


}
