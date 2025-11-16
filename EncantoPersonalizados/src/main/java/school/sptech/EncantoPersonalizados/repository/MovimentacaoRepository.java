package school.sptech.EncantoPersonalizados.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.Movimentacao;

import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {
    Optional<Movimentacao> findByIdAndStatusTrue(Integer id);

    @Query("""
        SELECT m FROM Movimentacao m
        WHERE (:search IS NULL OR LOWER(m.descricao) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:tipo IS NULL OR LOWER(m.tipo) LIKE LOWER(CONCAT('%', :tipo, '%')))
        AND (:valor IS NULL OR m.valor = :valor)
        AND (:categoria IS NULL OR LOWER(m.categoriaMovimentacao.descricao) LIKE LOWER(CONCAT('%', :categoria, '%')))
        AND (:contraparte IS NULL OR LOWER(m.contraparte.descricao) LIKE LOWER(CONCAT('%', :contraparte, '%')))
        AND (:nome IS NULL OR LOWER(m.contraparte.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (m.status = COALESCE(:status, TRUE))
    """)
    Page<Movimentacao> filtrar(
            @Param("search") String search,
            @Param("tipo") String tipo,
            @Param("valor") Double valor,
            @Param("categoria") String categoria,
            @Param("contraparte") String contraparte,
            @Param("nome") String nome,
            @Param("status") Boolean status,
            Pageable pageable
    );
}
