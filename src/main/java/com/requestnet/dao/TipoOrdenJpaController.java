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
import com.requestnet.entidades.Orden;
import com.requestnet.entidades.TipoOrden;
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
public class TipoOrdenJpaController implements Serializable {

    public TipoOrdenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoOrden tipoOrden) throws RollbackFailureException, Exception {
        if (tipoOrden.getOrdenCollection() == null) {
            tipoOrden.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : tipoOrden.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getIdOrden());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            tipoOrden.setOrdenCollection(attachedOrdenCollection);
            em.persist(tipoOrden);
            for (Orden ordenCollectionOrden : tipoOrden.getOrdenCollection()) {
                TipoOrden oldIdTipoOrdenOfOrdenCollectionOrden = ordenCollectionOrden.getIdTipoOrden();
                ordenCollectionOrden.setIdTipoOrden(tipoOrden);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldIdTipoOrdenOfOrdenCollectionOrden != null) {
                    oldIdTipoOrdenOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldIdTipoOrdenOfOrdenCollectionOrden = em.merge(oldIdTipoOrdenOfOrdenCollectionOrden);
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

    public void edit(TipoOrden tipoOrden) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoOrden persistentTipoOrden = em.find(TipoOrden.class, tipoOrden.getIdTipoOrden());
            Collection<Orden> ordenCollectionOld = persistentTipoOrden.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = tipoOrden.getOrdenCollection();
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getIdOrden());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            tipoOrden.setOrdenCollection(ordenCollectionNew);
            tipoOrden = em.merge(tipoOrden);
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    ordenCollectionOldOrden.setIdTipoOrden(null);
                    ordenCollectionOldOrden = em.merge(ordenCollectionOldOrden);
                }
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    TipoOrden oldIdTipoOrdenOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getIdTipoOrden();
                    ordenCollectionNewOrden.setIdTipoOrden(tipoOrden);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldIdTipoOrdenOfOrdenCollectionNewOrden != null && !oldIdTipoOrdenOfOrdenCollectionNewOrden.equals(tipoOrden)) {
                        oldIdTipoOrdenOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldIdTipoOrdenOfOrdenCollectionNewOrden = em.merge(oldIdTipoOrdenOfOrdenCollectionNewOrden);
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
                Integer id = tipoOrden.getIdTipoOrden();
                if (findTipoOrden(id) == null) {
                    throw new NonexistentEntityException("The tipoOrden with id " + id + " no longer exists.");
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
            TipoOrden tipoOrden;
            try {
                tipoOrden = em.getReference(TipoOrden.class, id);
                tipoOrden.getIdTipoOrden();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoOrden with id " + id + " no longer exists.", enfe);
            }
            Collection<Orden> ordenCollection = tipoOrden.getOrdenCollection();
            for (Orden ordenCollectionOrden : ordenCollection) {
                ordenCollectionOrden.setIdTipoOrden(null);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
            }
            em.remove(tipoOrden);
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

    public List<TipoOrden> findTipoOrdenEntities() {
        return findTipoOrdenEntities(true, -1, -1);
    }

    public List<TipoOrden> findTipoOrdenEntities(int maxResults, int firstResult) {
        return findTipoOrdenEntities(false, maxResults, firstResult);
    }

    private List<TipoOrden> findTipoOrdenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoOrden.class));
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

    public TipoOrden findTipoOrden(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoOrden.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoOrdenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoOrden> rt = cq.from(TipoOrden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
