/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import br.cesjf.bibliotecalpwsd.util.Mensagem;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.Calendar;
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
public class ReservaListBean extends ProcessReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private Reserva reserva;
    private List<Reserva> reservas;
    private List reservasSelecionados;
    private List reservasFiltrados;
    private Integer id;
    private final DAO<Reserva> reservaDao;

    //construtor
    public ReservaListBean() {
        reservaDao = new DAO<Reserva>();
        reservas = reservaDao.buscarTodas(Reserva.class);
        reserva = new Reserva();
        Calendar c = Calendar.getInstance();
        for (Reserva r : reservas) {
            if (!r.getCancelada()) {
                if (r.getIdEmprestimo() == null) {
                    c.setTime(r.getDataReserva());
                    c.add(Calendar.DAY_OF_MONTH, 10);
                    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        c.add(Calendar.DAY_OF_MONTH, 2);
                    } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        c.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    if (c.getTime().compareTo(new Date()) < 0) {
                        r.setCancelada(Boolean.TRUE);
                        r.setObsCancelamento("Sistema");
                        reservaDao.persistir(r);
                    }
                }
            }
        }
        reservas = reservaDao.buscarTodas(Reserva.class);
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        Mensagem.msgScreen(reservaDao.persistir(reserva));
        reservas = reservaDao.buscarTodas(Reserva.class);
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a : reservasSelecionados) {
            Mensagem.msgScreen(reservaDao.remover((Reserva) a));
        }
        reservas = reservaDao.buscarTodas(Reserva.class);
    }

    public void novo(ActionEvent actionEvent) {
        reserva = new Reserva();
    }

    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        reservasSelecionados.add(reservaDao.buscar(Reserva.class, id));
    }

    //getters and setters
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public List getReservas() {
        return reservas;
    }

    public void setReservas(List reservas) {
        this.reservas = reservas;
    }

    public List getReservasSelecionados() {
        return reservasSelecionados;
    }

    public void setReservasSelecionados(List reservasSelecionados) {
        this.reservasSelecionados = reservasSelecionados;
    }

    public List getReservasFiltrados() {
        return reservasFiltrados;
    }

    public void setReservasFiltrados(List reservasFiltrados) {
        this.reservasFiltrados = reservasFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getData(Date reserva) {
        if (reserva.compareTo(new Date()) <= 0) {
            return true;
        }
        return false;
    }

    public void geraEmprestimo(ActionEvent actionEvent) {
        Emprestimo emp = new Emprestimo();
        emp.setIdExemplar(reserva.getIdExemplar());
        emp.setIdUsuario(reserva.getIdUsuario());
        emp.setDataEmprestimo(new Date());
        emp.calculaDevolucaoPrevista();
        reserva.setIdEmprestimo(emp);
        Mensagem.msgScreen(reservaDao.persistir(reserva));
        reservas = reservaDao.buscarTodas(Reserva.class);
    }

    public void efetuaCancelamento() {
        reserva.setCancelada(Boolean.TRUE);
        reserva.setObsCancelamento("Usuário");
        reservaDao.persistir(reserva);
        reservas = reservaDao.buscarTodas(Reserva.class);
        Mensagem.msgScreen("Reserva cancelada com sucesso!");
    }
}
