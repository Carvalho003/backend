package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoArquivoStorageGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.FotoProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.gateway.ProdutoGateway;
import school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto.ArmazenarFotoProdutoUseCaseImpl;
import school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto.DeletarFotoUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.fotoProduto.SalvarFotoUseCase;
import school.sptech.EncantoPersonalizados.core.application.usecase.producer.ProducerUseCase;
import school.sptech.EncantoPersonalizados.core.domain.FotoArquivo;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;
import school.sptech.EncantoPersonalizados.core.domain.Produto;
import school.sptech.EncantoPersonalizados.core.domain.exception.ProdutoNaoEncontradoException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArmazenarFotoProdutoUseCaseImplTest {

    @Mock
    FotoProdutoGateway repository;

    @Mock
    ProdutoGateway produtoGateway;

    @Mock
    FotoArquivoStorageGateway fotoArquivoStorageGateway;

    @Mock
    SalvarFotoUseCase salvarFotoUseCase;

    @Mock
    DeletarFotoUseCase deletarFotoUseCase;

    @Mock
    ProducerUseCase producerUseCase;

    @InjectMocks
    ArmazenarFotoProdutoUseCaseImpl service;

    @Test
    @DisplayName("Deve lançar exceção quando cadastrar foto de produto e o produto não existir")
    void LancarExcecaoQuandoProdutoNaoExistir() {
        MultipartFile arquivo = mock(MultipartFile.class);

        ProdutoNaoEncontradoException excecao = assertThrows(
                ProdutoNaoEncontradoException.class,
                () -> service.store(1, arquivo)
        );

        assertEquals("Produto não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve armazenar a foto do produto caso ele exista")
    void ArmazenarFotoDoProdutoCasoExista() throws IOException {
        Integer produtoId = 1;
        Produto produto = new Produto();
        produto.setId(produtoId);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("imagem.jpg");
        when(file.getBytes()).thenReturn("conteudo".getBytes());

        when(produtoGateway.findById(produtoId)).thenReturn(Optional.of(produto));

        FotoArquivo fotoArquivo = new FotoArquivo(
                "/uploads/produtos/" + produtoId + "/uuid-imagem.jpg",
                "uuid-imagem.jpg",
                produtoId
        );

        when(fotoArquivoStorageGateway.salvar(eq(produtoId), anyString(), any(byte[].class)))
                .thenReturn(CompletableFuture.completedFuture(fotoArquivo));

        FotoProduto fotoSalva = new FotoProduto();
        fotoSalva.setProduto(produto);
        fotoSalva.setCreatedAt(LocalDateTime.now());
        fotoSalva.setFoto(fotoArquivo.getCaminhoRelativo());

        when(repository.save(any(FotoProduto.class))).thenReturn(fotoSalva);

        FotoProduto resultado = service.store(produtoId, file).join();

        assertNotNull(resultado);
        assertNotNull(resultado.getCreatedAt());
        assertTrue(resultado.getFoto().startsWith("/uploads/produtos/" + produtoId + "/"));
        verify(fotoArquivoStorageGateway, times(1)).salvar(eq(produtoId), anyString(), any(byte[].class));
        verify(repository, times(1)).save(any(FotoProduto.class));
    }

    @Test
    @DisplayName("Quando excluir foto e foto não existir, lançar exceção")
    void QuandoExcluirFotoMasFotoNaoExistirLancarExcecao() {
        when(repository.findByIdUncached(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.deletarFoto(1)
        );

        assertEquals("Foto não encontrada", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve deletar a foto quando ela existe (passa pelo caminho não-cacheado)")
    void DeletarFotoQuandoExiste() {
        Integer fotoId = 1;
        FotoProduto foto = new FotoProduto();
        foto.setFoto("/uploads/produtos/1/uuid-imagem.jpg");

        when(repository.findByIdUncached(fotoId)).thenReturn(Optional.of(foto));
        when(fotoArquivoStorageGateway.deletar(foto.getFoto()))
                .thenReturn(CompletableFuture.completedFuture(null));

        service.deletarFoto(fotoId).join();

        verify(repository, times(1)).delete(foto);
        verify(repository, never()).findById(anyInt());
        verify(producerUseCase, times(1)).sendFotoProdutoEvent(any());
    }
}