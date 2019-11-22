/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.Date;
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
public class EmprestimoListBean extends ProcessReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private Emprestimo emprestimo;
    private List emprestimos;
    private List emprestimosSelecionados;
    private List emprestimosFiltrados;
    private Integer id;
    private final DAO<Emprestimo> emprestimoDao;

    //construtor
    public EmprestimoListBean() {
        emprestimoDao = new DAO<Emprestimo>();
        emprestimos = emprestimoDao.buscarTodas(Emprestimo.class);
        emprestimo = new Emprestimo();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(emprestimoDao.persistir(emprestimo));
        emprestimos = emprestimoDao.buscarTodas(Emprestimo.class);
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a : emprestimosSelecionados) {
            Mensagem.msgScreen(emprestimoDao.remover((Emprestimo) a));
        }
        emprestimos = emprestimoDao.buscarTodas(Emprestimo.class);
    }

    public void novo(ActionEvent actionEvent) {
        emprestimo = new Emprestimo();
    }

    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        emprestimosSelecionados.add(emprestimoDao.buscar(Emprestimo.class, id));
    }

    //getters and setters
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public List getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List emprestimos) {
        this.emprestimos = emprestimos;
    }

    public List getEmprestimosSelecionados() {
        return emprestimosSelecionados;
    }

    public void setEmprestimosSelecionados(List emprestimosSelecionados) {
        this.emprestimosSelecionados = emprestimosSelecionados;
    }

    public List getEmprestimosFiltrados() {
        return emprestimosFiltrados;
    }

    public void setEmprestimosFiltrados(List emprestimosFiltrados) {
        this.emprestimosFiltrados = emprestimosFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void efetuaDevolucao() {
        emprestimo.setDataDevolucao(new Date());
        emprestimoDao.persistir(emprestimo);
        Mensagem.msgScreen("Devolução efetuada com sucesso!");
    }
}
