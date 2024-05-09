package br.com.fiap.pizzaria.domain.resource;


import br.com.fiap.pizzaria.domain.dto.request.SaborRequest;
import br.com.fiap.pizzaria.domain.dto.response.SaborResponse;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.service.SaborService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/sabores")
public class SaborResource implements ResourceDTO<SaborRequest, SaborResponse> {


    @Autowired
    private SaborService service;


    @GetMapping
    public ResponseEntity<Collection<SaborResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao
    ) {
        var sabor = Sabor.builder()
                .nome( nome )
                .descricao( descricao )
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreCase()
                .withMatcher( "nome", match -> match.contains() )
                .withIgnoreNullValues();

        Example<Sabor> example = Example.of( sabor, matcher );
        Collection<Sabor> all = service.findAll( example );
        if (Objects.isNull( all ) || all.isEmpty()) return ResponseEntity.notFound().build();
        var response = all.stream().map( service::toResponse ).toList();
        return ResponseEntity.ok( response );

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<SaborResponse> findById(@PathVariable Long id) {
        var entity = service.findById( id );
        if (Objects.isNull( entity )) return ResponseEntity.notFound().build();
        var response = service.toResponse( entity );
        return ResponseEntity.ok( response );
    }

    @PostMapping
    @Transactional
    @Override
    public ResponseEntity<SaborResponse> save(@RequestBody @Valid SaborRequest r) {
        var entity = service.toEntity( r );

        entity = service.save( entity );

        var response = service.toResponse( entity );

        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( entity.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );
    }
}
