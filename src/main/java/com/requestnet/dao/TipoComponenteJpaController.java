/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.NonexistentEntityException;
import com.requestnet.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.TipoComponente;
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
public class TipoComponenteJpaController implements Serializable {

    public TipoComponenteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoComponente tipoComponente) throws RollbackFailureException, Exception {
        if (tipoComponente.getInventarioEquiposCollection() == null) {
            tipoComponente.setInventarioEquiposCollection(new ArrayList<InventarioEquipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<InventarioEquipos> attachedInventarioEquiposCollection = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquiposToAttach : tipoComponente.getInventarioEquiposCollection()) {
                inventarioEquiposCollectionInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollection.add(inventarioEquiposCollectionInventarioEquiposToAttach);
            }
            tipoComponente.setInventarioEquiposCollection(attachedInventarioEquiposCollection);
            em.persist(tipoComponente);
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : tipoComponente.getInventarioEquiposCollection()) {
                TipoComponente oldIdTipoComponenteOfInventarioEquiposCollectionInventarioEquipos = inventarioEquiposCollectionInventarioEquipos.getIdTipoComponente();
                inventarioEquiposCollectionInventarioEquipos.setIdTipoComponente(tipoComponente);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
                if (oldIdTipoComponenteOfInventarioEquiposCollectionInventarioEquipos != null) {
                    oldIdTipoComponenteOfInventarioEquiposCollectionInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionInventarioEquipos);
                    oldIdTipoComponenteOfInventarioEquiposCollectionInventarioEquipos = em.merge(oldIdTipoComponenteOfInventarioEquiposCollectionInventarioEquipos);
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

    public void edit(TipoComponente tipoComponente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoComponente persistentTipoComponente = em.find(TipoComponente.class, tipoComponente.getIdTipoComponente());
            Collection<InventarioEquipos> inventarioEquiposCollectionOld = persistentTipoComponente.getInventarioEquiposCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionNew = tipoComponente.getInventarioEquiposCollection();
            Collection<InventarioEquipos> attachedInventarioEquiposCollectionNew = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquiposToAttach : inventarioEquiposCollectionNew) {
                inventarioEquiposCollectionNewInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionNewInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionNewInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollectionNew.add(inventarioEquiposCollectionNewInventarioEquiposToAttach);
            }
            inventarioEquiposCollectionNew = attachedInventarioEquiposCollectionNew;
            tipoComponente.setInventarioEquiposCollection(inventarioEquiposCollectionNew);
            tipoComponente = em.merge(tipoComponente);
            for (InventarioEquipos inventarioEquiposCollectionOldInventarioEquipos : inventarioEquiposCollectionOld) {
                if (!inventarioEquiposCollectionNew.contains(inventarioEquiposCollectionOldInventarioEquipos)) {
                    inventarioEquiposCollectionOldInventarioEquipos.setIdTipoComponente(null);
                    inventarioEquiposCollectionOldInventarioEquipos = em.merge(inventarioEquiposCollectionOldInventarioEquipos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquipos : inventarioEquiposCollectionNew) {
                if (!inventarioEquiposCollectionOld.contains(inventarioEquiposCollectionNewInventarioEquipos)) {
                    TipoComponente oldIdTipoComponenteOfInventarioEquiposCollectionNewInventarioEquipos = inventarioEquiposCollectionNewInventarioEquipos.getIdTipoComponente();
                    inventarioEquiposCollectionNewInventarioEquipos.setIdTipoComponente(tipoComponente);
                    inventarioEquiposCollectionNewInventarioEquipos = em.merge(inventarioEquiposCollectionNewInventarioEquipos);
                    if (oldIdTipoComponenteOfInventarioEquiposCollectionNewInventarioEquipos != null && !oldIdTipoComponenteOfInventarioEquiposCollectionNewInventarioEquipos.equals(tipoComponente)) {
                        oldIdTipoComponenteOfInventarioEquiposCollectionNewInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionNewInventarioEquipos);
                        oldIdTipoComponenteOfInventarioEquiposCollectionNewInventarioEquipos = em.merge(oldIdTipoComponenteOfInventarioEquiposCollectionNewInventarioEquipos);
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
                Integer id = tipoComponente.getIdTipoComponente();
                if (findTipoComponente(id) == null) {
                    throw new NonexistentEntityException("The tipoComponente with id " + id + " no longer exists.");
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
            TipoComponente tipoComponente;
            try {
                tipoComponente = em.getReference(TipoComponente.class, id);
                tipoComponente.getIdTipoComponente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoComponente with id " + id + " no longer exists.", enfe);
            }
            Collection<InventarioEquipos> inventarioEquiposCollection = tipoComponente.getInventarioEquiposCollection();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : inventarioEquiposCollection) {
                inventarioEquiposCollectionInventarioEquipos.setIdTipoComponente(null);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
            }
            em.remove(tipoComponente);
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

    public List<TipoComponente> findTipoComponenteEntities() {
        return findTipoComponenteEntities(true, -1, -1);
    }

    public List<TipoComponente> findTipoComponenteEntities(int maxResults, int firstResult) {
        return findTipoComponenteEntities(false, maxResults, firstResult);
    }

    private List<TipoComponente> findTipoComponenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoComponente.class));
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

    public TipoComponente findTipoComponente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoComponente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoComponenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoComponente> rt = cq.from(TipoComponente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
