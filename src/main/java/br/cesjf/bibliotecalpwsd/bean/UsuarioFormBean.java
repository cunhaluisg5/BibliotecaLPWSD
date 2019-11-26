/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.EUsuarios;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
public class UsuarioFormBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private int id;
    Map<String, String> tipos;
    private final DAO<Usuario> usuarioDao;

    //construtor
    public UsuarioFormBean() {
        //1 - Aluno, 2 - Professor, 3 - Funcionário, 4 - Bibliotecário e 5 - Administrador
        tipos = new HashMap<>();
        tipos.put(EUsuarios.Aluno.toString(), "1");
        tipos.put(EUsuarios.Professor.toString(), "2");
        tipos.put(EUsuarios.Funcionário.toString(), "3");
        tipos.put(EUsuarios.Bibliotecário.toString(), "4");
        tipos.put(EUsuarios.Administrador.toString(), "5");
        usuarioDao = new DAO<>();
    }

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            usuario = (Usuario) usuarioDao.buscar(Usuario.class, id);
        } else {
            usuario = new Usuario();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(usuarioDao.persistir(usuario));
    }

    public void exclude(ActionEvent actionEvent) {
        Mensagem.msgScreen(usuarioDao.remover(usuario));
    }

    //getters and setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void clear() {
        usuario = new Usuario();
    }

    public boolean isNew() {
        return usuario == null || usuario.getId() == null || usuario.getId() == 0;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public void setTipos(Map<String, String> tipos) {
        this.tipos = tipos;
    }
}
