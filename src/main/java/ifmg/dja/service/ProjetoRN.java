/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.service;

import ifmg.dja.dao.ProjetoDAO;
import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Projeto;
import ifmg.dja.util.NegocioException;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class ProjetoRN {

    private ProjetoDAO projetoDAO;

    public ProjetoRN() {
        projetoDAO = new ProjetoDAO();
    }

    public void salvar(Projeto p) throws NegocioException {

        if (p.getCoordenador() != null && !p.getCoordenador().trim().equals("")) {
            if (p.getEdital() != null && !p.getEdital().trim().equals("")) {
                if (p.getFinanciador() != null && !p.getFinanciador().trim().equals("")) {
                    if (p.getTermoCompromisso() != null && !p.getTermoCompromisso().trim().equals("")) {
                        if (p.getTitulo() != null && !p.getTitulo().trim().equals("")) {
                            if (p.getHorasSemanais() != null && !(p.getHorasSemanais() == 0)) {
                                if ((p.getTipoFinanciador() != null) && (p.getTipoFinanciador() >= 0)) {

                                    projetoDAO.salvar(p);
                                } else {
                                    throw new NegocioException("Tipo de Fincanciador inválido");
                                }
                            } else {
                                throw new NegocioException("Horas Semanais inválida");
                            }
                        } else {
                            throw new NegocioException("Titulo inválido");
                        }
                    } else {
                        throw new NegocioException("Termo de Compromisso inválido");
                    }
                } else {
                    throw new NegocioException("Financiador inválido");
                }
            } else {
                throw new NegocioException("Edital inválido");
            }
        } else {
            throw new NegocioException("Coordenador inválido");
        }

    }

    public void excluir(Projeto p) throws NegocioException, Exception {

        if (p.getCodigo() != null) {
            try {
                projetoDAO.excluir(p);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new NegocioException("Código inválido");
        }

    }

    public List<Projeto> buscarTodos() {
        return projetoDAO.buscaTodos();
    }


    public Projeto buscarPorCodigo(Long cod) {

        if (cod != null && cod > 0) {
            return projetoDAO.bucasPeloCodigo(cod);
        } else {
            return null;
        }
    }
     
}
