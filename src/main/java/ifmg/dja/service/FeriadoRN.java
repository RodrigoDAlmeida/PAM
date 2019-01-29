/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.service;

import ifmg.dja.dao.FeriadoDAO;
import ifmg.dja.modelo.Feriado;
import ifmg.dja.util.NegocioException;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class FeriadoRN {

    private FeriadoDAO feriadoDAO;

    public FeriadoRN() {
        feriadoDAO = new FeriadoDAO();
    }

    public void salvar(Feriado f) throws NegocioException {

        if (f.getDescricao()!= null && !f.getDescricao().trim().equals("")) {
            if (f.getDataFeriado()!= null) {
             
                    feriadoDAO.salvar(f);
                } else {
                    throw new NegocioException("Data inválida");
                }
            } else {
                throw new NegocioException("Nome inválido");
            }
    }

    public void excluir(Feriado f) throws NegocioException, Exception {

        if (f.getCodigo() != null && f.getCodigo() > 0) {
            try {
                feriadoDAO.excluir(f);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new NegocioException("Código inválido");
        }

    }

    public List<Feriado> buscarTodos() {
        return feriadoDAO.buscaTodos();
    }

    public Feriado buscarPorCodigo(Long cod) {

        if (cod != null && cod > 0) {
            return feriadoDAO.bucasPeloCodigo(cod);
        } else {
            return null;
        }
    }
    
    
}
