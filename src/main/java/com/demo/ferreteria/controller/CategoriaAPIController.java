package com.demo.ferreteria.controller;

import com.demo.ferreteria.service.intefaces.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categorias")
@RequiredArgsConstructor
public class CategoriaAPIController {
    private final ICategoriaService categoriaService;


}
