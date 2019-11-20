/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Editora;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class EditoraFormBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Editora editora;
    private int id;
    private final DAO<Editora> editoraDao;

    //construtor
    public EditoraFormBean() {
        editoraDao = new DAO<Editora>();
    }

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            editora = (Editora) editoraDao.buscar(Editora.class, id);
        } else {
            editora = new Editora();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(editoraDao.persistir(editora));
    }

    public void exclude(ActionEvent actionEvent) {
        Mensagem.msgScreen(editoraDao.remover(editora));
    }

    //getters and setters
    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void clear() {
        editora = new Editora();
    }

    public boolean isNew() {
        return editora == null || editora.getId() == null || editora.getId() == 0;
    }
}
