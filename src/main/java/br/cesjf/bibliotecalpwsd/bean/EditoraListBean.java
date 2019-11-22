/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Editora;
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
public class EditoraListBean extends ProcessReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private Editora editora;
    private List editoras;
    private List editorasSelecionados;
    private List editorasFiltrados;
    private Integer id;
    private final DAO<Editora> editoraDao;

    //construtor
    public EditoraListBean() {
        editoraDao = new DAO<Editora>();
        editoras = editoraDao.buscarTodas(Editora.class);
        editora = new Editora();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(editoraDao.persistir(editora));
        editoras = editoraDao.buscarTodas(Editora.class);
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a : editorasSelecionados) {
            Mensagem.msgScreen(editoraDao.remover((Editora) a));
        }
        editoras = editoraDao.buscarTodas(Editora.class);
    }

    public void novo(ActionEvent actionEvent) {
        editora = new Editora();
    }

    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        editorasSelecionados.add(editoraDao.buscar(Editora.class, id));
    }

    //getters and setters
    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List getEditoras() {
        return editoras;
    }

    public void setEditoras(List editoras) {
        this.editoras = editoras;
    }

    public List getEditorasSelecionados() {
        return editorasSelecionados;
    }

    public void setEditorasSelecionados(List editorasSelecionados) {
        this.editorasSelecionados = editorasSelecionados;
    }

    public List getEditorasFiltrados() {
        return editorasFiltrados;
    }

    public void setEditorasFiltrados(List editorasFiltrados) {
        this.editorasFiltrados = editorasFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
