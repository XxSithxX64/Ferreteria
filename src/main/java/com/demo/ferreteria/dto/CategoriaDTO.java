package com.demo.ferreteria.dto;

public record CategoriaDTO (
        Long id,
        String nombre,
        String descripcion,
        Boolean estado
){}
