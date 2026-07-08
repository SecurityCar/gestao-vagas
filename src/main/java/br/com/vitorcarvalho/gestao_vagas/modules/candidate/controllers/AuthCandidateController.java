package br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorcarvalho.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {
    private final AuthCandidateUseCase authCandidateUseCase;

    AuthCandidateController(AuthCandidateUseCase authCandidateUseCase){
        this.authCandidateUseCase = authCandidateUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var token = this.authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
}
