/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.dao;

import com.requestnet.dao.exceptions.IllegalOrphanException;
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
import com.requestnet.entidades.IntegranteColaborador;
import java.util.ArrayList;
import java.util.Collection;
import com.requestnet.entidades.InventarioEquipos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class IntegranteColaboradorJpaController implements Serializable {

    public IntegranteColaboradorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntegranteColaborador integranteColaborador) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (integranteColaborador.getCasosCollection() == null) {
            integranteColaborador.setCasosCollection(new ArrayList<Casos>());
        }
        if (integranteColaborador.getInventarioEquiposCollection() == null) {
            integranteColaborador.setInventarioEquiposCollection(new ArrayList<InventarioEquipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios idUsuario = integranteColaborador.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                integranteColaborador.setIdUsuario(idUsuario);
            }
            Collection<Casos> attachedCasosCollection = new ArrayList<Casos>();
            for (Casos casosCollectionCasosToAttach : integranteColaborador.getCasosCollection()) {
                casosCollectionCasosToAttach = em.getReference(casosCollectionCasosToAttach.getClass(), casosCollectionCasosToAttach.getIdCaso());
                attachedCasosCollection.add(casosCollectionCasosToAttach);
            }
            integranteColaborador.setCasosCollection(attachedCasosCollection);
            Collection<InventarioEquipos> attachedInventarioEquiposCollection = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquiposToAttach : integranteColaborador.getInventarioEquiposCollection()) {
                inventarioEquiposCollectionInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollection.add(inventarioEquiposCollectionInventarioEquiposToAttach);
            }
            integranteColaborador.setInventarioEquiposCollection(attachedInventarioEquiposCollection);
            em.persist(integranteColaborador);
            if (idUsuario != null) {
                idUsuario.getIntegranteColaboradorCollection().add(integranteColaborador);
                idUsuario = em.merge(idUsuario);
            }
            for (Casos casosCollectionCasos : integranteColaborador.getCasosCollection()) {
                IntegranteColaborador oldIdClienteOfCasosCollectionCasos = casosCollectionCasos.getIdCliente();
                casosCollectionCasos.setIdCliente(integranteColaborador);
                casosCollectionCasos = em.merge(casosCollectionCasos);
                if (oldIdClienteOfCasosCollectionCasos != null) {
                    oldIdClienteOfCasosCollectionCasos.getCasosCollection().remove(casosCollectionCasos);
                    oldIdClienteOfCasosCollectionCasos = em.merge(oldIdClienteOfCasosCollectionCasos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : integranteColaborador.getInventarioEquiposCollection()) {
                IntegranteColaborador oldIdClienteOfInventarioEquiposCollectionInventarioEquipos = inventarioEquiposCollectionInventarioEquipos.getIdCliente();
                inventarioEquiposCollectionInventarioEquipos.setIdCliente(integranteColaborador);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
                if (oldIdClienteOfInventarioEquiposCollectionInventarioEquipos != null) {
                    oldIdClienteOfInventarioEquiposCollectionInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionInventarioEquipos);
                    oldIdClienteOfInventarioEquiposCollectionInventarioEquipos = em.merge(oldIdClienteOfInventarioEquiposCollectionInventarioEquipos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findIntegranteColaborador(integranteColaborador.getIdCliente()) != null) {
                throw new PreexistingEntityException("IntegranteColaborador " + integranteColaborador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntegranteColaborador integranteColaborador) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            IntegranteColaborador persistentIntegranteColaborador = em.find(IntegranteColaborador.class, integranteColaborador.getIdCliente());
            Usuarios idUsuarioOld = persistentIntegranteColaborador.getIdUsuario();
            Usuarios idUsuarioNew = integranteColaborador.getIdUsuario();
            Collection<Casos> casosCollectionOld = persistentIntegranteColaborador.getCasosCollection();
            Collection<Casos> casosCollectionNew = integranteColaborador.getCasosCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionOld = persistentIntegranteColaborador.getInventarioEquiposCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionNew = integranteColaborador.getInventarioEquiposCollection();
            List<String> illegalOrphanMessages = null;
            for (Casos casosCollectionOldCasos : casosCollectionOld) {
                if (!casosCollectionNew.contains(casosCollectionOldCasos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Casos " + casosCollectionOldCasos + " since its idCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                integranteColaborador.setIdUsuario(idUsuarioNew);
            }
            Collection<Casos> attachedCasosCollectionNew = new ArrayList<Casos>();
            for (Casos casosCollectionNewCasosToAttach : casosCollectionNew) {
                casosCollectionNewCasosToAttach = em.getReference(casosCollectionNewCasosToAttach.getClass(), casosCollectionNewCasosToAttach.getIdCaso());
                attachedCasosCollectionNew.add(casosCollectionNewCasosToAttach);
            }
            casosCollectionNew = attachedCasosCollectionNew;
            integranteColaborador.setCasosCollection(casosCollectionNew);
            Collection<InventarioEquipos> attachedInventarioEquiposCollectionNew = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquiposToAttach : inventarioEquiposCollectionNew) {
                inventarioEquiposCollectionNewInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionNewInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionNewInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollectionNew.add(inventarioEquiposCollectionNewInventarioEquiposToAttach);
            }
            inventarioEquiposCollectionNew = attachedInventarioEquiposCollectionNew;
            integranteColaborador.setInventarioEquiposCollection(inventarioEquiposCollectionNew);
            integranteColaborador = em.merge(integranteColaborador);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getIntegranteColaboradorCollection().remove(integranteColaborador);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getIntegranteColaboradorCollection().add(integranteColaborador);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Casos casosCollectionNewCasos : casosCollectionNew) {
                if (!casosCollectionOld.contains(casosCollectionNewCasos)) {
                    IntegranteColaborador oldIdClienteOfCasosCollectionNewCasos = casosCollectionNewCasos.getIdCliente();
                    casosCollectionNewCasos.setIdCliente(integranteColaborador);
                    casosCollectionNewCasos = em.merge(casosCollectionNewCasos);
                    if (oldIdClienteOfCasosCollectionNewCasos != null && !oldIdClienteOfCasosCollectionNewCasos.equals(integranteColaborador)) {
                        oldIdClienteOfCasosCollectionNewCasos.getCasosCollection().remove(casosCollectionNewCasos);
                        oldIdClienteOfCasosCollectionNewCasos = em.merge(oldIdClienteOfCasosCollectionNewCasos);
                    }
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionOldInventarioEquipos : inventarioEquiposCollectionOld) {
                if (!inventarioEquiposCollectionNew.contains(inventarioEquiposCollectionOldInventarioEquipos)) {
                    inventarioEquiposCollectionOldInventarioEquipos.setIdCliente(null);
                    inventarioEquiposCollectionOldInventarioEquipos = em.merge(inventarioEquiposCollectionOldInventarioEquipos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquipos : inventarioEquiposCollectionNew) {
                if (!inventarioEquiposCollectionOld.contains(inventarioEquiposCollectionNewInventarioEquipos)) {
                    IntegranteColaborador oldIdClienteOfInventarioEquiposCollectionNewInventarioEquipos = inventarioEquiposCollectionNewInventarioEquipos.getIdCliente();
                    inventarioEquiposCollectionNewInventarioEquipos.setIdCliente(integranteColaborador);
                    inventarioEquiposCollectionNewInventarioEquipos = em.merge(inventarioEquiposCollectionNewInventarioEquipos);
                    if (oldIdClienteOfInventarioEquiposCollectionNewInventarioEquipos != null && !oldIdClienteOfInventarioEquiposCollectionNewInventarioEquipos.equals(integranteColaborador)) {
                        oldIdClienteOfInventarioEquiposCollectionNewInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionNewInventarioEquipos);
                        oldIdClienteOfInventarioEquiposCollectionNewInventarioEquipos = em.merge(oldIdClienteOfInventarioEquiposCollectionNewInventarioEquipos);
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
                Integer id = integranteColaborador.getIdCliente();
                if (findIntegranteColaborador(id) == null) {
                    throw new NonexistentEntityException("The integranteColaborador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            IntegranteColaborador integranteColaborador;
            try {
                integranteColaborador = em.getReference(IntegranteColaborador.class, id);
                integranteColaborador.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The integranteColaborador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Casos> casosCollectionOrphanCheck = integranteColaborador.getCasosCollection();
            for (Casos casosCollectionOrphanCheckCasos : casosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IntegranteColaborador (" + integranteColaborador + ") cannot be destroyed since the Casos " + casosCollectionOrphanCheckCasos + " in its casosCollection field has a non-nullable idCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios idUsuario = integranteColaborador.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getIntegranteColaboradorCollection().remove(integranteColaborador);
                idUsuario = em.merge(idUsuario);
            }
            Collection<InventarioEquipos> inventarioEquiposCollection = integranteColaborador.getInventarioEquiposCollection();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : inventarioEquiposCollection) {
                inventarioEquiposCollectionInventarioEquipos.setIdCliente(null);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
            }
            em.remove(integranteColaborador);
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

    public List<IntegranteColaborador> findIntegranteColaboradorEntities() {
        return findIntegranteColaboradorEntities(true, -1, -1);
    }

    public List<IntegranteColaborador> findIntegranteColaboradorEntities(int maxResults, int firstResult) {
        return findIntegranteColaboradorEntities(false, maxResults, firstResult);
    }

    private List<IntegranteColaborador> findIntegranteColaboradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntegranteColaborador.class));
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

    public IntegranteColaborador findIntegranteColaborador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntegranteColaborador.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntegranteColaboradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntegranteColaborador> rt = cq.from(IntegranteColaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
