/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class EmprestimoFormBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Emprestimo emprestimo;
    private List<Exemplar> exemplaresPermitidos;
    private List<Usuario> usuariosPermitidos;
    private List<Usuario> usuarios;
    private Livro livro;
    private List<Livro> livros;
    private int id;
    private final DAO<Usuario> usuarioDao;
    private final DAO<Emprestimo> emprestimoDao;
    private final DAO<Exemplar> exemplarDao;
    private final DAO<Livro> livroDao;
    private final DAO<Reserva> reservaDao;

    //construtor
    public EmprestimoFormBean() {
        usuarioDao = new DAO<Usuario>();
        emprestimoDao = new DAO<Emprestimo>();
        exemplarDao = new DAO<Exemplar>();
        livroDao = new DAO<Livro>();
        reservaDao = new DAO<Reserva>();
    }

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            emprestimo = (Emprestimo) emprestimoDao.buscar(Emprestimo.class, id);
        } else {
            emprestimo = new Emprestimo();
        }
        livros = livroDao.buscarTodas(Livro.class);
        usuarios = usuarioDao.buscarTodas(Usuario.class);
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        emprestimo.calculaDevolucaoPrevista();
        Mensagem.msgScreen(emprestimoDao.persistir(emprestimo));
    }

    public void exclude(ActionEvent actionEvent) {
        Mensagem.msgScreen(emprestimoDao.remover(emprestimo));
    }

    //getters and setters
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Exemplar> getExemplaresPermitidos() {
        return exemplaresPermitidos;
    }

    public void setExemplaresPermitidos(List<Exemplar> exemplaresPermitidos) {
        this.exemplaresPermitidos = exemplaresPermitidos;
    }

    public List<Usuario> getUsuariosPermitidos() {
        usuariosPermitidos();
        return usuariosPermitidos;
    }

    public void setUsuariosPermitidos(List<Usuario> usuariosPermitidos) {
        this.usuariosPermitidos = usuariosPermitidos;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void clear() {
        emprestimo = new Emprestimo();
    }

    public boolean isNew() {
        return emprestimo == null || emprestimo.getId() == null || emprestimo.getId() == 0;
    }

    private void usuariosPermitidos() {
        usuariosPermitidos = new ArrayList<>();
        for (Usuario u : usuarios) {
            List<Emprestimo> emp = new ArrayList<>();
            for (Emprestimo e : u.getEmprestimoList()) {
                if (e.getDataDevolucao() == null) {
                    emp.add(e);
                }
            }
            if (u.getTipoTexto().equals("Aluno") && emp.size() < 3) {
                usuariosPermitidos.add(u);
            } else if (!u.getTipoTexto().equals("Aluno") && emp.size() < 5) {
                usuariosPermitidos.add(u);
            }
        }
    }

    public void verificaUsuario(SelectEvent event) {
        usuariosPermitidos();
        if (!usuariosPermitidos.contains(emprestimo.getIdUsuario())) {
            Mensagem.msgScreen("Não permitido. Usuário com alguma pendência.");
        }
    }

    public void calcularExemplaresPermitidos(SelectEvent event) {
        List<Exemplar> exemplares = exemplarDao.buscarTodas(Exemplar.class);
        List<Reserva> reservas = reservaDao.buscarTodas(Reserva.class);
        exemplaresPermitidos = new ArrayList<>();
        Date dataReserva = emprestimo.getDataEmprestimo();
        if (dataReserva != null) {
            if (livro == null) {
                exemplaresPermitidos.addAll(exemplares);
            } else {
                for (Exemplar e : exemplares) {
                    if (e.getIdLivro().getId() == livro.getId()) {
                        exemplaresPermitidos.add(e);
                    }
                }
            }
            List<Exemplar> lista = new ArrayList<>();
            lista.addAll(exemplaresPermitidos);
            List<Emprestimo> em = emprestimoDao.buscarTodas(Emprestimo.class);
            for (Exemplar e : lista) {
                for (Emprestimo emp : em) {
                    if (emp.getIdExemplar().getId() == e.getId()) {
                        if (emp.getDataDevolucao() == null && emp.getDataEmprestimo().compareTo(dataReserva) <= 0 && emp.getDataDevolucaoPrevista().compareTo(dataReserva) >= 0) {
                            exemplaresPermitidos.remove(e);
                            continue;
                        }
                        if (emp.getDataDevolucao() != null && emp.getDataEmprestimo().compareTo(dataReserva) <= 0 && emp.getDataDevolucao().compareTo(dataReserva) >= 0) {
                            exemplaresPermitidos.remove(e);
                            continue;
                        }
                    }
                }
                List<Reserva> re = reservaDao.buscarTodas(Reserva.class);
                for (Reserva r : re) {
                    if (r.getIdExemplar().getId() == e.getId()) {
                        if (!r.getCancelada() && r.getDataReserva().compareTo(dataReserva) <= 0 && r.getDataDevolucaoPrevista().compareTo(dataReserva) >= 0) {
                            exemplaresPermitidos.remove(e);
                        }
                    }
                }
            }
        }
        if (emprestimo.getIdExemplar() != null) {
            exemplaresPermitidos.add(emprestimo.getIdExemplar());
        }
    }
}
