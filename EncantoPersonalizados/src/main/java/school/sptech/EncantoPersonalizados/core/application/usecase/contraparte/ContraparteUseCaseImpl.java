package school.sptech.EncantoPersonalizados.core.application.usecase.contraparte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ContraparteGateway;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.MapperContraparteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.ResponseContraparteDTO;

@Component
public class ContraparteUseCaseImpl implements ContraparteUseCase {

    private final ContraparteGateway gateway;

    public ContraparteUseCaseImpl(ContraparteGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public ResponseContraparteDTO create(RequestContraparteDTO dto) {
        var entity = MapperContraparteDTO.toEntity(dto);
        var saved = gateway.save(entity);
        return MapperContraparteDTO.toDto(saved);
    }

    @Override
    public Page<ResponseContraparteDTO> get(String nome, String search, String segmento, String tipo, Boolean status, int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        nome = vazioParaNull(nome);
        search = vazioParaNull(search);
        segmento = vazioParaNull(segmento);
        tipo = vazioParaNull(tipo);

        var pagina = gateway.filtrar(search, tipo, segmento, nome, status, pageable);
        return pagina.map(MapperContraparteDTO::toDto);
    }

    @Override
    public ResponseContraparteDTO getById(Integer id) {
        var entity = gateway.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));
        return MapperContraparteDTO.toDto(entity);
    }

    @Override
    public ResponseContraparteDTO update(Integer id, RequestContraparteDTO dto) {
        var entity = gateway.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));

        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setSegmento(dto.segmento());
        entity.setTipoContrato(dto.tipoContrato());

        gateway.save(entity);
        return MapperContraparteDTO.toDto(entity);
    }

    @Override
    public boolean delete(Integer id) {
        var entity = gateway.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));
        entity.setStatus(false);
        gateway.save(entity);
        return true;
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }
}
