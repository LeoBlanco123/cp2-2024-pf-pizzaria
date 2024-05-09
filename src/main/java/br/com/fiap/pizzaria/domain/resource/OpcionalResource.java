package br.com.fiap.pizzaria.domain.resource;


import br.com.fiap.pizzaria.domain.dto.request.OpcionalRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.service.OpcionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/opcionais")
public class OpcionalResource implements ResourceDTO<OpcionalRequest, OpcionalResponse> {

    @Autowired
    private OpcionalService service;

    // http://localhost/opcionais?nome=refrigerante&preco=19.99&sabor.nome=catupiri

    @GetMapping
    public ResponseEntity<List<OpcionalResponse>> listar(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "preco", required = false) BigDecimal preco,
            @RequestParam(name = "sabor.nome", required = false) String saborNome
    ) {
        var sabor = Sabor.builder()
                .nome( saborNome )
                .build();

        var opcional = Opcional.builder()
                .nome( nome )
                .preco( preco )
                .sabor( sabor )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher( "nome", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "sabor.nome", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Opcional> example = Example.of( opcional, matcher );

        var entities = service.findAll( example );

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( response );
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<OpcionalResponse> findById(@PathVariable Long id) {
        var entity = service.findById( id );
        if (entity == null) return ResponseEntity.notFound().build();
        var response = service.toResponse( entity );
        return ResponseEntity.ok( response );
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<OpcionalResponse> save(@RequestBody @Valid OpcionalRequest r) {

        var entity = service.toEntity( r );
        entity = service.save( entity );
        var response = service.toResponse( entity );

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path( "/{id}" )
                .buildAndExpand( entity.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );
    }
}
