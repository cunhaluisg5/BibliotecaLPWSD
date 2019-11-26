/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author luisg
 */
public abstract class Dashboard {

    protected Calendar data1;
    protected Calendar data2;
    protected Calendar data3;
    protected Calendar data4;
    protected int mes1;
    protected int mes2;
    protected int mes3;

    public Calendar insertCalendar(int valor) {
        Calendar data = Calendar.getInstance();
        data.setTime(new Date());
        data.set(Calendar.DAY_OF_MONTH, 1);
        if (valor != 0) {
            data.set(Calendar.MONTH, data.get(Calendar.MONTH) - valor);
        }
        data.set(Calendar.HOUR, 0);
        data.set(Calendar.MINUTE, 0);
        data.set(Calendar.SECOND, 0);
        data.set(Calendar.MILLISECOND, 0);
        return data;
    }

    public void initCalendar() {
        data1 = insertCalendar(3);
        data2 = insertCalendar(2);
        data3 = insertCalendar(1);
        data4 = insertCalendar(0);
    }

    public void initMonth() {
        mes1 = 0;
        mes2 = 0;
        mes3 = 0;
    }

    public String[] initColors(String cor1, String cor2, String cor3) {
        String[] cores = {cor1, cor2, cor3};
        return cores;
    }

    public List<Number> insertValues(int mes1, int mes2, int mes3) {
        List<Number> values = new ArrayList<>();
        values.add(mes1);
        values.add(mes2);
        values.add(mes3);
        return values;
    }

    public List<String> insertColors(String[] cores) {
        List<String> bgColor = new ArrayList<>();
        bgColor.add(cores[0]);
        bgColor.add(cores[1]);
        bgColor.add(cores[2]);
        return bgColor;
    }

    public List<String> insertBorderColors(String[] cores) {
        List<String> borderColor = new ArrayList<>();
        borderColor.add(cores[0]);
        borderColor.add(cores[1]);
        borderColor.add(cores[2]);
        return borderColor;
    }

    public List<String> insertLabels(String obj1, String obj2, String obj3) {
        List<String> labels = new ArrayList<>();
        labels.add(obj1);
        labels.add(obj2);
        labels.add(obj3);
        return labels;
    }

    public BarChartOptions insertOptions() {
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        return options;
    }

    public Legend insertLegend(String posicao, String fonte, String cor) {
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition(posicao);
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle(fonte);
        legendLabels.setFontColor(cor);
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        return legend;
    }

    public Title insertTitle(String titulo) {
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titulo);
        return title;
    }

    public String mes(int m) {
        switch (m) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
            default:
                return "";
        }
    }
    
    
}
