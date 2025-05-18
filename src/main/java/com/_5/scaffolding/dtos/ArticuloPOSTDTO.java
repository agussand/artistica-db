package com._5.scaffolding.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticuloPOSTDTO {
    @NotBlank(message = "El campo 'descripcion' no puede estar vacío.")
    @Size(min = 3, max = 300, message = "El campo 'descripcion' debe tener 3 caracteres o más")
    private String descripcion;
    @Size(min = 3, max = 300, message = "El campo 'subcategoria' debe tener 3 caracteres o más")
    private String subCategoria;
    //NotNull
    private String codigoBarra;
    @Positive(message = "precio_lista debe ser positivo")
    private Double precioLista;
    @Positive(message = "precio_venta debe ser positivo")
    private Double precioVenta;
    private Double tasiva;
}
