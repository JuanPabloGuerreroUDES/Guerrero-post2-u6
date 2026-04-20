package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.util.Map;
import java.util.LinkedHashMap;

import java.io.IOException;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoService service = new ProductoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarSesion(req, resp)) return;


        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listar(req, resp);
                break;
            case "formulario":
                mostrarFormulario(req, resp);
                break;
            case "editar":
                mostrarEdicion(req, resp);
                break;
            case "eliminar":
                eliminar(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarSesion(req, resp)) return;


        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            guardar(req, resp);
        } else if ("actualizar".equals(accion)) {
            actualizar(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // --- Métodos privados ---



    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("productos", service.obtenerTodos());

        String mensaje = req.getParameter("mensaje");
        if (mensaje != null) {
            req.setAttribute("mensaje", mensaje);
        }

        forward(req, resp, "/WEB-INF/views/lista.jsp");
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        forward(req, resp, "/WEB-INF/views/formulario.jsp");
    }

    private void mostrarEdicion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Producto producto = service.obtenerPorId(id);

            if (producto == null) {
                resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+no+encontrado");
                return;
            }

            req.setAttribute("producto", producto);
            forward(req, resp, "/WEB-INF/views/formulario.jsp");

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/productos?mensaje=ID+inválido");
        }
    }

    private void guardar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // --- Parámetros ---
        String nombre = req.getParameter("nombre");
        String precioStr = req.getParameter("precio");
        String stockStr = req.getParameter("stock");
        String categoria = req.getParameter("categoria");

        // --- Validaciones ---
        Map<String, String> errores = new LinkedHashMap<>();

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre del producto es obligatorio.");
        } else if (nombre.trim().length() > 100) {
            errores.put("nombre", "El nombre no debe superar los 100 caracteres.");
        }

        // Validar precio
        double precio = 0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                errores.put("precio", "El precio no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.put("precio", "El precio debe ser un número válido (ej: 19.99).");
        }

        // Validar stock
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                errores.put("stock", "El stock no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.put("stock", "El stock debe ser un número entero.");
        }

        // --- Si hay errores ---
        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("nombre", nombre);
            req.setAttribute("precio", precioStr);
            req.setAttribute("stock", stockStr);
            req.setAttribute("categoria", categoria);

            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        // --- Guardar producto ---
        Producto producto = new Producto(0, nombre.trim(), categoria, precio, stock);
        service.guardar(producto);

        // --- Redirección (PRG) ---
        resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+guardado");
    }

    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Producto p = extraerProducto(req, id);

            service.actualizar(p);

            resp.sendRedirect(req.getContextPath() +
                    "/productos?mensaje=Producto+actualizado");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() +
                    "/productos?mensaje=Error+al+actualizar");
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            service.eliminar(id);

            resp.sendRedirect(req.getContextPath() +
                    "/productos?mensaje=Producto+eliminado");

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() +
                    "/productos?mensaje=ID+inválido");
        }
    }

    private Producto extraerProducto(HttpServletRequest req, int id) {

        String nombre = req.getParameter("nombre");
        String categoria = req.getParameter("categoria");

        double precio = Double.parseDouble(req.getParameter("precio"));
        int stock = Integer.parseInt(req.getParameter("stock"));

        return new Producto(id, nombre, categoria, precio, stock);
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {

        req.getRequestDispatcher(path).forward(req, resp);
    }

    private boolean verificarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuarioActual") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}