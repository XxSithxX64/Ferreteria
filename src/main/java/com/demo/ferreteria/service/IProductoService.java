package com.demo.ferreteria.service;

import com.demo.ferreteria.model.Categoria;
import com.demo.ferreteria.model.Producto;
import com.demo.ferreteria.model.Proveedor;

import java.time.LocalDate;
import java.util.List;


public interface IProductoService extends IGenericService<Producto,Long>{

    // ==========================
    // 🔍 Búsquedas básicas
    // ==========================
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarPorCategoria(Categoria categoria);
    List<Producto> buscarPorProveedor(Proveedor proveedor);
    List<Producto> buscarPorPrecioBetween(Double min, Double max);
    List<Producto> buscarPorEstado(Boolean activo); // reemplaza productosActivos y productosDescontinuados

    // ==========================
    // 📊 Consultas agregadas
    // ==========================
    Long contarPorCategoria(Categoria categoria);
    Double promedioPrecioPorCategoria(Categoria categoria);

    // ==========================
    // 🔄 Gestión de stock
    // ==========================
    void actualizarStock(Long productoId, Integer nuevoStock); // actualización directa
    void registrarEntradaStock(Long productoId, Integer cantidad); // entrada
    void registrarSalidaStock(Long productoId, Integer cantidad); // salida
    List<Producto> productosConStockCritico(Integer umbral);

    // ==========================
    // 💼 Casos de negocio
    // ==========================
    List<Producto> buscarProductosConDescuento();
    List<Producto> buscarProductosRecientes(LocalDate fechaDesde);

    // ==========================
    // 📈 Analítica y reportes
    // ==========================
    List<Producto> topProductosPorVentas(int limite); // reemplaza buscarProductosMasVendidos
    List<Producto> productosSinVentasDesde(LocalDate fecha);

    // ==========================
    // 💰 Gestión de precios
    // ==========================
    void aplicarDescuento(Long productoId, Double porcentaje);
    void actualizarPrecioPorCategoria(Categoria categoria, Double porcentaje);

    // ==========================
    // 🌐 Integración / Exportación
    // ==========================
    void exportarProductosCSV(String rutaArchivo);
    void importarProductosCSV(String rutaArchivo);
}
