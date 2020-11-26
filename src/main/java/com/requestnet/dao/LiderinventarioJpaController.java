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
import com.requestnet.entidades.Liderinventario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class LiderinventarioJpaController implements Serializable {

    public LiderinventarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Liderinventario liderinventario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (liderinventario.getCasosCollection() == null) {
            liderinventario.setCasosCollection(new ArrayList<Casos>());
        }
        if (liderinventario.getOrdenCollection() == null) {
            liderinventario.setOrdenCollection(new ArrayList<Orden>());
        }
        if (liderinventario.getInventarioEquiposCollection() == null) {
            liderinventario.setInventarioEquiposCollection(new ArrayList<InventarioEquipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios idUsuario = liderinventario.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                liderinventario.setIdUsuario(idUsuario);
            }
            Collection<Casos> attachedCasosCollection = new ArrayList<Casos>();
            for (Casos casosCollectionCasosToAttach : liderinventario.getCasosCollection()) {
                casosCollectionCasosToAttach = em.getReference(casosCollectionCasosToAttach.getClass(), casosCollectionCasosToAttach.getIdCaso());
                attachedCasosCollection.add(casosCollectionCasosToAttach);
            }
            liderinventario.setCasosCollection(attachedCasosCollection);
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : liderinventario.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getIdOrden());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            liderinventario.setOrdenCollection(attachedOrdenCollection);
            Collection<InventarioEquipos> attachedInventarioEquiposCollection = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquiposToAttach : liderinventario.getInventarioEquiposCollection()) {
                inventarioEquiposCollectionInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollection.add(inventarioEquiposCollectionInventarioEquiposToAttach);
            }
            liderinventario.setInventarioEquiposCollection(attachedInventarioEquiposCollection);
            em.persist(liderinventario);
            if (idUsuario != null) {
                idUsuario.getLiderinventarioCollection().add(liderinventario);
                idUsuario = em.merge(idUsuario);
            }
            for (Casos casosCollectionCasos : liderinventario.getCasosCollection()) {
                Liderinventario oldIdLiderOfCasosCollectionCasos = casosCollectionCasos.getIdLider();
                casosCollectionCasos.setIdLider(liderinventario);
                casosCollectionCasos = em.merge(casosCollectionCasos);
                if (oldIdLiderOfCasosCollectionCasos != null) {
                    oldIdLiderOfCasosCollectionCasos.getCasosCollection().remove(casosCollectionCasos);
                    oldIdLiderOfCasosCollectionCasos = em.merge(oldIdLiderOfCasosCollectionCasos);
                }
            }
            for (Orden ordenCollectionOrden : liderinventario.getOrdenCollection()) {
                Liderinventario oldIdLiderOfOrdenCollectionOrden = ordenCollectionOrden.getIdLider();
                ordenCollectionOrden.setIdLider(liderinventario);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldIdLiderOfOrdenCollectionOrden != null) {
                    oldIdLiderOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldIdLiderOfOrdenCollectionOrden = em.merge(oldIdLiderOfOrdenCollectionOrden);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : liderinventario.getInventarioEquiposCollection()) {
                Liderinventario oldIdLiderOfInventarioEquiposCollectionInventarioEquipos = inventarioEquiposCollectionInventarioEquipos.getIdLider();
                inventarioEquiposCollectionInventarioEquipos.setIdLider(liderinventario);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
                if (oldIdLiderOfInventarioEquiposCollectionInventarioEquipos != null) {
                    oldIdLiderOfInventarioEquiposCollectionInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionInventarioEquipos);
                    oldIdLiderOfInventarioEquiposCollectionInventarioEquipos = em.merge(oldIdLiderOfInventarioEquiposCollectionInventarioEquipos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLiderinventario(liderinventario.getIdLider()) != null) {
                throw new PreexistingEntityException("Liderinventario " + liderinventario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Liderinventario liderinventario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Liderinventario persistentLiderinventario = em.find(Liderinventario.class, liderinventario.getIdLider());
            Usuarios idUsuarioOld = persistentLiderinventario.getIdUsuario();
            Usuarios idUsuarioNew = liderinventario.getIdUsuario();
            Collection<Casos> casosCollectionOld = persistentLiderinventario.getCasosCollection();
            Collection<Casos> casosCollectionNew = liderinventario.getCasosCollection();
            Collection<Orden> ordenCollectionOld = persistentLiderinventario.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = liderinventario.getOrdenCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionOld = persistentLiderinventario.getInventarioEquiposCollection();
            Collection<InventarioEquipos> inventarioEquiposCollectionNew = liderinventario.getInventarioEquiposCollection();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                liderinventario.setIdUsuario(idUsuarioNew);
            }
            Collection<Casos> attachedCasosCollectionNew = new ArrayList<Casos>();
            for (Casos casosCollectionNewCasosToAttach : casosCollectionNew) {
                casosCollectionNewCasosToAttach = em.getReference(casosCollectionNewCasosToAttach.getClass(), casosCollectionNewCasosToAttach.getIdCaso());
                attachedCasosCollectionNew.add(casosCollectionNewCasosToAttach);
            }
            casosCollectionNew = attachedCasosCollectionNew;
            liderinventario.setCasosCollection(casosCollectionNew);
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getIdOrden());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            liderinventario.setOrdenCollection(ordenCollectionNew);
            Collection<InventarioEquipos> attachedInventarioEquiposCollectionNew = new ArrayList<InventarioEquipos>();
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquiposToAttach : inventarioEquiposCollectionNew) {
                inventarioEquiposCollectionNewInventarioEquiposToAttach = em.getReference(inventarioEquiposCollectionNewInventarioEquiposToAttach.getClass(), inventarioEquiposCollectionNewInventarioEquiposToAttach.getIdEquipo());
                attachedInventarioEquiposCollectionNew.add(inventarioEquiposCollectionNewInventarioEquiposToAttach);
            }
            inventarioEquiposCollectionNew = attachedInventarioEquiposCollectionNew;
            liderinventario.setInventarioEquiposCollection(inventarioEquiposCollectionNew);
            liderinventario = em.merge(liderinventario);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getLiderinventarioCollection().remove(liderinventario);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getLiderinventarioCollection().add(liderinventario);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Casos casosCollectionOldCasos : casosCollectionOld) {
                if (!casosCollectionNew.contains(casosCollectionOldCasos)) {
                    casosCollectionOldCasos.setIdLider(null);
                    casosCollectionOldCasos = em.merge(casosCollectionOldCasos);
                }
            }
            for (Casos casosCollectionNewCasos : casosCollectionNew) {
                if (!casosCollectionOld.contains(casosCollectionNewCasos)) {
                    Liderinventario oldIdLiderOfCasosCollectionNewCasos = casosCollectionNewCasos.getIdLider();
                    casosCollectionNewCasos.setIdLider(liderinventario);
                    casosCollectionNewCasos = em.merge(casosCollectionNewCasos);
                    if (oldIdLiderOfCasosCollectionNewCasos != null && !oldIdLiderOfCasosCollectionNewCasos.equals(liderinventario)) {
                        oldIdLiderOfCasosCollectionNewCasos.getCasosCollection().remove(casosCollectionNewCasos);
                        oldIdLiderOfCasosCollectionNewCasos = em.merge(oldIdLiderOfCasosCollectionNewCasos);
                    }
                }
            }
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    ordenCollectionOldOrden.setIdLider(null);
                    ordenCollectionOldOrden = em.merge(ordenCollectionOldOrden);
                }
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Liderinventario oldIdLiderOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getIdLider();
                    ordenCollectionNewOrden.setIdLider(liderinventario);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldIdLiderOfOrdenCollectionNewOrden != null && !oldIdLiderOfOrdenCollectionNewOrden.equals(liderinventario)) {
                        oldIdLiderOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldIdLiderOfOrdenCollectionNewOrden = em.merge(oldIdLiderOfOrdenCollectionNewOrden);
                    }
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionOldInventarioEquipos : inventarioEquiposCollectionOld) {
                if (!inventarioEquiposCollectionNew.contains(inventarioEquiposCollectionOldInventarioEquipos)) {
                    inventarioEquiposCollectionOldInventarioEquipos.setIdLider(null);
                    inventarioEquiposCollectionOldInventarioEquipos = em.merge(inventarioEquiposCollectionOldInventarioEquipos);
                }
            }
            for (InventarioEquipos inventarioEquiposCollectionNewInventarioEquipos : inventarioEquiposCollectionNew) {
                if (!inventarioEquiposCollectionOld.contains(inventarioEquiposCollectionNewInventarioEquipos)) {
                    Liderinventario oldIdLiderOfInventarioEquiposCollectionNewInventarioEquipos = inventarioEquiposCollectionNewInventarioEquipos.getIdLider();
                    inventarioEquiposCollectionNewInventarioEquipos.setIdLider(liderinventario);
                    inventarioEquiposCollectionNewInventarioEquipos = em.merge(inventarioEquiposCollectionNewInventarioEquipos);
                    if (oldIdLiderOfInventarioEquiposCollectionNewInventarioEquipos != null && !oldIdLiderOfInventarioEquiposCollectionNewInventarioEquipos.equals(liderinventario)) {
                        oldIdLiderOfInventarioEquiposCollectionNewInventarioEquipos.getInventarioEquiposCollection().remove(inventarioEquiposCollectionNewInventarioEquipos);
                        oldIdLiderOfInventarioEquiposCollectionNewInventarioEquipos = em.merge(oldIdLiderOfInventarioEquiposCollectionNewInventarioEquipos);
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
                Integer id = liderinventario.getIdLider();
                if (findLiderinventario(id) == null) {
                    throw new NonexistentEntityException("The liderinventario with id " + id + " no longer exists.");
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
            Liderinventario liderinventario;
            try {
                liderinventario = em.getReference(Liderinventario.class, id);
                liderinventario.getIdLider();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The liderinventario with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = liderinventario.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getLiderinventarioCollection().remove(liderinventario);
                idUsuario = em.merge(idUsuario);
            }
            Collection<Casos> casosCollection = liderinventario.getCasosCollection();
            for (Casos casosCollectionCasos : casosCollection) {
                casosCollectionCasos.setIdLider(null);
                casosCollectionCasos = em.merge(casosCollectionCasos);
            }
            Collection<Orden> ordenCollection = liderinventario.getOrdenCollection();
            for (Orden ordenCollectionOrden : ordenCollection) {
                ordenCollectionOrden.setIdLider(null);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
            }
            Collection<InventarioEquipos> inventarioEquiposCollection = liderinventario.getInventarioEquiposCollection();
            for (InventarioEquipos inventarioEquiposCollectionInventarioEquipos : inventarioEquiposCollection) {
                inventarioEquiposCollectionInventarioEquipos.setIdLider(null);
                inventarioEquiposCollectionInventarioEquipos = em.merge(inventarioEquiposCollectionInventarioEquipos);
            }
            em.remove(liderinventario);
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

    public List<Liderinventario> findLiderinventarioEntities() {
        return findLiderinventarioEntities(true, -1, -1);
    }

    public List<Liderinventario> findLiderinventarioEntities(int maxResults, int firstResult) {
        return findLiderinventarioEntities(false, maxResults, firstResult);
    }

    private List<Liderinventario> findLiderinventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Liderinventario.class));
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

    public Liderinventario findLiderinventario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Liderinventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getLiderinventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Liderinventario> rt = cq.from(Liderinventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
