package br.com.vitorcarvalho.gestao_vagas.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
    
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    AuthCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
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
    }
}
