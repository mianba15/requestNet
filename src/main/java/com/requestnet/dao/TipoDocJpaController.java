/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.NonexistentEntityException;
import com.requestnet.dao.exceptions.RollbackFailureException;
import com.requestnet.entidades.TipoDoc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.requestnet.entidades.Usuarios;
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
public class TipoDocJpaController implements Serializable {

    public TipoDocJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDoc tipoDoc) throws RollbackFailureException, Exception {
        if (tipoDoc.getUsuariosCollection() == null) {
            tipoDoc.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : tipoDoc.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            tipoDoc.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(tipoDoc);
            for (Usuarios usuariosCollectionUsuarios : tipoDoc.getUsuariosCollection()) {
                TipoDoc oldIdTipdocOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getIdTipdoc();
                usuariosCollectionUsuarios.setIdTipdoc(tipoDoc);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldIdTipdocOfUsuariosCollectionUsuarios != null) {
                    oldIdTipdocOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldIdTipdocOfUsuariosCollectionUsuarios = em.merge(oldIdTipdocOfUsuariosCollectionUsuarios);
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

    public void edit(TipoDoc tipoDoc) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoDoc persistentTipoDoc = em.find(TipoDoc.class, tipoDoc.getIdTipdoc());
            Collection<Usuarios> usuariosCollectionOld = persistentTipoDoc.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = tipoDoc.getUsuariosCollection();
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            tipoDoc.setUsuariosCollection(usuariosCollectionNew);
            tipoDoc = em.merge(tipoDoc);
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.setIdTipdoc(null);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    TipoDoc oldIdTipdocOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getIdTipdoc();
                    usuariosCollectionNewUsuarios.setIdTipdoc(tipoDoc);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldIdTipdocOfUsuariosCollectionNewUsuarios != null && !oldIdTipdocOfUsuariosCollectionNewUsuarios.equals(tipoDoc)) {
                        oldIdTipdocOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldIdTipdocOfUsuariosCollectionNewUsuarios = em.merge(oldIdTipdocOfUsuariosCollectionNewUsuarios);
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
                Integer id = tipoDoc.getIdTipdoc();
                if (findTipoDoc(id) == null) {
                    throw new NonexistentEntityException("The tipoDoc with id " + id + " no longer exists.");
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
            TipoDoc tipoDoc;
            try {
                tipoDoc = em.getReference(TipoDoc.class, id);
                tipoDoc.getIdTipdoc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDoc with id " + id + " no longer exists.", enfe);
            }
            Collection<Usuarios> usuariosCollection = tipoDoc.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.setIdTipdoc(null);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            em.remove(tipoDoc);
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

    public List<TipoDoc> findTipoDocEntities() {
        return findTipoDocEntities(true, -1, -1);
    }

    public List<TipoDoc> findTipoDocEntities(int maxResults, int firstResult) {
        return findTipoDocEntities(false, maxResults, firstResult);
    }

    private List<TipoDoc> findTipoDocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDoc.class));
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

    public TipoDoc findTipoDoc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDoc.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDoc> rt = cq.from(TipoDoc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
