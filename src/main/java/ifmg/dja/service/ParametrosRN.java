/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.service;

import ifmg.dja.dao.ParametrosDAO;
import ifmg.dja.modelo.Parametros;
import ifmg.dja.util.NegocioException;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class ParametrosRN {

    private ParametrosDAO parametrosDAO;

    public ParametrosRN() {
        parametrosDAO = new ParametrosDAO();
    }

    public void salvar(Parametros c) throws NegocioException {

        if (c.getDiretor()!= null && !c.getDiretor().trim().equals("")) {
            if (c.getGestor()!= null && !c.getGestor().trim().equals("")) {
                
                    parametrosDAO.salvar(c);
                } else {
                    throw new NegocioException("Coordenador de Gestão inválido");
                }
            } else {
                throw new NegocioException("Diretor Geral do Polo inválido");
            }
       

    }

    public void excluir(Parametros c) throws NegocioException, Exception {

        if (c.getCodigo() != null && c.getCodigo() > 0) {
            try {
                parametrosDAO.excluir(c);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new NegocioException("Código inválido");
        }

    }

    public List<Parametros> buscarTodos() {
        return parametrosDAO.buscaTodos();
    }

    public Parametros buscarPorCodigo(Long cod) {

        if (cod != null && cod > 0) {
            return parametrosDAO.bucasPeloCodigo(cod);
        } else {
            return null;
        }
    }

}
