package school.sptech.EncantoPersonalizados.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoRequestDTO;
import school.sptech.EncantoPersonalizados.dto.fotoProduto.FotoProdutoResponseDTO;
import school.sptech.EncantoPersonalizados.entities.FotoProduto;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.FotoProdutoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FotoProdutoService {

    private final FotoProdutoRepository repository;

    private final ProdutoService produtoService;

    public FotoProdutoService(FotoProdutoRepository repository, ProdutoService produtoService) {
        this.produtoService = produtoService;
        this.repository = repository;
    }

    @Value("${upload.directory}")
    private String uploadDir;



    public FotoProduto store(Integer produtoId, MultipartFile file) throws IOException {
        Produto produto = produtoService.findById(produtoId);
        if(produto == null) throw new ProdutoNaoEncontradoException("Produto não encontrado");

        Path produtoFolder = Paths.get(uploadDir, "produtos", produtoId.toString());

        Files.createDirectories(produtoFolder);

        String nomeArquivo = UUID.randomUUID() + "-" + file.getOriginalFilename();

        Path caminhoCompleto = produtoFolder.resolve(nomeArquivo);
        Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

        String caminhoRelativo = "/uploads/produtos/" + produtoId + "/" + nomeArquivo;

        FotoProduto fotoProduto = new FotoProduto();

        fotoProduto.setFoto(caminhoRelativo);
        fotoProduto.setProduto(produto);
        fotoProduto.setCreatedAt(LocalDateTime.now());

        return repository.save(fotoProduto);

    }

    public void deletarFoto(Integer fotoId) throws IOException {
        FotoProduto foto = repository.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));

        // remove do disco
        Path caminhoArquivo = Paths.get(foto.getFoto().replace("/uploads", "uploads"));
        Files.deleteIfExists(caminhoArquivo);

        // remove do banco
        repository.delete(foto);
    }
}
