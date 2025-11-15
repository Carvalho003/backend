package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    public final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Page<Produto> get(
        String search,
        String categoria,
        String tema,
        String item,
        boolean ativo,
        int page
    ){
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        search = vazioParaNull(search);
        categoria = vazioParaNull(categoria);
        tema = vazioParaNull(tema);
        item = vazioParaNull(item);

        return repository.filtrar(search, categoria, tema, item,ativo, pageable);
    }

    public Produto findById(Integer id){
        Optional<Produto> optionalProduto = repository.findById(id);
        if(optionalProduto.isEmpty()) return null;
        return optionalProduto.get();
    }

    public void mudarEstado(Integer id)
    {
        Produto entity = this.findById(id);
        if(entity == null) throw new ProdutoNaoEncontradoException("Produto não encontrado");
        if(entity.getAtivo()){
            entity.setAtivo(false);
        }else{
            entity.setAtivo(true);
        }
        repository.save(entity);
    }

    public Produto store(Produto entity){
        return repository.save(entity);
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }
}
