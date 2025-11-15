package school.sptech.EncantoPersonalizados.facade;

import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.itemProduto.ItemProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.temaProduto.TemaProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.ItemProduto;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.entities.TemaProduto;
import school.sptech.EncantoPersonalizados.exceptions.ItemProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.exceptions.TemaProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.service.FotoProdutoService;
import school.sptech.EncantoPersonalizados.service.ItemProdutoService;
import school.sptech.EncantoPersonalizados.service.ProdutoService;
import school.sptech.EncantoPersonalizados.service.TemaProdutoService;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class ProdutoFacade {
    private final ProdutoService produtoService;
    private final TemaProdutoService temaProdutoService;
    private final ItemProdutoService itemProdutoService;
    private final FotoProdutoService fotoProdutoService;

    public ProdutoFacade(ProdutoService produtoService, TemaProdutoService temaProdutoService, ItemProdutoService itemProdutoService, FotoProdutoService fotoProdutoService) {
        this.produtoService = produtoService;
        this.temaProdutoService = temaProdutoService;
        this.itemProdutoService = itemProdutoService;
        this.fotoProdutoService = fotoProdutoService;
    }

    public ProdutoResponseDTO store(ProdutoRequestDTO dto){
        ItemProduto entityItem = itemProdutoService.findByid(dto.itemId());
        // validar se existe
        if (entityItem == null) throw new ItemProdutoNaoEncontradoException("Item não encontrado");
        TemaProduto entityTema = temaProdutoService.findById(dto.temaId());
        if(entityTema == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");

        Produto entityProduto = ProdutoMapper.toEntity(dto);

        entityProduto.setItemProduto(entityItem);
        entityProduto.setTemaProduto(entityTema);

        Produto produtoSalvo = produtoService.store(entityProduto);


        return ProdutoMapper.toDto(
                produtoSalvo
        );




    }


    public ProdutoResponseDTO update(ProdutoRequestDTO dto, Integer id){

        Produto produtoAntigo = produtoService.findById(id);

        if(produtoAntigo == null ) throw new ProdutoNaoEncontradoException("Produto não encontrado");

        ItemProduto entityItem = itemProdutoService.findByid(dto.itemId());
        // validar se existe
        if (entityItem == null) throw new ItemProdutoNaoEncontradoException("Item não encontrado");
        TemaProduto entityTema = temaProdutoService.findById(dto.temaId());
        if(entityTema == null) throw new TemaProdutoNaoEncontradoException("Tema não encontrado");

        Produto entityProduto = ProdutoMapper.toEntity(dto);

        produtoAntigo.setItemProduto(entityItem);
        produtoAntigo.setTemaProduto(entityTema);
        produtoAntigo.setUpdatedAt(LocalDateTime.now());
        produtoAntigo.setTitulo(entityProduto.getTitulo());
        produtoAntigo.setDescricao(entityProduto.getDescricao());

        Produto produtoSalvo = produtoService.store(produtoAntigo);


        return ProdutoMapper.toDto(
                produtoSalvo
        );




    }


}
