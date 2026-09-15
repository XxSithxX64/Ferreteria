package com.demo.ferreteria.service.implement;

import com.demo.ferreteria.dto.ProveedorDTO;
import com.demo.ferreteria.model.Proveedor;
import com.demo.ferreteria.repository.IProveedorRepository;
import com.demo.ferreteria.service.intefaces.IProveedorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProveedorService implements IProveedorService {
    private final IProveedorRepository repository;

    @Override
    @Transactional
    @Async
    public CompletableFuture<ProveedorDTO> crearAsync(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.nombre());
        proveedor.setContacto(dto.contacto());
        proveedor.setTelefono(dto.telefono());
        proveedor.setDireccion(dto.direccion());
        proveedor.setEmail(dto.email());
        proveedor.setEstado(dto.estado());

        Proveedor guardado = repository.save(proveedor);

        ProveedorDTO resultado = new ProveedorDTO(
                guardado.getIdProveedor(),
                guardado.getNombre(),
                guardado.getContacto(),
                guardado.getTelefono(),
                guardado.getDireccion(),
                guardado.getEmail(),
                guardado.getEstado()
        );

        return CompletableFuture.completedFuture(resultado);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<ProveedorDTO> obtenerPorIdAsync(Long aLong) {
        Proveedor proveedor = repository.findById(aLong)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        ProveedorDTO dto = new ProveedorDTO(
                proveedor.getIdProveedor(),
                proveedor.getNombre(),
                proveedor.getContacto(),
                proveedor.getTelefono(),
                proveedor.getDireccion(),
                proveedor.getEmail(),
                proveedor.getEstado()
        );

        return CompletableFuture.completedFuture(dto);
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<List<ProveedorDTO>> listarTodosAsync() {
        return CompletableFuture.completedFuture(
                repository.findAll()
                        .stream()
                        .map(p -> new ProveedorDTO(
                                p.getIdProveedor(),
                                p.getNombre(),
                                p.getContacto(),
                                p.getTelefono(),
                                p.getDireccion(),
                                p.getEmail(),
                                p.getEstado()
                        ))
                        .toList()
        );
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<ProveedorDTO> actualizarAsync(Long id, ProveedorDTO dto) {
        Proveedor proveedor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        System.out.println("Service → proveedor encontrado: " + proveedor.getNombre());

        // Actualizamos los campos
        proveedor.setNombre(dto.nombre());
        proveedor.setContacto(dto.contacto());
        proveedor.setTelefono(dto.telefono());
        proveedor.setDireccion(dto.direccion());
        proveedor.setEmail(dto.email());
        proveedor.setEstado(dto.estado());

        Proveedor actualizado = repository.save(proveedor);

        ProveedorDTO resultado = new ProveedorDTO(
                actualizado.getIdProveedor(),
                actualizado.getNombre(),
                actualizado.getContacto(),
                actualizado.getTelefono(),
                actualizado.getDireccion(),
                actualizado.getEmail(),
                actualizado.getEstado()
        );

        return CompletableFuture.completedFuture(resultado);
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<Boolean> eliminarAsync(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado con id: " + id);
        }
        repository.deleteById(id);
        return CompletableFuture.completedFuture(true);
    }

    @Transactional
    public CompletableFuture<List<ProveedorDTO>> crearBatchAsync(List<ProveedorDTO> proveedores) {
        return CompletableFuture.supplyAsync(() -> {
            List<Proveedor> entidades = proveedores.stream()
                    .map(this::toEntity)
                    .toList();

            List<Proveedor> guardados = repository.saveAll(entidades);

            return guardados.stream()
                    .map(this::toDTO)
                    .toList();
        });
    }

    // Métodos auxiliares para conversión
    private Proveedor toEntity(ProveedorDTO dto) {
        return Proveedor.builder()
                .idProveedor(dto.id())
                .nombre(dto.nombre())
                .contacto(dto.contacto())
                .telefono(dto.telefono())
                .direccion(dto.direccion())
                .email(dto.email())
                .estado(dto.estado())
                .build();
    }

    private ProveedorDTO toDTO(Proveedor entidad) {
        return new ProveedorDTO(
                entidad.getIdProveedor(),
                entidad.getNombre(),
                entidad.getContacto(),
                entidad.getTelefono(),
                entidad.getDireccion(),
                entidad.getEmail(),
                entidad.getEstado()
        );
    }
}
