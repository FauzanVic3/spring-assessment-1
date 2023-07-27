/*
 * Copyright (C) 2023 Fauzan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package my.com.vic3.spring.assessment1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import my.com.vic3.spring.assessment1.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fauzan
 */
@Component
public class JwtProvider {
    
    private final String ROLES_KEY = "roles";    
    private JwtParser parser;
    private String secretKey;
    private long validityPeriodInMilliseconds;
    
    @Autowired
    public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                       @Value("${security.jwt.token.expiration}") long validityPeriodInMilliseconds){
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityPeriodInMilliseconds = validityPeriodInMilliseconds;        
    }
    
    /**
     * Create JWT string given username and roles
     * 
     * @param username
     * @param roles
     * @return 
     */
    public String createToken(String username, List<Role> roles){
        
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ROLES_KEY, roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList()));
                
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityPeriodInMilliseconds);
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();        
    }
    
    /**
     * Validate the JWT String
     * 
     * @param token JWT String
     * @return true if valid, else false
     */
    public boolean isValidToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }
    
    /**
     * Get the username from the token string
     * @param token
     * @return 
     */
    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }
    
    /**
     * Get the roles from the token string
     * 
     * @param token
     * @return 
     */
    public List<GrantedAuthority> getRoles(String token){
        List<Map<String, String>> roleClaims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get(ROLES_KEY, List.class);
        
        return roleClaims.stream().map(roleClaim -> 
                new SimpleGrantedAuthority(roleClaim.get("authority")))
                .collect(Collectors.toList());
    }
    
}
