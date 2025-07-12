package com._5.scaffolding.repositories;

import com._5.scaffolding.entities.ArticuloEntity;
import com._5.scaffolding.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<ArticuloEntity, Long>, JpaSpecificationExecutor<ArticuloEntity> {
    Page<ArticuloEntity> findAllByStatus(Status status, Pageable pageable);
}
