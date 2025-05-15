package com._5.scaffolding.dtos;

import lombok.Data;

@Data
public class ArticuloPOSTDTO {
    private String descripcion;
    private String subCategoria;
    private String codigoBarra;
    private Double precioLista;
    private Double precioVenta;
    private Double tasiva;
}
