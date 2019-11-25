/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
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
public class ExemplarListBean extends ProcessReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private Exemplar exemplar;
    private List exemplares;
    private List exemplaresSelecionados;
    private List exemplaresFiltrados;
    private Integer id;
    private final DAO<Exemplar> exemplarDao;

    //construtor
    public ExemplarListBean() {
        exemplarDao = new DAO<>();
        exemplares = exemplarDao.buscarTodas(Exemplar.class);
        exemplar = new Exemplar();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(exemplarDao.persistir(exemplar));
        exemplares = exemplarDao.buscarTodas(Exemplar.class);
    }

    public void exclude(ActionEvent actionEvent) {
        exemplaresSelecionados.forEach((a) -> {
            Mensagem.msgScreen(exemplarDao.remover((Exemplar) a));
        });
        exemplares = exemplarDao.buscarTodas(Exemplar.class);
    }

    public void novo(ActionEvent actionEvent) {
        exemplar = new Exemplar();
    }

    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        exemplaresSelecionados.add(exemplarDao.buscar(Exemplar.class, id));
    }

    //getters and setters
    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public List getExemplares() {
        return exemplares;
    }

    public void setExemplares(List exemplares) {
        this.exemplares = exemplares;
    }

    public List getExemplaresSelecionados() {
        return exemplaresSelecionados;
    }

    public void setExemplaresSelecionados(List exemplaresSelecionados) {
        this.exemplaresSelecionados = exemplaresSelecionados;
    }

    public List getExemplaresFiltrados() {
        return exemplaresFiltrados;
    }

    public void setExemplaresFiltrados(List exemplaresFiltrados) {
        this.exemplaresFiltrados = exemplaresFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
