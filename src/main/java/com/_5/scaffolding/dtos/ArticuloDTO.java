package com._5.scaffolding.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDTO {
    private Long id;
    private String descripcion;
    private String subCategoria;
    private Double precioVenta;
}
