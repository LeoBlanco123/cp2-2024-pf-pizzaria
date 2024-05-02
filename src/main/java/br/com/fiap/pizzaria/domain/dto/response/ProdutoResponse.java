package br.com.fiap.pizzaria.domain.dto.response;

import br.com.fiap.pizzaria.domain.dto.request.AbstractRequest;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Collection;

@Builder
public record ProdutoResponse(

        Long id,

        String nome,


        BigDecimal preco,


        SaborResponse sabor,


        Collection<OpcionalResponse> opcionais

) {
}
