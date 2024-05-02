package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaborRequest(

        @Size(min = 2, max = 100)
        @NotBlank
        @NotNull
        String nome,

        @Size(min = 5, max = 255)
        @NotBlank
        String descricao

) {
}
