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
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all")
    public ResponseEntity<List<Articulo>> getAll(){
        return ResponseEntity.ok(modelMapper.map(articuloService.getAll(), new TypeToken<List<Articulo>>() {}.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(modelMapper.map(articuloService.getById(id), Articulo.class));
    }

    @PostMapping("/new")
    public ResponseEntity<ArticuloDto> nuevoArticulo(@Valid @RequestBody ArticuloPOSTDTO nuevoArticulo){
        Articulo articuloCreado = articuloService.crear(modelMapper.map(nuevoArticulo, Articulo.class));
        return ResponseEntity.status(201).body(modelMapper.map(articuloCreado, ArticuloDto.class));
    }

    @PutMapping("/{id}")
    @Valid
    public ResponseEntity<Articulo> editarArticulo(@PathVariable Long id,
                                                   @Valid @RequestBody ArticuloPOSTDTO articulo){
        Articulo articuloEditado = articuloService.editar(id, modelMapper.map(articulo, Articulo.class));
        return ResponseEntity.status(200).body(articuloEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Articulo> eliminarArticulo(@PathVariable Long id){
        Articulo articuloEliminado = articuloService.eliminar(id);
        return ResponseEntity.status(200).body(articuloEliminado);
    }
}
