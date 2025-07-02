package com._5.scaffolding.specifications;

import com._5.scaffolding.entities.ArticuloEntity;
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
            // Si el término de búsqueda es nulo o vacío, no aplicamos ningún filtro.
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // Devuelve un predicado que siempre es verdadero.
            }

            // Preparamos una lista para los posibles predicados (condiciones OR)
            List<Predicate> orPredicates = new ArrayList<>();

            // 1. Búsqueda por descripción (siempre se aplica)
            orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")), "%" + searchTerm.toLowerCase() + "%"));

            // 2. Búsqueda por código de barra (siempre se aplica)
            // Cuando implementes la lectura, esto funcionará automáticamente.
            orPredicates.add(criteriaBuilder.equal(root.get("codigoBarra"), searchTerm));

            // 3. Búsqueda por ID (solo si el término es un número válido)
            try {
                Long id = Long.parseLong(searchTerm);
                orPredicates.add(criteriaBuilder.equal(root.get("id"), id));
            } catch (NumberFormatException e) {
                // Si no es un número, simplemente ignoramos la búsqueda por ID.
            }

            // Combinamos todos los predicados con un 'OR'.
            // El resultado será: (descripcion LIKE ?) OR (codigoBarra = ?) OR (id = ?)
            return criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
        };
    }
}
