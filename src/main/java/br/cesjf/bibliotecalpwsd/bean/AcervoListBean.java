/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class AcervoListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Livro> livros;
    private Integer id;
    private String titulo;
    private final DAO<Livro> livroDao;

    //construtor
    public AcervoListBean() {
        livroDao = new DAO<>();
        livros = livroDao.buscarTodas(Livro.class);
    }

    //getters and setters
    public List getLivros() {
        return livros;
    }

    public void setLivros(List livros) {
        this.livros = livros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        livros = new ArrayList<>();
        Livro l = (Livro) livroDao.buscar(Livro.class, id);
        if (l != null) {
            livros.add(l);
        }
        if (livros.isEmpty()) {
            Mensagem.msgScreen("Não foram encontrados livros");
            livros = livroDao.buscarTodas(Livro.class);
        }
        this.id = null;
        this.titulo = null;
    }

    public void buscarPorTitulo(String titulo) {
        if (titulo.equals("")) {
            throw new BusinessException("Insira um Título");
        }
        livros = new DAO().buscarTitulo(titulo);

        if (livros.isEmpty()) {
            Mensagem.msgScreen("Não foram encontrados livros");
            livros = livroDao.buscarTodas(Livro.class);
        }
        this.id = null;
        this.titulo = null;
    }

    public void limpar() {
        livros = new DAO().buscarTitulo(titulo);
        this.id = null;
        this.titulo = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
