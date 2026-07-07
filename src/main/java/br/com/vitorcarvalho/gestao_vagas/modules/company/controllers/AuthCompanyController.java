package br.com.vitorcarvalho.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {
    
    private final AuthCompanyUseCase authCompanyUseCase;

    AuthCompanyController(AuthCompanyUseCase authCompanyUseCase){
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/company")
    public String create(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        return this.authCompanyUseCase.execute(authCompanyDTO);
    }
}
