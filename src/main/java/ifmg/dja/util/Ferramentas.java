/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.util;

import ifmg.dja.modelo.Feriado;
import ifmg.dja.service.FeriadoRN;
import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class Ferramentas {

    public static String converteHora(String horas, String minutos) {

        if (horas.length() < 2) {
            horas = "0" + horas;
        }

        if (minutos.length() < 2) {
            minutos = "0" + minutos;
        }

        return horas + ":" + minutos + ":00";

    }

    public static String getTimeHoras(String tempo) {

        if (Character.isLetter(tempo.charAt(0))) {

            return tempo.substring(11, 13);

        } else {
            return tempo.substring(0, 2);
        }

    }

    public static String getTimeMinutos(String tempo) {

        if (Character.isLetter(tempo.charAt(0))) {

            return tempo.substring(14, 16);

        } else {
            return tempo.substring(3, 5);
        }

    }

    public static double arredondar(double valor, int casas) {
        double verif, arredondado = valor;

        int multp = 1;
        for (int i = 0; i < casas; i++) {
            multp *= 10;
        }
        arredondado *= multp;
        verif = arredondado;
        arredondado = Math.floor(arredondado);
        verif -= arredondado;
        verif = Math.floor(verif * 10);

        if (verif > 4) {
            arredondado++;
        }
        arredondado /= multp;
        return arredondado;
    }

    public static Boolean isFeriado(Date data) {

        FeriadoRN fRN = new FeriadoRN();
        List<Feriado> feriados = fRN.buscarTodos();

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH);
        int ano = calendar.get(GregorianCalendar.YEAR);

        int diaFeriado;
        int mesFeriado;
        int anoFeriado;
        GregorianCalendar feriadoCalendar;

        for (Feriado feriado : feriados) {

            feriadoCalendar = new GregorianCalendar();
            feriadoCalendar.setTime(feriado.getDataFeriado());
            diaFeriado = feriadoCalendar.get(GregorianCalendar.DAY_OF_MONTH);
            mesFeriado = feriadoCalendar.get(GregorianCalendar.MONTH);

            if ((dia == diaFeriado) && (mes == mesFeriado)) {

                if ((feriado.getDescricao().equals("Carnaval")) || (feriado.getDescricao().equals("Paixâo de Cristo")) || (feriado.getDescricao().equals("Corpus Christi"))) {

                    anoFeriado = feriadoCalendar.get(GregorianCalendar.YEAR);
                    if (anoFeriado == ano) {
                        return true;
                    }

                } else {

                    return true;
                }
            }
        }

        return false;
    }

    public static String intParaMes(Integer mes) {

        String saida = "";

        switch (mes) {

            case 0:
                saida = "Janeiro";
                break;
            case 1:
                saida = "Fevereiro";
                break;
            case 2:
                saida = "Março";
                break;
            case 3:
                saida = "Abril";
                break;
            case 4:
                saida = "Maio";
                break;
            case 5:
                saida = "Junho";
                break;
            case 6:
                saida = "Julho";
                break;
            case 7:
                saida = "Agosto";
                break;
            case 8:
                saida = "Setembro";
                break;
            case 9:
                saida = "Outubro";
                break;
            case 10:
                saida = "Novembro";
                break;
            case 11:
                saida = "Dezembro";
                break;

        }

        return saida;
    }

}
