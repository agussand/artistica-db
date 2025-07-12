package com._5.scaffolding.entities;

import com._5.scaffolding.models.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articulos")
@Where(clause = "status = 'ACTIVO'")
@Getter
@Setter
public class ArticuloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String descripcion;

    @Column(name = "sub_categoria")
    private String subcategoria; // Podría ser rubro, luego se puede convertir en relación

    @Column(name = "codigo_barra")
    private String codigoBarra; // Código de barras

    @Enumerated(EnumType.STRING)
    private Status status;

    // Precios
    @Column(name = "precio_lista", precision = 10, scale = 2)
    private BigDecimal precioLista;

    @Column(name = "precio_venta", precision = 10, scale = 2)
    private BigDecimal precioVenta;

    // Tasa de IVA - se puede dejar acá como valor numérico o separar en una tabla
    @Column(name = "tas_iva")
    private Double tasiva;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "ult_modificacion")
    private LocalDateTime ultModificacion;

    @Column(name = "fecha_eliminado")
    private LocalDateTime fechaEliminado;
}
