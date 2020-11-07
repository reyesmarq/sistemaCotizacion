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
import com.entities.Usuario;
import com.entities.Permiso;
import com.entities.Usuariopermiso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: UsuariopermisoJpaController
 *  Fecha: 11-04-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class UsuariopermisoJpaController implements Serializable {

    public UsuariopermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public UsuariopermisoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuariopermiso usuariopermiso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario codigoUsuario = usuariopermiso.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                usuariopermiso.setCodigoUsuario(codigoUsuario);
            }
            Permiso codigoPermiso = usuariopermiso.getCodigoPermiso();
            if (codigoPermiso != null) {
                codigoPermiso = em.getReference(codigoPermiso.getClass(), codigoPermiso.getCodigoPermiso());
                usuariopermiso.setCodigoPermiso(codigoPermiso);
            }
            em.persist(usuariopermiso);
            if (codigoUsuario != null) {
                codigoUsuario.getUsuariopermisoList().add(usuariopermiso);
                codigoUsuario = em.merge(codigoUsuario);
            }
            if (codigoPermiso != null) {
                codigoPermiso.getUsuariopermisoList().add(usuariopermiso);
                codigoPermiso = em.merge(codigoPermiso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuariopermiso usuariopermiso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuariopermiso persistentUsuariopermiso = em.find(Usuariopermiso.class, usuariopermiso.getCodigoUsuarioPermiso());
            Usuario codigoUsuarioOld = persistentUsuariopermiso.getCodigoUsuario();
            Usuario codigoUsuarioNew = usuariopermiso.getCodigoUsuario();
            Permiso codigoPermisoOld = persistentUsuariopermiso.getCodigoPermiso();
            Permiso codigoPermisoNew = usuariopermiso.getCodigoPermiso();
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                usuariopermiso.setCodigoUsuario(codigoUsuarioNew);
            }
            if (codigoPermisoNew != null) {
                codigoPermisoNew = em.getReference(codigoPermisoNew.getClass(), codigoPermisoNew.getCodigoPermiso());
                usuariopermiso.setCodigoPermiso(codigoPermisoNew);
            }
            usuariopermiso = em.merge(usuariopermiso);
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getUsuariopermisoList().remove(usuariopermiso);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getUsuariopermisoList().add(usuariopermiso);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            if (codigoPermisoOld != null && !codigoPermisoOld.equals(codigoPermisoNew)) {
                codigoPermisoOld.getUsuariopermisoList().remove(usuariopermiso);
                codigoPermisoOld = em.merge(codigoPermisoOld);
            }
            if (codigoPermisoNew != null && !codigoPermisoNew.equals(codigoPermisoOld)) {
                codigoPermisoNew.getUsuariopermisoList().add(usuariopermiso);
                codigoPermisoNew = em.merge(codigoPermisoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuariopermiso.getCodigoUsuarioPermiso();
                if (findUsuariopermiso(id) == null) {
                    throw new NonexistentEntityException("The usuariopermiso with id " + id + " no longer exists.");
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
            Usuariopermiso usuariopermiso;
            try {
                usuariopermiso = em.getReference(Usuariopermiso.class, id);
                usuariopermiso.getCodigoUsuarioPermiso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariopermiso with id " + id + " no longer exists.", enfe);
            }
            Usuario codigoUsuario = usuariopermiso.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getUsuariopermisoList().remove(usuariopermiso);
                codigoUsuario = em.merge(codigoUsuario);
            }
            Permiso codigoPermiso = usuariopermiso.getCodigoPermiso();
            if (codigoPermiso != null) {
                codigoPermiso.getUsuariopermisoList().remove(usuariopermiso);
                codigoPermiso = em.merge(codigoPermiso);
            }
            em.remove(usuariopermiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuariopermiso> findUsuariopermisoEntities() {
        return findUsuariopermisoEntities(true, -1, -1);
    }

    public List<Usuariopermiso> findUsuariopermisoEntities(int maxResults, int firstResult) {
        return findUsuariopermisoEntities(false, maxResults, firstResult);
    }

    private List<Usuariopermiso> findUsuariopermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuariopermiso.class));
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

    public Usuariopermiso findUsuariopermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuariopermiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariopermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuariopermiso> rt = cq.from(Usuariopermiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
