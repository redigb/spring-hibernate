package com.cursojava.cursoatl.controllers;

import com.cursojava.cursoatl.dao.UsuarioDao;
import com.cursojava.cursoatl.models.Usuario;
import com.cursojava.cursoatl.utils.JwtUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    // INYECION DE DE DEPENDENCIAS
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        if (usuarioId == null)
            return false;
        return true;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuario(@RequestHeader(value = "Authorization") String token){
        if (!validarToken(token))
            return null;
        return usuarioDao.getUsuario();
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    // RequestBody nose sirve para tranformar en json la solicitud dada
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        // Mientras mas interaciones mas segura la contraseña -> Depende de la seguridad que nesesites
        // i2 -> paralelismo
        String hash = argon2.hash(1,1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }


    @RequestMapping(value = "api/usuario/{id}" , method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("Vargas");
        usuario.setEmail("Lucasfelor@aw");
        usuario.setTelefono("98536355");
        return usuario;
    }

    @RequestMapping(value = "usuarioa")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Vargas");
        usuario.setEmail("Lucasfelor@aw");
        usuario.setTelefono("98536355");
        return usuario;
    }

    // Agregar el metodo solo especifica que se cumpla
    // el orden de entrada, no la funcion
    @RequestMapping(value = "api/usuario/{id}" , method = RequestMethod.DELETE)
    public Usuario eliminar(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){
        if (!validarToken(token))
            return null;
        usuarioDao.eliminar(id);
        return null;
    }

    @RequestMapping(value = "usuarioz")
    public Usuario buscar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Vargas");
        usuario.setEmail("Lucasfelor@aw");
        usuario.setTelefono("98536355");
        return usuario;
    }

}
