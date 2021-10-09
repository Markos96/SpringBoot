package com.example.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.models.Usuario;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;


@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);

        entityManager.remove(usuario);
        
    }

    @Override
    public void registrar(Usuario usuario) {
     
        entityManager.merge(usuario);
        
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario){
 
        String query = "FROM Usuario WHERE email= :email ";
        List<Usuario>lista = (List<Usuario>) entityManager.createQuery(query)
        .setParameter("email",usuario.getEmail())
        .getResultList();


        // Verifico que la lista no este vacia
        if(lista.isEmpty()){
            return null;
        }

        // Traigo la contraseña de la base de datos
        String passwordBdd = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);

        // Verifico mediante Argon2 si la contraseña de la base de datos es igual a la contraseña ingresada.
        if(argon2.verify(passwordBdd, usuario.getPassword())){
            return lista.get(0);
        }
        
        return null;
    }

    
}
