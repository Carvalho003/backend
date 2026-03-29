package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;

import java.util.Collection;

public class UserDetailDTO  implements UserDetails {
    private final String nome, email, senha;

    public UserDetailDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public UserDetailDTO(Usuario usuario) {
        this.nome = usuario.getName();
        this.email = usuario.getEmail();
        this.senha = usuario.getPassword();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
