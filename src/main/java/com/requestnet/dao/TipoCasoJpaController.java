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
import com.requestnet.entidades.Casos;
import com.requestnet.entidades.TipoCaso;
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
public class TipoCasoJpaController implements Serializable {

    public TipoCasoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCaso tipoCaso) throws RollbackFailureException, Exception {
        if (tipoCaso.getCasosCollection() == null) {
            tipoCaso.setCasosCollection(new ArrayList<Casos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Casos> attachedCasosCollection = new ArrayList<Casos>();
            for (Casos casosCollectionCasosToAttach : tipoCaso.getCasosCollection()) {
                casosCollectionCasosToAttach = em.getReference(casosCollectionCasosToAttach.getClass(), casosCollectionCasosToAttach.getIdCaso());
                attachedCasosCollection.add(casosCollectionCasosToAttach);
            }
            tipoCaso.setCasosCollection(attachedCasosCollection);
            em.persist(tipoCaso);
            for (Casos casosCollectionCasos : tipoCaso.getCasosCollection()) {
                TipoCaso oldIdTipoCasoOfCasosCollectionCasos = casosCollectionCasos.getIdTipoCaso();
                casosCollectionCasos.setIdTipoCaso(tipoCaso);
                casosCollectionCasos = em.merge(casosCollectionCasos);
                if (oldIdTipoCasoOfCasosCollectionCasos != null) {
                    oldIdTipoCasoOfCasosCollectionCasos.getCasosCollection().remove(casosCollectionCasos);
                    oldIdTipoCasoOfCasosCollectionCasos = em.merge(oldIdTipoCasoOfCasosCollectionCasos);
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

    public void edit(TipoCaso tipoCaso) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoCaso persistentTipoCaso = em.find(TipoCaso.class, tipoCaso.getIdTipoCaso());
            Collection<Casos> casosCollectionOld = persistentTipoCaso.getCasosCollection();
            Collection<Casos> casosCollectionNew = tipoCaso.getCasosCollection();
            Collection<Casos> attachedCasosCollectionNew = new ArrayList<Casos>();
            for (Casos casosCollectionNewCasosToAttach : casosCollectionNew) {
                casosCollectionNewCasosToAttach = em.getReference(casosCollectionNewCasosToAttach.getClass(), casosCollectionNewCasosToAttach.getIdCaso());
                attachedCasosCollectionNew.add(casosCollectionNewCasosToAttach);
            }
            casosCollectionNew = attachedCasosCollectionNew;
            tipoCaso.setCasosCollection(casosCollectionNew);
            tipoCaso = em.merge(tipoCaso);
            for (Casos casosCollectionOldCasos : casosCollectionOld) {
                if (!casosCollectionNew.contains(casosCollectionOldCasos)) {
                    casosCollectionOldCasos.setIdTipoCaso(null);
                    casosCollectionOldCasos = em.merge(casosCollectionOldCasos);
                }
            }
            for (Casos casosCollectionNewCasos : casosCollectionNew) {
                if (!casosCollectionOld.contains(casosCollectionNewCasos)) {
                    TipoCaso oldIdTipoCasoOfCasosCollectionNewCasos = casosCollectionNewCasos.getIdTipoCaso();
                    casosCollectionNewCasos.setIdTipoCaso(tipoCaso);
                    casosCollectionNewCasos = em.merge(casosCollectionNewCasos);
                    if (oldIdTipoCasoOfCasosCollectionNewCasos != null && !oldIdTipoCasoOfCasosCollectionNewCasos.equals(tipoCaso)) {
                        oldIdTipoCasoOfCasosCollectionNewCasos.getCasosCollection().remove(casosCollectionNewCasos);
                        oldIdTipoCasoOfCasosCollectionNewCasos = em.merge(oldIdTipoCasoOfCasosCollectionNewCasos);
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
                Integer id = tipoCaso.getIdTipoCaso();
                if (findTipoCaso(id) == null) {
                    throw new NonexistentEntityException("The tipoCaso with id " + id + " no longer exists.");
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
            TipoCaso tipoCaso;
            try {
                tipoCaso = em.getReference(TipoCaso.class, id);
                tipoCaso.getIdTipoCaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCaso with id " + id + " no longer exists.", enfe);
            }
            Collection<Casos> casosCollection = tipoCaso.getCasosCollection();
            for (Casos casosCollectionCasos : casosCollection) {
                casosCollectionCasos.setIdTipoCaso(null);
                casosCollectionCasos = em.merge(casosCollectionCasos);
            }
            em.remove(tipoCaso);
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

    public List<TipoCaso> findTipoCasoEntities() {
        return findTipoCasoEntities(true, -1, -1);
    }

    public List<TipoCaso> findTipoCasoEntities(int maxResults, int firstResult) {
        return findTipoCasoEntities(false, maxResults, firstResult);
    }

    private List<TipoCaso> findTipoCasoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCaso.class));
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

    public TipoCaso findTipoCaso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCaso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCasoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCaso> rt = cq.from(TipoCaso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
