package school.sptech.EncantoPersonalizados.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.entities.Movimentacao;

import java.time.LocalDate;
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
    AND (:statusPagamento IS NULL OR m.statusPagamento = :statusPagamento)
    
    AND (:dataVencInicio IS NULL OR m.dataVencimento >= :dataVencInicio)
    AND (:dataVencFim IS NULL OR m.dataVencimento <= :dataVencFim)

    AND (:dataPagInicio IS NULL OR m.dataPagamento >= :dataPagInicio)
    AND (:dataPagFim IS NULL OR m.dataPagamento <= :dataPagFim)

    ORDER BY m.dataVencimento ASC
""")
    Page<Movimentacao> filtrar(
            @Param("search") String search,
            @Param("tipo") String tipo,
            @Param("valor") Double valor,
            @Param("categoria") String categoria,
            @Param("contraparte") String contraparte,
            @Param("nome") String nome,
            @Param("status") Boolean status,
            @Param("statusPagamento") String statusPagamento,
            @Param("dataVencInicio") LocalDate dataVencInicio,
            @Param("dataVencFim") LocalDate dataVencFim,
            @Param("dataPagInicio") LocalDate dataPagInicio,
            @Param("dataPagFim") LocalDate dataPagFim,
            Pageable pageable
    );
}
