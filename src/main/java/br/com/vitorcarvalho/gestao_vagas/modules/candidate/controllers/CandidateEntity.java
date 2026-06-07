package br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @CreationTimestamp
    private LocalDateTime createdAt;
}
