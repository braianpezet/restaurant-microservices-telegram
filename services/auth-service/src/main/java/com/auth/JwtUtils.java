package com.auth;

import com.auth.controller.dto.UserInformation;
import com.auth.entity.AuthToken;
import com.auth.entity.AuthUser;
import com.auth.entity.JwtUserDetails;
import com.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    @Autowired
    private UserRepository repository;

    @Value("${jwt.private-key}")
    private String signedKey;


    public AuthToken generateToken(AuthUser user) {
        Claims claims = Jwts.claims().setSubject(user.getUserId());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + (300 * 1000)));
        claims.put("userName", user.getUserName());
        claims.put("role", "customer");
        claims.put("environment", user.getEnvironment());
        return AuthToken
                .builder()
                .token(Jwts.builder()
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.HS512, signedKey)
                        .compact())
                .build();
    }

    public JwtUserDetails validateToken(String authToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(signedKey).parseClaimsJws(authToken).getBody();
            Date expireTime = claims.getExpiration();

            if (expireTime.before(new Date(System.currentTimeMillis()))) {
                throw new RuntimeException("token is expired");
            }

            return JwtUserDetails
                    .builder()
                    .userName((String) claims.get("userName"))
                    .roles((String) claims.get("role"))
                    .userId(claims.getSubject())
                    .build();

        } catch (Exception ex) {
            throw new RuntimeException("token is not valid");
        }
    }

    public UserInformation validateTokenAndInformation(String authToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(signedKey).parseClaimsJws(authToken).getBody();
            Date expireTime = claims.getExpiration();

            if (expireTime.before(new Date(System.currentTimeMillis()))) {
                throw new RuntimeException("token is expired");
            }

            Optional<AuthUser> user = repository.findByUserName(String.valueOf(claims.get("userName")));


            return UserInformation
                    .builder()
                    .userName((String) claims.get("userName"))
                    .email(user.get().getEmail())
                    .adress(user.get().getAdress())
                    .mobile(user.get().getMobile())
                    .build();

        } catch (Exception ex) {
            throw new RuntimeException("token is not valid");
        }
    }
}
