package com.universidad.mvc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoDAO {

    // Lista en memoria (simulación de base de datos)
    private static final List<Producto> lista = new ArrayList<>();
    private static int contador = 3;

    // Bloque estático para datos iniciales
    static {
        lista.add(new Producto(1, "Laptop Pro 15", "Computadoras", 1299.99, 10));
        lista.add(new Producto(2, "Monitor 27 4K", "Monitores", 549.00, 25));
        lista.add(new Producto(3, "Teclado Mecánico", "Periféricos", 89.99, 50));
    }

    // Obtener todos los productos
    public List<Producto> findAll() {
        return Collections.unmodifiableList(lista);
    }

    // Buscar por ID
    public Producto findById(int id) {
        return lista.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Guardar nuevo producto
    public void save(Producto p) {
        p.setId(++contador);
        lista.add(p);
    }

    // Actualizar producto existente
    public void update(Producto p) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == p.getId()) {
                lista.set(i, p);
                return;
            }
        }
    }

    // Eliminar producto por ID
    public void delete(int id) {
        lista.removeIf(p -> p.getId() == id);
    }
}