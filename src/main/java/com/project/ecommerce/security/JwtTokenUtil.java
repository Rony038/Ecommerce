package com.project.ecommerce.security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtTokenUtil {
    private final String key = "MMvUxHrMHVyaQNcwq3tH0k1qMdNT7iR291+QeczxSaxhyAz8hMkBUzd/eZDq3oXt";
	//private final String key = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb255QGdtYWlsLmNvbSIsImlhdCI6MTcwNjcyNzUzOSwiZXhwIjoxNzA3NTkxNTM5fQ._-abVkN5YIOdpoy6CBFnm6zpaag2GXcTwE6eZ8LuBmY";
    public static long JWT_TOKEN_VALIDITY = 1000*60*60*240;
    
    SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));

//    private final String SECRET_KEY = "ELP_SECRET_KEY_2023";
//    public static long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 240;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        System.out.println(createToken(claims, userDetails.getUsername()));
        
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
