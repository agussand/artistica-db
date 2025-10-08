package com._5.scaffolding.controllers;

import com._5.scaffolding.dtos.PresupuestoDTO;
import com._5.scaffolding.services.impl.PdfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/presupuestos")
@CrossOrigin(origins = "http://localhost:4200")
public class PresupuestoController {

    @Autowired
    private PdfServiceImpl pdfService;

    @PostMapping("/{id}/pdf")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USUARIO')")
    public ResponseEntity<byte[]> descargarPresupuestoPdf(@RequestBody PresupuestoDTO presupuesto) {
        // 1. Obtener el presupuesto de la base de datos
        // 2. Generar el PDF
        byte[] pdfBytes = pdfService.generarPdf(presupuesto);

        // 3. Devolver el PDF como una descarga
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"presupuesto_" + LocalDateTime.now().toString() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
