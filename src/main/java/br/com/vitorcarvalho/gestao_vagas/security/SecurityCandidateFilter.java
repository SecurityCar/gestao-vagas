package br.com.vitorcarvalho.gestao_vagas.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.vitorcarvalho.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter{
    private final JWTCandidateProvider jwtCandidateProvider;

    SecurityCandidateFilter(JWTCandidateProvider jwtCandidateProvider){
        this.jwtCandidateProvider = jwtCandidateProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");

        if(request.getRequestURI().startsWith("/candidate")){
            if(header != null){
                var token = this.jwtCandidateProvider.validateToken(header);

                if(token == null){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", token.getSubject());
                System.out.println("==== TOKEN ====");
                System.out.println(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
