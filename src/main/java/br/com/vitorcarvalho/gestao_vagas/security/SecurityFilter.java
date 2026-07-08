package br.com.vitorcarvalho.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.vitorcarvalho.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        SecurityContextHolder.getContext().setAuthentication(null);

        if(request.getRequestURI().startsWith("/company")){
            if(header != null){
                var subject_token = this.jwtProvider.validadeToken(header);
                if(subject_token.isEmpty()){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("company_id", subject_token);
                UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(subject_token, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
