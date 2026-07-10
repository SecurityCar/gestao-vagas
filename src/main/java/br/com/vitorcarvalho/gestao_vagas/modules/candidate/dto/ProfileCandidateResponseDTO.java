package br.com.vitorcarvalho.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(example = "Roberto")
    private String name;

    @Schema(example = "roberto")
    private String username;

    @Schema(example = "roberto@gmail.com")
    private String email;

    @Schema(example = "Desenvolvedor Kotlin")
    private String description;
    private UUID id;
}
