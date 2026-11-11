package com.clinicavida.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PacienteRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
        String nome,

        @NotNull(message = "A idade é obrigatória")
        @Min(value = 0, message = "A idade não pode ser negativa")
        @Max(value = 120, message = "A idade deve ser no máximo 120")
        Integer idade,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$",
                 message = "Telefone no formato (11) 99999-9999")
        String telefone) {
}