package com.demo.ferreteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarca;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    @Column(nullable = false)
    private Boolean estado; // true = activa, false = inactiva

    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY)
    private List<Producto> productos; // relación con productos
}
