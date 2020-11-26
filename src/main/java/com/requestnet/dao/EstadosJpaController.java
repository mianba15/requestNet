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
import com.requestnet.entidades.Estados;
import java.util.ArrayList;
import java.util.Collection;
import com.requestnet.entidades.Orden;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class EstadosJpaController implements Serializable {

    public EstadosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estados estados) throws RollbackFailureException, Exception {
        if (estados.getCasosCollection() == null) {
            estados.setCasosCollection(new ArrayList<Casos>());
        }
        if (estados.getOrdenCollection() == null) {
            estados.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Casos> attachedCasosCollection = new ArrayList<Casos>();
            for (Casos casosCollectionCasosToAttach : estados.getCasosCollection()) {
                casosCollectionCasosToAttach = em.getReference(casosCollectionCasosToAttach.getClass(), casosCollectionCasosToAttach.getIdCaso());
                attachedCasosCollection.add(casosCollectionCasosToAttach);
            }
            estados.setCasosCollection(attachedCasosCollection);
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : estados.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getIdOrden());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            estados.setOrdenCollection(attachedOrdenCollection);
            em.persist(estados);
            for (Casos casosCollectionCasos : estados.getCasosCollection()) {
                Estados oldIdEstadoOfCasosCollectionCasos = casosCollectionCasos.getIdEstado();
                casosCollectionCasos.setIdEstado(estados);
                casosCollectionCasos = em.merge(casosCollectionCasos);
                if (oldIdEstadoOfCasosCollectionCasos != null) {
                    oldIdEstadoOfCasosCollectionCasos.getCasosCollection().remove(casosCollectionCasos);
                    oldIdEstadoOfCasosCollectionCasos = em.merge(oldIdEstadoOfCasosCollectionCasos);
                }
            }
            for (Orden ordenCollectionOrden : estados.getOrdenCollection()) {
                Estados oldIdEstadoOfOrdenCollectionOrden = ordenCollectionOrden.getIdEstado();
                ordenCollectionOrden.setIdEstado(estados);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldIdEstadoOfOrdenCollectionOrden != null) {
                    oldIdEstadoOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldIdEstadoOfOrdenCollectionOrden = em.merge(oldIdEstadoOfOrdenCollectionOrden);
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

    public void edit(Estados estados) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estados persistentEstados = em.find(Estados.class, estados.getIdEstado());
            Collection<Casos> casosCollectionOld = persistentEstados.getCasosCollection();
            Collection<Casos> casosCollectionNew = estados.getCasosCollection();
            Collection<Orden> ordenCollectionOld = persistentEstados.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = estados.getOrdenCollection();
            Collection<Casos> attachedCasosCollectionNew = new ArrayList<Casos>();
            for (Casos casosCollectionNewCasosToAttach : casosCollectionNew) {
                casosCollectionNewCasosToAttach = em.getReference(casosCollectionNewCasosToAttach.getClass(), casosCollectionNewCasosToAttach.getIdCaso());
                attachedCasosCollectionNew.add(casosCollectionNewCasosToAttach);
            }
            casosCollectionNew = attachedCasosCollectionNew;
            estados.setCasosCollection(casosCollectionNew);
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getIdOrden());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            estados.setOrdenCollection(ordenCollectionNew);
            estados = em.merge(estados);
            for (Casos casosCollectionOldCasos : casosCollectionOld) {
                if (!casosCollectionNew.contains(casosCollectionOldCasos)) {
                    casosCollectionOldCasos.setIdEstado(null);
                    casosCollectionOldCasos = em.merge(casosCollectionOldCasos);
                }
            }
            for (Casos casosCollectionNewCasos : casosCollectionNew) {
                if (!casosCollectionOld.contains(casosCollectionNewCasos)) {
                    Estados oldIdEstadoOfCasosCollectionNewCasos = casosCollectionNewCasos.getIdEstado();
                    casosCollectionNewCasos.setIdEstado(estados);
                    casosCollectionNewCasos = em.merge(casosCollectionNewCasos);
                    if (oldIdEstadoOfCasosCollectionNewCasos != null && !oldIdEstadoOfCasosCollectionNewCasos.equals(estados)) {
                        oldIdEstadoOfCasosCollectionNewCasos.getCasosCollection().remove(casosCollectionNewCasos);
                        oldIdEstadoOfCasosCollectionNewCasos = em.merge(oldIdEstadoOfCasosCollectionNewCasos);
                    }
                }
            }
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    ordenCollectionOldOrden.setIdEstado(null);
                    ordenCollectionOldOrden = em.merge(ordenCollectionOldOrden);
                }
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Estados oldIdEstadoOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getIdEstado();
                    ordenCollectionNewOrden.setIdEstado(estados);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldIdEstadoOfOrdenCollectionNewOrden != null && !oldIdEstadoOfOrdenCollectionNewOrden.equals(estados)) {
                        oldIdEstadoOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldIdEstadoOfOrdenCollectionNewOrden = em.merge(oldIdEstadoOfOrdenCollectionNewOrden);
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
                Integer id = estados.getIdEstado();
                if (findEstados(id) == null) {
                    throw new NonexistentEntityException("The estados with id " + id + " no longer exists.");
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
            Estados estados;
            try {
                estados = em.getReference(Estados.class, id);
                estados.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estados with id " + id + " no longer exists.", enfe);
            }
            Collection<Casos> casosCollection = estados.getCasosCollection();
            for (Casos casosCollectionCasos : casosCollection) {
                casosCollectionCasos.setIdEstado(null);
                casosCollectionCasos = em.merge(casosCollectionCasos);
            }
            Collection<Orden> ordenCollection = estados.getOrdenCollection();
            for (Orden ordenCollectionOrden : ordenCollection) {
                ordenCollectionOrden.setIdEstado(null);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
            }
            em.remove(estados);
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

    public List<Estados> findEstadosEntities() {
        return findEstadosEntities(true, -1, -1);
    }

    public List<Estados> findEstadosEntities(int maxResults, int firstResult) {
        return findEstadosEntities(false, maxResults, firstResult);
    }

    private List<Estados> findEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estados.class));
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

    public Estados findEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estados> rt = cq.from(Estados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
