package com.demo.ferreteria.service;

import com.demo.ferreteria.model.Proveedor;
import com.demo.ferreteria.repository.IProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService implements  IProveedorService{
    private final IProveedorRepository repository;

    @Autowired
    public ProveedorService(IProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Proveedor crear(Proveedor entity) {
         return repository.save(entity);
    }

    @Override
    public Proveedor obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con id: " + id));
    }

    @Override
    public List<Proveedor> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Proveedor actualizar(Proveedor entity, Long id) {
        return repository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(entity.getNombre());
                    proveedor.setContacto(entity.getContacto());
                    proveedor.setTelefono(entity.getTelefono());
                    proveedor.setDireccion(entity.getDireccion());
                    proveedor.setEmail(entity.getEmail());
                    proveedor.setEstado(entity.getEstado());
                    return repository.save(proveedor);
                })
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con id: " + id));
    }

    @Override
    public Boolean eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado con id: " + id);
        }
        repository.deleteById(id);
        return true;
    }
}
