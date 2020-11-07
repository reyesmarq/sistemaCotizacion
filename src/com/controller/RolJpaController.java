/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Rolpermiso;
import java.util.ArrayList;
import java.util.List;
import com.entities.Rolusuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: RolJpaController
 *  Fecha: 11-04-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public RolJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getRolpermisoList() == null) {
            rol.setRolpermisoList(new ArrayList<Rolpermiso>());
        }
        if (rol.getRolusuarioList() == null) {
            rol.setRolusuarioList(new ArrayList<Rolusuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Rolpermiso> attachedRolpermisoList = new ArrayList<Rolpermiso>();
            for (Rolpermiso rolpermisoListRolpermisoToAttach : rol.getRolpermisoList()) {
                rolpermisoListRolpermisoToAttach = em.getReference(rolpermisoListRolpermisoToAttach.getClass(), rolpermisoListRolpermisoToAttach.getCodigoRolPermiso());
                attachedRolpermisoList.add(rolpermisoListRolpermisoToAttach);
            }
            rol.setRolpermisoList(attachedRolpermisoList);
            List<Rolusuario> attachedRolusuarioList = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioListRolusuarioToAttach : rol.getRolusuarioList()) {
                rolusuarioListRolusuarioToAttach = em.getReference(rolusuarioListRolusuarioToAttach.getClass(), rolusuarioListRolusuarioToAttach.getCodigoRolUsuario());
                attachedRolusuarioList.add(rolusuarioListRolusuarioToAttach);
            }
            rol.setRolusuarioList(attachedRolusuarioList);
            em.persist(rol);
            for (Rolpermiso rolpermisoListRolpermiso : rol.getRolpermisoList()) {
                Rol oldCodigoRolOfRolpermisoListRolpermiso = rolpermisoListRolpermiso.getCodigoRol();
                rolpermisoListRolpermiso.setCodigoRol(rol);
                rolpermisoListRolpermiso = em.merge(rolpermisoListRolpermiso);
                if (oldCodigoRolOfRolpermisoListRolpermiso != null) {
                    oldCodigoRolOfRolpermisoListRolpermiso.getRolpermisoList().remove(rolpermisoListRolpermiso);
                    oldCodigoRolOfRolpermisoListRolpermiso = em.merge(oldCodigoRolOfRolpermisoListRolpermiso);
                }
            }
            for (Rolusuario rolusuarioListRolusuario : rol.getRolusuarioList()) {
                Rol oldCodigoRolOfRolusuarioListRolusuario = rolusuarioListRolusuario.getCodigoRol();
                rolusuarioListRolusuario.setCodigoRol(rol);
                rolusuarioListRolusuario = em.merge(rolusuarioListRolusuario);
                if (oldCodigoRolOfRolusuarioListRolusuario != null) {
                    oldCodigoRolOfRolusuarioListRolusuario.getRolusuarioList().remove(rolusuarioListRolusuario);
                    oldCodigoRolOfRolusuarioListRolusuario = em.merge(oldCodigoRolOfRolusuarioListRolusuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getCodigoRol());
            List<Rolpermiso> rolpermisoListOld = persistentRol.getRolpermisoList();
            List<Rolpermiso> rolpermisoListNew = rol.getRolpermisoList();
            List<Rolusuario> rolusuarioListOld = persistentRol.getRolusuarioList();
            List<Rolusuario> rolusuarioListNew = rol.getRolusuarioList();
            List<String> illegalOrphanMessages = null;
            for (Rolpermiso rolpermisoListOldRolpermiso : rolpermisoListOld) {
                if (!rolpermisoListNew.contains(rolpermisoListOldRolpermiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolpermiso " + rolpermisoListOldRolpermiso + " since its codigoRol field is not nullable.");
                }
            }
            for (Rolusuario rolusuarioListOldRolusuario : rolusuarioListOld) {
                if (!rolusuarioListNew.contains(rolusuarioListOldRolusuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolusuario " + rolusuarioListOldRolusuario + " since its codigoRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Rolpermiso> attachedRolpermisoListNew = new ArrayList<Rolpermiso>();
            for (Rolpermiso rolpermisoListNewRolpermisoToAttach : rolpermisoListNew) {
                rolpermisoListNewRolpermisoToAttach = em.getReference(rolpermisoListNewRolpermisoToAttach.getClass(), rolpermisoListNewRolpermisoToAttach.getCodigoRolPermiso());
                attachedRolpermisoListNew.add(rolpermisoListNewRolpermisoToAttach);
            }
            rolpermisoListNew = attachedRolpermisoListNew;
            rol.setRolpermisoList(rolpermisoListNew);
            List<Rolusuario> attachedRolusuarioListNew = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioListNewRolusuarioToAttach : rolusuarioListNew) {
                rolusuarioListNewRolusuarioToAttach = em.getReference(rolusuarioListNewRolusuarioToAttach.getClass(), rolusuarioListNewRolusuarioToAttach.getCodigoRolUsuario());
                attachedRolusuarioListNew.add(rolusuarioListNewRolusuarioToAttach);
            }
            rolusuarioListNew = attachedRolusuarioListNew;
            rol.setRolusuarioList(rolusuarioListNew);
            rol = em.merge(rol);
            for (Rolpermiso rolpermisoListNewRolpermiso : rolpermisoListNew) {
                if (!rolpermisoListOld.contains(rolpermisoListNewRolpermiso)) {
                    Rol oldCodigoRolOfRolpermisoListNewRolpermiso = rolpermisoListNewRolpermiso.getCodigoRol();
                    rolpermisoListNewRolpermiso.setCodigoRol(rol);
                    rolpermisoListNewRolpermiso = em.merge(rolpermisoListNewRolpermiso);
                    if (oldCodigoRolOfRolpermisoListNewRolpermiso != null && !oldCodigoRolOfRolpermisoListNewRolpermiso.equals(rol)) {
                        oldCodigoRolOfRolpermisoListNewRolpermiso.getRolpermisoList().remove(rolpermisoListNewRolpermiso);
                        oldCodigoRolOfRolpermisoListNewRolpermiso = em.merge(oldCodigoRolOfRolpermisoListNewRolpermiso);
                    }
                }
            }
            for (Rolusuario rolusuarioListNewRolusuario : rolusuarioListNew) {
                if (!rolusuarioListOld.contains(rolusuarioListNewRolusuario)) {
                    Rol oldCodigoRolOfRolusuarioListNewRolusuario = rolusuarioListNewRolusuario.getCodigoRol();
                    rolusuarioListNewRolusuario.setCodigoRol(rol);
                    rolusuarioListNewRolusuario = em.merge(rolusuarioListNewRolusuario);
                    if (oldCodigoRolOfRolusuarioListNewRolusuario != null && !oldCodigoRolOfRolusuarioListNewRolusuario.equals(rol)) {
                        oldCodigoRolOfRolusuarioListNewRolusuario.getRolusuarioList().remove(rolusuarioListNewRolusuario);
                        oldCodigoRolOfRolusuarioListNewRolusuario = em.merge(oldCodigoRolOfRolusuarioListNewRolusuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getCodigoRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getCodigoRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rolpermiso> rolpermisoListOrphanCheck = rol.getRolpermisoList();
            for (Rolpermiso rolpermisoListOrphanCheckRolpermiso : rolpermisoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the Rolpermiso " + rolpermisoListOrphanCheckRolpermiso + " in its rolpermisoList field has a non-nullable codigoRol field.");
            }
            List<Rolusuario> rolusuarioListOrphanCheck = rol.getRolusuarioList();
            for (Rolusuario rolusuarioListOrphanCheckRolusuario : rolusuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the Rolusuario " + rolusuarioListOrphanCheckRolusuario + " in its rolusuarioList field has a non-nullable codigoRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
