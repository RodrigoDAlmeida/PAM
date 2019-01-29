/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.dao;

import ifmg.dja.util.FabricaEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Rodrigo
 */
public class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private EntityManager em;
    private Class<T> classeEntidade;

    public HibernateGenericDAO() {
        em = FabricaEntity.getEntityManager();
        this.classeEntidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T bucasPeloCodigo(ID id) {
        return em.find(classeEntidade, id);
       
    }

    @Override
    public void salvar(T entidade) {
        em.getTransaction().begin();
        em.merge(entidade);
        em.getTransaction().commit();
    }
    
 
    public void excluir(T entidade) {
        em.getTransaction().begin();
        em.remove(entidade);
        em.getTransaction().commit();
    }

    public List<T> buscaTodos(){
        return em.createQuery("from " + classeEntidade.getName()+" c", classeEntidade).getResultList();
    }
    
    protected EntityManager getEntityManager() {
        return em;
    }
}
