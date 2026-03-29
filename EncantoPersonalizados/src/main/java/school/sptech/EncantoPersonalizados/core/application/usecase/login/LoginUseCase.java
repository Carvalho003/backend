package school.sptech.EncantoPersonalizados.core.application.usecase.login;

import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UserTokenDTO;

public interface LoginUseCase {
    UserTokenDTO validateLogin(String email, String password);
    boolean updatePassword(Usuario usuario, Integer id);
}
