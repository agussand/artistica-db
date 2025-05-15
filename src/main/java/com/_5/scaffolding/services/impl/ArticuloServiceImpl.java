package com._5.scaffolding.services.impl;

import com._5.scaffolding.entities.ArticuloEntity;
import com._5.scaffolding.exception.InvalidOperationException;
import com._5.scaffolding.exception.RecursoNoEncontradoException;
import com._5.scaffolding.models.Articulo;
import com._5.scaffolding.models.Status;
import com._5.scaffolding.repositories.ArticuloRepository;
import com._5.scaffolding.services.ArticuloService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticuloServiceImpl implements ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapper mergerMapper;

    @Override
    public List<Articulo> getAll() {
        List<ArticuloEntity> articuloEntities = articuloRepository.findAllByStatus(Status.ACTIVO);
        return modelMapper.map(articuloEntities, new TypeToken<List<Articulo>>() {}.getType());
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
