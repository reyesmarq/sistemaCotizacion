/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Rol;
import com.entities.Permiso;
import com.entities.Rolpermiso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: RolpermisoJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class RolpermisoJpaController implements Serializable {

    public RolpermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public RolpermisoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rolpermiso rolpermiso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol codigoRol = rolpermiso.getCodigoRol();
            if (codigoRol != null) {
                codigoRol = em.getReference(codigoRol.getClass(), codigoRol.getCodigoRol());
                rolpermiso.setCodigoRol(codigoRol);
            }
            Permiso codigoPermiso = rolpermiso.getCodigoPermiso();
            if (codigoPermiso != null) {
                codigoPermiso = em.getReference(codigoPermiso.getClass(), codigoPermiso.getCodigoPermiso());
                rolpermiso.setCodigoPermiso(codigoPermiso);
            }
            em.persist(rolpermiso);
            if (codigoRol != null) {
                codigoRol.getRolpermisoList().add(rolpermiso);
                codigoRol = em.merge(codigoRol);
            }
            if (codigoPermiso != null) {
                codigoPermiso.getRolpermisoList().add(rolpermiso);
                codigoPermiso = em.merge(codigoPermiso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rolpermiso rolpermiso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolpermiso persistentRolpermiso = em.find(Rolpermiso.class, rolpermiso.getCodigoRolPermiso());
            Rol codigoRolOld = persistentRolpermiso.getCodigoRol();
            Rol codigoRolNew = rolpermiso.getCodigoRol();
            Permiso codigoPermisoOld = persistentRolpermiso.getCodigoPermiso();
            Permiso codigoPermisoNew = rolpermiso.getCodigoPermiso();
            if (codigoRolNew != null) {
                codigoRolNew = em.getReference(codigoRolNew.getClass(), codigoRolNew.getCodigoRol());
                rolpermiso.setCodigoRol(codigoRolNew);
            }
            if (codigoPermisoNew != null) {
                codigoPermisoNew = em.getReference(codigoPermisoNew.getClass(), codigoPermisoNew.getCodigoPermiso());
                rolpermiso.setCodigoPermiso(codigoPermisoNew);
            }
            rolpermiso = em.merge(rolpermiso);
            if (codigoRolOld != null && !codigoRolOld.equals(codigoRolNew)) {
                codigoRolOld.getRolpermisoList().remove(rolpermiso);
                codigoRolOld = em.merge(codigoRolOld);
            }
            if (codigoRolNew != null && !codigoRolNew.equals(codigoRolOld)) {
                codigoRolNew.getRolpermisoList().add(rolpermiso);
                codigoRolNew = em.merge(codigoRolNew);
            }
            if (codigoPermisoOld != null && !codigoPermisoOld.equals(codigoPermisoNew)) {
                codigoPermisoOld.getRolpermisoList().remove(rolpermiso);
                codigoPermisoOld = em.merge(codigoPermisoOld);
            }
            if (codigoPermisoNew != null && !codigoPermisoNew.equals(codigoPermisoOld)) {
                codigoPermisoNew.getRolpermisoList().add(rolpermiso);
                codigoPermisoNew = em.merge(codigoPermisoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolpermiso.getCodigoRolPermiso();
                if (findRolpermiso(id) == null) {
                    throw new NonexistentEntityException("The rolpermiso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolpermiso rolpermiso;
            try {
                rolpermiso = em.getReference(Rolpermiso.class, id);
                rolpermiso.getCodigoRolPermiso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolpermiso with id " + id + " no longer exists.", enfe);
            }
            Rol codigoRol = rolpermiso.getCodigoRol();
            if (codigoRol != null) {
                codigoRol.getRolpermisoList().remove(rolpermiso);
                codigoRol = em.merge(codigoRol);
            }
            Permiso codigoPermiso = rolpermiso.getCodigoPermiso();
            if (codigoPermiso != null) {
                codigoPermiso.getRolpermisoList().remove(rolpermiso);
                codigoPermiso = em.merge(codigoPermiso);
            }
            em.remove(rolpermiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rolpermiso> findRolpermisoEntities() {
        return findRolpermisoEntities(true, -1, -1);
    }

    public List<Rolpermiso> findRolpermisoEntities(int maxResults, int firstResult) {
        return findRolpermisoEntities(false, maxResults, firstResult);
    }

    private List<Rolpermiso> findRolpermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rolpermiso.class));
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

    public Rolpermiso findRolpermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rolpermiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolpermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rolpermiso> rt = cq.from(Rolpermiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
