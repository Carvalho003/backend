package school.sptech.EncantoPersonalizados.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;

import java.util.List;
import java.util.Optional;

public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Integer> {
    Optional<List<ItemProduto>> findByPrecoVendaLessThan(Double preco);

    @Query(
            """
            SELECT i FROM ItemProduto i
            WHERE (:search IS NULL OR LOWER(i.descricao) LIKE LOWER(CONCAT('%', :search, '%')) )
            AND :ativo = i.ativo        
            """
    )
    Page<ItemProduto> filtrar(
            @Param("search") String search,
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );
}
