package school.sptech.EncantoPersonalizados.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.net.Authenticator;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    @Value("${jwt.validity.remember:604800}")
    private long jwtRememberMeValidity;

    public String getUsernameFromToken(String token){
        return getClaimForToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String generateToken(final Authentication authentication, boolean rememberMe){
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long validity = rememberMe ? jwtRememberMeValidity : jwtTokenValidity;

        return Jwts.builder()
                .setSubject(authentication.getName())
                .signWith(parseSecret())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity * 1_000)) // Multiplicado por 1000 se as properties estiverem em segundos
                .compact();
    }

    private <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {

        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token){

        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsFromToken(String token){
        //return Jwts.parserBuilder().setSigningKey(parseSecret()).build().parseClaimsJwt(token).getBody();
        return Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey parseSecret(){
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }


}
