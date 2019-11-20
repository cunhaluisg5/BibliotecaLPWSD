/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class AssuntoListBean extends ProcessReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private Assunto assunto;
    private List assuntos;
    private List assuntosSelecionados;
    private List assuntosFiltrados;
    private Integer id;
    private final DAO<Assunto> assuntoDao;

    //construtor
    public AssuntoListBean() {
        assuntoDao = new DAO<Assunto>();
        assuntos = assuntoDao.buscarTodas(Assunto.class);
        assunto = new Assunto();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(assuntoDao.persistir(assunto));
        assuntos = assuntoDao.buscarTodas(Assunto.class);
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a : assuntosSelecionados) {
            Mensagem.msgScreen(assuntoDao.remover((Assunto) a));
        }
        assuntos = assuntoDao.buscarTodas(Assunto.class);
    }

    public void novo(ActionEvent actionEvent) {
        assunto = new Assunto();
    }

    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        assuntosSelecionados.add(assuntoDao.buscar(Assunto.class, id));
    }

    //getters and setters
    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public List getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List assuntos) {
        this.assuntos = assuntos;
    }

    public List getAssuntosSelecionados() {
        return assuntosSelecionados;
    }

    public void setAssuntosSelecionados(List assuntosSelecionados) {
        this.assuntosSelecionados = assuntosSelecionados;
    }

    public List getAssuntosFiltrados() {
        return assuntosFiltrados;
    }

    public void setAssuntosFiltrados(List assuntosFiltrados) {
        this.assuntosFiltrados = assuntosFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
