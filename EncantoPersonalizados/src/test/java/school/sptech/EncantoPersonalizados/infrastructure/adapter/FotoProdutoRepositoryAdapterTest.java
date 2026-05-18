package school.sptech.EncantoPersonalizados.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.core.domain.FotoProduto;
import school.sptech.EncantoPersonalizados.infrastructure.persistence.repository.FotoProdutoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FotoProdutoRepositoryAdapterTest {

    @Mock
    FotoProdutoRepository repository;

    @InjectMocks
    FotoProdutoRepositoryAdapter adapter;

    private FotoProduto foto;

    @BeforeEach
    void setUp() {
        foto = new FotoProduto();
        foto.setId(42);
        foto.setFoto("/uploads/produtos/9/uuid-imagem.jpg");
    }

    @Test
    @DisplayName("save delega ao repositório JPA e retorna a entidade persistida")
    void saveDelegaAoRepositorio() {
        when(repository.save(foto)).thenReturn(foto);

        FotoProduto resultado = adapter.save(foto);

        assertSame(foto, resultado);
        verify(repository, times(1)).save(foto);
    }

    @Test
    @DisplayName("findById delega ao repositório JPA")
    void findByIdDelegaAoRepositorio() {
        when(repository.findById(42)).thenReturn(Optional.of(foto));

        Optional<FotoProduto> resultado = adapter.findById(42);

        assertTrue(resultado.isPresent());
        assertEquals(42, resultado.get().getId());
        verify(repository, times(1)).findById(42);
    }

    @Test
    @DisplayName("findByIdUncached delega ao repositório JPA (mesmo caminho de findById, mas sem @Cacheable)")
    void findByIdUncachedDelegaAoRepositorio() {
        when(repository.findById(42)).thenReturn(Optional.of(foto));

        Optional<FotoProduto> resultado = adapter.findByIdUncached(42);

        assertTrue(resultado.isPresent());
        verify(repository, times(1)).findById(42);
    }

    @Test
    @DisplayName("delete usa deleteById em vez de delete(entity) para evitar problema de entidade detached")
    void deleteUsaDeleteById() {
        adapter.delete(foto);

        verify(repository, times(1)).deleteById(42);
        verify(repository, never()).delete(any(FotoProduto.class));
    }

    @Test
    @DisplayName("delete com id nulo não chama o repositório (evita NullPointerException ao limpar cache)")
    void deleteIgnoraIdNulo() {
        FotoProduto sem = new FotoProduto();

        adapter.delete(sem);

        verify(repository, never()).deleteById(any());
        verify(repository, never()).delete(any(FotoProduto.class));
    }
}
