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
import com.requestnet.entidades.Orden;
import com.requestnet.entidades.Proveedores;
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
public class ProveedoresJpaController implements Serializable {

    public ProveedoresJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedores proveedores) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (proveedores.getOrdenCollection() == null) {
            proveedores.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios idUsuario = proveedores.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                proveedores.setIdUsuario(idUsuario);
            }
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : proveedores.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getIdOrden());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            proveedores.setOrdenCollection(attachedOrdenCollection);
            em.persist(proveedores);
            if (idUsuario != null) {
                idUsuario.getProveedoresCollection().add(proveedores);
                idUsuario = em.merge(idUsuario);
            }
            for (Orden ordenCollectionOrden : proveedores.getOrdenCollection()) {
                Proveedores oldIdProveedorOfOrdenCollectionOrden = ordenCollectionOrden.getIdProveedor();
                ordenCollectionOrden.setIdProveedor(proveedores);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldIdProveedorOfOrdenCollectionOrden != null) {
                    oldIdProveedorOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldIdProveedorOfOrdenCollectionOrden = em.merge(oldIdProveedorOfOrdenCollectionOrden);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProveedores(proveedores.getIdProveedor()) != null) {
                throw new PreexistingEntityException("Proveedores " + proveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedores proveedores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedores persistentProveedores = em.find(Proveedores.class, proveedores.getIdProveedor());
            Usuarios idUsuarioOld = persistentProveedores.getIdUsuario();
            Usuarios idUsuarioNew = proveedores.getIdUsuario();
            Collection<Orden> ordenCollectionOld = persistentProveedores.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = proveedores.getOrdenCollection();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                proveedores.setIdUsuario(idUsuarioNew);
            }
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getIdOrden());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            proveedores.setOrdenCollection(ordenCollectionNew);
            proveedores = em.merge(proveedores);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getProveedoresCollection().remove(proveedores);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getProveedoresCollection().add(proveedores);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    ordenCollectionOldOrden.setIdProveedor(null);
                    ordenCollectionOldOrden = em.merge(ordenCollectionOldOrden);
                }
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Proveedores oldIdProveedorOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getIdProveedor();
                    ordenCollectionNewOrden.setIdProveedor(proveedores);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldIdProveedorOfOrdenCollectionNewOrden != null && !oldIdProveedorOfOrdenCollectionNewOrden.equals(proveedores)) {
                        oldIdProveedorOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldIdProveedorOfOrdenCollectionNewOrden = em.merge(oldIdProveedorOfOrdenCollectionNewOrden);
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
                Integer id = proveedores.getIdProveedor();
                if (findProveedores(id) == null) {
                    throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.");
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
            Proveedores proveedores;
            try {
                proveedores = em.getReference(Proveedores.class, id);
                proveedores.getIdProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = proveedores.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getProveedoresCollection().remove(proveedores);
                idUsuario = em.merge(idUsuario);
            }
            Collection<Orden> ordenCollection = proveedores.getOrdenCollection();
            for (Orden ordenCollectionOrden : ordenCollection) {
                ordenCollectionOrden.setIdProveedor(null);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
            }
            em.remove(proveedores);
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

    public List<Proveedores> findProveedoresEntities() {
        return findProveedoresEntities(true, -1, -1);
    }

    public List<Proveedores> findProveedoresEntities(int maxResults, int firstResult) {
        return findProveedoresEntities(false, maxResults, firstResult);
    }

    private List<Proveedores> findProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedores.class));
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

    public Proveedores findProveedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedores> rt = cq.from(Proveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
