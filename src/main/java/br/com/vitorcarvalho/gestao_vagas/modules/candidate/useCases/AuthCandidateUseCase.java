package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {
    @Value("${security.token.secret.candidate}")
    private String secretKey;
    
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    AuthCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder){
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{
        var candidate = candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found.");
        });

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("gestao_vagas")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("CANDIDATE"))
        .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
        .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder().access_token(token).build();
        return authCandidateResponse;
    }
}
