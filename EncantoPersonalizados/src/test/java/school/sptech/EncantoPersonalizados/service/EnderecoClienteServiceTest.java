package school.sptech.EncantoPersonalizados.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.EncantoPersonalizados.dto.cliente.EnderecoClienteRequestDTO;
import school.sptech.EncantoPersonalizados.entities.Cliente;
import school.sptech.EncantoPersonalizados.entities.EnderecoCliente;
import school.sptech.EncantoPersonalizados.exceptions.CategoriaTemaNaoEncontradaException;
import school.sptech.EncantoPersonalizados.exceptions.EntidadeNaoEncontradaException;
import school.sptech.EncantoPersonalizados.repository.ClienteRepository;
import school.sptech.EncantoPersonalizados.repository.EnderecoClienteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoClienteServiceTest {
    @Mock
    EnderecoClienteRepository repository;

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    EnderecoClienteService service;

    //function store
    @Test
    @DisplayName("Lançar exceção quando o cliente não for encontrado")
    void LancarExcecaoQuandoClienteNaoForEncontrado() {
        EnderecoClienteRequestDTO dto = new EnderecoClienteRequestDTO(
                "Rua Teste",
                "123",
                "Bairro Teste",
                "12345-678",
                "SP",
                "São Paulo",
                "São Paulo",
                "São Paulo",
                "Apto 22"
        );

        when(clienteRepository.findById(1))
                .thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.store(dto, 1)
        );

        assertEquals("Cliente não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Salvar o endereço do cliente com sucesso")
    void SalvarEnderecoComSucesso() {
        EnderecoClienteRequestDTO dto = new EnderecoClienteRequestDTO(
                "Rua Teste",
                "123",
                "Bairro Teste",
                "12345-678",
                "SP",
                "São Paulo",
                "São Paulo",
                "São Paulo",
                "Apto 22"
        );

        Cliente cliente = new Cliente();
        cliente.setId(1);

        when(clienteRepository.findById(1))
                .thenReturn(Optional.of(cliente));

        EnderecoCliente enderecoSalvo = new EnderecoCliente();
        enderecoSalvo.setId(1);

        when(repository.save(any(EnderecoCliente.class)))
                .thenReturn(enderecoSalvo);

        EnderecoCliente resultado = service.store(dto, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());

        verify(repository, times(1)).save(any(EnderecoCliente.class));
    }

    //function update
    @Test
    @DisplayName("Lançar exceção quando o endereço não for encontrado")
    void deveLancarExcecaoQuandoEnderecoNaoForEncontrado() {
        EnderecoClienteRequestDTO dto = new EnderecoClienteRequestDTO(
                "Rua Teste",
                "123",
                "Bairro Teste",
                "12345-678",
                "SP",
                "São Paulo",
                "São Paulo",
                "São Paulo",
                "Apto 22"
        );

        when(repository.findById(1)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException excecao = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> service.update(dto, 1)
        );

        assertEquals("Endereco não encontrado", excecao.getMessage());
    }

    //function mudarEstado
    @Test
    @DisplayName("Atualizar o endereço com sucesso")
    void AtualizarEnderecoComSucesso() {
        EnderecoClienteRequestDTO dto = new EnderecoClienteRequestDTO(
                "Rua Nova",
                "55A",
                "Bairro Novo",
                "88888-888",
                "RJ",
                "Rio de Janeiro",
                "Estado RJ",
                "Município RJ",
                "Bloco B"
        );

        EnderecoCliente enderecoExistente = new EnderecoCliente();
        enderecoExistente.setId(1);
        enderecoExistente.setLogradouro("Antigo");
        enderecoExistente.setNumLogradouro("100");
        enderecoExistente.setBairro("Bairro Velho");
        enderecoExistente.setCidade("Cidade Velha");
        enderecoExistente.setComplemento("Casa antiga");

        when(repository.findById(1)).thenReturn(Optional.of(enderecoExistente));

        when(repository.save(enderecoExistente)).thenReturn(enderecoExistente);

        EnderecoCliente resultado = service.update(dto, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());

        assertEquals("Rua Nova", enderecoExistente.getLogradouro());
        assertEquals("55A", enderecoExistente.getNumLogradouro());
        assertEquals("Bairro Novo", enderecoExistente.getBairro());
        assertEquals("88888-888", enderecoExistente.getCep());
        assertEquals("RJ", enderecoExistente.getUf());
        assertEquals("Rio de Janeiro", enderecoExistente.getCidade());
        assertEquals("Estado RJ", enderecoExistente.getEstado());
        assertEquals("Município RJ", enderecoExistente.getMunicipio());
        assertEquals("Bloco B", enderecoExistente.getComplemento());
        assertNotNull(enderecoExistente.getCreatedAt());

        verify(repository, times(1)).save(enderecoExistente);
    }

    @Test
    @DisplayName("Lançar exceção quando o endereço não for encontrado")
    void LancarExcecaoQuandoEnderecoNaoExiste() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        CategoriaTemaNaoEncontradaException excecao = assertThrows(
                CategoriaTemaNaoEncontradaException.class,
                () -> service.mudarEstado(1)
        );

        assertEquals("Categoria de tema não encontrado", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve desativar o endereço quando estiver ativo")
    void deveDesativarQuandoAtivo() {
        EnderecoCliente endereco = new EnderecoCliente();
        endereco.setId(1);
        endereco.setAtivo(true);

        when(repository.findById(1)).thenReturn(Optional.of(endereco));

        service.mudarEstado(1);

        assertFalse(endereco.getAtivo());
        verify(repository, times(1)).save(endereco);
    }

    @Test
    @DisplayName("Deve ativar o endereço quando estiver inativo")
    void deveAtivarQuandoInativo() {
        EnderecoCliente endereco = new EnderecoCliente();
        endereco.setId(1);
        endereco.setAtivo(false);

        when(repository.findById(1)).thenReturn(Optional.of(endereco));

        service.mudarEstado(1);

        assertTrue(endereco.getAtivo());
        verify(repository, times(1)).save(endereco);
    }
}