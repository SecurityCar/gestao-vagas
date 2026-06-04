package br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {
    
    private UUID id;
    private String name;

    @Pattern(regexp = "\\S+", message = "O campo [username] não pode conter espaços.")
    private String username;
    private String description;

    @Email(message = "O campo [e-mail] deve conter um e-mail válido.")
    private String email;
    private String curriculum;

    @Length(min = 10, max = 100)
    private String password;
}
