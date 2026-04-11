package school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ItemProdutoResponseDTO(
        Integer id,
        @Schema(description = "Nome do produto")
         String descricao,
        @Schema(description = "Double do preço que o produto será vendido")
         Double precoVenda,
        @Schema(description = "Double do custo de produção do produto")
         Double custoProducao,
        @Schema(description = "Prazo emd dias para produção do produto")
         Integer prazoProducao,
        @Schema(description = "Largura em cm do produto")
         Double largura,
        @Schema(description = "Altura em cm do produto")
         Double altura,
        @Schema(description = "Peso em g do produto")
         Double peso,
        @Schema(description = "Comprimento em cm do produto")
         Double comprimento,
        @Schema(description = "Materia prima do produto ")
         String material,
        @Schema(description = "Descrição detalhada Padrão do produto")
        String descricaoPadrao,
        @Schema(description = "Preço promocional caso exista", example = "20,99")
        Double precoPromocional,
        @Schema(description = "Data da criação do usuário")
        LocalDateTime createdAt,
        @Schema(description = "Data da última atualização do usuário")
        LocalDateTime updatedAt
) {
}
