package br.com.fiap.pizzaria.domain.dto.response;

import br.com.fiap.pizzaria.domain.dto.request.AbstractRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OpcionalResponse(

        Long id,

        String nome,

        BigDecimal preco,

        SaborResponse sabor

) {
}
