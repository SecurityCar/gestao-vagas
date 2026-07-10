package br.com.vitorcarvalho.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CreateJobDTO {
    @Schema(example = "Vaga para desenvolvedor", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    private String level;

    @Schema(example = "Vale Transporte, Vale Alimentação etc..", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
}
