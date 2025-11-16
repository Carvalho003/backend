package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.ItemProduto;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeConflitoException;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.ItemProdutoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemProdutoService {

    private final ItemProdutoRepository repository;

    public ItemProdutoService (ItemProdutoRepository repository){
        this.repository = repository;
    }

    public ItemProduto findByid(Integer id){
        Optional<ItemProduto> optionalItemProduto = repository.findById(id);
        if(optionalItemProduto.isEmpty()) return null;
        return optionalItemProduto.get();
    }

    public ItemProdutoResponseDTO cadastrar(ItemProdutoRequestDTO itemParaCadastro){
        ItemProduto entity = ItemProdutoMapper.toEntity(itemParaCadastro);
        entity.setCreatedAt(LocalDateTime.now());
        ItemProduto itemRegistrado = repository.save(entity);
        return ItemProdutoMapper.toDto(itemRegistrado);
    }

    public Page<ItemProduto> listar(String search, boolean ativo, int page){
        Pageable pageable = PageRequest.of(page, 10);

        Page<ItemProduto> result = repository.filtrar(search, ativo, pageable);

        return result;

    }

    public List<ItemProdutoResponseDTO> buscarPorPrecoMenorQue(Double preco){
        List<ItemProduto> itensEncontrados = repository.findByPrecoVendaLessThan(preco).get();
        List<ItemProdutoResponseDTO> dtos = ItemProdutoMapper.toDto(itensEncontrados);
        return dtos;
    }

    public ItemProdutoResponseDTO update(Integer id, ItemProduto itemAtualizado){
        Optional<ItemProduto> itemOpt = repository.findById(id);
        if (!itemOpt.isPresent()) {
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
        repository.save(itemAtual);
        ItemProdutoResponseDTO dto = ItemProdutoMapper.toDto(itemAtual);
        return dto;
    }

    public void mudarEstado(Integer id){
        Optional<ItemProduto> itemOpt = repository.findById(id);
        if (!itemOpt.isPresent()) {
            throw new EntidadeNaoEncontradaException("Item não encontrado!");
        }
        ItemProduto itemProduto = itemOpt.get();

        if(itemProduto.getAtivo()){
            itemProduto.setAtivo(false);
        }else{
            itemProduto.setAtivo(true);
        }

        repository.save(itemProduto);
    }

}
