/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.NonexistentEntityException;
import com.requestnet.dao.exceptions.PreexistingEntityException;
import com.requestnet.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.requestnet.entidades.Usuarios;
import com.requestnet.entidades.Casos;
import com.requestnet.entidades.Tecnico;
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
public class TecnicoJpaController implements Serializable {

    public TecnicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tecnico tecnico) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tecnico.getCasosCollection() == null) {
            tecnico.setCasosCollection(new ArrayList<Casos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios idUsuario = tecnico.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                tecnico.setIdUsuario(idUsuario);
            }
            Collection<Casos> attachedCasosCollection = new ArrayList<Casos>();
            for (Casos casosCollectionCasosToAttach : tecnico.getCasosCollection()) {
                casosCollectionCasosToAttach = em.getReference(casosCollectionCasosToAttach.getClass(), casosCollectionCasosToAttach.getIdCaso());
                attachedCasosCollection.add(casosCollectionCasosToAttach);
            }
            tecnico.setCasosCollection(attachedCasosCollection);
            em.persist(tecnico);
            if (idUsuario != null) {
                idUsuario.getTecnicoCollection().add(tecnico);
                idUsuario = em.merge(idUsuario);
            }
            for (Casos casosCollectionCasos : tecnico.getCasosCollection()) {
                Tecnico oldIdTecnicoOfCasosCollectionCasos = casosCollectionCasos.getIdTecnico();
                casosCollectionCasos.setIdTecnico(tecnico);
                casosCollectionCasos = em.merge(casosCollectionCasos);
                if (oldIdTecnicoOfCasosCollectionCasos != null) {
                    oldIdTecnicoOfCasosCollectionCasos.getCasosCollection().remove(casosCollectionCasos);
                    oldIdTecnicoOfCasosCollectionCasos = em.merge(oldIdTecnicoOfCasosCollectionCasos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTecnico(tecnico.getIdTecnico()) != null) {
                throw new PreexistingEntityException("Tecnico " + tecnico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tecnico tecnico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tecnico persistentTecnico = em.find(Tecnico.class, tecnico.getIdTecnico());
            Usuarios idUsuarioOld = persistentTecnico.getIdUsuario();
            Usuarios idUsuarioNew = tecnico.getIdUsuario();
            Collection<Casos> casosCollectionOld = persistentTecnico.getCasosCollection();
            Collection<Casos> casosCollectionNew = tecnico.getCasosCollection();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                tecnico.setIdUsuario(idUsuarioNew);
            }
            Collection<Casos> attachedCasosCollectionNew = new ArrayList<Casos>();
            for (Casos casosCollectionNewCasosToAttach : casosCollectionNew) {
                casosCollectionNewCasosToAttach = em.getReference(casosCollectionNewCasosToAttach.getClass(), casosCollectionNewCasosToAttach.getIdCaso());
                attachedCasosCollectionNew.add(casosCollectionNewCasosToAttach);
            }
            casosCollectionNew = attachedCasosCollectionNew;
            tecnico.setCasosCollection(casosCollectionNew);
            tecnico = em.merge(tecnico);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getTecnicoCollection().remove(tecnico);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getTecnicoCollection().add(tecnico);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Casos casosCollectionOldCasos : casosCollectionOld) {
                if (!casosCollectionNew.contains(casosCollectionOldCasos)) {
                    casosCollectionOldCasos.setIdTecnico(null);
                    casosCollectionOldCasos = em.merge(casosCollectionOldCasos);
                }
            }
            for (Casos casosCollectionNewCasos : casosCollectionNew) {
                if (!casosCollectionOld.contains(casosCollectionNewCasos)) {
                    Tecnico oldIdTecnicoOfCasosCollectionNewCasos = casosCollectionNewCasos.getIdTecnico();
                    casosCollectionNewCasos.setIdTecnico(tecnico);
                    casosCollectionNewCasos = em.merge(casosCollectionNewCasos);
                    if (oldIdTecnicoOfCasosCollectionNewCasos != null && !oldIdTecnicoOfCasosCollectionNewCasos.equals(tecnico)) {
                        oldIdTecnicoOfCasosCollectionNewCasos.getCasosCollection().remove(casosCollectionNewCasos);
                        oldIdTecnicoOfCasosCollectionNewCasos = em.merge(oldIdTecnicoOfCasosCollectionNewCasos);
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
                Integer id = tecnico.getIdTecnico();
                if (findTecnico(id) == null) {
                    throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.");
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
            Tecnico tecnico;
            try {
                tecnico = em.getReference(Tecnico.class, id);
                tecnico.getIdTecnico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = tecnico.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getTecnicoCollection().remove(tecnico);
                idUsuario = em.merge(idUsuario);
            }
            Collection<Casos> casosCollection = tecnico.getCasosCollection();
            for (Casos casosCollectionCasos : casosCollection) {
                casosCollectionCasos.setIdTecnico(null);
                casosCollectionCasos = em.merge(casosCollectionCasos);
            }
            em.remove(tecnico);
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

    public List<Tecnico> findTecnicoEntities() {
        return findTecnicoEntities(true, -1, -1);
    }

    public List<Tecnico> findTecnicoEntities(int maxResults, int firstResult) {
        return findTecnicoEntities(false, maxResults, firstResult);
    }

    private List<Tecnico> findTecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tecnico.class));
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

    public Tecnico findTecnico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tecnico> rt = cq.from(Tecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
