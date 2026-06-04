package br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;
import lombok.Data;

@Data
public class CandidateEntity {
    
    private UUID id;
    private String name;
    private String username;
    private String description;
    private String email;
    private String curriculum;
    private String password;
}
