package br.com.vitorcarvalho.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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

    @Schema(example = "Roberto Robert", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Pattern(regexp = "\\S+", message = "O campo [username] não pode conter espaços.")
    @Schema(example = "robertorobert", requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Schema(example = "Senior especialista em automações", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Email(message = "O campo [e-mail] deve conter um e-mail válido.")
    @Schema(example = "robertorobert@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;
    private String curriculum;

    @Length(min = 10, max = 100)
    @Schema(example = "1234567890", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
