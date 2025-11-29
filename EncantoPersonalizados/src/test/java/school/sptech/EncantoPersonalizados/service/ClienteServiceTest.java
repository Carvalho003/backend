package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import school.sptech.EncantoPersonalizados.dto.cliente.CreateClienteDTO;
import school.sptech.EncantoPersonalizados.dto.cliente.EnderecoClienteRequestDTO;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.ClienteRepository;
import school.sptech.EncantoPersonalizados.repository.EnderecoClienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @Test
    @DisplayName("Deve salvar cliente com endereços corretamente")
    void deveSalvarClienteComEnderecosCorretamente() {
        CreateClienteDTO dto = new CreateClienteDTO("teste", "121332432432", List.of());

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("teste");
        cliente.setTelefone("121332432432");

        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = service.store(dto);

        assertNotNull(resultado);
        assertEquals("teste", resultado.getNome());
        assertEquals("121332432432", resultado.getTelefone());
        assertEquals(1, resultado.getId());

    }

    @Test
    @DisplayName("Deve atualizar cliente existente com novos dados")
    void deveAtualizarClienteExistente() {

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("teste");
        cliente.setTelefone("121332432432");

        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        CreateClienteDTO cliente2 = new CreateClienteDTO("teste2", "1234", List.of());

        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = service.update(cliente2, 1);

        assertNotNull(resultado);
        assertEquals("teste2", resultado.getNome());
        assertEquals("1234", resultado.getTelefone());
        assertEquals(1, resultado.getId());

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar cliente inexistente")
    void deveLancarExcecaoAoAtualizarClienteInexistente() {

        CreateClienteDTO dto = new CreateClienteDTO("teste", "121332432432", List.of());

        when(repository.findById(999)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.update(dto, 999)
        );

        assertEquals("Cliente não encontrado", excecao.getMessage());

    }

    @Test
    @DisplayName("Deve retornar página de clientes filtrados")
    void deveRetornarPaginaDeClientesFiltrados() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("teste");
        cliente.setTelefone("123456");

        Page<Cliente> paginaSimulada = new PageImpl<>(List.of(cliente));

        when(repository.filtrar(eq("teste"), any(Pageable.class)))
                .thenReturn(paginaSimulada);

        Page<Cliente> resultado = service.getAll("teste", 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("teste", resultado.getContent().get(0).getNome());

        verify(repository, times(1)).filtrar(eq("teste"), any(Pageable.class));
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver clientes")
    void deveRetornarPaginaVaziaSemClientes() {
        Page<Cliente> paginaVazia = Page.empty();

        when(repository.filtrar(eq("naoexiste"), any(Pageable.class)))
                .thenReturn(paginaVazia);

        Page<Cliente> resultado = service.getAll("naoexiste", 0);

        assertNotNull(resultado);
        assertEquals(0, resultado.getTotalElements());

        verify(repository, times(1)).filtrar(eq("naoexiste"), any(Pageable.class));
    }


    @Test
    @DisplayName("Deve remover cliente existente por ID")
    void deveRemoverClienteExistentePorId() {

        when(repository.existsById(1)).thenReturn(true);

        service.removerPorId(1);

        verify(repository, times(1)).deleteById(1);

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover cliente inexistente")
    void deveLancarExcecaoAoRemoverClienteInexistente() {

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("teste");
        cliente.setTelefone("121332432432");

        when(repository.existsById(1)).thenReturn(false);

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.removerPorId(1)
        );

        assertEquals("Cliente de id 1 não encontrado", excecao.getMessage());

    }

    @Test
    @DisplayName("Deve retornar cliente existente por ID")
    void deveRetornarClienteExistentePorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("teste");
        cliente.setTelefone("121332432432");

        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals("teste", resultado.getNome());
        assertEquals("121332432432", resultado.getTelefone());
        assertEquals(1, resultado.getId());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar cliente inexistente por ID")
    void deveRetornarNullAoBuscarClienteInexistentePorId() {

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("teste");
        cliente.setTelefone("121332432432");

        when(repository.findById(1)).thenReturn(Optional.empty());

        Cliente resultado = service.findById(1);

        assertNull(resultado);

    }


}