/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.NonexistentEntityException;
import com.requestnet.dao.exceptions.RollbackFailureException;
import com.requestnet.entidades.Casos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.requestnet.entidades.TipoCaso;
import com.requestnet.entidades.Tecnico;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.Estados;
import com.requestnet.entidades.IntegranteColaborador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class CasosJpaController implements Serializable {

    public CasosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Casos casos) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoCaso idTipoCaso = casos.getIdTipoCaso();
            if (idTipoCaso != null) {
                idTipoCaso = em.getReference(idTipoCaso.getClass(), idTipoCaso.getIdTipoCaso());
                casos.setIdTipoCaso(idTipoCaso);
            }
            Tecnico idTecnico = casos.getIdTecnico();
            if (idTecnico != null) {
                idTecnico = em.getReference(idTecnico.getClass(), idTecnico.getIdTecnico());
                casos.setIdTecnico(idTecnico);
            }
            Jefeinfraestructura idJefe = casos.getIdJefe();
            if (idJefe != null) {
                idJefe = em.getReference(idJefe.getClass(), idJefe.getIdJefe());
                casos.setIdJefe(idJefe);
            }
            Liderinventario idLider = casos.getIdLider();
            if (idLider != null) {
                idLider = em.getReference(idLider.getClass(), idLider.getIdLider());
                casos.setIdLider(idLider);
            }
            Estados idEstado = casos.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                casos.setIdEstado(idEstado);
            }
            IntegranteColaborador idCliente = casos.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                casos.setIdCliente(idCliente);
            }
            em.persist(casos);
            if (idTipoCaso != null) {
                idTipoCaso.getCasosCollection().add(casos);
                idTipoCaso = em.merge(idTipoCaso);
            }
            if (idTecnico != null) {
                idTecnico.getCasosCollection().add(casos);
                idTecnico = em.merge(idTecnico);
            }
            if (idJefe != null) {
                idJefe.getCasosCollection().add(casos);
                idJefe = em.merge(idJefe);
            }
            if (idLider != null) {
                idLider.getCasosCollection().add(casos);
                idLider = em.merge(idLider);
            }
            if (idEstado != null) {
                idEstado.getCasosCollection().add(casos);
                idEstado = em.merge(idEstado);
            }
            if (idCliente != null) {
                idCliente.getCasosCollection().add(casos);
                idCliente = em.merge(idCliente);
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

    public void edit(Casos casos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Casos persistentCasos = em.find(Casos.class, casos.getIdCaso());
            TipoCaso idTipoCasoOld = persistentCasos.getIdTipoCaso();
            TipoCaso idTipoCasoNew = casos.getIdTipoCaso();
            Tecnico idTecnicoOld = persistentCasos.getIdTecnico();
            Tecnico idTecnicoNew = casos.getIdTecnico();
            Jefeinfraestructura idJefeOld = persistentCasos.getIdJefe();
            Jefeinfraestructura idJefeNew = casos.getIdJefe();
            Liderinventario idLiderOld = persistentCasos.getIdLider();
            Liderinventario idLiderNew = casos.getIdLider();
            Estados idEstadoOld = persistentCasos.getIdEstado();
            Estados idEstadoNew = casos.getIdEstado();
            IntegranteColaborador idClienteOld = persistentCasos.getIdCliente();
            IntegranteColaborador idClienteNew = casos.getIdCliente();
            if (idTipoCasoNew != null) {
                idTipoCasoNew = em.getReference(idTipoCasoNew.getClass(), idTipoCasoNew.getIdTipoCaso());
                casos.setIdTipoCaso(idTipoCasoNew);
            }
            if (idTecnicoNew != null) {
                idTecnicoNew = em.getReference(idTecnicoNew.getClass(), idTecnicoNew.getIdTecnico());
                casos.setIdTecnico(idTecnicoNew);
            }
            if (idJefeNew != null) {
                idJefeNew = em.getReference(idJefeNew.getClass(), idJefeNew.getIdJefe());
                casos.setIdJefe(idJefeNew);
            }
            if (idLiderNew != null) {
                idLiderNew = em.getReference(idLiderNew.getClass(), idLiderNew.getIdLider());
                casos.setIdLider(idLiderNew);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                casos.setIdEstado(idEstadoNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                casos.setIdCliente(idClienteNew);
            }
            casos = em.merge(casos);
            if (idTipoCasoOld != null && !idTipoCasoOld.equals(idTipoCasoNew)) {
                idTipoCasoOld.getCasosCollection().remove(casos);
                idTipoCasoOld = em.merge(idTipoCasoOld);
            }
            if (idTipoCasoNew != null && !idTipoCasoNew.equals(idTipoCasoOld)) {
                idTipoCasoNew.getCasosCollection().add(casos);
                idTipoCasoNew = em.merge(idTipoCasoNew);
            }
            if (idTecnicoOld != null && !idTecnicoOld.equals(idTecnicoNew)) {
                idTecnicoOld.getCasosCollection().remove(casos);
                idTecnicoOld = em.merge(idTecnicoOld);
            }
            if (idTecnicoNew != null && !idTecnicoNew.equals(idTecnicoOld)) {
                idTecnicoNew.getCasosCollection().add(casos);
                idTecnicoNew = em.merge(idTecnicoNew);
            }
            if (idJefeOld != null && !idJefeOld.equals(idJefeNew)) {
                idJefeOld.getCasosCollection().remove(casos);
                idJefeOld = em.merge(idJefeOld);
            }
            if (idJefeNew != null && !idJefeNew.equals(idJefeOld)) {
                idJefeNew.getCasosCollection().add(casos);
                idJefeNew = em.merge(idJefeNew);
            }
            if (idLiderOld != null && !idLiderOld.equals(idLiderNew)) {
                idLiderOld.getCasosCollection().remove(casos);
                idLiderOld = em.merge(idLiderOld);
            }
            if (idLiderNew != null && !idLiderNew.equals(idLiderOld)) {
                idLiderNew.getCasosCollection().add(casos);
                idLiderNew = em.merge(idLiderNew);
            }
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getCasosCollection().remove(casos);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getCasosCollection().add(casos);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getCasosCollection().remove(casos);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getCasosCollection().add(casos);
                idClienteNew = em.merge(idClienteNew);
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
                Integer id = casos.getIdCaso();
                if (findCasos(id) == null) {
                    throw new NonexistentEntityException("The casos with id " + id + " no longer exists.");
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
            Casos casos;
            try {
                casos = em.getReference(Casos.class, id);
                casos.getIdCaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The casos with id " + id + " no longer exists.", enfe);
            }
            TipoCaso idTipoCaso = casos.getIdTipoCaso();
            if (idTipoCaso != null) {
                idTipoCaso.getCasosCollection().remove(casos);
                idTipoCaso = em.merge(idTipoCaso);
            }
            Tecnico idTecnico = casos.getIdTecnico();
            if (idTecnico != null) {
                idTecnico.getCasosCollection().remove(casos);
                idTecnico = em.merge(idTecnico);
            }
            Jefeinfraestructura idJefe = casos.getIdJefe();
            if (idJefe != null) {
                idJefe.getCasosCollection().remove(casos);
                idJefe = em.merge(idJefe);
            }
            Liderinventario idLider = casos.getIdLider();
            if (idLider != null) {
                idLider.getCasosCollection().remove(casos);
                idLider = em.merge(idLider);
            }
            Estados idEstado = casos.getIdEstado();
            if (idEstado != null) {
                idEstado.getCasosCollection().remove(casos);
                idEstado = em.merge(idEstado);
            }
            IntegranteColaborador idCliente = casos.getIdCliente();
            if (idCliente != null) {
                idCliente.getCasosCollection().remove(casos);
                idCliente = em.merge(idCliente);
            }
            em.remove(casos);
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

    public List<Casos> findCasosEntities() {
        return findCasosEntities(true, -1, -1);
    }

    public List<Casos> findCasosEntities(int maxResults, int firstResult) {
        return findCasosEntities(false, maxResults, firstResult);
    }

    private List<Casos> findCasosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Casos.class));
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

    public Casos findCasos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Casos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCasosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Casos> rt = cq.from(Casos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
