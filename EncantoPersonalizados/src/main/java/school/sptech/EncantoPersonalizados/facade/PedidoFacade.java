package school.sptech.EncantoPersonalizados.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoCreatedResponseDto;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoMapper;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.pedido.PedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoMapper;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.pedidoStatusPedido.PedidoStatusPedidoResponseDto;
import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoMapper;
import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoRequestDto;
import school.sptech.EncantoPersonalizados.dto.produtosEmUmPedido.ProdutosPedidoResponseDto;
import school.sptech.EncantoPersonalizados.entities.*;
import school.sptech.EncantoPersonalizados.repository.PedidoRepository;
import school.sptech.EncantoPersonalizados.repository.PedidoStatusPedidoRepository;
import school.sptech.EncantoPersonalizados.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoFacade {
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final PedidoStatusPedidoService pedidoStatusPedidoService;
    private final ProdutoPedidoService produtoPedidoService;
    private final PedidoRepository pedidoRepository;
    private final StatusPedidoService statusPedidoService;
    private final PedidoStatusPedidoRepository pedidoStatusPedidoRepository;


    public PedidoFacade(
            ProdutoService produtoService,
            ClienteService clienteService,
            PedidoStatusPedidoService pedidoStatusPedidoService,
            ProdutoPedidoService produtoPedidoService,
            PedidoRepository pedidoRepository,
            StatusPedidoService statusPedidoService,
            PedidoStatusPedidoRepository pedidoStatusPedidoRepository
    ) {
        this.statusPedidoService = statusPedidoService;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
        this.pedidoStatusPedidoService = pedidoStatusPedidoService;
        this.produtoPedidoService = produtoPedidoService;
        this.pedidoRepository = pedidoRepository;
        this.pedidoStatusPedidoRepository = pedidoStatusPedidoRepository;
    }

    public PedidoCreatedResponseDto store(PedidoRequestDto pedidoDto) {

        // verificar se o cliente existe

        Cliente cliente = clienteService.findById(pedidoDto.clienteId());
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado");
        }

        Pedido entity = PedidoMapper.toEntity(pedidoDto);
        entity.setCliente(cliente);

        StatusPedido statusPedido = statusPedidoService.findFirstOfKanbanOrder();
        if(statusPedido == null){
            throw new RuntimeException("Status do pedido não encontrado");
        }
        Pedido pedido = pedidoRepository.save(entity);
        PedidoStatusPedido entityStatusPedido = new PedidoStatusPedido();
        entityStatusPedido.setPedido(pedido);
        entityStatusPedido.setStatus(statusPedido);
        entityStatusPedido.setStatusAtual(true);
        PedidoStatusPedido statusAtualDoPedido = pedidoStatusPedidoService.salvar(entityStatusPedido);
        Double precoTotalPedido = 0.0;
        Double pesoTotalPedido = 0.0;
        Integer diasDeProducaoMaisLongoDosProdutos =0;
        List<ProdutosPedidoResponseDto> produtosPedidoResponseDtos = new ArrayList<>();

        for(ProdutosPedidoRequestDto pedidoRequestDto : pedidoDto.produtos()){
            // verificar se o produto existe

            Produto produto = produtoService.findById(pedidoRequestDto.idProduto());
            if(produto == null){
                throw new RuntimeException("Produto não encontrado");
            }

            ProdutoPedido produtoPedido = ProdutosPedidoMapper.toEntity(pedidoRequestDto);

            // antes de salvar preciso preencher o restante dos campos de pedido produto, do dto so vem a quantidade
            produtoPedido.setPedido(pedido);
            produtoPedido.setCreatedAt(LocalDateTime.now());
            produtoPedido.setProduto(produto);
            produtoPedido.setPesoUnitario(produto.getItemProduto().getPeso());
            Double precoUnitario = 0.0;

            if(produto.getItemProduto().getPrecoPromocional() != null){
                precoUnitario = produto.getItemProduto().getPrecoPromocional();
            }else{
                precoUnitario = produto.getItemProduto().getPrecoVenda();
            }
            produtoPedido.setPrecoUnitario(precoUnitario);

            produtoPedido.setPrecoTotal(produtoPedido.getQtdProduto() * produtoPedido.getPesoUnitario());;
            produtoPedido.setPesoTotal(produtoPedido.getQtdProduto() * produtoPedido.getPesoUnitario());

            precoTotalPedido += produtoPedido.getPrecoTotal();
            pesoTotalPedido += produtoPedido.getPesoTotal();

            ProdutoPedido produtoPedidoSalvo = produtoPedidoService.salvar(produtoPedido);
            ProdutosPedidoResponseDto dtoProdutoPedido = ProdutosPedidoMapper.toDto(produtoPedidoSalvo);
            produtosPedidoResponseDtos.add(dtoProdutoPedido);

            if(produtoPedido.getProduto().getItemProduto().getPrazoProducao() > diasDeProducaoMaisLongoDosProdutos){
                diasDeProducaoMaisLongoDosProdutos = produtoPedido.getProduto().getItemProduto().getPrazoProducao();
            }

        }
        LocalDateTime dataLimite = LocalDateTime.now().plusDays(diasDeProducaoMaisLongoDosProdutos);
        pedido.setDataLimite(dataLimite);

        pedido.setPrecoTotal(precoTotalPedido);
        pedido.setPesoTotal(pesoTotalPedido);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Fazer o get novamento do pedido para trazer as informações salvas






        return new PedidoCreatedResponseDto(
                pedidoSalvo.getId(),
                pedidoSalvo.getObservacoes(),
                pedidoSalvo.getOrigem(),
                pedidoSalvo.getDataLimite()
        );
    }

    public Page<PedidoResponseDto> listar(String search, Integer page, Boolean ativo){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Pedido> pedidos = pedidoRepository.filtrar(search, ativo, pageable);
        // transformar em page de dtos

        return pedidos.map(pedido -> {
            // pegar o status atual do pedido
            PedidoStatusPedido statusAtual = pedidoStatusPedidoRepository.findStatusAtualByPedidoId(pedido.getId()).get(0);
            PedidoStatusPedidoResponseDto statusPedidoResponseDto = PedidoStatusPedidoMapper.toResponseDto(statusAtual);
            return PedidoMapper.toDto(pedido, statusPedidoResponseDto);
        });

    }

    public PedidoResponseDto getById(Integer id){

        Pedido pedido = pedidoRepository.findByIdWithDetails(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        // transformar em page de dtos


            // pegar o status atual do pedido
        PedidoStatusPedido statusAtual = pedidoStatusPedidoRepository.findStatusAtualByPedidoId(pedido.getId()).get(0);
        PedidoStatusPedidoResponseDto statusPedidoResponseDto = PedidoStatusPedidoMapper.toResponseDto(statusAtual);
        return PedidoMapper.toDto(pedido, statusPedidoResponseDto);

    }

    public PedidoCreatedResponseDto update(Integer id, PedidoRequestDto pedidoDto) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedidoExistente.setObservacoes(pedidoDto.observacoes());
        pedidoExistente.setOrigem(pedidoDto.origem());
        pedidoExistente.setUpdatedAt(LocalDateTime.now());

        Pedido pedidoAtualizado = pedidoRepository.save(pedidoExistente);

        return new PedidoCreatedResponseDto(
                pedidoAtualizado.getId(),
                pedidoAtualizado.getObservacoes(),
                pedidoAtualizado.getOrigem(),
                pedidoAtualizado.getDataLimite()
        );
    }

    public void mudarEstado(Integer id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setAtivo(!pedido.isAtivo());
        pedidoRepository.save(pedido);
    }

    public void mudarStatus(PedidoStatusPedidoRequestDto dto){
        Pedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        StatusPedido statusPedido = statusPedidoService.findById(dto.idStatusPedido());

        if(statusPedido == null) {
            throw new RuntimeException("Status do pedido não encontrado");
        }

        PedidoStatusPedido pedidoStatusPedido = new PedidoStatusPedido();
        pedidoStatusPedido.setPedido(pedido);
        pedidoStatusPedido.setStatus(statusPedido);
        pedidoStatusPedido.setStatusAtual(true);

        pedidoStatusPedidoService.salvar(pedidoStatusPedido);




    }




}
