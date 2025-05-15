package com._5.scaffolding.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDto {
    private Long id;
    private String descripcion;
    private String subCategoria;
    private String codigoBarra;
    private Double precioLista;
    private Double precioVenta;
}
