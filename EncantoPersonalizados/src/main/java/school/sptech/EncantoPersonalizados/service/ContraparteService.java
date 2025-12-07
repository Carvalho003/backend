package school.sptech.EncantoPersonalizados.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.contraparte.MapperContraparteDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.dto.contraparte.ResponseContraparteDTO;
import school.sptech.EncantoPersonalizados.entities.Contraparte;
import school.sptech.EncantoPersonalizados.repository.ContraparteRepository;

@Service
public class ContraparteService {
    private final ContraparteRepository repository;

    public ContraparteService(ContraparteRepository repository) {
        this.repository = repository;
    }

    public ResponseContraparteDTO create(RequestContraparteDTO dto) {
        var entity = MapperContraparteDTO.toEntity(dto);

        var saved = repository.save(entity);

        return MapperContraparteDTO.toDto(saved);
    }

    public Page<ResponseContraparteDTO> get(
            String nome,
            String search,
            String segmento,
            String tipo,
            Boolean status,
            int page
    ){
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        nome = vazioParaNull(nome);
        search = vazioParaNull(search);
        segmento = vazioParaNull(segmento);
        tipo = vazioParaNull(tipo);

        Page<Contraparte> pagina = repository.filtrar(search, tipo, segmento, nome, status, pageable);

        return pagina.map(MapperContraparteDTO::toDto);
    }

    public ResponseContraparteDTO getById(Integer id) {
        var entity = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));

        var response = MapperContraparteDTO.toDto(entity);

        return response;
    }

    public ResponseContraparteDTO update(Integer id, RequestContraparteDTO dto) {
        var entity = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));

        entity.setNome(dto.nome());
        entity.setDescricao((dto.descricao()));
        entity.setSegmento(dto.segmento());
        entity.setTipoContrato(dto.tipoContrato());

        repository.save(entity);

        return MapperContraparteDTO.toDto(entity);
    }

    public boolean delete(Integer id) {
        var entity = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new RuntimeException("Contraparte não encontrada"));

        entity.setStatus(false);

        return true;
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }
}
