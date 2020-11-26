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
import com.requestnet.entidades.IntegranteColaborador;
import com.requestnet.entidades.TipoComponente;
import com.requestnet.entidades.Marca;
import com.requestnet.entidades.TipoProcedencia;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.EstadoEq;
import com.requestnet.entidades.InventarioEquipos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class InventarioEquiposJpaController implements Serializable {

    public InventarioEquiposJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InventarioEquipos inventarioEquipos) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            IntegranteColaborador idCliente = inventarioEquipos.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                inventarioEquipos.setIdCliente(idCliente);
            }
            TipoComponente idTipoComponente = inventarioEquipos.getIdTipoComponente();
            if (idTipoComponente != null) {
                idTipoComponente = em.getReference(idTipoComponente.getClass(), idTipoComponente.getIdTipoComponente());
                inventarioEquipos.setIdTipoComponente(idTipoComponente);
            }
            Marca idMarca = inventarioEquipos.getIdMarca();
            if (idMarca != null) {
                idMarca = em.getReference(idMarca.getClass(), idMarca.getIdMarca());
                inventarioEquipos.setIdMarca(idMarca);
            }
            TipoProcedencia idTipoProcedencia = inventarioEquipos.getIdTipoProcedencia();
            if (idTipoProcedencia != null) {
                idTipoProcedencia = em.getReference(idTipoProcedencia.getClass(), idTipoProcedencia.getIdTipoProcedencia());
                inventarioEquipos.setIdTipoProcedencia(idTipoProcedencia);
            }
            Jefeinfraestructura idJefe = inventarioEquipos.getIdJefe();
            if (idJefe != null) {
                idJefe = em.getReference(idJefe.getClass(), idJefe.getIdJefe());
                inventarioEquipos.setIdJefe(idJefe);
            }
            Liderinventario idLider = inventarioEquipos.getIdLider();
            if (idLider != null) {
                idLider = em.getReference(idLider.getClass(), idLider.getIdLider());
                inventarioEquipos.setIdLider(idLider);
            }
            EstadoEq idEstadoeq = inventarioEquipos.getIdEstadoeq();
            if (idEstadoeq != null) {
                idEstadoeq = em.getReference(idEstadoeq.getClass(), idEstadoeq.getIdEstadoeq());
                inventarioEquipos.setIdEstadoeq(idEstadoeq);
            }
            em.persist(inventarioEquipos);
            if (idCliente != null) {
                idCliente.getInventarioEquiposCollection().add(inventarioEquipos);
                idCliente = em.merge(idCliente);
            }
            if (idTipoComponente != null) {
                idTipoComponente.getInventarioEquiposCollection().add(inventarioEquipos);
                idTipoComponente = em.merge(idTipoComponente);
            }
            if (idMarca != null) {
                idMarca.getInventarioEquiposCollection().add(inventarioEquipos);
                idMarca = em.merge(idMarca);
            }
            if (idTipoProcedencia != null) {
                idTipoProcedencia.getInventarioEquiposCollection().add(inventarioEquipos);
                idTipoProcedencia = em.merge(idTipoProcedencia);
            }
            if (idJefe != null) {
                idJefe.getInventarioEquiposCollection().add(inventarioEquipos);
                idJefe = em.merge(idJefe);
            }
            if (idLider != null) {
                idLider.getInventarioEquiposCollection().add(inventarioEquipos);
                idLider = em.merge(idLider);
            }
            if (idEstadoeq != null) {
                idEstadoeq.getInventarioEquiposCollection().add(inventarioEquipos);
                idEstadoeq = em.merge(idEstadoeq);
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

    public void edit(InventarioEquipos inventarioEquipos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InventarioEquipos persistentInventarioEquipos = em.find(InventarioEquipos.class, inventarioEquipos.getIdEquipo());
            IntegranteColaborador idClienteOld = persistentInventarioEquipos.getIdCliente();
            IntegranteColaborador idClienteNew = inventarioEquipos.getIdCliente();
            TipoComponente idTipoComponenteOld = persistentInventarioEquipos.getIdTipoComponente();
            TipoComponente idTipoComponenteNew = inventarioEquipos.getIdTipoComponente();
            Marca idMarcaOld = persistentInventarioEquipos.getIdMarca();
            Marca idMarcaNew = inventarioEquipos.getIdMarca();
            TipoProcedencia idTipoProcedenciaOld = persistentInventarioEquipos.getIdTipoProcedencia();
            TipoProcedencia idTipoProcedenciaNew = inventarioEquipos.getIdTipoProcedencia();
            Jefeinfraestructura idJefeOld = persistentInventarioEquipos.getIdJefe();
            Jefeinfraestructura idJefeNew = inventarioEquipos.getIdJefe();
            Liderinventario idLiderOld = persistentInventarioEquipos.getIdLider();
            Liderinventario idLiderNew = inventarioEquipos.getIdLider();
            EstadoEq idEstadoeqOld = persistentInventarioEquipos.getIdEstadoeq();
            EstadoEq idEstadoeqNew = inventarioEquipos.getIdEstadoeq();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                inventarioEquipos.setIdCliente(idClienteNew);
            }
            if (idTipoComponenteNew != null) {
                idTipoComponenteNew = em.getReference(idTipoComponenteNew.getClass(), idTipoComponenteNew.getIdTipoComponente());
                inventarioEquipos.setIdTipoComponente(idTipoComponenteNew);
            }
            if (idMarcaNew != null) {
                idMarcaNew = em.getReference(idMarcaNew.getClass(), idMarcaNew.getIdMarca());
                inventarioEquipos.setIdMarca(idMarcaNew);
            }
            if (idTipoProcedenciaNew != null) {
                idTipoProcedenciaNew = em.getReference(idTipoProcedenciaNew.getClass(), idTipoProcedenciaNew.getIdTipoProcedencia());
                inventarioEquipos.setIdTipoProcedencia(idTipoProcedenciaNew);
            }
            if (idJefeNew != null) {
                idJefeNew = em.getReference(idJefeNew.getClass(), idJefeNew.getIdJefe());
                inventarioEquipos.setIdJefe(idJefeNew);
            }
            if (idLiderNew != null) {
                idLiderNew = em.getReference(idLiderNew.getClass(), idLiderNew.getIdLider());
                inventarioEquipos.setIdLider(idLiderNew);
            }
            if (idEstadoeqNew != null) {
                idEstadoeqNew = em.getReference(idEstadoeqNew.getClass(), idEstadoeqNew.getIdEstadoeq());
                inventarioEquipos.setIdEstadoeq(idEstadoeqNew);
            }
            inventarioEquipos = em.merge(inventarioEquipos);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idTipoComponenteOld != null && !idTipoComponenteOld.equals(idTipoComponenteNew)) {
                idTipoComponenteOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idTipoComponenteOld = em.merge(idTipoComponenteOld);
            }
            if (idTipoComponenteNew != null && !idTipoComponenteNew.equals(idTipoComponenteOld)) {
                idTipoComponenteNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idTipoComponenteNew = em.merge(idTipoComponenteNew);
            }
            if (idMarcaOld != null && !idMarcaOld.equals(idMarcaNew)) {
                idMarcaOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idMarcaOld = em.merge(idMarcaOld);
            }
            if (idMarcaNew != null && !idMarcaNew.equals(idMarcaOld)) {
                idMarcaNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idMarcaNew = em.merge(idMarcaNew);
            }
            if (idTipoProcedenciaOld != null && !idTipoProcedenciaOld.equals(idTipoProcedenciaNew)) {
                idTipoProcedenciaOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idTipoProcedenciaOld = em.merge(idTipoProcedenciaOld);
            }
            if (idTipoProcedenciaNew != null && !idTipoProcedenciaNew.equals(idTipoProcedenciaOld)) {
                idTipoProcedenciaNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idTipoProcedenciaNew = em.merge(idTipoProcedenciaNew);
            }
            if (idJefeOld != null && !idJefeOld.equals(idJefeNew)) {
                idJefeOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idJefeOld = em.merge(idJefeOld);
            }
            if (idJefeNew != null && !idJefeNew.equals(idJefeOld)) {
                idJefeNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idJefeNew = em.merge(idJefeNew);
            }
            if (idLiderOld != null && !idLiderOld.equals(idLiderNew)) {
                idLiderOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idLiderOld = em.merge(idLiderOld);
            }
            if (idLiderNew != null && !idLiderNew.equals(idLiderOld)) {
                idLiderNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idLiderNew = em.merge(idLiderNew);
            }
            if (idEstadoeqOld != null && !idEstadoeqOld.equals(idEstadoeqNew)) {
                idEstadoeqOld.getInventarioEquiposCollection().remove(inventarioEquipos);
                idEstadoeqOld = em.merge(idEstadoeqOld);
            }
            if (idEstadoeqNew != null && !idEstadoeqNew.equals(idEstadoeqOld)) {
                idEstadoeqNew.getInventarioEquiposCollection().add(inventarioEquipos);
                idEstadoeqNew = em.merge(idEstadoeqNew);
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
                Integer id = inventarioEquipos.getIdEquipo();
                if (findInventarioEquipos(id) == null) {
                    throw new NonexistentEntityException("The inventarioEquipos with id " + id + " no longer exists.");
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
            InventarioEquipos inventarioEquipos;
            try {
                inventarioEquipos = em.getReference(InventarioEquipos.class, id);
                inventarioEquipos.getIdEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventarioEquipos with id " + id + " no longer exists.", enfe);
            }
            IntegranteColaborador idCliente = inventarioEquipos.getIdCliente();
            if (idCliente != null) {
                idCliente.getInventarioEquiposCollection().remove(inventarioEquipos);
                idCliente = em.merge(idCliente);
            }
            TipoComponente idTipoComponente = inventarioEquipos.getIdTipoComponente();
            if (idTipoComponente != null) {
                idTipoComponente.getInventarioEquiposCollection().remove(inventarioEquipos);
                idTipoComponente = em.merge(idTipoComponente);
            }
            Marca idMarca = inventarioEquipos.getIdMarca();
            if (idMarca != null) {
                idMarca.getInventarioEquiposCollection().remove(inventarioEquipos);
                idMarca = em.merge(idMarca);
            }
            TipoProcedencia idTipoProcedencia = inventarioEquipos.getIdTipoProcedencia();
            if (idTipoProcedencia != null) {
                idTipoProcedencia.getInventarioEquiposCollection().remove(inventarioEquipos);
                idTipoProcedencia = em.merge(idTipoProcedencia);
            }
            Jefeinfraestructura idJefe = inventarioEquipos.getIdJefe();
            if (idJefe != null) {
                idJefe.getInventarioEquiposCollection().remove(inventarioEquipos);
                idJefe = em.merge(idJefe);
            }
            Liderinventario idLider = inventarioEquipos.getIdLider();
            if (idLider != null) {
                idLider.getInventarioEquiposCollection().remove(inventarioEquipos);
                idLider = em.merge(idLider);
            }
            EstadoEq idEstadoeq = inventarioEquipos.getIdEstadoeq();
            if (idEstadoeq != null) {
                idEstadoeq.getInventarioEquiposCollection().remove(inventarioEquipos);
                idEstadoeq = em.merge(idEstadoeq);
            }
            em.remove(inventarioEquipos);
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

    public List<InventarioEquipos> findInventarioEquiposEntities() {
        return findInventarioEquiposEntities(true, -1, -1);
    }

    public List<InventarioEquipos> findInventarioEquiposEntities(int maxResults, int firstResult) {
        return findInventarioEquiposEntities(false, maxResults, firstResult);
    }

    private List<InventarioEquipos> findInventarioEquiposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InventarioEquipos.class));
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

    public InventarioEquipos findInventarioEquipos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InventarioEquipos.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventarioEquiposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InventarioEquipos> rt = cq.from(InventarioEquipos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
