package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.AbstractRequest;
import br.com.fiap.pizzaria.domain.dto.request.ProdutoRequest;
import br.com.fiap.pizzaria.domain.dto.response.ProdutoResponse;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.service.OpcionalService;
import br.com.fiap.pizzaria.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collection;


@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoService service;
    @Autowired
    private OpcionalService opcionalService;

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var entity = service.findById( id );
        if (entity == null) return ResponseEntity.notFound().build();
        var response = service.toResponse( entity );
        return ResponseEntity.ok( response );
    }


    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest r) {
        var entity = service.toEntity( r );
        entity = service.save( entity );

        var response = service.toResponse( entity );

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path( "/{id}" )
                .buildAndExpand( entity.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );
    }

    @GetMapping
    public ResponseEntity<Collection<ProdutoResponse>> findAll(

            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "preco", required = false) BigDecimal preco,
            @RequestParam(name = "sabor.nome", required = false) String saborNome

    ) {

        var sabor = Sabor.builder()
                .nome( saborNome )
                .build();

        var produto = Produto.builder()
                .nome( nome )
                .preco( preco )
                .sabor( sabor )
                .build();

        var matcher = ExampleMatcher.matching()
                .withMatcher( "nome", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "sabor.nome", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Produto> example = Example.of( produto, matcher );

        var entities = service.findAll( example );

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( response );

    }


    @Transactional
    @PostMapping(value = "/{id}/opcionais")
    public ResponseEntity<ProdutoResponse> optionais(@PathVariable Long id, @RequestBody @Valid AbstractRequest r) {

        var entity = service.findById( id );
        if (entity == null) return ResponseEntity.notFound().build();

        var opcional = opcionalService.findById( r.id() );
        if (opcional == null) return ResponseEntity.badRequest().build();

        entity.getOpcionais().add( opcional );

        var response = service.toResponse( entity );

        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( entity.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );

    }

    @Transactional
    @PostMapping(value = "/{id}/fotos")
    public ResponseEntity<ProdutoResponse> fotos(@RequestPart MultipartFile file, @PathVariable Long id) {
        //TODO: Precisamos implementar.
        return null;
    }


}
