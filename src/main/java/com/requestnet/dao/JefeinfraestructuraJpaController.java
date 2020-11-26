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
import java.util.ArrayList;
import java.util.Collection;
import com.requestnet.entidades.Orden;
import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.Jefeinfraestructura;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class JefeinfraestructuraJpaController implements Serializable {

    public JefeinfraestructuraJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jefeinfraestructura jefeinfraestructura) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (jefeinfraestructura.getCasosCollection() == null) {
            jefeinfraestructura.setCasosCollection(new ArrayList<Casos>());
        }
        if (jefeinfraestructura.getOrdenCollection() == null) {
            jefeinfraestructura.setOrdenCollection(new ArrayList<Orden>());
        }
        if (jefeinfraestructura.getInventarioEquiposCollection() == null) {
            jefeinfraestructura.setInventarioEquiposCollection(new ArrayList<InventarioEquipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios idUsuario = jefeinfraestructura.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                jefeinfraestructura.setIdUsuario(idUsuario);
            }
            Collection<Casos> attachedCasosCollection = new ArrayList<Casos>();
            for (Casos casosCollectionCasosToAttach : jefeinfraestructura.getCasosCollection()) {
                casosCollectionCasosToAttach = em.getReference(casosCollectionCasosToAttach.getClass(), casosCollectionCasosToAttach.getIdCaso());
                attachedCasosCollection.add(casosCollectionCasosToAttach);
            }
            jefeinfraestructura.setCasosCollection(attachedCasosCollection);
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : jefeinfraestructura.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getIdOrden());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            jefeinfraestructura.setOrdenCollection(attachedOrdenCollection);
            Collection<InventarioEquipos> attachedInventarioEquiposCollection = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquiposToAttach : jefeinfraestructura.getInventarioEquiposCollection()) {
                inventarioEquiposCollectionInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollection.add(inventarioEquiposCollectionInventarioEquiposToAttach);
            }
            jefeinfraestructura.setInventarioEquiposCollection(attachedInventarioEquiposCollection);
            em.persist(jefeinfraestructura);
            if (idUsuario != null) {
                idUsuario.getJefeinfraestructuraCollection().add(jefeinfraestructura);
                idUsuario = em.merge(idUsuario);
            }
            for (Casos casosCollectionCasos : jefeinfraestructura.getCasosCollection()) {
                Jefeinfraestructura oldIdJefeOfCasosCollectionCasos = casosCollectionCasos.getIdJefe();
                casosCollectionCasos.setIdJefe(jefeinfraestructura);
                casosCollectionCasos = em.merge(casosCollectionCasos);
                if (oldIdJefeOfCasosCollectionCasos != null) {
                    oldIdJefeOfCasosCollectionCasos.getCasosCollection().remove(casosCollectionCasos);
                    oldIdJefeOfCasosCollectionCasos = em.merge(oldIdJefeOfCasosCollectionCasos);
                }
            }
            for (Orden ordenCollectionOrden : jefeinfraestructura.getOrdenCollection()) {
                Jefeinfraestructura oldIdJefeOfOrdenCollectionOrden = ordenCollectionOrden.getIdJefe();
                ordenCollectionOrden.setIdJefe(jefeinfraestructura);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldIdJefeOfOrdenCollectionOrden != null) {
                    oldIdJefeOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldIdJefeOfOrdenCollectionOrden = em.merge(oldIdJefeOfOrdenCollectionOrden);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : jefeinfraestructura.getInventarioEquiposCollection()) {
                Jefeinfraestructura oldIdJefeOfInventarioEquiposCollectionInventarioEquipos = inventarioEquiposCollectionInventarioEquipos.getIdJefe();
                inventarioEquiposCollectionInventarioEquipos.setIdJefe(jefeinfraestructura);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
                if (oldIdJefeOfInventarioEquiposCollectionInventarioEquipos != null) {
                    oldIdJefeOfInventarioEquiposCollectionInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionInventarioEquipos);
                    oldIdJefeOfInventarioEquiposCollectionInventarioEquipos = em.merge(oldIdJefeOfInventarioEquiposCollectionInventarioEquipos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findJefeinfraestructura(jefeinfraestructura.getIdJefe()) != null) {
                throw new PreexistingEntityException("Jefeinfraestructura " + jefeinfraestructura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jefeinfraestructura jefeinfraestructura) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jefeinfraestructura persistentJefeinfraestructura = em.find(Jefeinfraestructura.class, jefeinfraestructura.getIdJefe());
            Usuarios idUsuarioOld = persistentJefeinfraestructura.getIdUsuario();
            Usuarios idUsuarioNew = jefeinfraestructura.getIdUsuario();
            Collection<Casos> casosCollectionOld = persistentJefeinfraestructura.getCasosCollection();
            Collection<Casos> casosCollectionNew = jefeinfraestructura.getCasosCollection();
            Collection<Orden> ordenCollectionOld = persistentJefeinfraestructura.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = jefeinfraestructura.getOrdenCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionOld = persistentJefeinfraestructura.getInventarioEquiposCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionNew = jefeinfraestructura.getInventarioEquiposCollection();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                jefeinfraestructura.setIdUsuario(idUsuarioNew);
            }
            Collection<Casos> attachedCasosCollectionNew = new ArrayList<Casos>();
            for (Casos casosCollectionNewCasosToAttach : casosCollectionNew) {
                casosCollectionNewCasosToAttach = em.getReference(casosCollectionNewCasosToAttach.getClass(), casosCollectionNewCasosToAttach.getIdCaso());
                attachedCasosCollectionNew.add(casosCollectionNewCasosToAttach);
            }
            casosCollectionNew = attachedCasosCollectionNew;
            jefeinfraestructura.setCasosCollection(casosCollectionNew);
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getIdOrden());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            jefeinfraestructura.setOrdenCollection(ordenCollectionNew);
            Collection<InventarioEquipos> attachedInventarioEquiposCollectionNew = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquiposToAttach : inventarioEquiposCollectionNew) {
                inventarioEquiposCollectionNewInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionNewInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionNewInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollectionNew.add(inventarioEquiposCollectionNewInventarioEquiposToAttach);
            }
            inventarioEquiposCollectionNew = attachedInventarioEquiposCollectionNew;
            jefeinfraestructura.setInventarioEquiposCollection(inventarioEquiposCollectionNew);
            jefeinfraestructura = em.merge(jefeinfraestructura);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getJefeinfraestructuraCollection().remove(jefeinfraestructura);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getJefeinfraestructuraCollection().add(jefeinfraestructura);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Casos casosCollectionOldCasos : casosCollectionOld) {
                if (!casosCollectionNew.contains(casosCollectionOldCasos)) {
                    casosCollectionOldCasos.setIdJefe(null);
                    casosCollectionOldCasos = em.merge(casosCollectionOldCasos);
                }
            }
            for (Casos casosCollectionNewCasos : casosCollectionNew) {
                if (!casosCollectionOld.contains(casosCollectionNewCasos)) {
                    Jefeinfraestructura oldIdJefeOfCasosCollectionNewCasos = casosCollectionNewCasos.getIdJefe();
                    casosCollectionNewCasos.setIdJefe(jefeinfraestructura);
                    casosCollectionNewCasos = em.merge(casosCollectionNewCasos);
                    if (oldIdJefeOfCasosCollectionNewCasos != null && !oldIdJefeOfCasosCollectionNewCasos.equals(jefeinfraestructura)) {
                        oldIdJefeOfCasosCollectionNewCasos.getCasosCollection().remove(casosCollectionNewCasos);
                        oldIdJefeOfCasosCollectionNewCasos = em.merge(oldIdJefeOfCasosCollectionNewCasos);
                    }
                }
            }
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    ordenCollectionOldOrden.setIdJefe(null);
                    ordenCollectionOldOrden = em.merge(ordenCollectionOldOrden);
                }
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Jefeinfraestructura oldIdJefeOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getIdJefe();
                    ordenCollectionNewOrden.setIdJefe(jefeinfraestructura);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldIdJefeOfOrdenCollectionNewOrden != null && !oldIdJefeOfOrdenCollectionNewOrden.equals(jefeinfraestructura)) {
                        oldIdJefeOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldIdJefeOfOrdenCollectionNewOrden = em.merge(oldIdJefeOfOrdenCollectionNewOrden);
                    }
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionOldInventarioEquipos : inventarioEquiposCollectionOld) {
                if (!inventarioEquiposCollectionNew.contains(inventarioEquiposCollectionOldInventarioEquipos)) {
                    inventarioEquiposCollectionOldInventarioEquipos.setIdJefe(null);
                    inventarioEquiposCollectionOldInventarioEquipos = em.merge(inventarioEquiposCollectionOldInventarioEquipos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquipos : inventarioEquiposCollectionNew) {
                if (!inventarioEquiposCollectionOld.contains(inventarioEquiposCollectionNewInventarioEquipos)) {
                    Jefeinfraestructura oldIdJefeOfInventarioEquiposCollectionNewInventarioEquipos = inventarioEquiposCollectionNewInventarioEquipos.getIdJefe();
                    inventarioEquiposCollectionNewInventarioEquipos.setIdJefe(jefeinfraestructura);
                    inventarioEquiposCollectionNewInventarioEquipos = em.merge(inventarioEquiposCollectionNewInventarioEquipos);
                    if (oldIdJefeOfInventarioEquiposCollectionNewInventarioEquipos != null && !oldIdJefeOfInventarioEquiposCollectionNewInventarioEquipos.equals(jefeinfraestructura)) {
                        oldIdJefeOfInventarioEquiposCollectionNewInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionNewInventarioEquipos);
                        oldIdJefeOfInventarioEquiposCollectionNewInventarioEquipos = em.merge(oldIdJefeOfInventarioEquiposCollectionNewInventarioEquipos);
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
                Integer id = jefeinfraestructura.getIdJefe();
                if (findJefeinfraestructura(id) == null) {
                    throw new NonexistentEntityException("The jefeinfraestructura with id " + id + " no longer exists.");
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
            Jefeinfraestructura jefeinfraestructura;
            try {
                jefeinfraestructura = em.getReference(Jefeinfraestructura.class, id);
                jefeinfraestructura.getIdJefe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jefeinfraestructura with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = jefeinfraestructura.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getJefeinfraestructuraCollection().remove(jefeinfraestructura);
                idUsuario = em.merge(idUsuario);
            }
            Collection<Casos> casosCollection = jefeinfraestructura.getCasosCollection();
            for (Casos casosCollectionCasos : casosCollection) {
                casosCollectionCasos.setIdJefe(null);
                casosCollectionCasos = em.merge(casosCollectionCasos);
            }
            Collection<Orden> ordenCollection = jefeinfraestructura.getOrdenCollection();
            for (Orden ordenCollectionOrden : ordenCollection) {
                ordenCollectionOrden.setIdJefe(null);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
            }
            Collection<InventarioEquipos> inventarioEquiposCollection = jefeinfraestructura.getInventarioEquiposCollection();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : inventarioEquiposCollection) {
                inventarioEquiposCollectionInventarioEquipos.setIdJefe(null);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
            }
            em.remove(jefeinfraestructura);
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

    public List<Jefeinfraestructura> findJefeinfraestructuraEntities() {
        return findJefeinfraestructuraEntities(true, -1, -1);
    }

    public List<Jefeinfraestructura> findJefeinfraestructuraEntities(int maxResults, int firstResult) {
        return findJefeinfraestructuraEntities(false, maxResults, firstResult);
    }

    private List<Jefeinfraestructura> findJefeinfraestructuraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jefeinfraestructura.class));
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

    public Jefeinfraestructura findJefeinfraestructura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jefeinfraestructura.class, id);
        } finally {
            em.close();
        }
    }

    public int getJefeinfraestructuraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jefeinfraestructura> rt = cq.from(Jefeinfraestructura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
