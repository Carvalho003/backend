package school.sptech.EncantoPersonalizados.core.application.usecase.itemProduto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ItemProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.domain.exception.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ItemProdutoUseCaseImpl implements ItemProdutoUseCase {

    private final ItemProdutoGateway gateway;

    public ItemProdutoUseCaseImpl(ItemProdutoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public ItemProduto findByid(Integer id) {
        return gateway.findById(id).orElse(null);
    }

    @Override
    public ItemProdutoResponseDTO cadastrar(ItemProdutoRequestDTO itemParaCadastro) {
        ItemProduto entity = ItemProdutoMapper.toEntity(itemParaCadastro);
        entity.setCreatedAt(LocalDateTime.now());
        ItemProduto itemRegistrado = gateway.save(entity);
        return ItemProdutoMapper.toDto(itemRegistrado);
    }

    @Override
    public Page<ItemProduto> listar(String search, boolean ativo, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return gateway.filtrar(search, ativo, pageable);
    }

    @Override
    public List<ItemProdutoResponseDTO> buscarPorPrecoMenorQue(Double preco) {
        List<ItemProduto> itensEncontrados = gateway.findByPrecoVendaLessThan(preco);
        return ItemProdutoMapper.toDto(itensEncontrados);
    }

    @Override
    public ItemProdutoResponseDTO update(Integer id, ItemProduto itemAtualizado) {
        Optional<ItemProduto> itemOpt = gateway.findById(id);
        if (itemOpt.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Item não encontrado!");
        }
        var itemAtual = itemOpt.get();
        itemAtual.setDescricao(itemAtualizado.getDescricao());
        itemAtual.setPrecoVenda(itemAtualizado.getPrecoVenda());
        itemAtual.setCustoProducao(itemAtualizado.getCustoProducao());
        itemAtual.setPrazoProducao(itemAtualizado.getPrazoProducao());
        itemAtual.setLargura(itemAtualizado.getLargura());
        itemAtual.setPeso(itemAtualizado.getPeso());
        itemAtual.setComprimento(itemAtualizado.getComprimento());
        itemAtual.setMaterial(itemAtualizado.getMaterial());
        itemAtual.setUpdatedAt(LocalDateTime.now());
        gateway.save(itemAtual);
        return ItemProdutoMapper.toDto(itemAtual);
    }

    @Override
    public void mudarEstado(Integer id) {
        Optional<ItemProduto> itemOpt = gateway.findById(id);
        if (itemOpt.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Item não encontrado!");
        }
        ItemProduto itemProduto = itemOpt.get();
        itemProduto.setAtivo(!itemProduto.getAtivo());
        gateway.save(itemProduto);
    }
}
