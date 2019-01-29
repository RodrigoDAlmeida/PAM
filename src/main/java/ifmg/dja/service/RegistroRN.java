/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.service;

import ifmg.dja.dao.RegistroDAO;
import ifmg.dja.modelo.Lancamento;
import ifmg.dja.modelo.Registro;
import ifmg.dja.util.NegocioException;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class RegistroRN {

    private RegistroDAO registroDAO;

    public RegistroRN() {
        registroDAO = new RegistroDAO();
    }

    public void salvar(Registro r) throws NegocioException {

        if ((r.getDescricao() != null) && (!r.getDescricao().trim().equals(""))) {
            if (r.getDataRegistro() != null) {
                if (r.getHoraEntrada() != null) {
                    if (r.getHoraSaida() != null) {

                        registroDAO.salvar(r);

                    } else {
                        throw new NegocioException("Hora de Saída Inválida inválida");
                    }
                } else {
                    throw new NegocioException("Hora de Entrada Inválida inválida");
                }
            } else {
                throw new NegocioException("Data inválida");
            }

        } else {
            throw new NegocioException("Descrição Inválida inválida");
        }

    }

    public void excluir(Registro r) throws NegocioException, Exception {

        if (r.getCodigo() != null && r.getCodigo() > 0) {
            try {
                registroDAO.excluir(r);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new NegocioException("Código inválido");
        }

    }

    public List<Registro> buscarTodos() {
        return registroDAO.buscaTodos();
    }

    public Registro buscarPorCodigo(Long cod) {

        if (cod != null && cod > 0) {
            return registroDAO.bucasPeloCodigo(cod);
        } else {
            return null;
        }
    }

    public List<Registro> buscaRegistroPesquisa(Lancamento lancamento) {

        return registroDAO.buscaRegistrosPesquisa(lancamento);

    }

    public List<Registro> buscaRegistroPolo(Lancamento lancamento) {

        return registroDAO.buscaRegistrosPolo(lancamento);
    }

    public Double somaTotalPesquisa(Lancamento lancamento) {

        Double soma = 0.0;
        List<Registro> registros = buscaRegistroPesquisa(lancamento);

        for (Registro r : registros) {
            soma += r.getHorasTotais();
        }
        return soma;
    }

    public Double somaTotalPolo(Lancamento lancamento) {

        Double soma = 0.0;
        List<Registro> registros = buscaRegistroPolo(lancamento);

        for (Registro r : registros) {
            soma += r.getHorasTotais();
        }
        return soma;
    }

    public Registro verificaChoqueHorario(Lancamento l, Registro r) {

        List<Registro> registrosPesquisa = registroDAO.buscaRegistrosPesquisa(l);

        Calendar calenRegistroAntigo = new GregorianCalendar();
        Calendar calenRegistroNovo = new GregorianCalendar();
        calenRegistroNovo.setTime(r.getDataRegistro());

        for (Registro registro : registrosPesquisa) {

            calenRegistroAntigo.setTime(registro.getDataRegistro());

            if (((calenRegistroAntigo.get(GregorianCalendar.DAY_OF_MONTH)) == (calenRegistroNovo.get(GregorianCalendar.DAY_OF_MONTH)))
                    && (calenRegistroAntigo.get(GregorianCalendar.MONTH)) == (calenRegistroNovo.get(GregorianCalendar.MONTH))) {

                Double antigoHoraEntrada = registro.getHoraEntrada().getTime() * 0.001 / 60;
                Double antigoHoraSaida = registro.getHoraSaida().getTime() * 0.001 / 60;

                Double novoHoraEntrada = r.getHoraEntrada().getTime() * 0.001 / 60;
                Double novoHoraSaida = r.getHoraSaida().getTime() * 0.001 / 60;

                if ((antigoHoraSaida > novoHoraEntrada) && (antigoHoraEntrada < novoHoraSaida)){
                
                    return registro;
                }
            }

        }

        List<Registro> registrosPolo = registroDAO.buscaRegistrosPolo(l);

        for (Registro registro : registrosPolo) {

            calenRegistroAntigo.setTime(registro.getDataRegistro());

            if (((calenRegistroAntigo.get(GregorianCalendar.DAY_OF_MONTH)) == (calenRegistroNovo.get(GregorianCalendar.DAY_OF_MONTH)))
                    && (calenRegistroAntigo.get(GregorianCalendar.MONTH)) == (calenRegistroNovo.get(GregorianCalendar.MONTH))) {

                Double antigoHoraEntrada = registro.getHoraEntrada().getTime() * 0.001 / 60;
                Double antigoHoraSaida = registro.getHoraSaida().getTime() * 0.001 / 60;

                Double novoHoraEntrada = r.getHoraEntrada().getTime() * 0.001 / 60;
                Double novoHoraSaida = r.getHoraSaida().getTime() * 0.001 / 60;

                if ((antigoHoraSaida > novoHoraEntrada) && (antigoHoraEntrada < novoHoraSaida)){

                    return registro;
                }
            }

        }

        return null;

    }

}
