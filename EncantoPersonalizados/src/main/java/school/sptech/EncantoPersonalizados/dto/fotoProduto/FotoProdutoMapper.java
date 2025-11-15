package school.sptech.EncantoPersonalizados.dto.fotoProduto;

import school.sptech.EncantoPersonalizados.entities.FotoProduto;

import java.time.LocalDateTime;
import java.util.List;

public class FotoProdutoMapper {
    public static FotoProduto toEntity(FotoProdutoRequestDTO dto){
        if (dto == null) return null;
        FotoProduto entity = new FotoProduto();
        entity.setFoto(dto.foto());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static FotoProdutoResponseDTO toDto(FotoProduto entity){
        if(entity == null) return null;
        FotoProdutoResponseDTO dto = new FotoProdutoResponseDTO(
                entity.getId(),
                entity.getFoto(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return dto;
    }

    public static List<FotoProdutoResponseDTO> toDto(List<FotoProduto> entity){
        if(entity == null) return null;

        return entity
                .stream()
                .map(e -> new FotoProdutoResponseDTO(
                        e.getId(),
                        e.getFoto(),
                        e.getCreatedAt(),
                        e.getUpdatedAt()
                ))
                .toList();

    }
}
