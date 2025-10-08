package com._5.scaffolding.services.impl;

import com._5.scaffolding.dtos.PresupuestoDTO;
import com._5.scaffolding.dtos.PresupuestoItemDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfServiceImpl {

    public byte[] generarPdf(PresupuestoDTO dto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();

            PdfContentByte canvas = writer.getDirectContent();

            BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, false);

            try {
                Image logo = Image.getInstance("src/main/resources/logo.png");
                logo.scaleToFit(100, 100);
                logo.setAbsolutePosition(50, 770);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo no encontrado. Saltando...");
            }

            // Fecha
            drawText(canvas, font, 10, dto.getFechaHora(), 545, 800, PdfContentByte.ALIGN_RIGHT);

            // Título
            drawText(canvas, font, 16, "PRESUPUESTO", 297.5f, 750, PdfContentByte.ALIGN_CENTER);
            drawText(canvas, font, 10, "DOCUMENTO NO VÁLIDO COMO FACTURA", 297.5f, 735, PdfContentByte.ALIGN_CENTER);

            canvas.setLineWidth(1f);
            canvas.setGrayStroke(0.75f);
            canvas.moveTo(50, 720);
            canvas.lineTo(545, 720);
            canvas.stroke();

            // Cliente

            canvas.setLineWidth(0.5f);
            canvas.setGrayStroke(0.75f); // gris claro
            canvas.rectangle(45, 675, 500, 40);
            canvas.stroke();

            drawText(canvas, font, 11, "Cliente: " + dto.getClienteNombre(), 50, 700, PdfContentByte.ALIGN_LEFT);
            drawText(canvas, font, 11, "I.V.A: " + dto.getClienteIva(), 320, 700, PdfContentByte.ALIGN_LEFT);

            drawText(canvas, font, 11, "Dirección: " + dto.getClienteDireccion(), 50, 685, PdfContentByte.ALIGN_LEFT);
            drawText(canvas, font, 11, "CUIT: " + dto.getClienteCuit(), 320, 685, PdfContentByte.ALIGN_LEFT);

            // Tabla
            int y = 650;

            canvas.setGrayFill(0.9f); // gris muy claro
            canvas.rectangle(45, y - 5, 500, 18);
            canvas.fill();
            canvas.setGrayFill(0); // reset color a negro
            canvas.setLineWidth(0.5f);
            canvas.setGrayStroke(0.75f);
            canvas.rectangle(45, y - 5, 500, 18);
            canvas.stroke();

            drawText(canvas, font, 11, "Código", 50, y, PdfContentByte.ALIGN_LEFT);
            drawText(canvas, font, 11, "Cant.", 110, y, PdfContentByte.ALIGN_LEFT);
            drawText(canvas, font, 11, "Descripción", 160, y, PdfContentByte.ALIGN_LEFT);
            drawText(canvas, font, 11, "Precio Unit.", 470, y, PdfContentByte.ALIGN_RIGHT);
            drawText(canvas, font, 11, "Subtotal", 545, y, PdfContentByte.ALIGN_RIGHT);

            y -= 20;
            for (PresupuestoItemDTO item : dto.getItems()) {
                canvas.setLineWidth(0.25f);
                canvas.setGrayStroke(0.85f);
                canvas.moveTo(45, y + 14);
                canvas.lineTo(545, y + 14);
                canvas.stroke();


                drawText(canvas, font, 11, item.getCodigo(), 50, y, PdfContentByte.ALIGN_LEFT);
                drawText(canvas, font, 11, String.valueOf(item.getCantidad()), 110, y, PdfContentByte.ALIGN_LEFT);
                drawText(canvas, font, 11, item.getDescripcion(), 160, y, PdfContentByte.ALIGN_LEFT);
                drawText(canvas, font, 11, formatPrice(item.getPrecioUnitario()), 470, y, PdfContentByte.ALIGN_RIGHT);
                drawText(canvas, font, 11, formatPrice(item.getSubtotal()), 545, y, PdfContentByte.ALIGN_RIGHT);
                y -= 20;
            }

            // Total
            canvas.setGrayFill(0.92f);
            canvas.rectangle(400, y - 10, 145, 20);
            canvas.fill();
            canvas.setGrayFill(0);
            double total = dto.getItems().stream().mapToDouble(PresupuestoItemDTO::getSubtotal).sum();
            drawText(canvas, font, 13, "TOTAL: $ " + formatPrice(total), 545, y - 10, PdfContentByte.ALIGN_RIGHT);

            // Nota
            drawText(canvas, font, 10, "NOTA:", 50, y - 50, PdfContentByte.ALIGN_LEFT);

            document.close();
        } catch (IOException e) {
            // Manejar excepción
        }
        return baos.toByteArray();
    }

    private static void drawText(PdfContentByte canvas, BaseFont font, int size, String text, float x, float y, int align) {
        canvas.beginText();
        canvas.setFontAndSize(font, size);
        canvas.showTextAligned(align, text, x, y, 0);
        canvas.endText();
    }

    private static String formatPrice(double value) {
        return String.format("%,.2f", value);
    }
}
