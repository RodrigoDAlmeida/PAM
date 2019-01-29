/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.dao;

import java.io.Serializable;

/**
 *
 * @author Rodrigo
 */
public interface GenericDAO <T, ID extends Serializable>{
    
    public T bucasPeloCodigo(ID id);
    public void salvar(T entidade);
    
}
