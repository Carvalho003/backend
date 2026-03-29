package school.sptech.EncantoPersonalizados.core.application.usecase.itemProduto;

import org.springframework.data.domain.Page;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.itemProduto.ItemProdutoResponseDTO;

import java.util.List;

public interface ItemProdutoUseCase {
    ItemProduto findByid(Integer id);
    ItemProdutoResponseDTO cadastrar(ItemProdutoRequestDTO itemParaCadastro);
    Page<ItemProduto> listar(String search, boolean ativo, int page);
    List<ItemProdutoResponseDTO> buscarPorPrecoMenorQue(Double preco);
    ItemProdutoResponseDTO update(Integer id, ItemProduto itemAtualizado);
    void mudarEstado(Integer id);
}
