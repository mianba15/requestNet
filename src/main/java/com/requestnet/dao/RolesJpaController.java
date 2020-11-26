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
import com.requestnet.entidades.EstadosRu;
import com.requestnet.entidades.Roles;
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
public class RolesJpaController implements Serializable {

    public RolesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) throws RollbackFailureException, Exception {
        if (roles.getUsuariosCollection() == null) {
            roles.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadosRu idEstadoru = roles.getIdEstadoru();
            if (idEstadoru != null) {
                idEstadoru = em.getReference(idEstadoru.getClass(), idEstadoru.getIdEstadoru());
                roles.setIdEstadoru(idEstadoru);
            }
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : roles.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            roles.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(roles);
            if (idEstadoru != null) {
                idEstadoru.getRolesCollection().add(roles);
                idEstadoru = em.merge(idEstadoru);
            }
            for (Usuarios usuariosCollectionUsuarios : roles.getUsuariosCollection()) {
                Roles oldIdRolOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getIdRol();
                usuariosCollectionUsuarios.setIdRol(roles);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldIdRolOfUsuariosCollectionUsuarios != null) {
                    oldIdRolOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldIdRolOfUsuariosCollectionUsuarios = em.merge(oldIdRolOfUsuariosCollectionUsuarios);
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

    public void edit(Roles roles) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Roles persistentRoles = em.find(Roles.class, roles.getIdRol());
            EstadosRu idEstadoruOld = persistentRoles.getIdEstadoru();
            EstadosRu idEstadoruNew = roles.getIdEstadoru();
            Collection<Usuarios> usuariosCollectionOld = persistentRoles.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = roles.getUsuariosCollection();
            if (idEstadoruNew != null) {
                idEstadoruNew = em.getReference(idEstadoruNew.getClass(), idEstadoruNew.getIdEstadoru());
                roles.setIdEstadoru(idEstadoruNew);
            }
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            roles.setUsuariosCollection(usuariosCollectionNew);
            roles = em.merge(roles);
            if (idEstadoruOld != null && !idEstadoruOld.equals(idEstadoruNew)) {
                idEstadoruOld.getRolesCollection().remove(roles);
                idEstadoruOld = em.merge(idEstadoruOld);
            }
            if (idEstadoruNew != null && !idEstadoruNew.equals(idEstadoruOld)) {
                idEstadoruNew.getRolesCollection().add(roles);
                idEstadoruNew = em.merge(idEstadoruNew);
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.setIdRol(null);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Roles oldIdRolOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getIdRol();
                    usuariosCollectionNewUsuarios.setIdRol(roles);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldIdRolOfUsuariosCollectionNewUsuarios != null && !oldIdRolOfUsuariosCollectionNewUsuarios.equals(roles)) {
                        oldIdRolOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldIdRolOfUsuariosCollectionNewUsuarios = em.merge(oldIdRolOfUsuariosCollectionNewUsuarios);
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
                Integer id = roles.getIdRol();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            EstadosRu idEstadoru = roles.getIdEstadoru();
            if (idEstadoru != null) {
                idEstadoru.getRolesCollection().remove(roles);
                idEstadoru = em.merge(idEstadoru);
            }
            Collection<Usuarios> usuariosCollection = roles.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.setIdRol(null);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            em.remove(roles);
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

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
