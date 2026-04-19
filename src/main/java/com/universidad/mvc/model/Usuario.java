package com.universidad.mvc.model;

public class Usuario {

    // Atributos
    private String username;
    private String email;
    private String rol; // "ADMIN" o "VIEWER"

    // Constructor
    public Usuario(String username, String email, String rol) {
        this.username = username;
        this.email = email;
        this.rol = rol;
    }

    // --- Getters ---

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}