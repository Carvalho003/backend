package school.sptech.EncantoPersonalizados.infrastructure.dto.produto;

import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.Produto;

import java.time.LocalDateTime;
import java.util.List;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoRequestDTO dto){
        if(dto == null) return null;

        Produto entity = new Produto();
        entity.setDescricao(dto.descricao());
        entity.setTitulo(dto.titulo());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static List<ProdutoResponseDTO> toDto(
            List<Produto> entity
    ){
        if(entity == null) return null;

        return entity.stream()
                .map(e -> {
                    TemaProdutoResponseDTO responseDtoTema = TemaProdutoMapper.toDto(e.getTemaProduto());
                    ItemProdutoResponseDTO responseDtoItem = ItemProdutoMapper.toDto(e.getItemProduto());
                    List<FotoProdutoResponseDTO> responseDtoFoto = FotoProdutoMapper.toDto(e.getFotoProdutos());

                    ProdutoResponseDTO dto = new ProdutoResponseDTO(
                            e.getId(),
                            e.getTitulo(),
                            e.getDescricao(),
                            responseDtoFoto,
                            responseDtoTema,
                            responseDtoItem,
                            e.getCreatedAt(),
                            e.getUpdatedAt()
                    );
                    return dto;
                })

                .toList();


    }

    public static ProdutoResponseDTO toDto(
            Produto entity
    ){
        if(entity == null) return null;

        TemaProdutoResponseDTO responseDtoTema = TemaProdutoMapper.toDto(entity.getTemaProduto());
        ItemProdutoResponseDTO responseDtoItem = ItemProdutoMapper.toDto(entity.getItemProduto());
        List<FotoProdutoResponseDTO> responseDtoFoto = FotoProdutoMapper.toDto(entity.getFotoProdutos());

        ProdutoResponseDTO dto = new ProdutoResponseDTO(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescricao(),
                responseDtoFoto,
                responseDtoTema,
                responseDtoItem,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return dto;
    }
}
