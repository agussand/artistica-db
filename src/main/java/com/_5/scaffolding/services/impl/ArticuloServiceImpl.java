package com._5.scaffolding.services.impl;

import com._5.scaffolding.dtos.ArticuloAdminDTO;
import com._5.scaffolding.dtos.ArticuloDTO;
import com._5.scaffolding.entities.ArticuloEntity;
import com._5.scaffolding.exception.InvalidOperationException;
import com._5.scaffolding.exception.RecursoNoEncontradoException;
import com._5.scaffolding.models.Articulo;
import com._5.scaffolding.models.Status;
import com._5.scaffolding.repositories.ArticuloRepository;
import com._5.scaffolding.services.ArticuloService;
import com._5.scaffolding.specifications.ArticuloSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ArticuloServiceImpl implements ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapper mergerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ArticuloAdminDTO> getAllArticulosForAdmin(String searchTerm, Pageable pageable) {
        Specification<ArticuloEntity> spec = ArticuloSpecification.build(searchTerm);
        Page<ArticuloEntity> articulosPage = articuloRepository.findAll(spec, pageable);

        // Usamos ModelMapper para convertir cada entidad en la página a ArticuloAdminDTO.
        return articulosPage.map(articuloEntity -> modelMapper.map(articuloEntity, ArticuloAdminDTO.class));
    }

    @Transactional(readOnly = true)
    public Page<ArticuloDTO> searchArticulos(String searchTerm, Pageable pageable) {
        // 1. Construimos la especificación usando nuestra clase helper.
        Specification<ArticuloEntity> spec = ArticuloSpecification.build(searchTerm);

        // 2. Pasamos la especificación y la paginación al repositorio.
        Page<ArticuloEntity> articulosPage = articuloRepository.findAll(spec, pageable);

        return modelMapper.map(articulosPage, new TypeToken<Page<ArticuloDTO>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true) // Es buena práctica usar transacciones de solo lectura para consultas
    public Page<Articulo> getAll(Pageable pageable) {
        Page<ArticuloEntity> articulosPage = articuloRepository.findAllByStatus(Status.ACTIVO, pageable);
        return modelMapper.map(articulosPage, new TypeToken<Page<Articulo>>() {}.getType());
    }

    @Override
    public Articulo getById(Long id) {
        return modelMapper.map(articuloRepository.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Articulo no encontrado con ID: " + id)), Articulo.class);
    }

    @Override
    public Articulo crear(Articulo articulo) {
        ArticuloEntity articuloEntity = modelMapper.map(articulo, ArticuloEntity.class);
        articuloEntity.setFechaAlta(LocalDateTime.now());
        articuloEntity.setStatus(Status.ACTIVO);
        return modelMapper.map(articuloRepository.save(articuloEntity), Articulo.class);
    }

    @Override
    public Articulo editar(Long id, Articulo articuloNuevo) {
        ArticuloEntity articuloEntity = modelMapper.map(this.getById(id), ArticuloEntity.class);
        if(articuloEntity.getStatus() == Status.ELIMINADO){
            throw new InvalidOperationException("No se puede editar un Articulo eliminado");
        }
        mergerMapper.map(articuloNuevo, articuloEntity);
        articuloEntity.setUltModificacion(LocalDateTime.now());
        return modelMapper.map(articuloRepository.save(articuloEntity), Articulo.class);
    }

    @Override
    public Articulo eliminar(Long id) {
        ArticuloEntity articuloEntity = modelMapper.map(this.getById(id), ArticuloEntity.class);
        if(articuloEntity.getStatus() == Status.ELIMINADO){
            throw new InvalidOperationException("El artículo ya se encuentra eliminado");
        }
        articuloEntity.setFechaEliminado(LocalDateTime.now());
        articuloEntity.setStatus(Status.ELIMINADO);
        return modelMapper.map(articuloRepository.save(articuloEntity), Articulo.class);
    }
}
