package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.application.gateway.ContraparteGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.contraparte.ContraparteUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.RequestContraparteDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.contraparte.ResponseContraparteDTO;
import school.sptech.EncantoPersonalizados.core.domain.*;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContraparteUseCaseImplTest {

    @Mock
    private ContraparteGateway repository;

    @InjectMocks
    private ContraparteUseCaseImpl service;

    @Test
    void deveCriarContraparte() {
        RequestContraparteDTO dto = new RequestContraparteDTO(
                "Fornecedor XPTO",
                "Fornecedor de equipamentos",
                "Industrial",
                "Fixado"
        );

        Contraparte entity = new Contraparte();
        entity.setId(1);
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setSegmento(dto.segmento());
        entity.setTipoContrato(dto.tipoContrato());
        entity.setStatus(true);

        when(repository.save(any(Contraparte.class))).thenReturn(entity);

        ResponseContraparteDTO resp = service.create(dto);

        assertNotNull(resp);
        assertEquals("Fornecedor XPTO", resp.nome());
        assertEquals("Fornecedor de equipamentos", resp.descricao());
        assertEquals("Industrial", resp.segmento());
        assertEquals("Fixado", resp.tipoContrato());
    }

    @Test
    void deveListarContrapartesComFiltro() {
        Contraparte c1 = new Contraparte();
        c1.setId(10);
        c1.setNome("Fornecedor A");
        c1.setDescricao("Desc");
        c1.setSegmento("Industrial");
        c1.setTipoContrato("Variável");
        c1.setStatus(true);

        Page<Contraparte> paginaMock =
                new PageImpl<>(List.of(c1));

        when(repository.filtrar(
                any(), any(), any(), any(), any(), any(Pageable.class)
        )).thenReturn(paginaMock);

        Page<ResponseContraparteDTO> pagina = service.get(
                "Fornecedor A",
                null,
                null,
                null,
                true,
                0
        );

        assertEquals(1, pagina.getTotalElements());
        assertEquals("Fornecedor A", pagina.getContent().get(0).nome());
    }

    @Test
    void deveRetornarContrapartePorId() {
        Contraparte c = new Contraparte();
        c.setId(5);
        c.setNome("Fornecedor Z");
        c.setDescricao("Importante");
        c.setSegmento("Logística");
        c.setTipoContrato("Longo prazo");
        c.setStatus(true);

        when(repository.findByIdAndStatusTrue(5)).thenReturn(Optional.of(c));

        ResponseContraparteDTO resp = service.getById(5);

        assertNotNull(resp);
        assertEquals("Fornecedor Z", resp.nome());
        assertEquals("Importante", resp.descricao());
        assertEquals("Logística", resp.segmento());
        assertEquals("Longo prazo", resp.tipoContrato());
    }

    @Test
    void deveAtualizarContraparte() {
        Contraparte entity = new Contraparte();
        entity.setId(8);
        entity.setNome("Original");
        entity.setDescricao("Original Desc");
        entity.setSegmento("Original Seg");
        entity.setTipoContrato("Original");
        entity.setStatus(true);

        RequestContraparteDTO dto = new RequestContraparteDTO(
                "Novo Nome",
                "Nova Descrição",
                "Novo Segmento",
                "Novo Contrato"
        );

        when(repository.findByIdAndStatusTrue(8)).thenReturn(Optional.of(entity));
        when(repository.save(any(Contraparte.class))).thenReturn(entity);

        ResponseContraparteDTO resp = service.update(8, dto);

        assertEquals("Novo Contrato", entity.getTipoContrato());
        assertEquals("Nova Descrição", entity.getDescricao());
        assertEquals("Novo Segmento", entity.getSegmento());
        assertEquals("Novo Contrato", entity.getTipoContrato());
    }

    @Test
    void deveDesativarContraparte() {
        Contraparte entity = new Contraparte();
        entity.setId(11);
        entity.setStatus(true);

        when(repository.findByIdAndStatusTrue(11)).thenReturn(Optional.of(entity));

        boolean resp = service.delete(11);

        assertTrue(resp);
        assertFalse(entity.getStatus());
    }
}

