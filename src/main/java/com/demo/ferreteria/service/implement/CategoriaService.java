package com.demo.ferreteria.service.implement;

import com.demo.ferreteria.model.Categoria;
import com.demo.ferreteria.repository.ICategoriaRepository;
import com.demo.ferreteria.service.intefaces.ICategoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService {
    private final ICategoriaRepository repository;


    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<Categoria> crearAsync(Categoria entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<Categoria> obtenerPorIdAsync(Long aLong) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<List<Categoria>> listarTodosAsync() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<Categoria> actualizarAsync(Long aLong, Categoria entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<Boolean> eliminarAsync(Long aLong) {
        return null;
    }
}
