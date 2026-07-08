package br.com.vitorcarvalho.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorcarvalho.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {
    
    private final AuthCompanyUseCase authCompanyUseCase;

    AuthCompanyController(AuthCompanyUseCase authCompanyUseCase){
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO){
        try{
            var result = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
