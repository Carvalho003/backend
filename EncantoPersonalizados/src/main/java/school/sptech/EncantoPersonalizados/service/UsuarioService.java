package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.config.GerenciadorTokenJwt;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioMapper;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.EncantoPersonalizados.dto.usuario.UserMapper;
import school.sptech.EncantoPersonalizados.dto.usuario.UserTokenDTO;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.repository.UsuarioRepository;

import javax.swing.plaf.PanelUI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public UsuarioResponseDTO store(Usuario usuario) {
        String encryptedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encryptedPassword);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setStatus(true);

        Usuario newEntity = repository.save(usuario);
        return UsuarioMapper.toResponseDTO(newEntity);
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
