package com.arca.product_service.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenFilter extends OncePerRequestFilter
{

    @Autowired
    private TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = extractTokenFromHeader(request);

        if (token != null)
        {
            List<String> roles = tokenService.extractRolesFromToken(token);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    null, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


    private String extractTokenFromHeader(HttpServletRequest request)
    {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer "))
        {
            return header.substring(7);
        }
        return null;
    }
}
