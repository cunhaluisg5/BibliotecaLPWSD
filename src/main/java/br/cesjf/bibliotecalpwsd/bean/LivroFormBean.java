/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
import br.cesjf.bibliotecalpwsd.model.Autor;
import br.cesjf.bibliotecalpwsd.model.Editora;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class LivroFormBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Livro livro;
    private List assuntos;
    private List autores;
    private List<Editora> editoras;
    private int id;
    private UploadedFile uploadedFile;
    private final String diretorio;
    private final DAO<Assunto> assuntoDao;
    private final DAO<Autor> autorDao;
    private final DAO<Editora> editoraDao;
    private final DAO<Livro> livroDao;

    //construtor
    public LivroFormBean() {
        assuntoDao = new DAO<Assunto>();
        autorDao = new DAO<Autor>();
        editoraDao = new DAO<Editora>();
        livroDao = new DAO<Livro>();

        assuntos = assuntoDao.buscarTodas(Assunto.class);
        autores = autorDao.buscarTodas(Autor.class);
        editoras = editoraDao.buscarTodas(Editora.class);

        ExternalContext e = FacesContext.getCurrentInstance().getExternalContext();
        diretorio = e.getRealPath("resources\\arquivos");
    }

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            livro = (Livro) livroDao.buscar(Livro.class, id);
        } else {
            livro = new Livro();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        upload();
        Mensagem.msgScreen(livroDao.persistir(livro));
    }

    public void exclude(ActionEvent actionEvent) {
        delete(1);
        delete(2);
        Mensagem.msgScreen(livroDao.remover(livro));
    }

    //getters and setters
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getAssuntos() {
        Collections.sort(assuntos);
        return assuntos;
    }

    public void setAssuntos(List assuntos) {
        this.assuntos = assuntos;
    }

    public List getAutores() {
        Collections.sort(autores);
        return autores;
    }

    public void setAutores(List autores) {
        this.autores = autores;
    }

    public List getEditoras() {
        Collections.sort(editoras);
        return editoras;
    }

    public void setEditoras(List editoras) {
        this.editoras = editoras;
    }

    public String getCapa() {
        return diretorio + "\\" + livro.getArquivo();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
        upload();
    }

    public void clear() {
        livro = new Livro();
    }

    public boolean isNew() {
        return livro == null || livro.getId() == null || livro.getId() == 0;
    }

    public void upload() {

        if (uploadedFile != null) {

            File dir = new File(diretorio);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                String name = new Timestamp(System.currentTimeMillis()).toString();
                name = name.replace("-", "").replace(".", "").replace(":", "").replace(" ", "");
                name = name + uploadedFile.getFileName();
                File file = new File(dir, name);
                OutputStream out = new FileOutputStream(file);
                out.write(uploadedFile.getContents());
                out.close();
                Mensagem.msgScreen("O arquivo " + uploadedFile.getFileName() + " foi salvo!");
                if (uploadedFile.getFileName().toUpperCase().contains(".PDF")) {
                    livro.setArquivo(name);
                } else {
                    livro.setCapa(name);
                }
                uploadedFile = null;
            } catch (IOException e) {
                Mensagem.msgScreen("Não foi possível salvar o arquivo " + uploadedFile.getFileName() + "!" + e);
            }
        }
    }

    public void delete(int i) {

        File file;
        if (i == 1 && livro.getCapa() != null) {
            file = new File(diretorio + "\\" + livro.getCapa());
            file.delete();
        } else if (i == 2 && livro.getArquivo() != null) {
            file = new File(diretorio + "\\" + livro.getArquivo());
            file.delete();
        }

        Mensagem.msgScreen("Arquivo apagado com sucesso");

        if (i == 1) {
            livro.setCapa(null);
        } else {
            livro.setArquivo(null);
        }

        livroDao.persistir(livro);

    }
}
