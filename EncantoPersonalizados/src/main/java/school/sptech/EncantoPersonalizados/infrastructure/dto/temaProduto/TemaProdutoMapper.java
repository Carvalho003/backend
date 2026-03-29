package school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto;

import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;

import java.time.LocalDateTime;
import java.util.List;

public class TemaProdutoMapper {

    public static TemaProduto toEntity(TemaProdutoRequestDTO dto){
        if (dto == null) return null;

        TemaProduto entity = new TemaProduto();
        entity.setDescricao(dto.descricao());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static TemaProdutoResponseDTO toDto(TemaProduto entity){
        if (entity == null) return null;
        TemaProdutoResponseDTO dto = new TemaProdutoResponseDTO(
                entity.getId(),
                entity.getDescricao(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return dto;
    }

    public static List<TemaProdutoResponseDTO> toDto(List<TemaProduto> entity){
        if (entity == null) return null;

        if(entity.isEmpty()) return null;

        return entity
                .stream()
                .map(e -> new TemaProdutoResponseDTO(
                        e.getId(),
                        e.getDescricao(),
                        e.getCreatedAt(),
                        e.getUpdatedAt()
                        )
                )
                .toList();
    }
}
