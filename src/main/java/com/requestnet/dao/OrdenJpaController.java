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
import com.requestnet.entidades.Proveedores;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.TipoOrden;
import com.requestnet.entidades.Estados;
import com.requestnet.entidades.Orden;
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
public class OrdenJpaController implements Serializable {

    public OrdenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orden orden) throws RollbackFailureException, Exception {
        if (orden.getTipoProcedenciaCollection() == null) {
            orden.setTipoProcedenciaCollection(new ArrayList<TipoProcedencia>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedores idProveedor = orden.getIdProveedor();
            if (idProveedor != null) {
                idProveedor = em.getReference(idProveedor.getClass(), idProveedor.getIdProveedor());
                orden.setIdProveedor(idProveedor);
            }
            Liderinventario idLider = orden.getIdLider();
            if (idLider != null) {
                idLider = em.getReference(idLider.getClass(), idLider.getIdLider());
                orden.setIdLider(idLider);
            }
            Jefeinfraestructura idJefe = orden.getIdJefe();
            if (idJefe != null) {
                idJefe = em.getReference(idJefe.getClass(), idJefe.getIdJefe());
                orden.setIdJefe(idJefe);
            }
            TipoOrden idTipoOrden = orden.getIdTipoOrden();
            if (idTipoOrden != null) {
                idTipoOrden = em.getReference(idTipoOrden.getClass(), idTipoOrden.getIdTipoOrden());
                orden.setIdTipoOrden(idTipoOrden);
            }
            Estados idEstado = orden.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                orden.setIdEstado(idEstado);
            }
            Collection<TipoProcedencia> attachedTipoProcedenciaCollection = new ArrayList<TipoProcedencia>();
            for (TipoProcedencia tipoProcedenciaCollectionTipoProcedenciaToAttach : orden.getTipoProcedenciaCollection()) {
                tipoProcedenciaCollectionTipoProcedenciaToAttach = em.getReference(tipoProcedenciaCollectionTipoProcedenciaToAttach.getClass(), tipoProcedenciaCollectionTipoProcedenciaToAttach.getIdTipoProcedencia());
                attachedTipoProcedenciaCollection.add(tipoProcedenciaCollectionTipoProcedenciaToAttach);
            }
            orden.setTipoProcedenciaCollection(attachedTipoProcedenciaCollection);
            em.persist(orden);
            if (idProveedor != null) {
                idProveedor.getOrdenCollection().add(orden);
                idProveedor = em.merge(idProveedor);
            }
            if (idLider != null) {
                idLider.getOrdenCollection().add(orden);
                idLider = em.merge(idLider);
            }
            if (idJefe != null) {
                idJefe.getOrdenCollection().add(orden);
                idJefe = em.merge(idJefe);
            }
            if (idTipoOrden != null) {
                idTipoOrden.getOrdenCollection().add(orden);
                idTipoOrden = em.merge(idTipoOrden);
            }
            if (idEstado != null) {
                idEstado.getOrdenCollection().add(orden);
                idEstado = em.merge(idEstado);
            }
            for (TipoProcedencia tipoProcedenciaCollectionTipoProcedencia : orden.getTipoProcedenciaCollection()) {
                Orden oldIdOrdenOfTipoProcedenciaCollectionTipoProcedencia = tipoProcedenciaCollectionTipoProcedencia.getIdOrden();
                tipoProcedenciaCollectionTipoProcedencia.setIdOrden(orden);
                tipoProcedenciaCollectionTipoProcedencia = em.merge(tipoProcedenciaCollectionTipoProcedencia);
                if (oldIdOrdenOfTipoProcedenciaCollectionTipoProcedencia != null) {
                    oldIdOrdenOfTipoProcedenciaCollectionTipoProcedencia.getTipoProcedenciaCollection().remove(tipoProcedenciaCollectionTipoProcedencia);
                    oldIdOrdenOfTipoProcedenciaCollectionTipoProcedencia = em.merge(oldIdOrdenOfTipoProcedenciaCollectionTipoProcedencia);
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

    public void edit(Orden orden) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orden persistentOrden = em.find(Orden.class, orden.getIdOrden());
            Proveedores idProveedorOld = persistentOrden.getIdProveedor();
            Proveedores idProveedorNew = orden.getIdProveedor();
            Liderinventario idLiderOld = persistentOrden.getIdLider();
            Liderinventario idLiderNew = orden.getIdLider();
            Jefeinfraestructura idJefeOld = persistentOrden.getIdJefe();
            Jefeinfraestructura idJefeNew = orden.getIdJefe();
            TipoOrden idTipoOrdenOld = persistentOrden.getIdTipoOrden();
            TipoOrden idTipoOrdenNew = orden.getIdTipoOrden();
            Estados idEstadoOld = persistentOrden.getIdEstado();
            Estados idEstadoNew = orden.getIdEstado();
            Collection<TipoProcedencia> tipoProcedenciaCollectionOld = persistentOrden.getTipoProcedenciaCollection();
            Collection<TipoProcedencia> tipoProcedenciaCollectionNew = orden.getTipoProcedenciaCollection();
            if (idProveedorNew != null) {
                idProveedorNew = em.getReference(idProveedorNew.getClass(), idProveedorNew.getIdProveedor());
                orden.setIdProveedor(idProveedorNew);
            }
            if (idLiderNew != null) {
                idLiderNew = em.getReference(idLiderNew.getClass(), idLiderNew.getIdLider());
                orden.setIdLider(idLiderNew);
            }
            if (idJefeNew != null) {
                idJefeNew = em.getReference(idJefeNew.getClass(), idJefeNew.getIdJefe());
                orden.setIdJefe(idJefeNew);
            }
            if (idTipoOrdenNew != null) {
                idTipoOrdenNew = em.getReference(idTipoOrdenNew.getClass(), idTipoOrdenNew.getIdTipoOrden());
                orden.setIdTipoOrden(idTipoOrdenNew);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                orden.setIdEstado(idEstadoNew);
            }
            Collection<TipoProcedencia> attachedTipoProcedenciaCollectionNew = new ArrayList<TipoProcedencia>();
            for (TipoProcedencia tipoProcedenciaCollectionNewTipoProcedenciaToAttach : tipoProcedenciaCollectionNew) {
                tipoProcedenciaCollectionNewTipoProcedenciaToAttach = em.getReference(tipoProcedenciaCollectionNewTipoProcedenciaToAttach.getClass(), tipoProcedenciaCollectionNewTipoProcedenciaToAttach.getIdTipoProcedencia());
                attachedTipoProcedenciaCollectionNew.add(tipoProcedenciaCollectionNewTipoProcedenciaToAttach);
            }
            tipoProcedenciaCollectionNew = attachedTipoProcedenciaCollectionNew;
            orden.setTipoProcedenciaCollection(tipoProcedenciaCollectionNew);
            orden = em.merge(orden);
            if (idProveedorOld != null && !idProveedorOld.equals(idProveedorNew)) {
                idProveedorOld.getOrdenCollection().remove(orden);
                idProveedorOld = em.merge(idProveedorOld);
            }
            if (idProveedorNew != null && !idProveedorNew.equals(idProveedorOld)) {
                idProveedorNew.getOrdenCollection().add(orden);
                idProveedorNew = em.merge(idProveedorNew);
            }
            if (idLiderOld != null && !idLiderOld.equals(idLiderNew)) {
                idLiderOld.getOrdenCollection().remove(orden);
                idLiderOld = em.merge(idLiderOld);
            }
            if (idLiderNew != null && !idLiderNew.equals(idLiderOld)) {
                idLiderNew.getOrdenCollection().add(orden);
                idLiderNew = em.merge(idLiderNew);
            }
            if (idJefeOld != null && !idJefeOld.equals(idJefeNew)) {
                idJefeOld.getOrdenCollection().remove(orden);
                idJefeOld = em.merge(idJefeOld);
            }
            if (idJefeNew != null && !idJefeNew.equals(idJefeOld)) {
                idJefeNew.getOrdenCollection().add(orden);
                idJefeNew = em.merge(idJefeNew);
            }
            if (idTipoOrdenOld != null && !idTipoOrdenOld.equals(idTipoOrdenNew)) {
                idTipoOrdenOld.getOrdenCollection().remove(orden);
                idTipoOrdenOld = em.merge(idTipoOrdenOld);
            }
            if (idTipoOrdenNew != null && !idTipoOrdenNew.equals(idTipoOrdenOld)) {
                idTipoOrdenNew.getOrdenCollection().add(orden);
                idTipoOrdenNew = em.merge(idTipoOrdenNew);
            }
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getOrdenCollection().remove(orden);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getOrdenCollection().add(orden);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (TipoProcedencia tipoProcedenciaCollectionOldTipoProcedencia : tipoProcedenciaCollectionOld) {
                if (!tipoProcedenciaCollectionNew.contains(tipoProcedenciaCollectionOldTipoProcedencia)) {
                    tipoProcedenciaCollectionOldTipoProcedencia.setIdOrden(null);
                    tipoProcedenciaCollectionOldTipoProcedencia = em.merge(tipoProcedenciaCollectionOldTipoProcedencia);
                }
            }
            for (TipoProcedencia tipoProcedenciaCollectionNewTipoProcedencia : tipoProcedenciaCollectionNew) {
                if (!tipoProcedenciaCollectionOld.contains(tipoProcedenciaCollectionNewTipoProcedencia)) {
                    Orden oldIdOrdenOfTipoProcedenciaCollectionNewTipoProcedencia = tipoProcedenciaCollectionNewTipoProcedencia.getIdOrden();
                    tipoProcedenciaCollectionNewTipoProcedencia.setIdOrden(orden);
                    tipoProcedenciaCollectionNewTipoProcedencia = em.merge(tipoProcedenciaCollectionNewTipoProcedencia);
                    if (oldIdOrdenOfTipoProcedenciaCollectionNewTipoProcedencia != null && !oldIdOrdenOfTipoProcedenciaCollectionNewTipoProcedencia.equals(orden)) {
                        oldIdOrdenOfTipoProcedenciaCollectionNewTipoProcedencia.getTipoProcedenciaCollection().remove(tipoProcedenciaCollectionNewTipoProcedencia);
                        oldIdOrdenOfTipoProcedenciaCollectionNewTipoProcedencia = em.merge(oldIdOrdenOfTipoProcedenciaCollectionNewTipoProcedencia);
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
                Integer id = orden.getIdOrden();
                if (findOrden(id) == null) {
                    throw new NonexistentEntityException("The orden with id " + id + " no longer exists.");
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
            Orden orden;
            try {
                orden = em.getReference(Orden.class, id);
                orden.getIdOrden();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orden with id " + id + " no longer exists.", enfe);
            }
            Proveedores idProveedor = orden.getIdProveedor();
            if (idProveedor != null) {
                idProveedor.getOrdenCollection().remove(orden);
                idProveedor = em.merge(idProveedor);
            }
            Liderinventario idLider = orden.getIdLider();
            if (idLider != null) {
                idLider.getOrdenCollection().remove(orden);
                idLider = em.merge(idLider);
            }
            Jefeinfraestructura idJefe = orden.getIdJefe();
            if (idJefe != null) {
                idJefe.getOrdenCollection().remove(orden);
                idJefe = em.merge(idJefe);
            }
            TipoOrden idTipoOrden = orden.getIdTipoOrden();
            if (idTipoOrden != null) {
                idTipoOrden.getOrdenCollection().remove(orden);
                idTipoOrden = em.merge(idTipoOrden);
            }
            Estados idEstado = orden.getIdEstado();
            if (idEstado != null) {
                idEstado.getOrdenCollection().remove(orden);
                idEstado = em.merge(idEstado);
            }
            Collection<TipoProcedencia> tipoProcedenciaCollection = orden.getTipoProcedenciaCollection();
            for (TipoProcedencia tipoProcedenciaCollectionTipoProcedencia : tipoProcedenciaCollection) {
                tipoProcedenciaCollectionTipoProcedencia.setIdOrden(null);
                tipoProcedenciaCollectionTipoProcedencia = em.merge(tipoProcedenciaCollectionTipoProcedencia);
            }
            em.remove(orden);
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

    public List<Orden> findOrdenEntities() {
        return findOrdenEntities(true, -1, -1);
    }

    public List<Orden> findOrdenEntities(int maxResults, int firstResult) {
        return findOrdenEntities(false, maxResults, firstResult);
    }

    private List<Orden> findOrdenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orden.class));
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

    public Orden findOrden(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orden.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orden> rt = cq.from(Orden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
