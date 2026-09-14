package com.demo.ferreteria.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericService<T, ID> {
    T crear(T entity);
    T obtenerPorId(ID id);
    List<T> listarTodos();
    T actualizar(T entity, ID id);
    Boolean eliminar(ID id);
}
