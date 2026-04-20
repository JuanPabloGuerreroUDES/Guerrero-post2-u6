package com.universidad.mvc.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/idioma")
public class IdiomaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String lang = req.getParameter("lang");

        // Validar idioma permitido
        if (lang != null && (lang.equals("es") || lang.equals("en"))) {
            Locale locale = new Locale(lang);

            // Guardar en sesión
            HttpSession session = req.getSession(true);
            session.setAttribute("locale", locale);
        }

        // Redirigir a la página anterior
        String referer = req.getHeader("Referer");

        if (referer != null) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/productos");
        }
    }
}