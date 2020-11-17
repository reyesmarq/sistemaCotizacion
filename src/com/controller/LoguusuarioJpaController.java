/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Loguusuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: LoguusuarioJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class LoguusuarioJpaController implements Serializable {

    public LoguusuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public LoguusuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Loguusuario loguusuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario codigoUsuario = loguusuario.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                loguusuario.setCodigoUsuario(codigoUsuario);
            }
            em.persist(loguusuario);
            if (codigoUsuario != null) {
                codigoUsuario.getLoguusuarioList().add(loguusuario);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Loguusuario loguusuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loguusuario persistentLoguusuario = em.find(Loguusuario.class, loguusuario.getCodigoLog());
            Usuario codigoUsuarioOld = persistentLoguusuario.getCodigoUsuario();
            Usuario codigoUsuarioNew = loguusuario.getCodigoUsuario();
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                loguusuario.setCodigoUsuario(codigoUsuarioNew);
            }
            loguusuario = em.merge(loguusuario);
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getLoguusuarioList().remove(loguusuario);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getLoguusuarioList().add(loguusuario);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = loguusuario.getCodigoLog();
                if (findLoguusuario(id) == null) {
                    throw new NonexistentEntityException("The loguusuario with id " + id + " no longer exists.");
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
            Loguusuario loguusuario;
            try {
                loguusuario = em.getReference(Loguusuario.class, id);
                loguusuario.getCodigoLog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loguusuario with id " + id + " no longer exists.", enfe);
            }
            Usuario codigoUsuario = loguusuario.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getLoguusuarioList().remove(loguusuario);
                codigoUsuario = em.merge(codigoUsuario);
            }
            em.remove(loguusuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Loguusuario> findLoguusuarioEntities() {
        return findLoguusuarioEntities(true, -1, -1);
    }

    public List<Loguusuario> findLoguusuarioEntities(int maxResults, int firstResult) {
        return findLoguusuarioEntities(false, maxResults, firstResult);
    }

    private List<Loguusuario> findLoguusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Loguusuario.class));
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

    public Loguusuario findLoguusuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Loguusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoguusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Loguusuario> rt = cq.from(Loguusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
