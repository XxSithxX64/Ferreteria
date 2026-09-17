package com.demo.ferreteria.controller;

import com.demo.ferreteria.dto.CategoriaDTO;
import com.demo.ferreteria.service.intefaces.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/categorias")
@RequiredArgsConstructor
public class CategoriaAPIController {
    private final ICategoriaService categoriaService;

    @GetMapping
    public CompletableFuture<ResponseEntity<?>> listarTodos(){
        return categoriaService.listarTodosAsync()
                .<ResponseEntity<?>>thenApply(lista ->{
                    if(lista.isEmpty()){
                        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                .body(Map.of("mensaje", "No hay categorias"));
                    }
                    return ResponseEntity.ok(Map.of("mensaje", "Lista de categorias encontrada","data",lista));
                })
                .exceptionally(ex->ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "error al listar categorias", "detalle", ex.getMessage())));
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> create(@RequestBody CategoriaDTO dto){
        return categoriaService.crearAsync(dto)
                .<ResponseEntity<?>>thenApply(creado -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Map.of("mensaje", "Categoria creada correnctamente", "date", creado)))
                .exceptionally(ex->ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "error al crear Categoria", "detalle", ex.getMessage())));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> obtenerPorId(@PathVariable Long id){
        return categoriaService.obtenerPorIdAsync(id)
                .<ResponseEntity<?>>thenApply(categoria -> ResponseEntity
                        .ok(Map.of("mensaje", "Categoria encontrado", "date", categoria)))
                .exceptionally(ex->ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Categoria no encontrado", "detalle", ex.getMessage())));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> actualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto){
        return categoriaService.actualizarAsync(id,dto)
                .<ResponseEntity<?>>thenApply(actualizado->ResponseEntity
                        .ok(Map.of("mensaje", "Categoria actualizada correctamente", "date", actualizado)))
                .exceptionally(ex->ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Categoria no encontrada", "detalle", ex.getMessage())));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> eliminar(@PathVariable Long id){
        return categoriaService.eliminarAsync(id)
                .<ResponseEntity<?>>thenApply(eliminado->eliminado
                        ?ResponseEntity.ok(Map.of("mensaje", "Categoria eliminada correctamente"))
                        :ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Categoria no encontrada")))
                .exceptionally(ex->ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "error al eliminar Categoria", "detalle", ex.getMessage())));
    }
}
