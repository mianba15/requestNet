/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.NonexistentEntityException;
import com.requestnet.dao.exceptions.RollbackFailureException;
import com.requestnet.entidades.EstadosRu;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.requestnet.entidades.Roles;
import java.util.ArrayList;
import java.util.Collection;
import com.requestnet.entidades.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class EstadosRuJpaController implements Serializable {

    public EstadosRuJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadosRu estadosRu) throws RollbackFailureException, Exception {
        if (estadosRu.getRolesCollection() == null) {
            estadosRu.setRolesCollection(new ArrayList<Roles>());
        }
        if (estadosRu.getUsuariosCollection() == null) {
            estadosRu.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Roles> attachedRolesCollection = new ArrayList<Roles>();
            for (Roles rolesCollectionRolesToAttach : estadosRu.getRolesCollection()) {
                rolesCollectionRolesToAttach = em.getReference(rolesCollectionRolesToAttach.getClass(), rolesCollectionRolesToAttach.getIdRol());
                attachedRolesCollection.add(rolesCollectionRolesToAttach);
            }
            estadosRu.setRolesCollection(attachedRolesCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : estadosRu.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            estadosRu.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(estadosRu);
            for (Roles rolesCollectionRoles : estadosRu.getRolesCollection()) {
                EstadosRu oldIdEstadoruOfRolesCollectionRoles = rolesCollectionRoles.getIdEstadoru();
                rolesCollectionRoles.setIdEstadoru(estadosRu);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
                if (oldIdEstadoruOfRolesCollectionRoles != null) {
                    oldIdEstadoruOfRolesCollectionRoles.getRolesCollection().remove(rolesCollectionRoles);
                    oldIdEstadoruOfRolesCollectionRoles = em.merge(oldIdEstadoruOfRolesCollectionRoles);
                }
            }
            for (Usuarios usuariosCollectionUsuarios : estadosRu.getUsuariosCollection()) {
                EstadosRu oldIdEstadoruOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getIdEstadoru();
                usuariosCollectionUsuarios.setIdEstadoru(estadosRu);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldIdEstadoruOfUsuariosCollectionUsuarios != null) {
                    oldIdEstadoruOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldIdEstadoruOfUsuariosCollectionUsuarios = em.merge(oldIdEstadoruOfUsuariosCollectionUsuarios);
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

    public void edit(EstadosRu estadosRu) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadosRu persistentEstadosRu = em.find(EstadosRu.class, estadosRu.getIdEstadoru());
            Collection<Roles> rolesCollectionOld = persistentEstadosRu.getRolesCollection();
            Collection<Roles> rolesCollectionNew = estadosRu.getRolesCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentEstadosRu.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = estadosRu.getUsuariosCollection();
            Collection<Roles> attachedRolesCollectionNew = new ArrayList<Roles>();
            for (Roles rolesCollectionNewRolesToAttach : rolesCollectionNew) {
                rolesCollectionNewRolesToAttach = em.getReference(rolesCollectionNewRolesToAttach.getClass(), rolesCollectionNewRolesToAttach.getIdRol());
                attachedRolesCollectionNew.add(rolesCollectionNewRolesToAttach);
            }
            rolesCollectionNew = attachedRolesCollectionNew;
            estadosRu.setRolesCollection(rolesCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            estadosRu.setUsuariosCollection(usuariosCollectionNew);
            estadosRu = em.merge(estadosRu);
            for (Roles rolesCollectionOldRoles : rolesCollectionOld) {
                if (!rolesCollectionNew.contains(rolesCollectionOldRoles)) {
                    rolesCollectionOldRoles.setIdEstadoru(null);
                    rolesCollectionOldRoles = em.merge(rolesCollectionOldRoles);
                }
            }
            for (Roles rolesCollectionNewRoles : rolesCollectionNew) {
                if (!rolesCollectionOld.contains(rolesCollectionNewRoles)) {
                    EstadosRu oldIdEstadoruOfRolesCollectionNewRoles = rolesCollectionNewRoles.getIdEstadoru();
                    rolesCollectionNewRoles.setIdEstadoru(estadosRu);
                    rolesCollectionNewRoles = em.merge(rolesCollectionNewRoles);
                    if (oldIdEstadoruOfRolesCollectionNewRoles != null && !oldIdEstadoruOfRolesCollectionNewRoles.equals(estadosRu)) {
                        oldIdEstadoruOfRolesCollectionNewRoles.getRolesCollection().remove(rolesCollectionNewRoles);
                        oldIdEstadoruOfRolesCollectionNewRoles = em.merge(oldIdEstadoruOfRolesCollectionNewRoles);
                    }
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    usuariosCollectionOldUsuarios.setIdEstadoru(null);
                    usuariosCollectionOldUsuarios = em.merge(usuariosCollectionOldUsuarios);
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    EstadosRu oldIdEstadoruOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getIdEstadoru();
                    usuariosCollectionNewUsuarios.setIdEstadoru(estadosRu);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldIdEstadoruOfUsuariosCollectionNewUsuarios != null && !oldIdEstadoruOfUsuariosCollectionNewUsuarios.equals(estadosRu)) {
                        oldIdEstadoruOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldIdEstadoruOfUsuariosCollectionNewUsuarios = em.merge(oldIdEstadoruOfUsuariosCollectionNewUsuarios);
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
                Integer id = estadosRu.getIdEstadoru();
                if (findEstadosRu(id) == null) {
                    throw new NonexistentEntityException("The estadosRu with id " + id + " no longer exists.");
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
            EstadosRu estadosRu;
            try {
                estadosRu = em.getReference(EstadosRu.class, id);
                estadosRu.getIdEstadoru();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadosRu with id " + id + " no longer exists.", enfe);
            }
            Collection<Roles> rolesCollection = estadosRu.getRolesCollection();
            for (Roles rolesCollectionRoles : rolesCollection) {
                rolesCollectionRoles.setIdEstadoru(null);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            Collection<Usuarios> usuariosCollection = estadosRu.getUsuariosCollection();
            for (Usuarios usuariosCollectionUsuarios : usuariosCollection) {
                usuariosCollectionUsuarios.setIdEstadoru(null);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
            }
            em.remove(estadosRu);
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

    public List<EstadosRu> findEstadosRuEntities() {
        return findEstadosRuEntities(true, -1, -1);
    }

    public List<EstadosRu> findEstadosRuEntities(int maxResults, int firstResult) {
        return findEstadosRuEntities(false, maxResults, firstResult);
    }

    private List<EstadosRu> findEstadosRuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadosRu.class));
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

    public EstadosRu findEstadosRu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadosRu.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosRuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadosRu> rt = cq.from(EstadosRu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
