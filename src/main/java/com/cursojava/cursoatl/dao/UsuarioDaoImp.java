package com.cursojava.cursoatl.dao;

import com.cursojava.cursoatl.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository // Acceso a la base de datos
@Transactional //Consultas SQL - a la base de datos
public class UsuarioDaoImp implements UsuarioDao {

    // Conexion a la base de datos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuario() {
        // Consulta HIBERNATE
        String query = "FROM Usuario"; // -> Nombre de la clase de models correctamento escrito
        return entityManager.createQuery(query, Usuario.class).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioCredenciales(Usuario usuario) {
        // Consulta HIBERNATE
        /* No concadenar para evitar inyecion de independecia*/
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> lista = entityManager.createQuery(query, Usuario.class)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        // Validacion de contraseña hasheada
        String passworHash = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //LA contraseña de la base de datos y la contraseña que se le pasa
        if (lista.isEmpty())
            return null;

        if(argon2.verify(passworHash, usuario.getPassword()))
            return lista.get(0);

        return null;
    }
}
