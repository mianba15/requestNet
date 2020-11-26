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
import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.TipoProcedencia;
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
public class TipoProcedenciaJpaController implements Serializable {

    public TipoProcedenciaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProcedencia tipoProcedencia) throws RollbackFailureException, Exception {
        if (tipoProcedencia.getInventarioEquiposCollection() == null) {
            tipoProcedencia.setInventarioEquiposCollection(new ArrayList<InventarioEquipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orden idOrden = tipoProcedencia.getIdOrden();
            if (idOrden != null) {
                idOrden = em.getReference(idOrden.getClass(), idOrden.getIdOrden());
                tipoProcedencia.setIdOrden(idOrden);
            }
            Collection<InventarioEquipos> attachedInventarioEquiposCollection = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquiposToAttach : tipoProcedencia.getInventarioEquiposCollection()) {
                inventarioEquiposCollectionInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollection.add(inventarioEquiposCollectionInventarioEquiposToAttach);
            }
            tipoProcedencia.setInventarioEquiposCollection(attachedInventarioEquiposCollection);
            em.persist(tipoProcedencia);
            if (idOrden != null) {
                idOrden.getTipoProcedenciaCollection().add(tipoProcedencia);
                idOrden = em.merge(idOrden);
            }
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : tipoProcedencia.getInventarioEquiposCollection()) {
                TipoProcedencia oldIdTipoProcedenciaOfInventarioEquiposCollectionInventarioEquipos = inventarioEquiposCollectionInventarioEquipos.getIdTipoProcedencia();
                inventarioEquiposCollectionInventarioEquipos.setIdTipoProcedencia(tipoProcedencia);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
                if (oldIdTipoProcedenciaOfInventarioEquiposCollectionInventarioEquipos != null) {
                    oldIdTipoProcedenciaOfInventarioEquiposCollectionInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionInventarioEquipos);
                    oldIdTipoProcedenciaOfInventarioEquiposCollectionInventarioEquipos = em.merge(oldIdTipoProcedenciaOfInventarioEquiposCollectionInventarioEquipos);
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

    public void edit(TipoProcedencia tipoProcedencia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoProcedencia persistentTipoProcedencia = em.find(TipoProcedencia.class, tipoProcedencia.getIdTipoProcedencia());
            Orden idOrdenOld = persistentTipoProcedencia.getIdOrden();
            Orden idOrdenNew = tipoProcedencia.getIdOrden();
            Collection<InventarioEquipos> inventarioEquiposCollectionOld = persistentTipoProcedencia.getInventarioEquiposCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionNew = tipoProcedencia.getInventarioEquiposCollection();
            if (idOrdenNew != null) {
                idOrdenNew = em.getReference(idOrdenNew.getClass(), idOrdenNew.getIdOrden());
                tipoProcedencia.setIdOrden(idOrdenNew);
            }
            Collection<InventarioEquipos> attachedInventarioEquiposCollectionNew = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquiposToAttach : inventarioEquiposCollectionNew) {
                inventarioEquiposCollectionNewInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionNewInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionNewInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollectionNew.add(inventarioEquiposCollectionNewInventarioEquiposToAttach);
            }
            inventarioEquiposCollectionNew = attachedInventarioEquiposCollectionNew;
            tipoProcedencia.setInventarioEquiposCollection(inventarioEquiposCollectionNew);
            tipoProcedencia = em.merge(tipoProcedencia);
            if (idOrdenOld != null && !idOrdenOld.equals(idOrdenNew)) {
                idOrdenOld.getTipoProcedenciaCollection().remove(tipoProcedencia);
                idOrdenOld = em.merge(idOrdenOld);
            }
            if (idOrdenNew != null && !idOrdenNew.equals(idOrdenOld)) {
                idOrdenNew.getTipoProcedenciaCollection().add(tipoProcedencia);
                idOrdenNew = em.merge(idOrdenNew);
            }
            for (InventarioEquipos inventarioEquiposCollectionOldInventarioEquipos : inventarioEquiposCollectionOld) {
                if (!inventarioEquiposCollectionNew.contains(inventarioEquiposCollectionOldInventarioEquipos)) {
                    inventarioEquiposCollectionOldInventarioEquipos.setIdTipoProcedencia(null);
                    inventarioEquiposCollectionOldInventarioEquipos = em.merge(inventarioEquiposCollectionOldInventarioEquipos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquipos : inventarioEquiposCollectionNew) {
                if (!inventarioEquiposCollectionOld.contains(inventarioEquiposCollectionNewInventarioEquipos)) {
                    TipoProcedencia oldIdTipoProcedenciaOfInventarioEquiposCollectionNewInventarioEquipos = inventarioEquiposCollectionNewInventarioEquipos.getIdTipoProcedencia();
                    inventarioEquiposCollectionNewInventarioEquipos.setIdTipoProcedencia(tipoProcedencia);
                    inventarioEquiposCollectionNewInventarioEquipos = em.merge(inventarioEquiposCollectionNewInventarioEquipos);
                    if (oldIdTipoProcedenciaOfInventarioEquiposCollectionNewInventarioEquipos != null && !oldIdTipoProcedenciaOfInventarioEquiposCollectionNewInventarioEquipos.equals(tipoProcedencia)) {
                        oldIdTipoProcedenciaOfInventarioEquiposCollectionNewInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionNewInventarioEquipos);
                        oldIdTipoProcedenciaOfInventarioEquiposCollectionNewInventarioEquipos = em.merge(oldIdTipoProcedenciaOfInventarioEquiposCollectionNewInventarioEquipos);
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
                Integer id = tipoProcedencia.getIdTipoProcedencia();
                if (findTipoProcedencia(id) == null) {
                    throw new NonexistentEntityException("The tipoProcedencia with id " + id + " no longer exists.");
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
            TipoProcedencia tipoProcedencia;
            try {
                tipoProcedencia = em.getReference(TipoProcedencia.class, id);
                tipoProcedencia.getIdTipoProcedencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProcedencia with id " + id + " no longer exists.", enfe);
            }
            Orden idOrden = tipoProcedencia.getIdOrden();
            if (idOrden != null) {
                idOrden.getTipoProcedenciaCollection().remove(tipoProcedencia);
                idOrden = em.merge(idOrden);
            }
            Collection<InventarioEquipos> inventarioEquiposCollection = tipoProcedencia.getInventarioEquiposCollection();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : inventarioEquiposCollection) {
                inventarioEquiposCollectionInventarioEquipos.setIdTipoProcedencia(null);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
            }
            em.remove(tipoProcedencia);
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

    public List<TipoProcedencia> findTipoProcedenciaEntities() {
        return findTipoProcedenciaEntities(true, -1, -1);
    }

    public List<TipoProcedencia> findTipoProcedenciaEntities(int maxResults, int firstResult) {
        return findTipoProcedenciaEntities(false, maxResults, firstResult);
    }

    private List<TipoProcedencia> findTipoProcedenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProcedencia.class));
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

    public TipoProcedencia findTipoProcedencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProcedencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProcedenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProcedencia> rt = cq.from(TipoProcedencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
