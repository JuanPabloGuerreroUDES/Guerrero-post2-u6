package com.universidad.mvc.controller;

import com.universidad.mvc.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Usuarios en memoria (simulación de BD)
    private static final Map<String, String> CREDS = Map.of(
            "admin", "Admin123!",
            "viewer", "View456!"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {



        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (credencialesValidas(username, password)) {

            HttpSession session = req.getSession(true);

            String rol = "admin".equals(username) ? "ADMIN" : "VIEWER";

            Usuario usuario = new Usuario(
                    username,
                    username + "@universidad.edu",
                    rol
            );

            session.setAttribute("usuarioActual", usuario);

            // Tiempo de sesión: 30 minutos
            session.setMaxInactiveInterval(1800);

            resp.sendRedirect(req.getContextPath() + "/productos");

        } else {
            req.setAttribute("errorLogin",
                    "Credenciales incorrectas. Intente de nuevo.");

            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);
        }
    }

    // --- Método auxiliar ---
    private boolean credencialesValidas(String username, String password) {
        return username != null
                && password != null
                && CREDS.containsKey(username)
                && CREDS.get(username).equals(password);
    }
}