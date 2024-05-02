package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PizzariaRequest(
        @Size(min = 2, max = 100)
        @NotBlank
        @NotNull
        String nome
) {
}
