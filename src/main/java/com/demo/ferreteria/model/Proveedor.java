package com.demo.ferreteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El contacto es obligatorio")
    private String contacto; // persona de contacto

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El Email es obligatorio")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado; // activo/inactivo

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<Producto> productos; // relación con productos
}
