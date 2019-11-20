/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.IEntidade;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 * @param <E>
 */
public class DAO<E extends IEntidade> implements IDAO<E> {

    private static final EntityManager manager = PersistenceUtil.getEntityManager();

    @Override
    public E buscar(Class<E> classe, int id) {
        try {
            Query query = manager.createQuery("SELECT e FROM " + classe.getName() + " e WHERE e.id = :id");
            query.setParameter("id", id);
            E entidade = (E) query.getSingleResult();
            if (entidade != null && entidade.getId() > 0) {
                return entidade;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não encontrado!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não encontrado!", e.getMessage());
            return null;
        }
    }

    @Override
    public E buscar(Class<E> classe, E entidade) {
        try {
            Query query = manager.createQuery("SELECT e FROM " + classe.getName() + " e WHERE e.id = :id");
            query.setParameter("id", entidade.getId());
            E en = (E) query.getSingleResult();
            if (en != null && en.getId() > 0) {
                return en;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Não encontrado!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não encontrado!", e.getMessage());
            return null;
        }
    }

    @Override
    public String persistir(E entidade) {
        try {
            manager.getTransaction().begin();
            entidade = manager.merge(entidade);
            manager.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Salvo com sucesso!");
            return "Salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar!", e);
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar!";
            }
            return "Não foi possível salvar!";
        }
    }

    @Override
    public String remover(E entidade) {
        try {
            manager.getTransaction().begin();
            entidade = manager.merge(entidade);
            manager.remove(entidade);
            manager.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Removido com sucesso!");
            return "Removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover!", e.getMessage());
            return "Não foi possível remover!";
        }
    }

    @Override
    public List<E> buscarTodas(Class<E> classe) {
        try {
            Query query = manager.createQuery("SELECT e FROM " + classe.getName() + " e");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.WARNING, "Registros não encontrados!", e.getMessage());
            return new ArrayList<>();
        }
    }
}
