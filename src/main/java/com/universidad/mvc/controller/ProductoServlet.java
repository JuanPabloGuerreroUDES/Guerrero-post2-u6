package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoService service = new ProductoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
            throws IOException {

        try {
            Producto p = extraerProducto(req, 0);
            service.guardar(p);

            resp.sendRedirect(req.getContextPath() +
                    "/productos?mensaje=Producto+guardado+exitosamente");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() +
                    "/productos?mensaje=Error+al+guardar");
        }
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
}