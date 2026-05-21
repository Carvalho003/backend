package school.sptech.EncantoPersonalizados.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.core.application.usecase.pedido.PedidoUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoRequestDto;
import school.sptech.EncantoPersonalizados.core.domain.*;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.core.application.gateway.ClienteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoStatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.UsuarioGateway;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseImplTest {
    @Mock
    private ProdutoGateway produtoGateway;

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private PedidoStatusPedidoGateway pedidoStatusPedidoGateway;

    @Mock
    private ProdutoPedidoGateway produtoPedidoGateway;

    @Mock
    private PedidoGateway pedidoRepository;

    @Mock
    private StatusPedidoGateway statusPedidoGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private PedidoUseCaseImpl facade;


    //function store
    @Test
    @DisplayName("Lançar exceção quando o cliente não existe")
    void LancarExcecaoQuandoClienteNaoExiste() {
        PedidoRequestDto dto = new PedidoRequestDto(
                "a",
                "a",
                1,
                1,
                LocalDate.of(2026, 5, 21), List.of()
        );

        when(clienteGateway.findById(dto.clienteId())).thenReturn(null);

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.store(dto));

        assertEquals("Cliente não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Lançar exceção quando o usuário não existe")
    void LancarExcecaoQuandoUsuarioNaoExiste() {
        PedidoRequestDto dto = new PedidoRequestDto(
                "a",
                "a",
                1,
                1,
                LocalDate.of(2026, 5, 21),List.of()
        );

        Cliente cliente = new Cliente();
        cliente.setId(1);

        when(clienteGateway.findById(1)).thenReturn(cliente);
        when(usuarioGateway.findById(1)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> facade.store(dto)
        );

        assertEquals("Usuario responsável não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Lançar exceção quando status inicial do Kanban não existe")
    void LancarExcecaoQuandoStatusInicialNaoExiste() {
        PedidoRequestDto dto = new PedidoRequestDto(
                "a",
                "a",
                1,
                1,
                LocalDate.of(2026, 5, 21),List.of()
        );

        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();

        when(clienteGateway.findById(1)).thenReturn(cliente);
        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));
        when(statusPedidoGateway.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.store(dto));

        assertEquals("Status do pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Store - Deve lançar exceção quando um produto não existe")
    void store_DeveLancarExcecaoQuandoProdutoNaoExiste() {
        ProdutosPedidoRequestDto produtosPedidoRequestDto = new ProdutosPedidoRequestDto(
                1,
                1,
                1
        );
        PedidoRequestDto dto = new PedidoRequestDto(
                "a",
                "a",
                1,
                1,
                LocalDate.of(2026, 5, 21), List.of(produtosPedidoRequestDto)
        );

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Usuario usuario = new Usuario();
        usuario.setId(1);

        StatusPedido statusInicial = new StatusPedido();
        statusInicial.setId(1);

        when(clienteGateway.findById(1)).thenReturn(cliente);
        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));
        when(statusPedidoGateway.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(statusInicial));

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(1);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvo);
        when(pedidoStatusPedidoGateway.findStatusAtualByPedidoId(anyInt())).thenReturn(List.of());
        when(pedidoStatusPedidoGateway.salvar(any(PedidoStatusPedido.class))).thenAnswer(inv -> inv.getArgument(0));

        when(produtoGateway.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.store(dto));

        assertEquals("Produto não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Salvar pedido com sucesso")
    void salvarPedidoComSucesso() {
        ProdutosPedidoRequestDto produtoReq = new ProdutosPedidoRequestDto(1, 1, 1);
        PedidoRequestDto pedidoDto = new PedidoRequestDto(
                "Observações de teste",
                "Online",
                1,
                1,
                LocalDate.of(2026, 5, 21), List.of(produtoReq)
        );

        Cliente cliente = new Cliente();
        cliente.setId(1);

        Usuario usuario = new Usuario();
        usuario.setId(1);

        StatusPedido statusInicial = new StatusPedido();
        statusInicial.setId(1);

        Produto produto = new Produto();
        produto.setId(1);
        ItemProduto item = new ItemProduto();
        item.setPrazoProducao(3);
        item.setPrecoVenda(50.0);
        item.setPeso(1.5);
        produto.setItemProduto(item);

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(1);

        ProdutoPedido produtoPedidoSalvo = new ProdutoPedido();
        produtoPedidoSalvo.setPrecoTotal(50.0);
        produtoPedidoSalvo.setPesoTotal(1.5);
        produtoPedidoSalvo.setPrecoUnitario(50.0);
        produtoPedidoSalvo.setPesoUnitario(1.5);
        produtoPedidoSalvo.setQtdProduto(1);

        when(clienteGateway.findById(1)).thenReturn(cliente);
        when(usuarioGateway.findById(1)).thenReturn(Optional.of(usuario));
        when(statusPedidoGateway.findFirstByOrderByOrdemKanbanAsc()).thenReturn(Optional.of(statusInicial));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(1);
            return p;
        });
        when(pedidoStatusPedidoGateway.findStatusAtualByPedidoId(anyInt())).thenReturn(List.of());
        when(pedidoStatusPedidoGateway.salvar(any(PedidoStatusPedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(produtoGateway.findById(1)).thenReturn(Optional.of(produto));
        when(produtoPedidoGateway.save(any(ProdutoPedido.class))).thenAnswer(inv -> {
            ProdutoPedido pp = inv.getArgument(0);
            pp.setPrecoTotal(50.0);
            pp.setPesoTotal(1.5);
            return pp;
        });

        PedidoCreatedResponseDto resultado = facade.store(pedidoDto);

        assertNotNull(resultado);
        assertEquals(1, resultado.id());
        assertEquals(pedidoDto.observacoes(), resultado.observacoes());
        assertEquals(pedidoDto.origem(), resultado.origem());
        assertNotNull(resultado.dataLimite());

        verify(clienteGateway).findById(1);
        verify(usuarioGateway).findById(1);
        verify(statusPedidoGateway).findFirstByOrderByOrdemKanbanAsc();
        verify(produtoGateway).findById(1);
        verify(pedidoRepository, times(2)).save(any(Pedido.class));
        verify(produtoPedidoGateway).save(any(ProdutoPedido.class));
        verify(pedidoStatusPedidoGateway).salvar(any(PedidoStatusPedido.class));
    }

    //function listar
    @Test
    @DisplayName("Retornar página vazia quando não há pedidos")
    void RetornarPaginaVaziaQuandoNaoHaPedidos() {
        String search = "teste";
        Integer page = 0;
        Boolean ativo = true;

        Pageable pageable = PageRequest.of(page, 10);

        when(pedidoRepository.filtrar(search, ativo, null, null, pageable, 0))
                .thenReturn(Page.empty());

        Page<PedidoResponseDto> resultado = facade.listar(search, page, ativo, null, null, 0);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Verificar se filtro pelo search funciona corretamente")
    void filtroComSearchFuncional() {
        String search = "online";
        Integer page = 0;
        Boolean ativo = true;

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setObservacoes("Pedido Online");
        pedido.setOrigem("Online");
        pedido.setProdutoPedidos(new ArrayList<>());
        pedido.setPedidoStatusPedidos(new ArrayList<>());

        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(1);
        statusPedido.setStatus("Em Produção");

        PedidoStatusPedido statusAtual = new PedidoStatusPedido();
        statusAtual.setId(1);
        statusAtual.setPedido(pedido);
        statusAtual.setStatus(statusPedido);
        statusAtual.setStatusAtual(true);

        Page<Pedido> pedidosPage = new PageImpl<>(List.of(pedido));

        when(pedidoRepository.filtrar(eq(search), eq(ativo), any(), any(), any(Pageable.class), any(Integer.class)))
                .thenReturn(pedidosPage);
        when(pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId()))
                .thenReturn(List.of(statusAtual));

        Page<PedidoResponseDto> resultado = facade.listar(search, page, ativo, null, null, 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());

        PedidoResponseDto dto = resultado.getContent().get(0);
        assertEquals(pedido.getObservacoes(), dto.observacoes());
        assertEquals(pedido.getOrigem(), dto.origem());

        assertNotNull(dto.statusAtual());
        assertEquals(statusAtual.getId(), dto.statusAtual().id());
        assertEquals(statusAtual.getPedido().getId(), dto.statusAtual().idPedido());
        assertEquals(statusAtual.getStatus().getId(), dto.statusAtual().idStatusPedido());
        assertTrue(dto.statusAtual().statusAtual());

        verify(pedidoRepository).filtrar(eq(search), eq(ativo), any(), any(), any(Pageable.class), any(Integer.class));
        verify(pedidoStatusPedidoGateway).findStatusAtualByPedidoId(pedido.getId());
    }

    @Test
    @DisplayName("Mapeamento para DTO está funcionando corretamente")
    void mapeamentoDtoFuncionandoCorretamente() {
        String search = null;
        Integer page = 0;
        Boolean ativo = true;

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setObservacoes("Teste DTO");
        pedido.setOrigem("Loja Física");
        pedido.setPedidoStatusPedidos(new ArrayList<>());
        pedido.setProdutoPedidos(new ArrayList<>());

        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(1);
        statusPedido.setStatus("Concluído");

        PedidoStatusPedido statusAtual = new PedidoStatusPedido();
        statusAtual.setStatus(statusPedido);
        statusAtual.setPedido(pedido);
        statusAtual.setStatusAtual(true);
        statusAtual.setId(1);

        Page<Pedido> pedidosPage = new PageImpl<>(List.of(pedido));

        when(pedidoRepository.filtrar(eq(search), eq(ativo), any(), any(), any(Pageable.class), any(Integer.class)))
                .thenReturn(pedidosPage);
        when(pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId()))
                .thenReturn(List.of(statusAtual));

        Page<PedidoResponseDto> resultado = facade.listar(search, page, ativo, null, null, 0);
        PedidoResponseDto dto = resultado.getContent().get(0);

        assertEquals(pedido.getId(), dto.id());
        assertEquals(pedido.getObservacoes(), dto.observacoes());
        assertEquals(pedido.getOrigem(), dto.origem());
        assertNotNull(dto.statusAtual());
        assertEquals(statusAtual.getId(), dto.statusAtual().id());
        assertEquals("Concluído", statusAtual.getStatus().getStatus());

        verify(pedidoRepository).filtrar(eq(search), eq(ativo), any(), any(), any(Pageable.class), any(Integer.class));
        verify(pedidoStatusPedidoGateway).findStatusAtualByPedidoId(pedido.getId());
    }

    //function getById
    @Test
    @DisplayName("Lançar exceção quando pedido não existe")
    void LancarExcecaoQuandoPedidoNaoExistir() {
        when(pedidoRepository.findByIdWithDetails(1))
                .thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.getById(1));

        assertEquals("Pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Retornar PedidoResponseDto quando pedido existe")
    void RetornarPedidoResponseDtoQuandoPedidoExistir() {

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setObservacoes("Pedido teste");
        pedido.setOrigem("Online");
        pedido.setProdutoPedidos(new ArrayList<>());
        pedido.setPedidoStatusPedidos(new ArrayList<>());

        PedidoStatusPedido statusAtual = new PedidoStatusPedido();
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(1);
        statusPedido.setStatus("Em Produção");
        statusAtual.setPedido(pedido);
        statusAtual.setStatus(statusPedido);
        statusAtual.setStatusAtual(true);
        statusAtual.setId(1);

        when(pedidoRepository.findByIdWithDetails(1)).thenReturn(Optional.of(pedido));
        when(pedidoStatusPedidoGateway.findStatusAtualByPedidoId(1))
                .thenReturn(List.of(statusAtual));

        PedidoResponseDto resultado = facade.getById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.id());
        assertEquals("Pedido teste", resultado.observacoes());
        assertEquals("Online", resultado.origem());

        assertNotNull(resultado.statusAtual());
        assertEquals(statusAtual.getId(), resultado.statusAtual().id());
        assertEquals(1, resultado.statusAtual().idPedido());
        assertEquals(statusPedido.getId(), resultado.statusAtual().idStatusPedido());
        assertTrue(resultado.statusAtual().statusAtual());

        verify(pedidoRepository).findByIdWithDetails(1);
        verify(pedidoStatusPedidoGateway).findStatusAtualByPedidoId(1);
    }

    //function update
    @Test
    @DisplayName("Lançar exceção quando o pedido não existe")
    void LancarExcecaoQuandoAtualizarMasPedidoNaoExiste() {
        PedidoRequestDto dto = new PedidoRequestDto(
                "Observação X",
                "Online",
                1,
                1,
                LocalDate.of(2026, 5, 21), List.of()
        );

        when(pedidoRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.update(1, dto));

        assertEquals("Pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Atualizar o pedido com sucesso")
    void AtualizarPedidoComSucesso() {
        PedidoRequestDto dto = new PedidoRequestDto(
                "a",
                "a",
                1,
                1,
                LocalDate.of(2026, 5, 21), List.of()
        );

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(1);
        pedidoExistente.setObservacoes("b");
        pedidoExistente.setOrigem("b");
        pedidoExistente.setDataLimite(LocalDateTime.now());

        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setId(1);
        pedidoAtualizado.setObservacoes(dto.observacoes());
        pedidoAtualizado.setOrigem(dto.origem());
        pedidoAtualizado.setDataLimite(pedidoExistente.getDataLimite());

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoExistente));
        when(pedidoRepository.save(pedidoExistente)).thenReturn(pedidoAtualizado);

        PedidoCreatedResponseDto resposta = facade.update(1, dto);

        assertEquals(1, resposta.id());
        assertEquals("a", resposta.observacoes());
        assertEquals("a", resposta.origem());
        assertEquals(pedidoExistente.getDataLimite(), resposta.dataLimite());

        verify(pedidoRepository).findById(1);
        verify(pedidoRepository, times(1)).save(pedidoExistente);
    }

    //function mudarEstado
    @Test
    @DisplayName("Lançar exceção quando mudar estado e o pedido não existe")
    void LancarExcecaoMudarEstadoMasPedidoNaoExiste() {
        PedidoRequestDto dto = new PedidoRequestDto(
                "Observação X",
                "Online",
                1,
                1,
                LocalDate.of(2026, 5, 21), List.of()
        );

        when(pedidoRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.update(1, dto));

        assertEquals("Pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Mudar o estado do pedido e salvar")
    void MudarEstadoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setAtivo(true);

        when(pedidoRepository.findById(1))
                .thenReturn(Optional.of(pedido));

        when(pedidoRepository.save(pedido))
                .thenReturn(pedido);

        facade.mudarEstado(1);

        assertFalse(pedido.isAtivo());

        verify(pedidoRepository).findById(1);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    //function mudarStatus
    @Test
    @DisplayName("mudarStatus - Deve lançar exceção quando o pedido não existe")
    void LancarExcecaoQuandoMudarStatusMasPedidoNaoExiste() {
        PedidoStatusPedidoRequestDto dto = new PedidoStatusPedidoRequestDto(
                1,
                2
        );

        when(pedidoRepository.findById(dto.idPedido()))
                .thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.mudarStatus(dto));

        assertEquals("Pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Lançar exceção quando o statusPedido não existe")
    void LancarExcecaoQuandoStatusPedidoNaoExiste() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        PedidoStatusPedidoRequestDto dto = new PedidoStatusPedidoRequestDto(1, 1);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        when(statusPedidoGateway.findById(dto.idStatusPedido()))
                .thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class,
                () -> facade.mudarStatus(dto));

        assertEquals("Status do pedido não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Criar e salvar corretamente um novo PedidoStatusPedido")
    void SalvarNovoPedidoStatusPedidoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        StatusPedido status = new StatusPedido();
        status.setId(5);

        PedidoStatusPedidoRequestDto dto = new PedidoStatusPedidoRequestDto(
                1,
                5
        );

        when(pedidoRepository.findById(dto.idPedido())).thenReturn(Optional.of(pedido));
        when(statusPedidoGateway.findById(dto.idStatusPedido())).thenReturn(Optional.of(status));
        when(pedidoStatusPedidoGateway.findStatusAtualByPedidoId(1)).thenReturn(List.of());
        when(pedidoStatusPedidoGateway.salvar(any(PedidoStatusPedido.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        facade.mudarStatus(dto);

        verify(pedidoRepository).findById(1);
        verify(statusPedidoGateway).findById(5);
        verify(pedidoStatusPedidoGateway, times(1))
                .salvar(any(PedidoStatusPedido.class));
    }

    //function atualizarPrecoPesoDoPedido
    @Test
    @DisplayName("Lançar exceção quando atualizar o preço e o peso do pedido mas o pedido não existe")
    void LancarExcecaoQuandoAtualizarPrecoPesoDoPedidoMasPedidoNaoExiste() {
        when(pedidoRepository.findById(1)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> facade.atualizarPrecoPesoDoPedido(
                        true,
                        10.0,
                        2.0,
                        1
                )
        );

        assertEquals("Pedido não encontrado", excecao.getMessage());

        verify(pedidoRepository).findById(1);
    }

    @Test
    @DisplayName("Aumentar preço e peso corretamente")
    void AumentarPrecoPesoCorretamente() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setPrecoTotal(1.0);
        pedido.setPesoTotal(1.0);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        facade.atualizarPrecoPesoDoPedido(
                true,
                1.0,
                1.0,
                1
        );

        assertEquals(2.0, pedido.getPrecoTotal());
        assertEquals(2.0, pedido.getPesoTotal());

        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    @DisplayName("Diminuir preço e peso corretamente")
    void DiminuirPrecoPesoCorretamente() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setPrecoTotal(2.0);
        pedido.setPesoTotal(2.0);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        facade.atualizarPrecoPesoDoPedido(
                false,
                1.0,
                1.0,
                1
        );

        assertEquals(1.0, pedido.getPrecoTotal());
        assertEquals(1.0, pedido.getPesoTotal());

        verify(pedidoRepository, times(1)).save(pedido);
    }
}
