package br.com.fiap.pizzaria.domain.dto.request;

import br.com.fiap.pizzaria.domain.entity.Opcional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Collection;

public record ProdutoRequest(

        @Size(min = 2, max = 100)
        @NotBlank
        @NotNull
        String nome,

        @NotNull
        @Positive
        BigDecimal preco,

        @Valid
        @NotNull
        AbstractRequest sabor
) {
}
