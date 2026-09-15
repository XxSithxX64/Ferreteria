package com.demo.ferreteria.controller;

import com.demo.ferreteria.dto.ProveedorDTO;
import com.demo.ferreteria.service.intefaces.IProveedorService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorAPIController {
    private final IProveedorService proveedorService;

    @GetMapping
    public CompletableFuture<ResponseEntity<?>> listarTodos() {
        return proveedorService.listarTodosAsync()
                .<ResponseEntity<?>>thenApply(lista -> {
                    if (lista.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                .body(Map.of("mensaje", "No hay proveedores registrados"));
                    }
                    return ResponseEntity.ok(Map.of("mensaje", "Lista de proveedores", "data", lista));
                })
                .exceptionally(ex -> ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Error al listar proveedores", "detalle", ex.getMessage())));
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> crear(@RequestBody ProveedorDTO dto) {
        return proveedorService.crearAsync(dto)
                .<ResponseEntity<?>>thenApply(creado -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Map.of("mensaje", "Proveedor creado correctamente", "data", creado)))
                .exceptionally(ex -> ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Error al crear proveedor", "detalle", ex.getMessage())));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerPorIdAsync(id)
                .<ResponseEntity<?>>thenApply(proveedor -> ResponseEntity
                        .ok(Map.of("mensaje", "Proveedor encontrado", "data", proveedor)))
                .exceptionally(ex -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Proveedor no encontrado", "detalle", ex.getMessage())));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> actualizar(@PathVariable Long id, @RequestBody ProveedorDTO dto) {
        return proveedorService.actualizarAsync(id, dto)
                .<ResponseEntity<?>>thenApply(actualizado -> ResponseEntity
                        .ok(Map.of("mensaje", "Proveedor actualizado correctamente", "data", actualizado)))
                .exceptionally(ex -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Error al actualizar proveedor", "detalle", ex.getMessage())));
    }


    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> eliminar(@PathVariable Long id) {
        return proveedorService.eliminarAsync(id)
                .<ResponseEntity<?>>thenApply(eliminado -> eliminado
                        ? ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado correctamente"))
                        : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Proveedor no encontrado")))
                .exceptionally(ex -> ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Error al eliminar proveedor", "detalle", ex.getMessage())));
    }

    @GetMapping("/export")
    public CompletableFuture<ResponseEntity<byte[]>> exportarExcel() {
        return proveedorService.listarTodosAsync()
                .thenApply(proveedores -> {
                    try (Workbook workbook = new XSSFWorkbook()) {
                        Sheet sheet = workbook.createSheet("Proveedores");

                        // Encabezados con estilo
                        String[] columnas = {"ID", "Nombre", "Contacto", "Teléfono", "Dirección", "Email", "Estado"};
                        Row header = sheet.createRow(0);

                        CellStyle headerStyle = workbook.createCellStyle();
                        Font font = workbook.createFont();
                        font.setBold(true);
                        headerStyle.setFont(font);
                        headerStyle.setAlignment(HorizontalAlignment.CENTER);

                        for (int i = 0; i < columnas.length; i++) {
                            Cell cell = header.createCell(i);
                            cell.setCellValue(columnas[i]);
                            cell.setCellStyle(headerStyle);
                        }

                        // Datos
                        int rowIdx = 1;
                        for (ProveedorDTO p : proveedores) {
                            Row row = sheet.createRow(rowIdx++);
                            row.createCell(0).setCellValue(p.id());
                            row.createCell(1).setCellValue(p.nombre());
                            row.createCell(2).setCellValue(p.contacto());
                            row.createCell(3).setCellValue(p.telefono());
                            row.createCell(4).setCellValue(p.direccion());
                            row.createCell(5).setCellValue(p.email());
                            row.createCell(6).setCellValue(p.estado() ? "Activo" : "Inactivo");
                        }

                        // Ajustar ancho automáticamente
                        for (int i = 0; i < columnas.length; i++) {
                            sheet.autoSizeColumn(i);
                        }

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        workbook.write(out);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=proveedores.xlsx")
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(out.toByteArray());

                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(("Error al exportar: " + e.getMessage()).getBytes());
                    }
                });
    }

    @PostMapping("/import")
    public CompletableFuture<ResponseEntity<?>> importarExcel(@RequestParam("file") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                List<ProveedorDTO> proveedores = new ArrayList<>();

                // Leer filas (saltando encabezado)
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    ProveedorDTO dto = new ProveedorDTO(
                            (long) row.getCell(0).getNumericCellValue(),
                            row.getCell(1).getStringCellValue(),
                            row.getCell(2).getStringCellValue(),
                            row.getCell(3).getStringCellValue(),
                            row.getCell(4).getStringCellValue(),
                            row.getCell(5).getStringCellValue(),
                            "Activo".equalsIgnoreCase(row.getCell(6).getStringCellValue())
                    );
                    proveedores.add(dto);
                }

                // Guardar proveedores en lote
                proveedorService.crearBatchAsync(proveedores);

                return ResponseEntity.ok(Map.of(
                        "mensaje", "Proveedores importados correctamente",
                        "total", proveedores.size()
                ));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "error", "Error al importar proveedores",
                                "detalle", e.getMessage()
                        ));
            }
        });
    }
}
