/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.beans.sessions;

import com.requestnet.entidades.Liderinventario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mianba
 */
@Stateless
public class LiderinventarioFacade extends AbstractFacade<Liderinventario> {

    @PersistenceContext(unitName = "requestNetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LiderinventarioFacade() {
        super(Liderinventario.class);
    }
    
}
