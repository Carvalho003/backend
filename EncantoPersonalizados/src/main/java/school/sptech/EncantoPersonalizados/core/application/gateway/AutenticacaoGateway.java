package school.sptech.EncantoPersonalizados.core.application.gateway;

import org.springframework.security.core.userdetails.UserDetails;

public interface AutenticacaoGateway {
    UserDetails loadUserByUsername(String username);
}
