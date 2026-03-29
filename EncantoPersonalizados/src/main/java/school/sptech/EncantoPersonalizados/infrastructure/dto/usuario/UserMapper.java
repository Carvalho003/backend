package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import school.sptech.EncantoPersonalizados.core.domain.Usuario;

public class UserMapper {
    public static Usuario of(CreateUserDTO createUserDTO) {
        Usuario usuario = new Usuario();

        usuario.setName(createUserDTO.getName());
        usuario.setCpf(createUserDTO.getCpf());
        usuario.setCargo(createUserDTO.getCargo());
        usuario.setEmail(createUserDTO.getEmail());
        usuario.setCreatedAt(createUserDTO.getCreatedAt());
        usuario.setPassword(createUserDTO.getPassword());

        return usuario;
    }

    public static UserTokenDTO of(Usuario usuario, String token) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();

        userTokenDTO.setUserId(usuario.getId());
        userTokenDTO.setEmail(usuario.getEmail());
        userTokenDTO.setNome(usuario.getName());
        userTokenDTO.setToken(token);

        return userTokenDTO;
    }

    public static ListUserDTO of(Usuario usuario) {
        ListUserDTO listUserDTO = new ListUserDTO();

        listUserDTO.setNome(usuario.getName());
        listUserDTO.setEmail(usuario.getEmail());
        listUserDTO.setCargo(usuario.getCargo());
        listUserDTO.setId(usuario.getId());
        listUserDTO.setDataNasc(usuario.getDataNasc());

        return listUserDTO;
    }
}
