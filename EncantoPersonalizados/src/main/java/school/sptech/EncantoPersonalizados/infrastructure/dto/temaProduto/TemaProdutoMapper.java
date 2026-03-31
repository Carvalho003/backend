package school.sptech.EncantoPersonalizados.infrastructure.dto.temaProduto;

import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.categoriaTema.CategoriaTemaMapper;

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
                CategoriaTemaMapper.toDto(entity.getCategoriaTema()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return dto;
    }

    public static List<TemaProdutoResponseDTO> toDto(List<TemaProduto> entity){
        if (entity == null || entity.isEmpty()) return null;

        return entity.stream()
                .map(TemaProdutoMapper::toDto)
                .toList();
    }
}
