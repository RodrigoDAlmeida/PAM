/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.dao;

import ifmg.dja.modelo.Colaborador;
import ifmg.dja.modelo.Lancamento;
import ifmg.dja.modelo.Projeto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Rodrigo
 */
public class LancamentoDAO extends HibernateGenericDAO<Lancamento, Long>{
    
    
    
     public List<Lancamento> buscaLancamentos(Projeto projeto, Colaborador colaborador){
        
       return getEntityManager().createQuery("select l from "
                    + "Lancamento l "
                    + "where l.projeto = :codProjeto and "
                    + "l.colaborador = :codColaborador", Lancamento.class)
                    .setParameter("codProjeto", projeto)
                    .setParameter("codColaborador", colaborador)
                    .getResultList();

        
    }
     
     public Lancamento buscaLancamentoMes(Projeto projeto, Colaborador colaborador, String mesRef, Integer ano){
        
         Lancamento aux = null;
         
         try {
         aux = getEntityManager().createQuery("select l from "
                    + "Lancamento l "
                    + "where l.projeto = :codProjeto and "
                    + "l.colaborador = :codColaborador and "
                    + "l.anoReferencia = :ano and "
                    + "l.mesReferencia = :mesRef", Lancamento.class)
                    .setParameter("codProjeto", projeto)
                    .setParameter("codColaborador", colaborador)
                    .setParameter("mesRef", mesRef)
                    .setParameter("ano", ano)
                    .getSingleResult();
         }catch (NoResultException e){
             
         }

        return aux;
    }   
     
    
    
}
