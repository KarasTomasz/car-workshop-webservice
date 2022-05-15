package pl.tkaras.carworkshopwebservice.securities.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtUtils {

    private String tokenHeader;

    private String tokenPrefix;

    private String tokenSecretKey;

    private long expirationTime;

    public String generateJwtToken(Authentication authResult) {

        return Jwts.builder()
                .setSubject(authResult.getName()) //header
                .claim("authorities", authResult.getAuthorities()) //body
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(tokenSecretKey.getBytes())) //sign
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }
}