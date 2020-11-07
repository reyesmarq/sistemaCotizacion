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
import com.entities.Rolusuario;
import com.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: RolusuarioJpaController
 *  Fecha: 11-04-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class RolusuarioJpaController implements Serializable {

    public RolusuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public RolusuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rolusuario rolusuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol codigoRol = rolusuario.getCodigoRol();
            if (codigoRol != null) {
                codigoRol = em.getReference(codigoRol.getClass(), codigoRol.getCodigoRol());
                rolusuario.setCodigoRol(codigoRol);
            }
            Usuario codigoUsuario = rolusuario.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                rolusuario.setCodigoUsuario(codigoUsuario);
            }
            em.persist(rolusuario);
            if (codigoRol != null) {
                codigoRol.getRolusuarioList().add(rolusuario);
                codigoRol = em.merge(codigoRol);
            }
            if (codigoUsuario != null) {
                codigoUsuario.getRolusuarioList().add(rolusuario);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rolusuario rolusuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolusuario persistentRolusuario = em.find(Rolusuario.class, rolusuario.getCodigoRolUsuario());
            Rol codigoRolOld = persistentRolusuario.getCodigoRol();
            Rol codigoRolNew = rolusuario.getCodigoRol();
            Usuario codigoUsuarioOld = persistentRolusuario.getCodigoUsuario();
            Usuario codigoUsuarioNew = rolusuario.getCodigoUsuario();
            if (codigoRolNew != null) {
                codigoRolNew = em.getReference(codigoRolNew.getClass(), codigoRolNew.getCodigoRol());
                rolusuario.setCodigoRol(codigoRolNew);
            }
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                rolusuario.setCodigoUsuario(codigoUsuarioNew);
            }
            rolusuario = em.merge(rolusuario);
            if (codigoRolOld != null && !codigoRolOld.equals(codigoRolNew)) {
                codigoRolOld.getRolusuarioList().remove(rolusuario);
                codigoRolOld = em.merge(codigoRolOld);
            }
            if (codigoRolNew != null && !codigoRolNew.equals(codigoRolOld)) {
                codigoRolNew.getRolusuarioList().add(rolusuario);
                codigoRolNew = em.merge(codigoRolNew);
            }
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getRolusuarioList().remove(rolusuario);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getRolusuarioList().add(rolusuario);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolusuario.getCodigoRolUsuario();
                if (findRolusuario(id) == null) {
                    throw new NonexistentEntityException("The rolusuario with id " + id + " no longer exists.");
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
            Rolusuario rolusuario;
            try {
                rolusuario = em.getReference(Rolusuario.class, id);
                rolusuario.getCodigoRolUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolusuario with id " + id + " no longer exists.", enfe);
            }
            Rol codigoRol = rolusuario.getCodigoRol();
            if (codigoRol != null) {
                codigoRol.getRolusuarioList().remove(rolusuario);
                codigoRol = em.merge(codigoRol);
            }
            Usuario codigoUsuario = rolusuario.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getRolusuarioList().remove(rolusuario);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.remove(rolusuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rolusuario> findRolusuarioEntities() {
        return findRolusuarioEntities(true, -1, -1);
    }

    public List<Rolusuario> findRolusuarioEntities(int maxResults, int firstResult) {
        return findRolusuarioEntities(false, maxResults, firstResult);
    }

    private List<Rolusuario> findRolusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rolusuario.class));
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

    public Rolusuario findRolusuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rolusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rolusuario> rt = cq.from(Rolusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
