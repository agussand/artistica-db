package com._5.scaffolding.controllers;

import com._5.scaffolding.dtos.ArticuloDto;
import com._5.scaffolding.dtos.ArticuloPOSTDTO;
import com._5.scaffolding.entities.ArticuloEntity;
import com._5.scaffolding.models.Articulo;
import com._5.scaffolding.repositories.ArticuloRepository;
import com._5.scaffolding.services.ArticuloService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USUARIO')")
    public ResponseEntity<Page<Articulo>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ){
        Page<Articulo> articulos = articuloService.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(articulos);
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USUARIO')")
    public ResponseEntity<Articulo> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(modelMapper.map(articuloService.getById(id), Articulo.class));
    }

    @PostMapping
    @Valid
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ArticuloDto> nuevoArticulo(@Valid @RequestBody ArticuloPOSTDTO nuevoArticulo){
        Articulo articuloCreado = articuloService.crear(modelMapper.map(nuevoArticulo, Articulo.class));
        return ResponseEntity.status(201).body(modelMapper.map(articuloCreado, ArticuloDto.class));
    }

    @PutMapping("/{id}")
    @Valid
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Articulo> editarArticulo(@PathVariable Long id,
                                                   @Valid @RequestBody ArticuloPOSTDTO articulo){
        Articulo articuloEditado = articuloService.editar(id, modelMapper.map(articulo, Articulo.class));
        return ResponseEntity.status(200).body(articuloEditado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Articulo> eliminarArticulo(@PathVariable Long id){
        Articulo articuloEliminado = articuloService.eliminar(id);
        return ResponseEntity.status(200).body(articuloEliminado);
    }
}
