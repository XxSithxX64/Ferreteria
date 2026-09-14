package com.demo.ferreteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100, unique = true)
    private String username; // nombre de usuario para login

    @Column(nullable = false)
    private String password; // encriptado con BCrypt

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol; // ADMIN, EMPLEADO, etc.

    @Column(nullable = false)
    private Boolean estado; // activo/inactivo

    // Auditoría opcional
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
