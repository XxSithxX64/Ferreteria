package com.demo.ferreteria.dto;

public record ProveedorDTO (Long id,
                            String nombre,
                            String contacto,
                            String telefono,
                            String direccion,
                            String email,
                            Boolean estado
){}
