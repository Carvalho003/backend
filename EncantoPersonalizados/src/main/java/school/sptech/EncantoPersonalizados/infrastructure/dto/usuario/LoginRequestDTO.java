package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequestDTO {

    @Schema(description = "Email do usuário", example = "email@.com")
    private String email;
    @Schema(description = "Senha do usuário", example = "111111@A")
    private String password;
    @Schema(description = "Manter conectado", example = "true")
    private Boolean rememberMe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe != null && rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
