/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.util.Dashboard;
import br.cesjf.bibliotecalpwsd.dao.DAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class DashboardBean extends Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<Reserva> reservas;
    private BarChartModel livrosReservados;
    private final List<Emprestimo> emprestimos;
    private BarChartModel livrosEmprestados;
    private final List<Assunto> assuntos;
    private BarChartModel livrosReservadosCategoria;
    private BarChartModel livrosEmprestadosCategoria;
    private BarChartModel livrosReservadosEmprestados;
    private final DAO<Assunto> assuntoDao;
    private final DAO<Emprestimo> emprestimoDao;
    private final DAO<Reserva> reservaDao;

    //construtor
    public DashboardBean() {
        assuntoDao = new DAO<>();
        emprestimoDao = new DAO<>();
        reservaDao = new DAO<>();
        reservas = reservaDao.buscarTodas(Reserva.class);
        emprestimos = emprestimoDao.buscarTodas(Emprestimo.class);
        assuntos = assuntoDao.buscarTodas(Assunto.class);
    }

    @PostConstruct
    public void init() {
        criarLivrosReservados();
        criarLivrosEmprestados();
        criarReservadosCategoria();
        criarEmprestadosCategoria();
        criarLivrosReservadosEmprestados();
    }

    public void criarLivrosReservados() {
        
        livrosReservados = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Quantidade de Livros Reservados por Mês ");

        initCalendar();
        initMonth();
        
        reservas.stream().map((r) -> {
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            return reserva;
        }).forEachOrdered((reserva) -> {
            if ((reserva.equals(data1) || reserva.after(data1)) && reserva.before(data2)) {
                mes1++;
            } else if ((reserva.equals(data2) || reserva.after(data2)) && reserva.before(data3)) {
                mes2++;
            } else if ((reserva.equals(data3) || reserva.after(data3)) && reserva.before(data4)) {
                mes3++;
            }
        });

        List<Number> values = insertValues(mes1, mes2, mes3);
        barDataSet.setData(values);

        List<String> bgColor = insertColors(initColors("rgba(255, 99, 132, 0.2)",
                "rgba(255, 159, 64, 0.2)",
                "rgba(255, 205, 86, 0.2)"));
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = insertBorderColors(initColors("rgb(255, 99, 132)",
                "rgb(255, 159, 64)",
                "rgb(255, 205, 86)"));
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = insertLabels(mes(data1.get(Calendar.MONTH)),
                mes(data2.get(Calendar.MONTH)),
                mes(data3.get(Calendar.MONTH)));
        data.setLabels(labels);
        livrosReservados.setData(data);

        //Options
        BarChartOptions options = insertOptions();

        Title title = insertTitle("Gráfico de Livros Reservados nos 3 Últimos Meses");
        options.setTitle(title);

        Legend legend = insertLegend("top", "bold", "#2980B9");
        options.setLegend(legend);

        livrosReservados.setOptions(options);
    }

    public void criarLivrosEmprestados() {
        livrosEmprestados = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Quantidade de Livros Emprestados por Mês ");

        initCalendar();
        initMonth();

        emprestimos.stream().map((e) -> {
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            return emprestimo;
        }).forEachOrdered((emprestimo) -> {
            if ((emprestimo.equals(data1) || emprestimo.after(data1)) && emprestimo.before(data2)) {
                mes1++;
            } else if ((emprestimo.equals(data2) || emprestimo.after(data2)) && emprestimo.before(data3)) {
                mes2++;
            } else if ((emprestimo.equals(data3) || emprestimo.after(data3)) && emprestimo.before(data4)) {
                mes3++;
            }
        });

        List<Number> values = insertValues(mes1, mes2, mes3);
        barDataSet.setData(values);

        List<String> bgColor = insertColors(initColors("rgba(255, 99, 132, 0.2)",
                "rgba(255, 159, 64, 0.2)",
                "rgba(255, 205, 86, 0.2)"));
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = insertBorderColors(initColors("rgb(255, 99, 132)",
                "rgb(255, 159, 64)",
                "rgb(255, 205, 86)"));
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = insertLabels(mes(data1.get(Calendar.MONTH)),
                mes(data2.get(Calendar.MONTH)),
                mes(data3.get(Calendar.MONTH)));
        data.setLabels(labels);
        livrosEmprestados.setData(data);

        //Options
        BarChartOptions options = insertOptions();

        Title title = insertTitle("Gráfico de Livros Emprestados nos 3 Últimos Meses");
        options.setTitle(title);

        Legend legend = insertLegend("top", "bold", "#2980B9");
        options.setLegend(legend);

        livrosEmprestados.setOptions(options);
    }

    public void criarReservadosCategoria() {
        livrosReservadosCategoria = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Quantidade de Livros Reservados por Categoria ");

        initCalendar();

        Map<String, Integer> aux = new HashMap<>();

        for (Assunto a : assuntos) {
            aux.put(a.getAssunto(), 0);
        }
        aux = aux.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        initMonth();

        for (Reserva r : reservas) {
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            if ((reserva.equals(data1) || reserva.after(data1)) && reserva.before(data4)) {
                for (Assunto a : r.getIdExemplar().getIdLivro().getAssuntoList()) {
                    aux.computeIfPresent(a.getAssunto(), (k, v) -> v + 1);
                }
            }
        }

        List<Number> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aux.entrySet()) {
            values.add(entry.getValue());
        }
        barDataSet.setData(values);

        int auxValue = 0;
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aux.entrySet()) {
            auxValue++;
            if (auxValue % 2 == 0) {
                bgColor.add("rgba(255, 99, 132, 0.2)");
                borderColor.add("rgb(255, 99, 132)");
            } else {
                bgColor.add("rgba(255, 159, 64, 0.2)");
                borderColor.add("rgb(255, 159, 64)");
            }
        }
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aux.entrySet()) {
            labels.add(entry.getKey());
        }
        data.setLabels(labels);
        livrosReservadosCategoria.setData(data);

        //Options
        BarChartOptions options = insertOptions();

        Title title = insertTitle("Gráfico de Livros Reservados por Categoria nos 3 Últimos Meses");
        options.setTitle(title);

        Legend legend = insertLegend("top", "bold", "#2980B9");
        options.setLegend(legend);

        livrosReservadosCategoria.setOptions(options);
    }

    public void criarEmprestadosCategoria() {
        livrosEmprestadosCategoria = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Quantidade de Livros Emprestados por Categoria ");

        initCalendar();

        Map<String, Integer> aux = new HashMap<>();

        for (Assunto a : assuntos) {
            aux.put(a.getAssunto(), 0);
        }
        aux = aux.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        initMonth();

        for (Emprestimo e : emprestimos) {
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            if ((emprestimo.equals(data1) || emprestimo.after(data1)) && emprestimo.before(data4)) {
                for (Assunto a : e.getIdExemplar().getIdLivro().getAssuntoList()) {
                    aux.computeIfPresent(a.getAssunto(), (k, v) -> v + 1);
                }
            }
        }

        List<Number> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aux.entrySet()) {
            values.add(entry.getValue());
        }
        barDataSet.setData(values);

        int auxValue = 0;
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aux.entrySet()) {
            auxValue++;
            if (auxValue % 2 == 0) {
                bgColor.add("rgba(255, 99, 132, 0.2)");
                borderColor.add("rgb(255, 99, 132)");
            } else {
                bgColor.add("rgba(255, 159, 64, 0.2)");
                borderColor.add("rgb(255, 159, 64)");
            }
        }
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aux.entrySet()) {
            labels.add(entry.getKey());
        }
        data.setLabels(labels);
        livrosEmprestadosCategoria.setData(data);

        //Options
        BarChartOptions options = insertOptions();

        Title title = insertTitle("Gráfico de Livros Emprestados por Categoria nos 3 Últimos Meses");
        options.setTitle(title);

        Legend legend = insertLegend("top", "bold", "#2980B9");
        options.setLegend(legend);

        livrosEmprestadosCategoria.setOptions(options);
    }

    public void criarLivrosReservadosEmprestados() {
        livrosReservadosEmprestados = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Quantidade de Livros Reservados e Emprestados no Último Mês");

        initCalendar();
        initMonth();

        reservas.stream().map((r) -> {
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            return reserva;
        }).filter((reserva) -> ((reserva.equals(data3) || reserva.after(data3)) && reserva.before(data4))).forEachOrdered((_item) -> {
            mes2++;
        });

        emprestimos.stream().map((e) -> {
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            return emprestimo;
        }).filter((emprestimo) -> ((emprestimo.equals(data3) || emprestimo.after(data3)) && emprestimo.before(data4))).forEachOrdered((_item) -> {
            mes1++;
        });

        List<Number> values = insertValues(mes2, mes1, 0);
        barDataSet.setData(values);

        List<String> bgColor = insertColors(initColors("rgba(255, 99, 132, 0.2)",
                "rgba(255, 159, 64, 0.2)",
                ""));
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = insertBorderColors(initColors("rgb(255, 99, 132)",
                "rgb(255, 159, 64)",
                ""));
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = insertLabels("Reservados", "Emprestados", "");
        data.setLabels(labels);
        livrosReservadosEmprestados.setData(data);

        //Options
        BarChartOptions options = insertOptions();

        Title title = insertTitle("Gráfico de Livros Reservados e Empresrados no Último Mês");
        options.setTitle(title);

        Legend legend = insertLegend("top", "bold", "#2980B9");

        livrosReservadosEmprestados.setOptions(options);
    }

    public BarChartModel getLivrosReservados() {
        return livrosReservados;
    }

    public void setLivrosReservados(BarChartModel livrosReservados) {
        this.livrosReservados = livrosReservados;
    }

    public BarChartModel getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(BarChartModel livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    public BarChartModel getLivrosReservadosCategoria() {
        return livrosReservadosCategoria;
    }

    public void setLivrosReservadosCategoria(BarChartModel livrosReservadosCategoria) {
        this.livrosReservadosCategoria = livrosReservadosCategoria;
    }

    public BarChartModel getLivrosEmprestadosCategoria() {
        return livrosEmprestadosCategoria;
    }

    public void setLivrosEmprestadosCategoria(BarChartModel livrosEmprestadosCategoria) {
        this.livrosEmprestadosCategoria = livrosEmprestadosCategoria;
    }

    public BarChartModel getLivrosReservadosEmprestados() {
        return livrosReservadosEmprestados;
    }

    public void setLivrosReservadosEmprestados(BarChartModel livrosReservadosEmprestados) {
        this.livrosReservadosEmprestados = livrosReservadosEmprestados;
    }

    public Calendar getData1() {
        return data1;
    }

    public void setData1(Calendar data1) {
        this.data1 = data1;
    }

    public Calendar getData2() {
        return data2;
    }

    public void setData2(Calendar data2) {
        this.data2 = data2;
    }

    public Calendar getData3() {
        return data3;
    }

    public void setData3(Calendar data3) {
        this.data3 = data3;
    }

    public Calendar getData4() {
        return data4;
    }

    public void setData4(Calendar data4) {
        this.data4 = data4;
    }

    public int getMes1() {
        return mes1;
    }

    public void setMes1(int mes1) {
        this.mes1 = mes1;
    }

    public int getMes2() {
        return mes2;
    }

    public void setMes2(int mes2) {
        this.mes2 = mes2;
    }

    public int getMes3() {
        return mes3;
    }

    public void setMes3(int mes3) {
        this.mes3 = mes3;
    }
}
