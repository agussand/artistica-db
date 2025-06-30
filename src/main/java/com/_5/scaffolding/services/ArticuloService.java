package com._5.scaffolding.services;

import com._5.scaffolding.entities.ArticuloEntity;
import com._5.scaffolding.models.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticuloService {
    Page<Articulo> getAll(Pageable pageable);
    Articulo getById(Long id);
    Articulo crear(Articulo articulo);
    Articulo editar(Long id, Articulo articulo);
    Articulo eliminar(Long id);
}
