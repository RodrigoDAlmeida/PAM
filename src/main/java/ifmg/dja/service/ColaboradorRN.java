/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.service;

import ifmg.dja.dao.ColaboradorDAO;
import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Colaborador_Projeto;
import ifmg.dja.modelo.Projeto;
import ifmg.dja.util.NegocioException;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class ColaboradorRN {

    private ColaboradorDAO colaboradorDAO;

    public ColaboradorRN() {
        colaboradorDAO = new ColaboradorDAO();
    }

    public void salvar(Colaborador c) throws NegocioException {

        if (c.getNome() != null && !c.getNome().trim().equals("")) {
            if (c.getLogin() != null && !c.getLogin().trim().equals("")) {
                if (c.getSenha() != null && !c.getSenha().trim().equals("")) {

                    colaboradorDAO.salvar(c);

                } else {
                    throw new NegocioException("Senha inválido");
                }
            } else {
                throw new NegocioException("Login inválido");
            }
        } else {
            throw new NegocioException("Nome inválido");
        }

    }

    public void excluir(Colaborador c) throws NegocioException, Exception {

        if (c.getCodigo() != null && c.getCodigo() > 0) {
            try {
                colaboradorDAO.excluir(c);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new NegocioException("Código inválido");
        }

    }

    public List<Colaborador> buscarTodos() {
        return colaboradorDAO.buscaTodos();
    }

    public Colaborador buscarPorCodigo(Long cod) {

        if (cod != null && cod > 0) {
            return colaboradorDAO.bucasPeloCodigo(cod);
        } else {
            return null;
        }
    }

    public Boolean verificaLogin(String login) {
        return colaboradorDAO.verificaDisponibilidadeLogin(login);
    }

    public Colaborador realizarLogin(String login, String senha) {
        return colaboradorDAO.logar(login, senha);
    }

    public void saiProjeto(Colaborador colaborador, Projeto projeto) {

        for (Colaborador_Projeto p : colaborador.getProjetos()) {

            if (p.getProjeto().equals(projeto)) {
                colaboradorDAO.saiProjeto(p);
                colaborador.saiProjeto(projeto);
                return;

            }

        }
    }

}
