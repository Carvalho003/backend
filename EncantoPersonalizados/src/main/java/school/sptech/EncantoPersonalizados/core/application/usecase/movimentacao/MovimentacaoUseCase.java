package school.sptech.EncantoPersonalizados.core.application.usecase.movimentacao;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.RequestMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.ResponseMovimentacaoDTO;

import java.time.LocalDate;

public interface MovimentacaoUseCase {
    ResponseMovimentacaoDTO create(RequestMovimentacaoDTO dto);
    Page<ResponseMovimentacaoDTO> get(
            String search, String tipo, Double valor, String categoria, String contraparte,
            String nome, Boolean status, String statusPagamento,
            LocalDate dataVencInicio, LocalDate dataVencFim,
            LocalDate dataPagInicio, LocalDate dataPagFim, int page);
    ResponseMovimentacaoDTO getById(Integer id);
    ResponseMovimentacaoDTO update(Integer id, RequestMovimentacaoDTO dto);
    boolean delete(Integer id);
}
