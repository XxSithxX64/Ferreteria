package com.demo.ferreteria.service.intefaces;

import com.demo.ferreteria.dto.ProveedorDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProveedorService extends IGenericService<ProveedorDTO, Long>{
    CompletableFuture<List<ProveedorDTO>> crearBatchAsync(List<ProveedorDTO> proveedores);
}
