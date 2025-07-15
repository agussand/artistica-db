package com._5.scaffolding.services;

import com._5.scaffolding.dtos.ArticuloAdminDTO;
import com._5.scaffolding.dtos.ArticuloDTO;
import com._5.scaffolding.models.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ArticuloService {
    Page<ArticuloAdminDTO> searchArticulosForAdmin(String searchTerm, Pageable pageable);
    Page<ArticuloDTO> searchArticulos(String searchTerm, Pageable pageable);
    Page<Articulo> getAll(Pageable pageable);
    Articulo getById(Long id);
    Articulo crear(Articulo articulo);
    Articulo editar(Long id, Articulo articulo);
    Articulo eliminar(Long id);
}
