package com.demo.ferreteria.service.implement;

import com.demo.ferreteria.dto.CategoriaDTO;
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
    @Transactional
    @Async
    public CompletableFuture<CategoriaDTO> crearAsync(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        categoria.setEstado(dto.estado());

        Categoria guardado = repository.save(categoria);

        CategoriaDTO resultado = new CategoriaDTO(
                guardado.getIdCategoria(),
                guardado.getNombre(),
                guardado.getDescripcion(),
                guardado.getEstado()
        );

        return CompletableFuture.completedFuture(resultado);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<CategoriaDTO> obtenerPorIdAsync(Long aLong) {
        Categoria categoria = repository.findById(aLong)
                .orElseThrow(()->new EntityNotFoundException("Categoria no encontrada"));

        CategoriaDTO resultado = new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getEstado()
        );
        return CompletableFuture.completedFuture(resultado);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<List<CategoriaDTO>> listarTodosAsync() {
        return CompletableFuture.completedFuture(
                repository.findAll()
                        .stream()
                        .map(p->new CategoriaDTO(
                                p.getIdCategoria(),
                                p.getNombre(),
                                p.getDescripcion(),
                                p.getEstado()
                        )).toList()
        );
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<CategoriaDTO> actualizarAsync(Long aLong, CategoriaDTO dto) {
        Categoria categoria = repository.findById(aLong)
                .orElseThrow(()->new EntityNotFoundException("Categoria no encontrada"));

        //Actualizamos los campos
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        categoria.setEstado(dto.estado());

        Categoria actualizado = repository.save(categoria);

        CategoriaDTO resultado = new CategoriaDTO(
                actualizado.getIdCategoria(),
                actualizado.getNombre(),
                actualizado.getDescripcion(),
                actualizado.getEstado()
        );
            return CompletableFuture.completedFuture(resultado);
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<Boolean> eliminarAsync(Long aLong) {
        if(!repository.existsById(aLong))
            throw new EntityNotFoundException("Categoria no encontrada");

        repository.deleteById(aLong);
        return  CompletableFuture.completedFuture(true);
    }
}
