package school.sptech.EncantoPersonalizados.core.application.usecase.pedido;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ClienteGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.PedidoStatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.StatusPedidoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.UsuarioGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.producer.ProducerUseCase;
import school.sptech.EncantoPersonalizados.core.domain.Cliente;
import school.sptech.EncantoPersonalizados.core.domain.Pedido;
import school.sptech.EncantoPersonalizados.core.domain.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedido;
import school.sptech.EncantoPersonalizados.core.domain.StatusPedidoRole;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.core.application.usecase.whatsapp.WhatsappUseCase;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.pedidoStatusPedido.PedidoStatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoRequestDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produtosEmUmPedido.ProdutosPedidoResponseDto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.rabbitMQ.NotificacaoWhatsappEventMessageDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoUseCaseImpl implements PedidoUseCase {

    private final ProdutoGateway produtoGateway;
    private final ClienteGateway clienteGateway;
    private final PedidoStatusPedidoGateway pedidoStatusPedidoGateway;
    private final ProdutoPedidoGateway produtoPedidoGateway;
    private final PedidoGateway pedidoGateway;
    private final StatusPedidoGateway statusPedidoGateway;
    private final UsuarioGateway usuarioGateway;
    private final ProducerUseCase producerUseCase;

    public PedidoUseCaseImpl(
            ProdutoGateway produtoGateway,
            ClienteGateway clienteGateway,
            PedidoStatusPedidoGateway pedidoStatusPedidoGateway,
            ProdutoPedidoGateway produtoPedidoGateway,
            PedidoGateway pedidoGateway,
            StatusPedidoGateway statusPedidoGateway,
            UsuarioGateway usuarioGateway,
            ProducerUseCase producerUseCase
    ) {
        this.produtoGateway = produtoGateway;
        this.clienteGateway = clienteGateway;
        this.pedidoStatusPedidoGateway = pedidoStatusPedidoGateway;
        this.produtoPedidoGateway = produtoPedidoGateway;
        this.pedidoGateway = pedidoGateway;
        this.statusPedidoGateway = statusPedidoGateway;
        this.usuarioGateway = usuarioGateway;
        this.producerUseCase = producerUseCase;
    }

    @Override
    public Pedido findById(Integer id) {
        return pedidoGateway.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public PedidoCreatedResponseDto store(PedidoRequestDto pedidoDto) {
        Cliente cliente = clienteGateway.findById(pedidoDto.clienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        Usuario usuario = usuarioGateway.findById(pedidoDto.usuarioId()).orElse(null);
        if (usuario == null) throw new EntidadeNaoEncontradaException("Usuario responsável não encontrado");

        Pedido entity = PedidoMapper.toEntity(pedidoDto);
        entity.setCliente(cliente);
        entity.setUsuario(usuario);

        StatusPedido statusPedido = statusPedidoGateway.findFirstByOrderByOrdemKanbanAsc().orElse(null);
        if (statusPedido == null) {
            throw new RuntimeException("Status do pedido não encontrado");
        }
        Pedido pedido = pedidoGateway.save(entity);
        PedidoStatusPedido entityStatusPedido = new PedidoStatusPedido();
        entityStatusPedido.setPedido(pedido);
        entityStatusPedido.setStatus(statusPedido);
        entityStatusPedido.setStatusAtual(true);

        List<PedidoStatusPedido> statusAtuais = pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId());
        for (PedidoStatusPedido statusAtual : statusAtuais) {
            statusAtual.setStatusAtual(false);
            pedidoStatusPedidoGateway.salvar(statusAtual);
        }
        PedidoStatusPedido statusAtualDoPedido = pedidoStatusPedidoGateway.salvar(entityStatusPedido);

        Double precoTotalPedido = 0.0;
        Double pesoTotalPedido = 0.0;
        Integer totalDiasProducao = 0;
        List<ProdutosPedidoResponseDto> produtosPedidoResponseDtos = new ArrayList<>();

        for (ProdutosPedidoRequestDto pedidoRequestDto : pedidoDto.produtos()) {
            Produto produto = produtoGateway.findById(pedidoRequestDto.idProduto()).orElse(null);
            if (produto == null) {
                throw new RuntimeException("Produto não encontrado");
            }

            school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido produtoPedido = ProdutosPedidoMapper.toEntity(pedidoRequestDto);
            produtoPedido.setPedido(pedido);
            produtoPedido.setProduto(produto);

            Integer quantidade = produtoPedido.getQtdProduto() != null ? produtoPedido.getQtdProduto() : 1;
            produtoPedido.setQtdProduto(quantidade);


            if (produtoPedido.getPrecoUnitario() == null) {
                produtoPedido.setPrecoUnitario(produto.getItemProduto().getPrecoVenda());
            }

            if (produtoPedido.getPesoUnitario() == null) {
                produtoPedido.setPesoUnitario(produto.getItemProduto().getPeso());
            }

            produtoPedido.setPrecoTotal(produtoPedido.getPrecoUnitario() * quantidade);
            produtoPedido.setPesoTotal(produtoPedido.getPesoUnitario() * quantidade);


            school.sptech.EncantoPersonalizados.core.domain.ProdutoPedido produtoPedidoSalvo = produtoPedidoGateway.save(produtoPedido);
            ProdutosPedidoResponseDto dtoProdutoPedido = ProdutosPedidoMapper.toDto(produtoPedidoSalvo);
            produtosPedidoResponseDtos.add(dtoProdutoPedido);

            Integer prazoProduto = produtoPedido.getProduto().getItemProduto().getPrazoProducao();
            totalDiasProducao += (prazoProduto * quantidade);

            precoTotalPedido += produtoPedidoSalvo.getPrecoTotal();
            pesoTotalPedido += produtoPedidoSalvo.getPesoTotal();
        }
        LocalDateTime dataLimiteFinal;
        if (pedidoDto.dataLimite() != null) {
            dataLimiteFinal = pedidoDto.dataLimite().atTime(23, 59, 59);
        } else {
            dataLimiteFinal = LocalDateTime.now().plusDays(totalDiasProducao);
        }
        pedido.setDataLimite(dataLimiteFinal);
        pedido.setDataLimite(dataLimiteFinal);
        pedido.setPrecoTotal(precoTotalPedido);
        pedido.setPesoTotal(pesoTotalPedido);
        Pedido pedidoSalvo = pedidoGateway.save(pedido);

        return new PedidoCreatedResponseDto(
                pedidoSalvo.getId(),
                pedidoSalvo.getObservacoes(),
                pedidoSalvo.getOrigem(),
                pedidoSalvo.getDataLimite()
        );
    }

    @Override
    public Page<PedidoResponseDto> listar(String search, Integer page, Boolean ativo, LocalDate inicio, LocalDate fim, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pedido> pedidos = pedidoGateway.filtrar(search, ativo, inicio, fim, pageable, size);

        return mapearPedidos(pedidos);
    }

    @Override
    public Page<PedidoResponseDto> listarAvancado(
            String search,
            Integer page,
            Boolean ativo,
            LocalDate inicio,
            LocalDate fim,
            Integer size,
            String origem,
            Integer statusId,
            LocalDate createdAtInicio,
            LocalDate createdAtFim,
            LocalDate dataLimiteInicio,
            LocalDate dataLimiteFim,
            Double valorMin,
            Double valorMax,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDate filtroCriacaoInicio = createdAtInicio != null ? createdAtInicio : inicio;
        LocalDate filtroCriacaoFim = createdAtFim != null ? createdAtFim : fim;
        Page<Pedido> pedidos = pedidoGateway.filtrarAvancado(
                search,
                ativo,
                origem,
                statusId,
                filtroCriacaoInicio,
                filtroCriacaoFim,
                dataLimiteInicio,
                dataLimiteFim,
                valorMin,
                valorMax,
                sortBy,
                sortDirection,
                pageable
        );

        return mapearPedidos(pedidos);
    }

    private Page<PedidoResponseDto> mapearPedidos(Page<Pedido> pedidos) {
        return pedidos.map(pedido -> {
            PedidoStatusPedido statusAtual = pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId()).get(0);
            PedidoStatusPedidoResponseDto statusPedidoResponseDto = PedidoStatusPedidoMapper.toResponseDto(statusAtual);
            return PedidoMapper.toDto(pedido, statusPedidoResponseDto);
        });
    }

    @Override
    public PedidoResponseDto getById(Integer id) {
        Pedido pedido = pedidoGateway.findByIdWithDetails(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        PedidoStatusPedido statusAtual = pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId()).get(0);
        PedidoStatusPedidoResponseDto statusPedidoResponseDto = PedidoStatusPedidoMapper.toResponseDto(statusAtual);
        return PedidoMapper.toDto(pedido, statusPedidoResponseDto);
    }

    @Override
    public PedidoCreatedResponseDto update(Integer id, PedidoRequestDto pedidoDto) {
        Pedido pedidoExistente = pedidoGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedidoExistente.setObservacoes(pedidoDto.observacoes());
        pedidoExistente.setOrigem(pedidoDto.origem());
        if (pedidoDto.dataLimite() != null) {
            pedidoExistente.setDataLimite(pedidoDto.dataLimite().atTime(23, 59, 59));
        }
        pedidoExistente.setUpdatedAt(LocalDateTime.now());

        Pedido pedidoAtualizado = pedidoGateway.save(pedidoExistente);

        return new PedidoCreatedResponseDto(
                pedidoAtualizado.getId(),
                pedidoAtualizado.getObservacoes(),
                pedidoAtualizado.getOrigem(),
                pedidoAtualizado.getDataLimite()
        );
    }

    @Override
    public void mudarEstado(Integer id) {
        Pedido pedido = pedidoGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setAtivo(!pedido.isAtivo());
        pedidoGateway.save(pedido);
    }

    @Override
    @Transactional
    public void mudarStatus(PedidoStatusPedidoRequestDto dto) {
        Pedido pedido = pedidoGateway.findById(dto.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        StatusPedido statusPedido = statusPedidoGateway.findById(dto.idStatusPedido()).orElse(null);
        if (statusPedido == null) {
            throw new RuntimeException("Status do pedido não encontrado");
        }

        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();
        pedidoStatusPedido.setPedido(pedido);
        pedidoStatusPedido.setStatus(statusPedido);
        pedidoStatusPedido.setStatusAtual(true);

        List<PedidoStatusPedido> statusAtuais = pedidoStatusPedidoGateway.findStatusAtualByPedidoId(pedido.getId());
        for (PedidoStatusPedido statusAtual : statusAtuais) {
            statusAtual.setStatusAtual(false);
            pedidoStatusPedidoGateway.salvar(statusAtual);
        }

        pedidoStatusPedidoGateway.salvar(pedidoStatusPedido);
        pedidoGateway.save(pedido);

        if (isStatusConcluido(statusPedido)) {
            // Cria o evento (certifique-se de ter criado o record NotificacaoWhatsappEventMessageDto)
            NotificacaoWhatsappEventMessageDto evento = new NotificacaoWhatsappEventMessageDto(
                    pedido.getId(),
                    pedido.getCliente() != null ? pedido.getCliente().getNome() : "Cliente",
                    pedido.getCliente() != null ? pedido.getCliente().getTelefone() : "",
                    statusPedido.getStatus(),
                    LocalDateTime.now()
            );

            // Dispara para o RabbitMQ (NÃO CHAMA MAIS O WHATSAPP DIRETO AQUI)
            // Isso libera o Front-end na hora!
            producerUseCase.sendNotificacaoWhatsappEvent(evento);
        }
    }

    private boolean isStatusConcluido(StatusPedido statusPedido) {
        if (statusPedido == null) {
            return false;
        }

        return StatusPedidoRole.ENTREGUE.equals(statusPedido.getRole());
    }

    @Override
    public void atualizarPrecoPesoDoPedido(boolean aumentar, Double preco, Double peso, Integer pedidoId) {
        Pedido pedido = pedidoGateway.findById(pedidoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));

        if (aumentar) {
            pedido.setPesoTotal(pedido.getPesoTotal() + peso);
            pedido.setPrecoTotal(pedido.getPrecoTotal() + preco);
        } else {
            pedido.setPesoTotal(pedido.getPesoTotal() - peso);
            pedido.setPrecoTotal(pedido.getPrecoTotal() - preco);
        }

        pedidoGateway.save(pedido);
    }
}
