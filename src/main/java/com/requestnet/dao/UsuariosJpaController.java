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
import com.requestnet.entidades.Roles;
import com.requestnet.entidades.TipoDoc;
import com.requestnet.entidades.Ciudades;
import com.requestnet.entidades.EstadosRu;
import com.requestnet.entidades.Tecnico;
import java.util.ArrayList;
import java.util.Collection;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Proveedores;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.IntegranteColaborador;
import com.requestnet.entidades.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mianba
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws RollbackFailureException, Exception {
        if (usuarios.getTecnicoCollection() == null) {
            usuarios.setTecnicoCollection(new ArrayList<Tecnico>());
        }
        if (usuarios.getJefeinfraestructuraCollection() == null) {
            usuarios.setJefeinfraestructuraCollection(new ArrayList<Jefeinfraestructura>());
        }
        if (usuarios.getProveedoresCollection() == null) {
            usuarios.setProveedoresCollection(new ArrayList<Proveedores>());
        }
        if (usuarios.getLiderinventarioCollection() == null) {
            usuarios.setLiderinventarioCollection(new ArrayList<Liderinventario>());
        }
        if (usuarios.getIntegranteColaboradorCollection() == null) {
            usuarios.setIntegranteColaboradorCollection(new ArrayList<IntegranteColaborador>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Roles idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                usuarios.setIdRol(idRol);
            }
            TipoDoc idTipdoc = usuarios.getIdTipdoc();
            if (idTipdoc != null) {
                idTipdoc = em.getReference(idTipdoc.getClass(), idTipdoc.getIdTipdoc());
                usuarios.setIdTipdoc(idTipdoc);
            }
            Ciudades idCiudad = usuarios.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                usuarios.setIdCiudad(idCiudad);
            }
            EstadosRu idEstadoru = usuarios.getIdEstadoru();
            if (idEstadoru != null) {
                idEstadoru = em.getReference(idEstadoru.getClass(), idEstadoru.getIdEstadoru());
                usuarios.setIdEstadoru(idEstadoru);
            }
            Collection<Tecnico> attachedTecnicoCollection = new ArrayList<Tecnico>();
            for (Tecnico tecnicoCollectionTecnicoToAttach : usuarios.getTecnicoCollection()) {
                tecnicoCollectionTecnicoToAttach = em.getReference(tecnicoCollectionTecnicoToAttach.getClass(), tecnicoCollectionTecnicoToAttach.getIdTecnico());
                attachedTecnicoCollection.add(tecnicoCollectionTecnicoToAttach);
            }
            usuarios.setTecnicoCollection(attachedTecnicoCollection);
            Collection<Jefeinfraestructura> attachedJefeinfraestructuraCollection = new ArrayList<Jefeinfraestructura>();
            for (Jefeinfraestructura jefeinfraestructuraCollectionJefeinfraestructuraToAttach : usuarios.getJefeinfraestructuraCollection()) {
                jefeinfraestructuraCollectionJefeinfraestructuraToAttach = em.getReference(jefeinfraestructuraCollectionJefeinfraestructuraToAttach.getClass(), jefeinfraestructuraCollectionJefeinfraestructuraToAttach.getIdJefe());
                attachedJefeinfraestructuraCollection.add(jefeinfraestructuraCollectionJefeinfraestructuraToAttach);
            }
            usuarios.setJefeinfraestructuraCollection(attachedJefeinfraestructuraCollection);
            Collection<Proveedores> attachedProveedoresCollection = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionProveedoresToAttach : usuarios.getProveedoresCollection()) {
                proveedoresCollectionProveedoresToAttach = em.getReference(proveedoresCollectionProveedoresToAttach.getClass(), proveedoresCollectionProveedoresToAttach.getIdProveedor());
                attachedProveedoresCollection.add(proveedoresCollectionProveedoresToAttach);
            }
            usuarios.setProveedoresCollection(attachedProveedoresCollection);
            Collection<Liderinventario> attachedLiderinventarioCollection = new ArrayList<Liderinventario>();
            for (Liderinventario liderinventarioCollectionLiderinventarioToAttach : usuarios.getLiderinventarioCollection()) {
                liderinventarioCollectionLiderinventarioToAttach = em.getReference(liderinventarioCollectionLiderinventarioToAttach.getClass(), liderinventarioCollectionLiderinventarioToAttach.getIdLider());
                attachedLiderinventarioCollection.add(liderinventarioCollectionLiderinventarioToAttach);
            }
            usuarios.setLiderinventarioCollection(attachedLiderinventarioCollection);
            Collection<IntegranteColaborador> attachedIntegranteColaboradorCollection = new ArrayList<IntegranteColaborador>();
            for (IntegranteColaborador integranteColaboradorCollectionIntegranteColaboradorToAttach : usuarios.getIntegranteColaboradorCollection()) {
                integranteColaboradorCollectionIntegranteColaboradorToAttach = em.getReference(integranteColaboradorCollectionIntegranteColaboradorToAttach.getClass(), integranteColaboradorCollectionIntegranteColaboradorToAttach.getIdCliente());
                attachedIntegranteColaboradorCollection.add(integranteColaboradorCollectionIntegranteColaboradorToAttach);
            }
            usuarios.setIntegranteColaboradorCollection(attachedIntegranteColaboradorCollection);
            em.persist(usuarios);
            if (idRol != null) {
                idRol.getUsuariosCollection().add(usuarios);
                idRol = em.merge(idRol);
            }
            if (idTipdoc != null) {
                idTipdoc.getUsuariosCollection().add(usuarios);
                idTipdoc = em.merge(idTipdoc);
            }
            if (idCiudad != null) {
                idCiudad.getUsuariosCollection().add(usuarios);
                idCiudad = em.merge(idCiudad);
            }
            if (idEstadoru != null) {
                idEstadoru.getUsuariosCollection().add(usuarios);
                idEstadoru = em.merge(idEstadoru);
            }
            for (Tecnico tecnicoCollectionTecnico : usuarios.getTecnicoCollection()) {
                Usuarios oldIdUsuarioOfTecnicoCollectionTecnico = tecnicoCollectionTecnico.getIdUsuario();
                tecnicoCollectionTecnico.setIdUsuario(usuarios);
                tecnicoCollectionTecnico = em.merge(tecnicoCollectionTecnico);
                if (oldIdUsuarioOfTecnicoCollectionTecnico != null) {
                    oldIdUsuarioOfTecnicoCollectionTecnico.getTecnicoCollection().remove(tecnicoCollectionTecnico);
                    oldIdUsuarioOfTecnicoCollectionTecnico = em.merge(oldIdUsuarioOfTecnicoCollectionTecnico);
                }
            }
            for (Jefeinfraestructura jefeinfraestructuraCollectionJefeinfraestructura : usuarios.getJefeinfraestructuraCollection()) {
                Usuarios oldIdUsuarioOfJefeinfraestructuraCollectionJefeinfraestructura = jefeinfraestructuraCollectionJefeinfraestructura.getIdUsuario();
                jefeinfraestructuraCollectionJefeinfraestructura.setIdUsuario(usuarios);
                jefeinfraestructuraCollectionJefeinfraestructura = em.merge(jefeinfraestructuraCollectionJefeinfraestructura);
                if (oldIdUsuarioOfJefeinfraestructuraCollectionJefeinfraestructura != null) {
                    oldIdUsuarioOfJefeinfraestructuraCollectionJefeinfraestructura.getJefeinfraestructuraCollection().remove(jefeinfraestructuraCollectionJefeinfraestructura);
                    oldIdUsuarioOfJefeinfraestructuraCollectionJefeinfraestructura = em.merge(oldIdUsuarioOfJefeinfraestructuraCollectionJefeinfraestructura);
                }
            }
            for (Proveedores proveedoresCollectionProveedores : usuarios.getProveedoresCollection()) {
                Usuarios oldIdUsuarioOfProveedoresCollectionProveedores = proveedoresCollectionProveedores.getIdUsuario();
                proveedoresCollectionProveedores.setIdUsuario(usuarios);
                proveedoresCollectionProveedores = em.merge(proveedoresCollectionProveedores);
                if (oldIdUsuarioOfProveedoresCollectionProveedores != null) {
                    oldIdUsuarioOfProveedoresCollectionProveedores.getProveedoresCollection().remove(proveedoresCollectionProveedores);
                    oldIdUsuarioOfProveedoresCollectionProveedores = em.merge(oldIdUsuarioOfProveedoresCollectionProveedores);
                }
            }
            for (Liderinventario liderinventarioCollectionLiderinventario : usuarios.getLiderinventarioCollection()) {
                Usuarios oldIdUsuarioOfLiderinventarioCollectionLiderinventario = liderinventarioCollectionLiderinventario.getIdUsuario();
                liderinventarioCollectionLiderinventario.setIdUsuario(usuarios);
                liderinventarioCollectionLiderinventario = em.merge(liderinventarioCollectionLiderinventario);
                if (oldIdUsuarioOfLiderinventarioCollectionLiderinventario != null) {
                    oldIdUsuarioOfLiderinventarioCollectionLiderinventario.getLiderinventarioCollection().remove(liderinventarioCollectionLiderinventario);
                    oldIdUsuarioOfLiderinventarioCollectionLiderinventario = em.merge(oldIdUsuarioOfLiderinventarioCollectionLiderinventario);
                }
            }
            for (IntegranteColaborador integranteColaboradorCollectionIntegranteColaborador : usuarios.getIntegranteColaboradorCollection()) {
                Usuarios oldIdUsuarioOfIntegranteColaboradorCollectionIntegranteColaborador = integranteColaboradorCollectionIntegranteColaborador.getIdUsuario();
                integranteColaboradorCollectionIntegranteColaborador.setIdUsuario(usuarios);
                integranteColaboradorCollectionIntegranteColaborador = em.merge(integranteColaboradorCollectionIntegranteColaborador);
                if (oldIdUsuarioOfIntegranteColaboradorCollectionIntegranteColaborador != null) {
                    oldIdUsuarioOfIntegranteColaboradorCollectionIntegranteColaborador.getIntegranteColaboradorCollection().remove(integranteColaboradorCollectionIntegranteColaborador);
                    oldIdUsuarioOfIntegranteColaboradorCollectionIntegranteColaborador = em.merge(oldIdUsuarioOfIntegranteColaboradorCollectionIntegranteColaborador);
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

    public void edit(Usuarios usuarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuario());
            Roles idRolOld = persistentUsuarios.getIdRol();
            Roles idRolNew = usuarios.getIdRol();
            TipoDoc idTipdocOld = persistentUsuarios.getIdTipdoc();
            TipoDoc idTipdocNew = usuarios.getIdTipdoc();
            Ciudades idCiudadOld = persistentUsuarios.getIdCiudad();
            Ciudades idCiudadNew = usuarios.getIdCiudad();
            EstadosRu idEstadoruOld = persistentUsuarios.getIdEstadoru();
            EstadosRu idEstadoruNew = usuarios.getIdEstadoru();
            Collection<Tecnico> tecnicoCollectionOld = persistentUsuarios.getTecnicoCollection();
            Collection<Tecnico> tecnicoCollectionNew = usuarios.getTecnicoCollection();
            Collection<Jefeinfraestructura> jefeinfraestructuraCollectionOld = persistentUsuarios.getJefeinfraestructuraCollection();
            Collection<Jefeinfraestructura> jefeinfraestructuraCollectionNew = usuarios.getJefeinfraestructuraCollection();
            Collection<Proveedores> proveedoresCollectionOld = persistentUsuarios.getProveedoresCollection();
            Collection<Proveedores> proveedoresCollectionNew = usuarios.getProveedoresCollection();
            Collection<Liderinventario> liderinventarioCollectionOld = persistentUsuarios.getLiderinventarioCollection();
            Collection<Liderinventario> liderinventarioCollectionNew = usuarios.getLiderinventarioCollection();
            Collection<IntegranteColaborador> integranteColaboradorCollectionOld = persistentUsuarios.getIntegranteColaboradorCollection();
            Collection<IntegranteColaborador> integranteColaboradorCollectionNew = usuarios.getIntegranteColaboradorCollection();
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                usuarios.setIdRol(idRolNew);
            }
            if (idTipdocNew != null) {
                idTipdocNew = em.getReference(idTipdocNew.getClass(), idTipdocNew.getIdTipdoc());
                usuarios.setIdTipdoc(idTipdocNew);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                usuarios.setIdCiudad(idCiudadNew);
            }
            if (idEstadoruNew != null) {
                idEstadoruNew = em.getReference(idEstadoruNew.getClass(), idEstadoruNew.getIdEstadoru());
                usuarios.setIdEstadoru(idEstadoruNew);
            }
            Collection<Tecnico> attachedTecnicoCollectionNew = new ArrayList<Tecnico>();
            for (Tecnico tecnicoCollectionNewTecnicoToAttach : tecnicoCollectionNew) {
                tecnicoCollectionNewTecnicoToAttach = em.getReference(tecnicoCollectionNewTecnicoToAttach.getClass(), tecnicoCollectionNewTecnicoToAttach.getIdTecnico());
                attachedTecnicoCollectionNew.add(tecnicoCollectionNewTecnicoToAttach);
            }
            tecnicoCollectionNew = attachedTecnicoCollectionNew;
            usuarios.setTecnicoCollection(tecnicoCollectionNew);
            Collection<Jefeinfraestructura> attachedJefeinfraestructuraCollectionNew = new ArrayList<Jefeinfraestructura>();
            for (Jefeinfraestructura jefeinfraestructuraCollectionNewJefeinfraestructuraToAttach : jefeinfraestructuraCollectionNew) {
                jefeinfraestructuraCollectionNewJefeinfraestructuraToAttach = em.getReference(jefeinfraestructuraCollectionNewJefeinfraestructuraToAttach.getClass(), jefeinfraestructuraCollectionNewJefeinfraestructuraToAttach.getIdJefe());
                attachedJefeinfraestructuraCollectionNew.add(jefeinfraestructuraCollectionNewJefeinfraestructuraToAttach);
            }
            jefeinfraestructuraCollectionNew = attachedJefeinfraestructuraCollectionNew;
            usuarios.setJefeinfraestructuraCollection(jefeinfraestructuraCollectionNew);
            Collection<Proveedores> attachedProveedoresCollectionNew = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionNewProveedoresToAttach : proveedoresCollectionNew) {
                proveedoresCollectionNewProveedoresToAttach = em.getReference(proveedoresCollectionNewProveedoresToAttach.getClass(), proveedoresCollectionNewProveedoresToAttach.getIdProveedor());
                attachedProveedoresCollectionNew.add(proveedoresCollectionNewProveedoresToAttach);
            }
            proveedoresCollectionNew = attachedProveedoresCollectionNew;
            usuarios.setProveedoresCollection(proveedoresCollectionNew);
            Collection<Liderinventario> attachedLiderinventarioCollectionNew = new ArrayList<Liderinventario>();
            for (Liderinventario liderinventarioCollectionNewLiderinventarioToAttach : liderinventarioCollectionNew) {
                liderinventarioCollectionNewLiderinventarioToAttach = em.getReference(liderinventarioCollectionNewLiderinventarioToAttach.getClass(), liderinventarioCollectionNewLiderinventarioToAttach.getIdLider());
                attachedLiderinventarioCollectionNew.add(liderinventarioCollectionNewLiderinventarioToAttach);
            }
            liderinventarioCollectionNew = attachedLiderinventarioCollectionNew;
            usuarios.setLiderinventarioCollection(liderinventarioCollectionNew);
            Collection<IntegranteColaborador> attachedIntegranteColaboradorCollectionNew = new ArrayList<IntegranteColaborador>();
            for (IntegranteColaborador integranteColaboradorCollectionNewIntegranteColaboradorToAttach : integranteColaboradorCollectionNew) {
                integranteColaboradorCollectionNewIntegranteColaboradorToAttach = em.getReference(integranteColaboradorCollectionNewIntegranteColaboradorToAttach.getClass(), integranteColaboradorCollectionNewIntegranteColaboradorToAttach.getIdCliente());
                attachedIntegranteColaboradorCollectionNew.add(integranteColaboradorCollectionNewIntegranteColaboradorToAttach);
            }
            integranteColaboradorCollectionNew = attachedIntegranteColaboradorCollectionNew;
            usuarios.setIntegranteColaboradorCollection(integranteColaboradorCollectionNew);
            usuarios = em.merge(usuarios);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getUsuariosCollection().remove(usuarios);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getUsuariosCollection().add(usuarios);
                idRolNew = em.merge(idRolNew);
            }
            if (idTipdocOld != null && !idTipdocOld.equals(idTipdocNew)) {
                idTipdocOld.getUsuariosCollection().remove(usuarios);
                idTipdocOld = em.merge(idTipdocOld);
            }
            if (idTipdocNew != null && !idTipdocNew.equals(idTipdocOld)) {
                idTipdocNew.getUsuariosCollection().add(usuarios);
                idTipdocNew = em.merge(idTipdocNew);
            }
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getUsuariosCollection().remove(usuarios);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getUsuariosCollection().add(usuarios);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idEstadoruOld != null && !idEstadoruOld.equals(idEstadoruNew)) {
                idEstadoruOld.getUsuariosCollection().remove(usuarios);
                idEstadoruOld = em.merge(idEstadoruOld);
            }
            if (idEstadoruNew != null && !idEstadoruNew.equals(idEstadoruOld)) {
                idEstadoruNew.getUsuariosCollection().add(usuarios);
                idEstadoruNew = em.merge(idEstadoruNew);
            }
            for (Tecnico tecnicoCollectionOldTecnico : tecnicoCollectionOld) {
                if (!tecnicoCollectionNew.contains(tecnicoCollectionOldTecnico)) {
                    tecnicoCollectionOldTecnico.setIdUsuario(null);
                    tecnicoCollectionOldTecnico = em.merge(tecnicoCollectionOldTecnico);
                }
            }
            for (Tecnico tecnicoCollectionNewTecnico : tecnicoCollectionNew) {
                if (!tecnicoCollectionOld.contains(tecnicoCollectionNewTecnico)) {
                    Usuarios oldIdUsuarioOfTecnicoCollectionNewTecnico = tecnicoCollectionNewTecnico.getIdUsuario();
                    tecnicoCollectionNewTecnico.setIdUsuario(usuarios);
                    tecnicoCollectionNewTecnico = em.merge(tecnicoCollectionNewTecnico);
                    if (oldIdUsuarioOfTecnicoCollectionNewTecnico != null && !oldIdUsuarioOfTecnicoCollectionNewTecnico.equals(usuarios)) {
                        oldIdUsuarioOfTecnicoCollectionNewTecnico.getTecnicoCollection().remove(tecnicoCollectionNewTecnico);
                        oldIdUsuarioOfTecnicoCollectionNewTecnico = em.merge(oldIdUsuarioOfTecnicoCollectionNewTecnico);
                    }
                }
            }
            for (Jefeinfraestructura jefeinfraestructuraCollectionOldJefeinfraestructura : jefeinfraestructuraCollectionOld) {
                if (!jefeinfraestructuraCollectionNew.contains(jefeinfraestructuraCollectionOldJefeinfraestructura)) {
                    jefeinfraestructuraCollectionOldJefeinfraestructura.setIdUsuario(null);
                    jefeinfraestructuraCollectionOldJefeinfraestructura = em.merge(jefeinfraestructuraCollectionOldJefeinfraestructura);
                }
            }
            for (Jefeinfraestructura jefeinfraestructuraCollectionNewJefeinfraestructura : jefeinfraestructuraCollectionNew) {
                if (!jefeinfraestructuraCollectionOld.contains(jefeinfraestructuraCollectionNewJefeinfraestructura)) {
                    Usuarios oldIdUsuarioOfJefeinfraestructuraCollectionNewJefeinfraestructura = jefeinfraestructuraCollectionNewJefeinfraestructura.getIdUsuario();
                    jefeinfraestructuraCollectionNewJefeinfraestructura.setIdUsuario(usuarios);
                    jefeinfraestructuraCollectionNewJefeinfraestructura = em.merge(jefeinfraestructuraCollectionNewJefeinfraestructura);
                    if (oldIdUsuarioOfJefeinfraestructuraCollectionNewJefeinfraestructura != null && !oldIdUsuarioOfJefeinfraestructuraCollectionNewJefeinfraestructura.equals(usuarios)) {
                        oldIdUsuarioOfJefeinfraestructuraCollectionNewJefeinfraestructura.getJefeinfraestructuraCollection().remove(jefeinfraestructuraCollectionNewJefeinfraestructura);
                        oldIdUsuarioOfJefeinfraestructuraCollectionNewJefeinfraestructura = em.merge(oldIdUsuarioOfJefeinfraestructuraCollectionNewJefeinfraestructura);
                    }
                }
            }
            for (Proveedores proveedoresCollectionOldProveedores : proveedoresCollectionOld) {
                if (!proveedoresCollectionNew.contains(proveedoresCollectionOldProveedores)) {
                    proveedoresCollectionOldProveedores.setIdUsuario(null);
                    proveedoresCollectionOldProveedores = em.merge(proveedoresCollectionOldProveedores);
                }
            }
            for (Proveedores proveedoresCollectionNewProveedores : proveedoresCollectionNew) {
                if (!proveedoresCollectionOld.contains(proveedoresCollectionNewProveedores)) {
                    Usuarios oldIdUsuarioOfProveedoresCollectionNewProveedores = proveedoresCollectionNewProveedores.getIdUsuario();
                    proveedoresCollectionNewProveedores.setIdUsuario(usuarios);
                    proveedoresCollectionNewProveedores = em.merge(proveedoresCollectionNewProveedores);
                    if (oldIdUsuarioOfProveedoresCollectionNewProveedores != null && !oldIdUsuarioOfProveedoresCollectionNewProveedores.equals(usuarios)) {
                        oldIdUsuarioOfProveedoresCollectionNewProveedores.getProveedoresCollection().remove(proveedoresCollectionNewProveedores);
                        oldIdUsuarioOfProveedoresCollectionNewProveedores = em.merge(oldIdUsuarioOfProveedoresCollectionNewProveedores);
                    }
                }
            }
            for (Liderinventario liderinventarioCollectionOldLiderinventario : liderinventarioCollectionOld) {
                if (!liderinventarioCollectionNew.contains(liderinventarioCollectionOldLiderinventario)) {
                    liderinventarioCollectionOldLiderinventario.setIdUsuario(null);
                    liderinventarioCollectionOldLiderinventario = em.merge(liderinventarioCollectionOldLiderinventario);
                }
            }
            for (Liderinventario liderinventarioCollectionNewLiderinventario : liderinventarioCollectionNew) {
                if (!liderinventarioCollectionOld.contains(liderinventarioCollectionNewLiderinventario)) {
                    Usuarios oldIdUsuarioOfLiderinventarioCollectionNewLiderinventario = liderinventarioCollectionNewLiderinventario.getIdUsuario();
                    liderinventarioCollectionNewLiderinventario.setIdUsuario(usuarios);
                    liderinventarioCollectionNewLiderinventario = em.merge(liderinventarioCollectionNewLiderinventario);
                    if (oldIdUsuarioOfLiderinventarioCollectionNewLiderinventario != null && !oldIdUsuarioOfLiderinventarioCollectionNewLiderinventario.equals(usuarios)) {
                        oldIdUsuarioOfLiderinventarioCollectionNewLiderinventario.getLiderinventarioCollection().remove(liderinventarioCollectionNewLiderinventario);
                        oldIdUsuarioOfLiderinventarioCollectionNewLiderinventario = em.merge(oldIdUsuarioOfLiderinventarioCollectionNewLiderinventario);
                    }
                }
            }
            for (IntegranteColaborador integranteColaboradorCollectionOldIntegranteColaborador : integranteColaboradorCollectionOld) {
                if (!integranteColaboradorCollectionNew.contains(integranteColaboradorCollectionOldIntegranteColaborador)) {
                    integranteColaboradorCollectionOldIntegranteColaborador.setIdUsuario(null);
                    integranteColaboradorCollectionOldIntegranteColaborador = em.merge(integranteColaboradorCollectionOldIntegranteColaborador);
                }
            }
            for (IntegranteColaborador integranteColaboradorCollectionNewIntegranteColaborador : integranteColaboradorCollectionNew) {
                if (!integranteColaboradorCollectionOld.contains(integranteColaboradorCollectionNewIntegranteColaborador)) {
                    Usuarios oldIdUsuarioOfIntegranteColaboradorCollectionNewIntegranteColaborador = integranteColaboradorCollectionNewIntegranteColaborador.getIdUsuario();
                    integranteColaboradorCollectionNewIntegranteColaborador.setIdUsuario(usuarios);
                    integranteColaboradorCollectionNewIntegranteColaborador = em.merge(integranteColaboradorCollectionNewIntegranteColaborador);
                    if (oldIdUsuarioOfIntegranteColaboradorCollectionNewIntegranteColaborador != null && !oldIdUsuarioOfIntegranteColaboradorCollectionNewIntegranteColaborador.equals(usuarios)) {
                        oldIdUsuarioOfIntegranteColaboradorCollectionNewIntegranteColaborador.getIntegranteColaboradorCollection().remove(integranteColaboradorCollectionNewIntegranteColaborador);
                        oldIdUsuarioOfIntegranteColaboradorCollectionNewIntegranteColaborador = em.merge(oldIdUsuarioOfIntegranteColaboradorCollectionNewIntegranteColaborador);
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
                Integer id = usuarios.getIdUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Roles idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol.getUsuariosCollection().remove(usuarios);
                idRol = em.merge(idRol);
            }
            TipoDoc idTipdoc = usuarios.getIdTipdoc();
            if (idTipdoc != null) {
                idTipdoc.getUsuariosCollection().remove(usuarios);
                idTipdoc = em.merge(idTipdoc);
            }
            Ciudades idCiudad = usuarios.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getUsuariosCollection().remove(usuarios);
                idCiudad = em.merge(idCiudad);
            }
            EstadosRu idEstadoru = usuarios.getIdEstadoru();
            if (idEstadoru != null) {
                idEstadoru.getUsuariosCollection().remove(usuarios);
                idEstadoru = em.merge(idEstadoru);
            }
            Collection<Tecnico> tecnicoCollection = usuarios.getTecnicoCollection();
            for (Tecnico tecnicoCollectionTecnico : tecnicoCollection) {
                tecnicoCollectionTecnico.setIdUsuario(null);
                tecnicoCollectionTecnico = em.merge(tecnicoCollectionTecnico);
            }
            Collection<Jefeinfraestructura> jefeinfraestructuraCollection = usuarios.getJefeinfraestructuraCollection();
            for (Jefeinfraestructura jefeinfraestructuraCollectionJefeinfraestructura : jefeinfraestructuraCollection) {
                jefeinfraestructuraCollectionJefeinfraestructura.setIdUsuario(null);
                jefeinfraestructuraCollectionJefeinfraestructura = em.merge(jefeinfraestructuraCollectionJefeinfraestructura);
            }
            Collection<Proveedores> proveedoresCollection = usuarios.getProveedoresCollection();
            for (Proveedores proveedoresCollectionProveedores : proveedoresCollection) {
                proveedoresCollectionProveedores.setIdUsuario(null);
                proveedoresCollectionProveedores = em.merge(proveedoresCollectionProveedores);
            }
            Collection<Liderinventario> liderinventarioCollection = usuarios.getLiderinventarioCollection();
            for (Liderinventario liderinventarioCollectionLiderinventario : liderinventarioCollection) {
                liderinventarioCollectionLiderinventario.setIdUsuario(null);
                liderinventarioCollectionLiderinventario = em.merge(liderinventarioCollectionLiderinventario);
            }
            Collection<IntegranteColaborador> integranteColaboradorCollection = usuarios.getIntegranteColaboradorCollection();
            for (IntegranteColaborador integranteColaboradorCollectionIntegranteColaborador : integranteColaboradorCollection) {
                integranteColaboradorCollectionIntegranteColaborador.setIdUsuario(null);
                integranteColaboradorCollectionIntegranteColaborador = em.merge(integranteColaboradorCollectionIntegranteColaborador);
            }
            em.remove(usuarios);
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

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
