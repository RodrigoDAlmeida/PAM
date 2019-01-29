/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.dao;

import ifmg.dja.modelo.Lancamento;
import ifmg.dja.modelo.Registro;
import ifmg.dja.modelo.TipoRegistroENUM;
import java.util.List;


/**
 *
 * @author Rodrigo
 */
public class RegistroDAO extends HibernateGenericDAO<Registro, Long>{
    
    
    public List<Registro> buscaRegistrosPesquisa(Lancamento lancamento){
        
    
     
            return getEntityManager().createQuery("select r from "
                    + "Registro r "
                    + "where r.lancamento = :codLancamento and "
                    + "r.tipo = :tipo", Registro.class)
                    .setParameter("codLancamento", lancamento)
                    .setParameter("tipo", TipoRegistroENUM.PESQUISA)
                    .getResultList();

        
    }
    
    
    public List<Registro> buscaRegistrosPolo(Lancamento lancamento){
        
    
     
            return getEntityManager().createQuery("select r from "
                    + "Registro r "
                    + "where r.lancamento = :codLancamento and "
                    + "r.tipo = :tipo", Registro.class)
                    .setParameter("codLancamento", lancamento)
                    .setParameter("tipo", TipoRegistroENUM.POLO)
                    .getResultList();

        
    }
    
}
