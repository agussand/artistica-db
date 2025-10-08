package com._5.scaffolding.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresupuestoDTO {
    private String fechaHora;
    private String clienteNombre;
    private String clienteDireccion;
    private String clienteCuit;
    private String clienteIva;
    List<PresupuestoItemDTO> items;
}
