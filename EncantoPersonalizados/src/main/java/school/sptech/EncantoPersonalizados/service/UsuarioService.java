package school.sptech.EncantoPersonalizados.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.config.GerenciadorTokenJwt;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioMapper;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.EncantoPersonalizados.dto.usuario.UserMapper;
import school.sptech.EncantoPersonalizados.dto.usuario.UserTokenDTO;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.repository.UsuarioRepository;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository repository;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(
            PasswordEncoder passwordEncoder,
            UsuarioRepository repository,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            AuthenticationManager authenticationManager
    ){
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
    }

    public boolean destroy(Integer id) {
        Optional<Usuario> usuarioAtualizar = repository.findById(id);
        if (usuarioAtualizar.isPresent()) {
            var usuarioAtual = usuarioAtualizar.get();
            usuarioAtual.setStatus(false);
            repository.save(usuarioAtual);
            return true;
        }
        return false;
    }

    public Page<Usuario> get(
            String search,
            String cargo,
            Boolean status,
            int page
    ) {
        int size =  10;
        Pageable pageable = PageRequest.of(page, size);
        search = vazioParaNull(search);
        cargo = vazioParaNull(cargo);

        return repository.filtrar(search, cargo, status, pageable);
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    public UsuarioResponseDTO getById(Integer id) {
        Optional<Usuario> entity = repository.findById(id);
        if (entity.isEmpty()) return null;
        return UsuarioMapper.toResponseDTO(entity.get());

    }

    public Usuario getEntityById(Integer id) {
        Optional<Usuario> entity = repository.findById(id);
        if (entity.isEmpty()) return null;
        return entity.get();
    }

    public UsuarioResponseDTO store(Usuario usuario) {
        String encryptedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encryptedPassword);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setStatus(true);

        Usuario newEntity = repository.save(usuario);
        return UsuarioMapper.toResponseDTO(newEntity);
    }

    @Value("uploads")
    private String uploadDir;

    public Usuario storeFoto(Integer id, MultipartFile file) throws IOException {
        if (file == null) {
            throw new RuntimeException("Foto não informada");
        }

        Path usuarioFolder = Paths.get(uploadDir, "funcionarios", id.toString());
        Files.createDirectories(usuarioFolder);
        String nomeArquivo = UUID.randomUUID() + "-" + file.getOriginalFilename();

        Path caminhoCompleto = usuarioFolder.resolve(nomeArquivo);
        Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

        String caminhoRelativo = "/uploads/funcionarios" + id + "/" + nomeArquivo;

        Optional<Usuario> find = repository.findById(id);

        if (find.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Usuario funcionario = find.get();

        funcionario.setFoto(caminhoRelativo);

        return repository.save(funcionario);
    }

    public void deleteFoto(Integer id) throws IOException {
        Optional<Usuario> find = repository.findById(id);

        if (find.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Usuario funcionario = find.get();
        String caminho = funcionario.getFoto();

        Path caminhoArquivo = Paths.get(caminho.replace("/uploads", "uploads"));
        Files.deleteIfExists(caminhoArquivo);

        funcionario.setFoto("");

        repository.save(funcionario);
    }

    public UsuarioResponseDTO update(Usuario usuario, Integer id) {
        Optional<Usuario> usuarioAtualizar = repository.findById(id);
        if (usuarioAtualizar.isPresent()) {
            var usuarioAtual = usuarioAtualizar.get();
            usuarioAtual.setName(usuario.getName());
            usuarioAtual.setCargo(usuario.getCargo());
            usuarioAtual.setCpf(usuario.getCpf());
            usuarioAtual.setEmail(usuario.getEmail());
            usuarioAtual.setUpdatedAt(LocalDateTime.now());
            // implementar logica de upload de foto
            usuarioAtual.setFoto(usuario.getFoto());
            usuarioAtual.setDataNasc(usuario.getDataNasc());

            Usuario entity = repository.save(usuarioAtual);
            return UsuarioMapper.toResponseDTO(entity);

        }
        return null;
    }

    public UsuarioResponseDTO updatePassword(String oldPassword, String newPassword, Integer id) {
        Optional<Usuario> usuarioAtualizar = repository.findById(id);
        if (usuarioAtualizar.isPresent()) {
            var usuarioAtual = usuarioAtualizar.get();
            if (!passwordEncoder.matches(oldPassword, usuarioAtual.getPassword())) {
                throw new RuntimeException("Senha atual incorreta");
            }

            if (passwordEncoder.matches(newPassword, usuarioAtual.getPassword())) {
                throw new RuntimeException("A nova senha não pode ser igual a atual");
            }

            usuarioAtual.setPassword(passwordEncoder.encode(newPassword));
            Usuario usuarioSalvo = repository.save(usuarioAtual);

            return UsuarioMapper.toResponseDTO(usuarioSalvo);
        }

        return null;
    }
;
    // INÍCIO DE IMPLEMENTAÇÃO DE ORDENAÇÃO RECURSIVA DE LISTA
    public List<Usuario> getUsuariosComMergeSortPorNome() {
        List<Usuario> usuarios = repository.findAll();
        mergeSort(usuarios, 0, usuarios.size() - 1);
        return usuarios;
    }

    private void mergeSort(List<Usuario> lista, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;

            mergeSort(lista, inicio, meio);
            mergeSort(lista, meio + 1, fim);

            merge(lista, inicio, meio, fim);
        }
    }

    private void merge(List<Usuario> lista, int inicio, int meio, int fim) {
        List<Usuario> metadeEsquerda = new ArrayList<>(lista.subList(inicio, meio + 1));
        List<Usuario> metadeDireita = new ArrayList<>(lista.subList(meio + 1, fim + 1));

        int i = 0;
        int j = 0;
        int k = inicio;

        while (i < metadeEsquerda.size() && j < metadeDireita.size()) {
            if (metadeEsquerda.get(i).getName().compareToIgnoreCase(metadeDireita.get(j).getName()) <= 0) {
                lista.set(k, metadeEsquerda.get(i));
                i++;
            } else {
                lista.set(k, metadeDireita.get(j));
                j++;
            }
            k++;
        }

        while (i < metadeEsquerda.size()) {
            lista.set(k, metadeEsquerda.get(i));
            i++;
            k++;
        }

        while (j < metadeDireita.size()) {
            lista.set(k, metadeDireita.get(j));
            j++;
            k++;
        }
    }
    // FIM DE IMPLEMENTAÇÃO DE ORDENAÇÃO RECURSIVA DE LISTA
}
