package school.sptech.EncantoPersonalizados.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.core.application.gateway.CategoriaMovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ContraparteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.MovimentacaoGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.movimentacao.MovimentacaoUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.RequestMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.movimentacao.ResponseMovimentacaoDTO;
import school.sptech.EncantoPersonalizados.core.domain.*;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimentacaoUseCaseImplTest {

    @Mock
    private MovimentacaoGateway movimentacaoRepository;

    @Mock
    private ContraparteGateway contraparteRepository;

    @Mock
    private CategoriaMovimentacaoGateway categoriaRepository;

    @InjectMocks
    private MovimentacaoUseCaseImpl service;

    @Test
    @DisplayName("Deve validar se o create funciona")
    void deveCriarMovimentacaoComSucesso() {
        RequestMovimentacaoDTO dto = new RequestMovimentacaoDTO(
                "Entrada",
                "Venda de produto",
                100.0,
                "PENDENTE",
                LocalDate.of(2025, 1, 10),
                null,
                1,
                1
        );

        Contraparte contraparte = new Contraparte();
        contraparte.setId(1);
        contraparte.setNome("Cliente XPTO");
        contraparte.setStatus(true);

        CategoriaMovimentacao categoria = new CategoriaMovimentacao();
        categoria.setId(1);
        categoria.setDescricao("Vendas");
        categoria.setStatus(true);

        Movimentacao salva = new Movimentacao();
        salva.setId(10);
        salva.setTipo("Entrada");
        salva.setDescricao("Venda de produto");
        salva.setValor(100.0);
        salva.setStatusPagamento("PENDENTE");
        salva.setDataVencimento(LocalDate.of(2025, 1, 10));
        salva.setDataPagamento(null);
        salva.setCategoriaMovimentacao(categoria);
        salva.setContraparte(contraparte);
        salva.setStatus(true);

        when(contraparteRepository.findByIdAndStatusTrue(1))
                .thenReturn(Optional.of(contraparte));

        when(categoriaRepository.findByIdAndStatusTrue(1))
                .thenReturn(Optional.of(categoria));

        when(movimentacaoRepository.save(any(Movimentacao.class)))
                .thenReturn(salva);

        ResponseMovimentacaoDTO resp = service.create(dto);

        assertNotNull(resp);
        assertEquals("Entrada", resp.tipo());
        assertEquals("Venda de produto", resp.descricao());
        assertEquals(100.0, resp.valor());
        assertEquals("PENDENTE", resp.statusPagamento());
        assertEquals(LocalDate.of(2025, 1, 10), resp.dataVencimento());
        assertNull(resp.dataPagamento());
        assertEquals("Vendas", resp.categoria().descricao());
        assertEquals("Cliente XPTO", resp.contraparte().nome());
    }

    @Test
    @DisplayName("Deve validar se o getById funciona corretamente")
    void deveRetornarMovimentacaoPorId() {
        Movimentacao mov = new Movimentacao();
        mov.setId(20);
        mov.setTipo("Saída");
        mov.setDescricao("Compra Estoque");
        mov.setValor(200.0);
        mov.setDataVencimento(LocalDate.of(2025, 2, 10));
        mov.setDataPagamento(LocalDate.of(2025, 2, 9));
        mov.setStatus(true);

        CategoriaMovimentacao cat = new CategoriaMovimentacao();
        cat.setId(1);
        cat.setDescricao("Operacional");
        cat.setStatus(true);

        Contraparte cp = new Contraparte();
        cp.setId(1);
        cp.setNome("Fornecedor A");
        cp.setDescricao("Fornecedor de insumos");
        cp.setSegmento("Industrial");
        cp.setTipoContrato("Fixado");
        cp.setStatus(true);

        mov.setCategoriaMovimentacao(cat);
        mov.setContraparte(cp);

        when(movimentacaoRepository.findByIdAndStatusTrue(20))
                .thenReturn(Optional.of(mov));

        ResponseMovimentacaoDTO resp = service.getById(20);

        assertNotNull(resp);
        assertEquals("Saída", resp.tipo());
        assertEquals("Compra Estoque", resp.descricao());
        assertEquals(200.0, resp.valor());

        assertEquals("Operacional", resp.categoria().descricao());
        assertEquals("Fornecedor A", resp.contraparte().nome());
    }

    @Test
    @DisplayName("Deve validar movimentação a função para realizar update")
    void deveAtualizarMovimentacao() {
        RequestMovimentacaoDTO dto = new RequestMovimentacaoDTO(
                "Saída",
                "Pagamento de fornecedor",
                500.0,
                null,
                LocalDate.of(2025, 3, 15),
                null,
                2,
                3
        );

        Movimentacao mov = new Movimentacao();
        mov.setId(30);
        mov.setStatus(true);

        CategoriaMovimentacao categoria = new CategoriaMovimentacao();
        categoria.setId(3);
        categoria.setDescricao("Operacional");
        categoria.setStatus(true);

        Contraparte contraparte = new Contraparte();
        contraparte.setId(2);
        contraparte.setNome("Fornecedor Tech");
        contraparte.setDescricao("Tecnologia industrial");
        contraparte.setSegmento("Industrial");
        contraparte.setTipoContrato("Variável");
        contraparte.setStatus(true);

        mov.setCategoriaMovimentacao(categoria);
        mov.setContraparte(contraparte);

        when(movimentacaoRepository.findByIdAndStatusTrue(30))
                .thenReturn(Optional.of(mov));

        when(contraparteRepository.findByIdAndStatusTrue(2))
                .thenReturn(Optional.of(contraparte));

        when(categoriaRepository.findByIdAndStatusTrue(3))
                .thenReturn(Optional.of(categoria));

        when(movimentacaoRepository.save(any(Movimentacao.class)))
                .thenReturn(mov);

        ResponseMovimentacaoDTO resp = service.update(30, dto);

        assertEquals("Saída", mov.getTipo());
        assertEquals("Pagamento de fornecedor", mov.getDescricao());
        assertEquals(500.0, mov.getValor());
        assertEquals("Operacional", mov.getCategoriaMovimentacao().getDescricao());
        assertEquals("Fornecedor Tech", mov.getContraparte().getNome());
    }

    @Test
    @DisplayName("Deve validar se o desativar funciona corretamente")
    void deveDesativarMovimentacao() {

        Movimentacao mov = new Movimentacao();
        mov.setId(5);
        mov.setStatus(true);

        when(movimentacaoRepository.findByIdAndStatusTrue(5))
                .thenReturn(Optional.of(mov));

        boolean result = service.delete(5);

        assertTrue(result);
        assertFalse(mov.getStatus());
    }
}
