/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author dmeireles
 */
public class UsuarioDAO implements Serializable {

    public static UsuarioDAO usuarioDAO;

    public UsuarioDAO getInstance() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    public Usuario buscarUsuario(String u) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario");
            query.setParameter("usuario", u);
            Usuario usuario = (Usuario) query.getSingleResult();
            if (usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não encontrado!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados usuarios!", e.getMessage());
            return null;
        }
    }
}
