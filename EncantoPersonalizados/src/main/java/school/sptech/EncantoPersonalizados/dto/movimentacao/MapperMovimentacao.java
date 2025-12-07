package school.sptech.EncantoPersonalizados.dto.movimentacao;

import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.MapperCategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.dto.categoriaMovimentacao.ResponseCategoriaMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.MapperContraparteDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.ResponseContraparteDTO;
import school.sptech.EncantoPersonalizados.entities.CategoriaMovimentacao;
import school.sptech.EncantoPersonalizados.entities.Contraparte;
import school.sptech.EncantoPersonalizados.entities.Movimentacao;

public class MapperMovimentacao {
    public static Movimentacao toEntity(RequestMovimentacaoDTO dto, Contraparte contraparte, CategoriaMovimentacao categoriaMovimentacao) {
        var entity = new Movimentacao();
        entity.setTipo(dto.tipo());
        entity.setDescricao(dto.descricao());
        entity.setValor(dto.valor());
        entity.setStatusPagamento(dto.statusPagamento());
        entity.setDataVencimento(dto.dataVencimento());
        entity.setDataPagamento(dto.dataPagamento());
        entity.setContraparte(contraparte);
        entity.setCategoriaMovimentacao(categoriaMovimentacao);
        entity.setStatus(true);

        return entity;
    }

    public static ResponseMovimentacaoDTO toDto(Movimentacao movimentacao) {

        var categoriaDTO = MapperCategoriaMovimentacao.toDTO(
                movimentacao.getCategoriaMovimentacao()
        );

        var contraparteDTO = MapperContraparteDTO.toDto(
                movimentacao.getContraparte()
        );

        return new ResponseMovimentacaoDTO(
                movimentacao.getTipo(),
                movimentacao.getDescricao(),
                movimentacao.getValor(),
                movimentacao.getStatusPagamento(),
                movimentacao.getDataVencimento(),
                movimentacao.getDataPagamento(),
                categoriaDTO,
                contraparteDTO
        );
    }
}
