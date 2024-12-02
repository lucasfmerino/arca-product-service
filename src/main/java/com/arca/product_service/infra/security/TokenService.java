package com.arca.product_service.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService
{
    @Value("${api.security.token.secret}")
    private String secret;


    public List<String> extractRolesFromToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("arca-api")
                    .build()
                    .verify(token)
                    .getClaim("roles").asList(String.class);
        }
        catch (JWTVerificationException exception)
        {
            throw new RuntimeException("Invalid or expired JWT Token!", exception);
        }
    }


    public String extractUserIdFromToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("arca-api")
                    .build()
                    .verify(token)
                    .getClaim("id").asString();
        }
        catch (JWTVerificationException exception)
        {
            throw new RuntimeException("Invalid or expired JWT Token!", exception);
        }
    }
}
