/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.dao;

import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Colaborador_Projeto;
import ifmg.dja.modelo.Projeto;
import java.awt.CardLayout;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Rodrigo
 */
public class ColaboradorDAO extends HibernateGenericDAO<Colaborador, Long> {

    public Boolean verificaDisponibilidadeLogin(String login) {

        Colaborador aux = null;

        try {
            aux = getEntityManager().createQuery("select c from "
                    + "Colaborador c "
                    + "where c.login = :login", Colaborador.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {

        }
        return (aux == null);
    }

    public Colaborador logar(String login, String senha) {
        Colaborador aux = null;

        try {
            aux = getEntityManager().createQuery("select c from "
                    + "Colaborador c "
                    + "where c.login = :login and "
                    + "c.senha = :senha", Colaborador.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {

        }
        return aux;
    }

    public void saiProjeto(Colaborador_Projeto projeto) {

        
        Colaborador c = projeto.getColaborador();
        Projeto p = projeto.getProjeto();
        ColaboradorDAO cDAO = new ColaboradorDAO();
        ProjetoDAO pDAO = new ProjetoDAO();
        
        getEntityManager().getTransaction().begin();
        projeto = getEntityManager().merge(projeto);
        
        c.saiProjeto(p);
        cDAO.salvar(c);
        
        p.removeColaborador(c);
        pDAO.salvar(p);
        
        
        getEntityManager().remove(projeto);
        
        
        
        getEntityManager().getTransaction().commit();

    }

}
