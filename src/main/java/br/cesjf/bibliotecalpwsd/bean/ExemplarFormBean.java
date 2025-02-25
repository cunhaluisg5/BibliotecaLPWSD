/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
public class ExemplarFormBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Exemplar exemplar;
    private int id;
    private List livros;
    private final DAO<Exemplar> exemplarDao;
    private final DAO<Livro> livroDao;

    //construtor
    public ExemplarFormBean() {
        exemplarDao = new DAO<>();
        livroDao = new DAO<>();
        livros = livroDao.buscarTodas(Livro.class);
    }

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            exemplar = (Exemplar) exemplarDao.buscar(Exemplar.class, id);
        } else {
            exemplar = new Exemplar();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(exemplarDao.persistir(exemplar));
    }

    public void exclude(ActionEvent actionEvent) {
        Mensagem.msgScreen(exemplarDao.remover(exemplar));
    }

    //getters and setters
    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getLivros() {
        Collections.sort(livros);
        return livros;
    }

    public void setLivros(List livros) {
        this.livros = livros;
    }

    public void clear() {
        exemplar = new Exemplar();
    }

    public boolean isNew() {
        return exemplar == null || exemplar.getId() == null || exemplar.getId() == 0;
    }
}
