package school.sptech.EncantoPersonalizados.core.application.usecase.contraparte;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.ResponseContraparteDTO;

public interface ContraparteUseCase {
    ResponseContraparteDTO create(RequestContraparteDTO dto);
    Page<ResponseContraparteDTO> get(String nome, String search, String segmento, String tipo, Boolean status, int page);
    ResponseContraparteDTO getById(Integer id);
    ResponseContraparteDTO update(Integer id, RequestContraparteDTO dto);
    boolean delete(Integer id);
}
