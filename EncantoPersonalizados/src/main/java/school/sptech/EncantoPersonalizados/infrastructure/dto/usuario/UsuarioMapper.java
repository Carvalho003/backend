package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import school.sptech.EncantoPersonalizados.core.domain.Usuario;

import java.util.List;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioRequestDTO dto){
        if(dto == null) return null;
        Usuario entity = new Usuario();
        entity.setPassword(dto.password());
        entity.setFoto(dto.foto());
        entity.setCpf(dto.cpf());
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setDataNasc(dto.dataNasc());
        entity.setCargo(dto.cargo());
        return entity;
    }

    public static List<UsuarioResponseDTO> toResponseDTO(List<Usuario> entities){
        if (entities == null) return null;
        return entities
                .stream()
                .map(entity -> {
                    UsuarioResponseDTO dto = new UsuarioResponseDTO(
                            entity.getId(),
                            entity.getName(),
                            entity.getEmail(),
                            entity.getFoto(),
                            entity.getCpf(),
                            entity.getDataNasc(),
                            entity.getCargo(),
                            entity.getCreatedAt(),
                            entity.getUpdatedAt()
                    );
                    return dto;
                })
                .toList();

    }

    public static UsuarioResponseDTO toResponseDTO(Usuario entity){
        if (entity == null) return null;
        UsuarioResponseDTO dto = new UsuarioResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getFoto(),
                entity.getCpf(),
                entity.getDataNasc(),
                entity.getCargo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return dto;
    }

    public static UserTokenDTO of(Usuario usuario, String token) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();

        userTokenDTO.setUserId(usuario.getId());
        userTokenDTO.setEmail(usuario.getEmail());
        userTokenDTO.setNome(usuario.getName());
        userTokenDTO.setFoto(usuario.getFoto());
        userTokenDTO.setCargo(usuario.getCargo());
        userTokenDTO.setToken(token);

        return userTokenDTO;
    }

    public static Usuario of(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = new Usuario();

        usuario.setEmail(loginRequestDTO.getEmail());
        usuario.setPassword(loginRequestDTO.getPassword());

        return usuario;
    }
}
