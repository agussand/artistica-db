package com._5.scaffolding.specifications;

import com._5.scaffolding.entities.ArticuloEntity;
import com._5.scaffolding.models.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
/**
 * Esta clase construye el objeto Specification dinámicamente.
 * La lógica ahora se centra en un único término de búsqueda.
 */
public class ArticuloSpecification {

    public static Specification<ArticuloEntity> build(String searchTerm){
        return (root, query, criteriaBuilder) -> {

            // Esta condición se aplicará SIEMPRE a todas las búsquedas.
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), Status.ACTIVO);

            // Si no hay término de búsqueda, devolvemos solo el filtro de estado.
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return statusPredicate;
            }

            String trimmedSearchTerm = searchTerm.trim();

            // VERIFICAMOS SI EL TÉRMINO DE BÚSQUEDA ES PURAMENTE NUMÉRICO
            if (trimmedSearchTerm.matches("\\d+")) {
                // Si es un número, buscamos por ID o por código de barras.
                try {
                    Long id = Long.parseLong(trimmedSearchTerm);
                    Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
                    Predicate codigoBarraPredicate = criteriaBuilder.equal(root.get("codigoBarra"), trimmedSearchTerm);

                    return criteriaBuilder.or(idPredicate, codigoBarraPredicate);
                } catch (NumberFormatException e) {
                    // Si es un número demasiado grande para ser un Long, solo buscamos por código de barras.
                    return criteriaBuilder.equal(root.get("codigoBarra"), trimmedSearchTerm);
                }
            } else {
                // Si contiene texto, buscamos ÚNICAMENTE en la descripción.
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), "%" + trimmedSearchTerm.toLowerCase() + "%");
            }
        };
    }
}
