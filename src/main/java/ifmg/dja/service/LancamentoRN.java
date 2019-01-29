/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.service;

import ifmg.dja.dao.LancamentoDAO;
import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Lancamento;
import ifmg.dja.modelo.Projeto;
import ifmg.dja.util.NegocioException;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class LancamentoRN {

    private LancamentoDAO lancamentoDAO;

    public LancamentoRN() {
        lancamentoDAO = new LancamentoDAO();
    }

    public void salvar(Lancamento l) throws NegocioException {

        if (l.getMesReferencia()!= null) {
            if(l.getAnoReferencia() != null){
            
                    lancamentoDAO.salvar(l);

        } else {
            throw new NegocioException("Erro ! Por favor, Reinicie a aplicativo");
        }
        }else{
            throw new NegocioException("Erro ! Por favor, Reinicie a aplicativo");
        }

    }

    public void excluir(Lancamento l) throws NegocioException, Exception {

        if (l.getCodigo() != null && l.getCodigo() > 0) {
            try {
                lancamentoDAO.excluir(l);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new NegocioException("Código inválido");
        }

    }

    public List<Lancamento> buscarTodos() {
        return lancamentoDAO.buscaTodos();
    }

    public Lancamento buscarPorCodigo(Long cod) {

        if (cod != null && cod > 0) {
            return lancamentoDAO.bucasPeloCodigo(cod);
        } else {
            return null;
        }
    }

    
    public List<Lancamento> buscarLancamentos(Projeto projeto, Colaborador colaborador){
        
        return lancamentoDAO.buscaLancamentos(projeto, colaborador);
    }
    
     public Lancamento buscarLancamentoMes(Projeto projeto, Colaborador colaborador, String mesRef, Integer ano){
        
        return lancamentoDAO.buscaLancamentoMes(projeto, colaborador, mesRef, ano);
    }

}
