/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Permiso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuariopermiso;
import java.util.ArrayList;
import java.util.List;
import com.entities.Rolpermiso;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: PermisoJpaController
 *  Fecha: 11-04-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class PermisoJpaController implements Serializable {

    public PermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PermisoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permiso permiso) {
        if (permiso.getUsuariopermisoList() == null) {
            permiso.setUsuariopermisoList(new ArrayList<Usuariopermiso>());
        }
        if (permiso.getRolpermisoList() == null) {
            permiso.setRolpermisoList(new ArrayList<Rolpermiso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuariopermiso> attachedUsuariopermisoList = new ArrayList<Usuariopermiso>();
            for (Usuariopermiso usuariopermisoListUsuariopermisoToAttach : permiso.getUsuariopermisoList()) {
                usuariopermisoListUsuariopermisoToAttach = em.getReference(usuariopermisoListUsuariopermisoToAttach.getClass(), usuariopermisoListUsuariopermisoToAttach.getCodigoUsuarioPermiso());
                attachedUsuariopermisoList.add(usuariopermisoListUsuariopermisoToAttach);
            }
            permiso.setUsuariopermisoList(attachedUsuariopermisoList);
            List<Rolpermiso> attachedRolpermisoList = new ArrayList<Rolpermiso>();
            for (Rolpermiso rolpermisoListRolpermisoToAttach : permiso.getRolpermisoList()) {
                rolpermisoListRolpermisoToAttach = em.getReference(rolpermisoListRolpermisoToAttach.getClass(), rolpermisoListRolpermisoToAttach.getCodigoRolPermiso());
                attachedRolpermisoList.add(rolpermisoListRolpermisoToAttach);
            }
            permiso.setRolpermisoList(attachedRolpermisoList);
            em.persist(permiso);
            for (Usuariopermiso usuariopermisoListUsuariopermiso : permiso.getUsuariopermisoList()) {
                Permiso oldCodigoPermisoOfUsuariopermisoListUsuariopermiso = usuariopermisoListUsuariopermiso.getCodigoPermiso();
                usuariopermisoListUsuariopermiso.setCodigoPermiso(permiso);
                usuariopermisoListUsuariopermiso = em.merge(usuariopermisoListUsuariopermiso);
                if (oldCodigoPermisoOfUsuariopermisoListUsuariopermiso != null) {
                    oldCodigoPermisoOfUsuariopermisoListUsuariopermiso.getUsuariopermisoList().remove(usuariopermisoListUsuariopermiso);
                    oldCodigoPermisoOfUsuariopermisoListUsuariopermiso = em.merge(oldCodigoPermisoOfUsuariopermisoListUsuariopermiso);
                }
            }
            for (Rolpermiso rolpermisoListRolpermiso : permiso.getRolpermisoList()) {
                Permiso oldCodigoPermisoOfRolpermisoListRolpermiso = rolpermisoListRolpermiso.getCodigoPermiso();
                rolpermisoListRolpermiso.setCodigoPermiso(permiso);
                rolpermisoListRolpermiso = em.merge(rolpermisoListRolpermiso);
                if (oldCodigoPermisoOfRolpermisoListRolpermiso != null) {
                    oldCodigoPermisoOfRolpermisoListRolpermiso.getRolpermisoList().remove(rolpermisoListRolpermiso);
                    oldCodigoPermisoOfRolpermisoListRolpermiso = em.merge(oldCodigoPermisoOfRolpermisoListRolpermiso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permiso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso persistentPermiso = em.find(Permiso.class, permiso.getCodigoPermiso());
            List<Usuariopermiso> usuariopermisoListOld = persistentPermiso.getUsuariopermisoList();
            List<Usuariopermiso> usuariopermisoListNew = permiso.getUsuariopermisoList();
            List<Rolpermiso> rolpermisoListOld = persistentPermiso.getRolpermisoList();
            List<Rolpermiso> rolpermisoListNew = permiso.getRolpermisoList();
            List<String> illegalOrphanMessages = null;
            for (Usuariopermiso usuariopermisoListOldUsuariopermiso : usuariopermisoListOld) {
                if (!usuariopermisoListNew.contains(usuariopermisoListOldUsuariopermiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuariopermiso " + usuariopermisoListOldUsuariopermiso + " since its codigoPermiso field is not nullable.");
                }
            }
            for (Rolpermiso rolpermisoListOldRolpermiso : rolpermisoListOld) {
                if (!rolpermisoListNew.contains(rolpermisoListOldRolpermiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolpermiso " + rolpermisoListOldRolpermiso + " since its codigoPermiso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuariopermiso> attachedUsuariopermisoListNew = new ArrayList<Usuariopermiso>();
            for (Usuariopermiso usuariopermisoListNewUsuariopermisoToAttach : usuariopermisoListNew) {
                usuariopermisoListNewUsuariopermisoToAttach = em.getReference(usuariopermisoListNewUsuariopermisoToAttach.getClass(), usuariopermisoListNewUsuariopermisoToAttach.getCodigoUsuarioPermiso());
                attachedUsuariopermisoListNew.add(usuariopermisoListNewUsuariopermisoToAttach);
            }
            usuariopermisoListNew = attachedUsuariopermisoListNew;
            permiso.setUsuariopermisoList(usuariopermisoListNew);
            List<Rolpermiso> attachedRolpermisoListNew = new ArrayList<Rolpermiso>();
            for (Rolpermiso rolpermisoListNewRolpermisoToAttach : rolpermisoListNew) {
                rolpermisoListNewRolpermisoToAttach = em.getReference(rolpermisoListNewRolpermisoToAttach.getClass(), rolpermisoListNewRolpermisoToAttach.getCodigoRolPermiso());
                attachedRolpermisoListNew.add(rolpermisoListNewRolpermisoToAttach);
            }
            rolpermisoListNew = attachedRolpermisoListNew;
            permiso.setRolpermisoList(rolpermisoListNew);
            permiso = em.merge(permiso);
            for (Usuariopermiso usuariopermisoListNewUsuariopermiso : usuariopermisoListNew) {
                if (!usuariopermisoListOld.contains(usuariopermisoListNewUsuariopermiso)) {
                    Permiso oldCodigoPermisoOfUsuariopermisoListNewUsuariopermiso = usuariopermisoListNewUsuariopermiso.getCodigoPermiso();
                    usuariopermisoListNewUsuariopermiso.setCodigoPermiso(permiso);
                    usuariopermisoListNewUsuariopermiso = em.merge(usuariopermisoListNewUsuariopermiso);
                    if (oldCodigoPermisoOfUsuariopermisoListNewUsuariopermiso != null && !oldCodigoPermisoOfUsuariopermisoListNewUsuariopermiso.equals(permiso)) {
                        oldCodigoPermisoOfUsuariopermisoListNewUsuariopermiso.getUsuariopermisoList().remove(usuariopermisoListNewUsuariopermiso);
                        oldCodigoPermisoOfUsuariopermisoListNewUsuariopermiso = em.merge(oldCodigoPermisoOfUsuariopermisoListNewUsuariopermiso);
                    }
                }
            }
            for (Rolpermiso rolpermisoListNewRolpermiso : rolpermisoListNew) {
                if (!rolpermisoListOld.contains(rolpermisoListNewRolpermiso)) {
                    Permiso oldCodigoPermisoOfRolpermisoListNewRolpermiso = rolpermisoListNewRolpermiso.getCodigoPermiso();
                    rolpermisoListNewRolpermiso.setCodigoPermiso(permiso);
                    rolpermisoListNewRolpermiso = em.merge(rolpermisoListNewRolpermiso);
                    if (oldCodigoPermisoOfRolpermisoListNewRolpermiso != null && !oldCodigoPermisoOfRolpermisoListNewRolpermiso.equals(permiso)) {
                        oldCodigoPermisoOfRolpermisoListNewRolpermiso.getRolpermisoList().remove(rolpermisoListNewRolpermiso);
                        oldCodigoPermisoOfRolpermisoListNewRolpermiso = em.merge(oldCodigoPermisoOfRolpermisoListNewRolpermiso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permiso.getCodigoPermiso();
                if (findPermiso(id) == null) {
                    throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.");
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
            Permiso permiso;
            try {
                permiso = em.getReference(Permiso.class, id);
                permiso.getCodigoPermiso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuariopermiso> usuariopermisoListOrphanCheck = permiso.getUsuariopermisoList();
            for (Usuariopermiso usuariopermisoListOrphanCheckUsuariopermiso : usuariopermisoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permiso (" + permiso + ") cannot be destroyed since the Usuariopermiso " + usuariopermisoListOrphanCheckUsuariopermiso + " in its usuariopermisoList field has a non-nullable codigoPermiso field.");
            }
            List<Rolpermiso> rolpermisoListOrphanCheck = permiso.getRolpermisoList();
            for (Rolpermiso rolpermisoListOrphanCheckRolpermiso : rolpermisoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permiso (" + permiso + ") cannot be destroyed since the Rolpermiso " + rolpermisoListOrphanCheckRolpermiso + " in its rolpermisoList field has a non-nullable codigoPermiso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(permiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisoEntities() {
        return findPermisoEntities(true, -1, -1);
    }

    public List<Permiso> findPermisoEntities(int maxResults, int firstResult) {
        return findPermisoEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
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

    public Permiso findPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
