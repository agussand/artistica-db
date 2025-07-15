package com._5.scaffolding.controllers;

import com._5.scaffolding.dtos.ArticuloAdminDTO;
import com._5.scaffolding.dtos.ArticuloDTO;
import com._5.scaffolding.dtos.ArticuloPOSTDTO;
import com._5.scaffolding.models.Articulo;
import com._5.scaffolding.services.ArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Obtiene una lista paginada y filtrada de artículos con datos de admin")
    public ResponseEntity<Page<ArticuloAdminDTO>> getAllArticulosForAdmin(
            @Parameter(description = "Término de búsqueda para filtrar por ID, descripción o código de barras.")
            @RequestParam(required = false) String searchTerm,

            @Parameter(description = "Paginación y ordenamiento")
            Pageable pageable
    ) {
        Page<ArticuloAdminDTO> articulos = articuloService.searchArticulosForAdmin(searchTerm, pageable);
        return ResponseEntity.ok(articulos);
    }


    @GetMapping()
    @Operation(summary = "Obtiene una lista paginada y filtrada de artículos")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USUARIO')")
    public ResponseEntity<Page<ArticuloDTO>> getAllArticulos(
            @Parameter(description = "Término de búsqueda para filtrar por ID, descripción o código de barras.")
            @RequestParam(required = false) String searchTerm,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ArticuloDTO> articulos = articuloService.searchArticulos(searchTerm, PageRequest.of(page, size));
        return ResponseEntity.ok(articulos);
    }


    @GetMapping("/all")
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
    public ResponseEntity<Articulo> nuevoArticulo(@Valid @RequestBody ArticuloPOSTDTO nuevoArticulo){
        Articulo articuloCreado = articuloService.crear(modelMapper.map(nuevoArticulo, Articulo.class));
        return ResponseEntity.status(201).body(articuloCreado);
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
