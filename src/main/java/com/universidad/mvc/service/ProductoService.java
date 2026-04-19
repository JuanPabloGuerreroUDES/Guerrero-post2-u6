package com.universidad.mvc.service;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.model.ProductoDAO;

import java.util.List;

public class ProductoService {

    private final ProductoDAO dao = new ProductoDAO();

    // Obtener todos los productos
    public List<Producto> obtenerTodos() {
        return dao.findAll();
    }

    // Obtener producto por ID
    public Producto obtenerPorId(int id) {
        return dao.findById(id);
    }

    // Guardar nuevo producto
    public void guardar(Producto p) {
        validarProducto(p);
        dao.save(p);
    }

    // Actualizar producto existente
    public void actualizar(Producto p) {
        if (dao.findById(p.getId()) == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        validarProducto(p);
        dao.update(p);
    }

    // Eliminar producto
    public void eliminar(int id) {
        dao.delete(id);
    }

    // --- Validaciones centralizadas ---
    private void validarProducto(Producto p) {
        if (p == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (p.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (p.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (p.getCategoria() == null || p.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria.");
        }
    }
}