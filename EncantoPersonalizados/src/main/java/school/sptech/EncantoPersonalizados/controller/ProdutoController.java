package school.sptech.EncantoPersonalizados.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import school.sptech.EncantoPersonalizados.dto.produto.ProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.facade.ProdutoFacade;
import school.sptech.EncantoPersonalizados.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoFacade facade;
    private final ProdutoService service;

    public ProdutoController(ProdutoFacade facade, ProdutoService service) {
        this.facade = facade;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody ProdutoRequestDTO dto
            ){
        ProdutoResponseDTO response = facade.store(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<Produto>> get(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String tema,
            @RequestParam(required = false) String item,
            @RequestParam(defaultValue = "0") int page
    ){
        Page<Produto> resposta = service.get(search, categoria, tema, item, page);
        return ResponseEntity.status(200).body(resposta);
    }
}
