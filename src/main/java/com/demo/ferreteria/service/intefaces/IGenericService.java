package com.demo.ferreteria.service.intefaces;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IGenericService<T, ID> {
    CompletableFuture<T>crearAsync(T entity);
    CompletableFuture<T> obtenerPorIdAsync(ID id);
    CompletableFuture<List<T>> listarTodosAsync();
    CompletableFuture<T> actualizarAsync(ID id, T entity);
    CompletableFuture<Boolean> eliminarAsync(ID id);
}
