package com.cursojava.cursoatl.controllers;

import com.cursojava.cursoatl.dao.UsuarioDao;
import com.cursojava.cursoatl.models.Usuario;
import com.cursojava.cursoatl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioLog = usuarioDao.obtenerUsuarioCredenciales(usuario);
       if (usuarioLog != null) {
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLog.getId()), usuarioLog.getEmail());
           return tokenJwt;
       }
        return "Fallo";
    }
}
