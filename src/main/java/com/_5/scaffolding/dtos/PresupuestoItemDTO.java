package com._5.scaffolding.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresupuestoItemDTO {
    private String codigo;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
}
