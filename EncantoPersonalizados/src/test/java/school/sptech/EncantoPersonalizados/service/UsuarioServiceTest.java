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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.core.application.usecase.usuario.UsuarioUseCaseImpl;
import school.sptech.EncantoPersonalizados.infrastructure.config.GerenciadorTokenJwt;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.core.application.gateway.UsuarioGateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseImplTest {

    @InjectMocks
    private UsuarioUseCaseImpl service;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioGateway repository;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Deve desativar usuário existente")
    void deveDesativarUsuarioExistente() {

        Usuario user = new Usuario();

        user.setId(1);
        user.setStatus(true);

        when(repository.findById(1)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);

        boolean resultado = service.destroy(1);

        assertTrue(resultado);
        assertFalse(user.getStatus());
    }

    @Test
    @DisplayName("Deve retornar UsuarioResponseDTO quando buscar por ID existente")
    void deveRetornarUsuarioResponseDTOPorIdExistente() {

        Usuario user = new Usuario();
        user.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(user));

        UsuarioResponseDTO resultado = service.getById(1);

        assertNotNull(resultado);
        assertEquals(1, user.getId());

    }

    @Test
    @DisplayName("Deve retornar null quando buscar por ID inexistente")
    void deveRetornarNullPorIdInexistente() {

        Usuario user = new Usuario();
        user.setId(1);

        when(repository.findById(1)).thenReturn(Optional.empty());

        UsuarioResponseDTO resultado = service.getById(1);

        assertNull(resultado);

    }

    @Test
    @DisplayName("Deve salvar novo usuário com senha criptografada")
    void deveSalvarNovoUsuarioComSenhaCriptografada() {

        Usuario user = new Usuario();
        String senha = "Senha123@";
        user.setName("teste");
        user.setEmail("teste@gmail.com");
        user.setPassword(senha);

        String senhaCriptografada = "encodedSenha123@";
        when(passwordEncoder.encode("encodedSenha123@")).thenReturn(senhaCriptografada);

        user.setPassword(senhaCriptografada);
        when(repository.save(Mockito.any(Usuario.class))).thenReturn(user);

        UsuarioResponseDTO resultado = service.store(user);

        assertNotNull(resultado);
        assertEquals("encodedSenha123@", user.getPassword());
        assertEquals("teste@gmail.com", resultado.email());
        assertEquals("teste", resultado.name());

    }

    @Test
    @DisplayName("Deve atualizar dados de usuário existente")
    void deveAtualizarDadosUsuarioExistente() {

        Usuario user = new Usuario();
        user.setId(1);
        user.setPassword("senha123");
        user.setEmail("userTest@gmail.com");
        user.setName("nome");

        when(repository.findById(1)).thenReturn(Optional.of(user));

        Usuario novo = new Usuario();
        novo.setId(1);
        novo.setPassword("senha123456");
        novo.setEmail("userTest2@gmail.com");
        novo.setName("novo nome");

        when(repository.save(any(Usuario.class))).thenReturn(user);

        UsuarioResponseDTO resultado = service.update(novo, 1);

        assertNotNull(resultado);
        assertEquals("userTest2@gmail.com", resultado.email());
        assertEquals("novo nome", resultado.name());
    }

    @Test
    @DisplayName("Deve atualizar senha corretamente")
    void deveAtualizarSenhaCorretamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setPassword("senhaAntigaCodificada"); // senha inicial

        String senhaAntiga = "senhaAntigaCodificada";
        String senhaNova = "senhaNova";

        when(repository.findById(1)).thenReturn(Optional.of(usuario));

        // senha antiga confere
        when(passwordEncoder.matches(senhaAntiga, "senhaAntigaCodificada")).thenReturn(true);
        // nova senha é diferente da atual
        when(passwordEncoder.matches(senhaNova, "senhaAntigaCodificada")).thenReturn(false);
        // encode da nova senha
        when(passwordEncoder.encode(senhaNova)).thenReturn("senhaNovaCodificada");

        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDTO resultado = service.updatePassword(senhaAntiga, senhaNova, 1);

        assertNotNull(resultado);
        assertEquals("senhaNovaCodificada", usuario.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar senha incorreta")
    void deveLancarExcecaoAoAtualizarSenhaIncorreta() {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setPassword("senhaAntiga");

        String senhaAntiga = "senhaAntiga";
        String senhaNova = "senhaAntiga";

        when(repository.findById(1)).thenReturn(Optional.of(usuario));

        // senha antiga confere
        when(passwordEncoder.matches(senhaAntiga, usuario.getPassword())).thenReturn(true);
        // nova senha é igual a senha atual
        when(passwordEncoder.matches(senhaNova, usuario.getPassword())).thenReturn(true);

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.updatePassword(senhaAntiga, senhaNova, 1)
        );

        assertEquals("A nova senha não pode ser igual a atual", excecao.getMessage());

    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(List.of());

        List<Usuario> resultado = service.getUsuariosComMergeSortPorNome();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve ordenar lista com apenas um usuário")
    void deveOrdenarListaComUmUsuario() {
        Usuario u1 = new Usuario();
        u1.setId(1);
        u1.setName("Maria");

        when(repository.findAll()).thenReturn(List.of(u1));

        List<Usuario> resultado = service.getUsuariosComMergeSortPorNome();

        assertEquals(1, resultado.size());
        assertEquals("Maria", resultado.get(0).getName());
    }

    @Test
    @DisplayName("Deve ordenar lista já ordenada")
    void deveOrdenarListaJaOrdenada() {
        Usuario u1 = new Usuario();
        u1.setName("Ana");
        Usuario u2 = new Usuario();
        u2.setName("João");
        Usuario u3 = new Usuario();
        u3.setName("Maria");

        List<Usuario> usuariosOrdenados = Arrays.asList(u1, u2, u3);

        when(repository.findAll()).thenReturn(usuariosOrdenados);

        List<Usuario> resultado = service.getUsuariosComMergeSortPorNome();

        assertEquals("Ana", resultado.get(0).getName());
        assertEquals("João", resultado.get(1).getName());
        assertEquals("Maria", resultado.get(2).getName());
    }

    @Test
    @DisplayName("Deve salvar foto de usuário existente usando diretório temporário")
    void deveSalvarFotoUsuarioExistente() throws IOException {
        // cria diretório temporário
        String tempDir = Files.createTempDirectory("test-uploads").toString();
        // injeta o valor no campo privado
        ReflectionTestUtils.setField(service, "uploadDir", tempDir);

        Usuario usuario = new Usuario();
        usuario.setId(1);

        MultipartFile arquivo = mock(MultipartFile.class);
        when(arquivo.getOriginalFilename()).thenReturn("foto.png");
        when(arquivo.getInputStream()).thenReturn(new ByteArrayInputStream("fake-image".getBytes()));

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = service.storeFoto(1, arquivo);

        assertNotNull(resultado.getFoto());
        assertTrue(resultado.getFoto().contains("foto.png"));
    }


    @Test
    @DisplayName("Deve lançar exceção ao salvar foto sem arquivo")
    void deveLancarExcecaoAoSalvarFotoSemArquivo() {
        MultipartFile arquivo = null;

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.storeFoto(1, arquivo)
        );

        assertEquals("Foto não informada", excecao.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar foto quando usuário não existe")
    void deveLancarExcecaoAoSalvarFotoUsuarioNaoExiste() throws IOException {
        String tempDir = Files.createTempDirectory("test-uploads").toString();
        ReflectionTestUtils.setField(service, "uploadDir", tempDir);

        MultipartFile arquivo = mock(MultipartFile.class);
        when(arquivo.getOriginalFilename()).thenReturn("foto.png");
        when(arquivo.getInputStream()).thenReturn(new ByteArrayInputStream("fake-image".getBytes()));

        when(repository.findById(1)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> service.storeFoto(1, arquivo)
        );

        assertEquals("Funcionário não encontrado", excecao.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar foto de usuário existente")
    void deveDeletarFotoUsuarioExistente() throws IOException {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setFoto("/uploads/funcionarios/1/foto.png");

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.deleteFoto(1);

        assertEquals("", usuario.getFoto());
    }

    @Test
    @DisplayName("Deve buscar usuários com filtros e paginação")
    void deveBuscarUsuariosComFiltros() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setName("Ana");

        Page<Usuario> paginaSimulada = new PageImpl<>(List.of(usuario));
        when(repository.filtrar(any(), any(), any(), any(Pageable.class))).thenReturn(paginaSimulada);

        Page<Usuario> resultado = service.get("Ana", "Gerente", true, 0);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Deve retornar entidade de usuário existente")
    void deveRetornarEntidadeUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setName("Teste");

        when(repository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.getEntityById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Teste", resultado.getName());
    }

    @Test
    @DisplayName("Deve retornar null quando usuário não existir")
    void deveRetornarNullQuandoUsuarioNaoExistir() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Usuario resultado = service.getEntityById(99);

        assertNull(resultado);
    }


}