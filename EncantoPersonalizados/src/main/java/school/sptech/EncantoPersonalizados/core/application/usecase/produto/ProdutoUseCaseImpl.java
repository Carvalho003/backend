package school.sptech.EncantoPersonalizados.core.application.usecase.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import school.sptech.EncantoPersonalizados.core.application.gateway.ItemProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.TemaProdutoGateway;
import school.sptech.EncantoPersonalizados.core.domain.ItemProduto;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.TemaProduto;
import school.sptech.EncantoPersonalizados.core.domain.exception.ItemProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.core.domain.exception.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.core.domain.exception.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoMapper;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.produto.ProdutoResponseDTO;

import java.time.LocalDateTime;

@Component
public class ProdutoUseCaseImpl implements ProdutoUseCase {

    private final ProdutoGateway produtoGateway;
    private final TemaProdutoGateway temaProdutoGateway;
    private final ItemProdutoGateway itemProdutoGateway;

    public ProdutoUseCaseImpl(
            ProdutoGateway produtoGateway,
            TemaProdutoGateway temaProdutoGateway,
            ItemProdutoGateway itemProdutoGateway
    ) {
        this.produtoGateway = produtoGateway;
        this.temaProdutoGateway = temaProdutoGateway;
        this.itemProdutoGateway = itemProdutoGateway;
    }

    @Override
    public Page<Produto> get(String search, String categoria, String tema, String item, boolean ativo, int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        search = vazioParaNull(search);
        categoria = vazioParaNull(categoria);
        tema = vazioParaNull(tema);
        item = vazioParaNull(item);
        return produtoGateway.filtrar(search, categoria, tema, item, ativo, pageable);
    }

    @Override
    public Produto findById(Integer id) {
        return produtoGateway.findById(id).orElse(null);
    }

    @Override
    public void mudarEstado(Integer id) {
        Produto entity = this.findById(id);
        if (entity == null) throw new ProdutoNaoEncontradoException("Produto não encontrado");
        if (entity.getAtivo()) {
            entity.setAtivo(false);
        } else {
            entity.setAtivo(true);
        }
        produtoGateway.save(entity);
    }

    @Override
    public Produto store(Produto entity) {
        return produtoGateway.save(entity);
    }

    @Override
    public ProdutoResponseDTO storeFull(ProdutoRequestDTO dto) {
        ItemProduto entityItem = itemProdutoGateway.findById(dto.itemId()).orElse(null);
        if (entityItem == null) throw new ItemProdutoNaoEncontradoException("Item não encontrado");
        TemaProduto entityTema = temaProdutoGateway.findById(dto.temaId()).orElse(null);
        if (entityTema == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");

        Produto entityProduto = ProdutoMapper.toEntity(dto);

        entityProduto.setItemProduto(entityItem);
        entityProduto.setTemaProduto(entityTema);

        Produto produtoSalvo = produtoGateway.save(entityProduto);

        return ProdutoMapper.toDto(produtoSalvo);
    }

    @Override
    public ProdutoResponseDTO update(ProdutoRequestDTO dto, Integer id) {
        Produto produtoAntigo = this.findById(id);
        if (produtoAntigo == null) throw new ProdutoNaoEncontradoException("Produto não encontrado");

        ItemProduto entityItem = itemProdutoGateway.findById(dto.itemId()).orElse(null);
        if (entityItem == null) throw new ItemProdutoNaoEncontradoException("Item não encontrado");
        TemaProduto entityTema = temaProdutoGateway.findById(dto.temaId()).orElse(null);
        if (entityTema == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");

        Produto entityProduto = ProdutoMapper.toEntity(dto);

        produtoAntigo.setItemProduto(entityItem);
        produtoAntigo.setTemaProduto(entityTema);
        produtoAntigo.setUpdatedAt(LocalDateTime.now());
        produtoAntigo.setTitulo(entityProduto.getTitulo());
        produtoAntigo.setDescricao(entityProduto.getDescricao());

        Produto produtoSalvo = produtoGateway.save(produtoAntigo);

        return ProdutoMapper.toDto(produtoSalvo);
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }
}
