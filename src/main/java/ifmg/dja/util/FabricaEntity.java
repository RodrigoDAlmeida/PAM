/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifmg.dja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author User
 */
public class FabricaEntity {
 
    private static final EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("freqPoloPU");
    
    public static EntityManager getEntityManager(){                      
        return emf.createEntityManager();
        
        
    }
    
    
}
