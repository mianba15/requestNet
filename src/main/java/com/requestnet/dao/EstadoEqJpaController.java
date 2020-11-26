/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.NonexistentEntityException;
import com.requestnet.dao.exceptions.RollbackFailureException;
import com.requestnet.entidades.EstadoEq;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.requestnet.entidades.InventarioEquipos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class EstadoEqJpaController implements Serializable {

    public EstadoEqJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoEq estadoEq) throws RollbackFailureException, Exception {
        if (estadoEq.getInventarioEquiposCollection() == null) {
            estadoEq.setInventarioEquiposCollection(new ArrayList<InventarioEquipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<InventarioEquipos> attachedInventarioEquiposCollection = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquiposToAttach : estadoEq.getInventarioEquiposCollection()) {
                inventarioEquiposCollectionInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollection.add(inventarioEquiposCollectionInventarioEquiposToAttach);
            }
            estadoEq.setInventarioEquiposCollection(attachedInventarioEquiposCollection);
            em.persist(estadoEq);
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : estadoEq.getInventarioEquiposCollection()) {
                EstadoEq oldIdEstadoeqOfInventarioEquiposCollectionInventarioEquipos = inventarioEquiposCollectionInventarioEquipos.getIdEstadoeq();
                inventarioEquiposCollectionInventarioEquipos.setIdEstadoeq(estadoEq);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
                if (oldIdEstadoeqOfInventarioEquiposCollectionInventarioEquipos != null) {
                    oldIdEstadoeqOfInventarioEquiposCollectionInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionInventarioEquipos);
                    oldIdEstadoeqOfInventarioEquiposCollectionInventarioEquipos = em.merge(oldIdEstadoeqOfInventarioEquiposCollectionInventarioEquipos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoEq estadoEq) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoEq persistentEstadoEq = em.find(EstadoEq.class, estadoEq.getIdEstadoeq());
            Collection<InventarioEquipos> inventarioEquiposCollectionOld = persistentEstadoEq.getInventarioEquiposCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionNew = estadoEq.getInventarioEquiposCollection();
            Collection<InventarioEquipos> attachedInventarioEquiposCollectionNew = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquiposToAttach : inventarioEquiposCollectionNew) {
                inventarioEquiposCollectionNewInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionNewInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionNewInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollectionNew.add(inventarioEquiposCollectionNewInventarioEquiposToAttach);
            }
            inventarioEquiposCollectionNew = attachedInventarioEquiposCollectionNew;
            estadoEq.setInventarioEquiposCollection(inventarioEquiposCollectionNew);
            estadoEq = em.merge(estadoEq);
            for (InventarioEquipos inventarioEquiposCollectionOldInventarioEquipos : inventarioEquiposCollectionOld) {
                if (!inventarioEquiposCollectionNew.contains(inventarioEquiposCollectionOldInventarioEquipos)) {
                    inventarioEquiposCollectionOldInventarioEquipos.setIdEstadoeq(null);
                    inventarioEquiposCollectionOldInventarioEquipos = em.merge(inventarioEquiposCollectionOldInventarioEquipos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquipos : inventarioEquiposCollectionNew) {
                if (!inventarioEquiposCollectionOld.contains(inventarioEquiposCollectionNewInventarioEquipos)) {
                    EstadoEq oldIdEstadoeqOfInventarioEquiposCollectionNewInventarioEquipos = inventarioEquiposCollectionNewInventarioEquipos.getIdEstadoeq();
                    inventarioEquiposCollectionNewInventarioEquipos.setIdEstadoeq(estadoEq);
                    inventarioEquiposCollectionNewInventarioEquipos = em.merge(inventarioEquiposCollectionNewInventarioEquipos);
                    if (oldIdEstadoeqOfInventarioEquiposCollectionNewInventarioEquipos != null && !oldIdEstadoeqOfInventarioEquiposCollectionNewInventarioEquipos.equals(estadoEq)) {
                        oldIdEstadoeqOfInventarioEquiposCollectionNewInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionNewInventarioEquipos);
                        oldIdEstadoeqOfInventarioEquiposCollectionNewInventarioEquipos = em.merge(oldIdEstadoeqOfInventarioEquiposCollectionNewInventarioEquipos);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoEq.getIdEstadoeq();
                if (findEstadoEq(id) == null) {
                    throw new NonexistentEntityException("The estadoEq with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoEq estadoEq;
            try {
                estadoEq = em.getReference(EstadoEq.class, id);
                estadoEq.getIdEstadoeq();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoEq with id " + id + " no longer exists.", enfe);
            }
            Collection<InventarioEquipos> inventarioEquiposCollection = estadoEq.getInventarioEquiposCollection();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : inventarioEquiposCollection) {
                inventarioEquiposCollectionInventarioEquipos.setIdEstadoeq(null);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
            }
            em.remove(estadoEq);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoEq> findEstadoEqEntities() {
        return findEstadoEqEntities(true, -1, -1);
    }

    public List<EstadoEq> findEstadoEqEntities(int maxResults, int firstResult) {
        return findEstadoEqEntities(false, maxResults, firstResult);
    }

    private List<EstadoEq> findEstadoEqEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoEq.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EstadoEq findEstadoEq(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoEq.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoEqCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoEq> rt = cq.from(EstadoEq.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
