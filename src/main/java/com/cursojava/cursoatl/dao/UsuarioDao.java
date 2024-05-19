package com.cursojava.cursoatl.dao;

import com.cursojava.cursoatl.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    // Indica funciones
    List<Usuario> getUsuario();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario obtenerUsuarioCredenciales(Usuario usuario);
}
