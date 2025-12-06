package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.entities.FotoProduto;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.exceptions.ProdutoNaoEncontradoException;
import school.sptech.EncantoPersonalizados.repository.FotoProdutoRepository;
import school.sptech.EncantoPersonalizados.repository.ProdutoRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FotoProdutoServiceTest {
    @Mock
    FotoProdutoRepository repository;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    ProdutoService produtoService;

    @InjectMocks
    FotoProdutoService service;


    //function store
    @Test
    @DisplayName("Deve lançar exceção quando cadastrar foto de produto e o produto não existir")
    void LancarExcecaoQuandoProdutoNaoExistir(){
        MultipartFile arquivo = mock(MultipartFile.class);

        ProdutoNaoEncontradoException excecao = assertThrows(
                ProdutoNaoEncontradoException.class,
                () -> service.store(1, arquivo)
        );

        assertEquals("Produto não encontrado", excecao.getMessage());

    }

    @Test
    @DisplayName("Deve armazenar a foto do produto caso ele exista")
    void ArmazenarFotoDoProdutoCasoExista() throws IOException, NoSuchFieldException, IllegalAccessException {
        Field campo = FotoProdutoService.class.getDeclaredField("uploadDir");
        campo.setAccessible(true);
        campo.set(service, "uploads");

        Integer produtoId = 1;
        Produto produto = new Produto();
        produto.setId(produtoId);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("imagem.jpg");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("conteudo".getBytes()));

        when(produtoService.findById(produtoId)).thenReturn(produto);

        FotoProduto fotoSalva = new FotoProduto();
        fotoSalva.setProduto(produto);
        fotoSalva.setCreatedAt(LocalDateTime.now());
        fotoSalva.setFoto("/uploads/produtos/" + produtoId + "/uuid-imagem.jpg");

        when(repository.save(any(FotoProduto.class))).thenReturn(fotoSalva);

        Path produtoFolder = Paths.get("uploads", "produtos", produtoId.toString());

        try (MockedStatic<Files> mockArquivo = mockStatic(Files.class)) {
            mockArquivo.when(() -> Files.createDirectories(produtoFolder))
                    .thenReturn(produtoFolder);

            mockArquivo.when(() -> Files.copy(any(InputStream.class),
                            any(Path.class),
                            eq(StandardCopyOption.REPLACE_EXISTING)))
                    .thenReturn(123L);

            FotoProduto resultado = service.store(produtoId, file);

            assertNotNull(resultado);
            assertNotNull(resultado.getCreatedAt());
            assertTrue(resultado.getFoto().startsWith("/uploads/produtos/" + produtoId + "/"));
            assertTrue(resultado.getFoto().endsWith("-imagem.jpg"));

            mockArquivo.verify(() -> Files.createDirectories(produtoFolder), times(1));
            mockArquivo.verify(() -> Files.copy(any(InputStream.class),
                    any(Path.class),
                    eq(StandardCopyOption.REPLACE_EXISTING)), times(1));

            verify(repository, times(1)).save(any(FotoProduto.class));
        }
    }


    //function deletarFoto
    @Test
    @DisplayName("quando excluir foto e foto não existir lançar exceção")
    void QuandoExcluirFotoMasFotoNaoExistirLancarExcecao(){
        when(repository.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.deletarFoto(1)
        );

        assertEquals("Foto não encontrada", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve deletar a foto quando ela existe")
    void DeletarFotoQuandoExiste() throws IOException {
        Integer fotoId = 1;
        FotoProduto foto = new FotoProduto();
        foto.setFoto("/uploads/produtos/1/uuid-imagem.jpg");

        when(repository.findById(fotoId)).thenReturn(Optional.of(foto));

        Path caminhoArquivo = Paths.get(foto.getFoto().replace("/uploads", "uploads"));

        try (MockedStatic<Files> mockArquivo = mockStatic(Files.class)) {
            mockArquivo.when(() -> Files.deleteIfExists(caminhoArquivo)).thenReturn(true);

            // Act
            service.deletarFoto(fotoId);

            verify(repository, times(1)).delete(foto);
        }
    }
}