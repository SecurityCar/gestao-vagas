package br.com.vitorcarvalho.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO.AuthCompanyResponseDTOBuilder;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
    
    @Value("${security.token.secret}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException("Company not found.");
            }
        );

        //Verificar se as senhas são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        //Se forem diferentes -> Erro
        if(!passwordMatches){
            throw new AuthenticationException();
        }

        //Se forem iguais -> Gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("gestao_vagas")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(company.getId().toString())
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

        return authCompanyResponseDTO;
    }
}
