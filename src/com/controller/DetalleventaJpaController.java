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
import com.entities.Venta;
import com.entities.Detalleingreso;
import com.entities.Detalleventa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: DetalleventaJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class DetalleventaJpaController implements Serializable {

    public DetalleventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public DetalleventaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalleventa detalleventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta idVenta = detalleventa.getIdVenta();
            if (idVenta != null) {
                idVenta = em.getReference(idVenta.getClass(), idVenta.getIdVenta());
                detalleventa.setIdVenta(idVenta);
            }
            Detalleingreso idDetalleIngreso = detalleventa.getIdDetalleIngreso();
            if (idDetalleIngreso != null) {
                idDetalleIngreso = em.getReference(idDetalleIngreso.getClass(), idDetalleIngreso.getIdDetalleIngreso());
                detalleventa.setIdDetalleIngreso(idDetalleIngreso);
            }
            em.persist(detalleventa);
            if (idVenta != null) {
                idVenta.getDetalleventaList().add(detalleventa);
                idVenta = em.merge(idVenta);
            }
            if (idDetalleIngreso != null) {
                idDetalleIngreso.getDetalleventaList().add(detalleventa);
                idDetalleIngreso = em.merge(idDetalleIngreso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleventa detalleventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleventa persistentDetalleventa = em.find(Detalleventa.class, detalleventa.getIdDetalleVenta());
            Venta idVentaOld = persistentDetalleventa.getIdVenta();
            Venta idVentaNew = detalleventa.getIdVenta();
            Detalleingreso idDetalleIngresoOld = persistentDetalleventa.getIdDetalleIngreso();
            Detalleingreso idDetalleIngresoNew = detalleventa.getIdDetalleIngreso();
            if (idVentaNew != null) {
                idVentaNew = em.getReference(idVentaNew.getClass(), idVentaNew.getIdVenta());
                detalleventa.setIdVenta(idVentaNew);
            }
            if (idDetalleIngresoNew != null) {
                idDetalleIngresoNew = em.getReference(idDetalleIngresoNew.getClass(), idDetalleIngresoNew.getIdDetalleIngreso());
                detalleventa.setIdDetalleIngreso(idDetalleIngresoNew);
            }
            detalleventa = em.merge(detalleventa);
            if (idVentaOld != null && !idVentaOld.equals(idVentaNew)) {
                idVentaOld.getDetalleventaList().remove(detalleventa);
                idVentaOld = em.merge(idVentaOld);
            }
            if (idVentaNew != null && !idVentaNew.equals(idVentaOld)) {
                idVentaNew.getDetalleventaList().add(detalleventa);
                idVentaNew = em.merge(idVentaNew);
            }
            if (idDetalleIngresoOld != null && !idDetalleIngresoOld.equals(idDetalleIngresoNew)) {
                idDetalleIngresoOld.getDetalleventaList().remove(detalleventa);
                idDetalleIngresoOld = em.merge(idDetalleIngresoOld);
            }
            if (idDetalleIngresoNew != null && !idDetalleIngresoNew.equals(idDetalleIngresoOld)) {
                idDetalleIngresoNew.getDetalleventaList().add(detalleventa);
                idDetalleIngresoNew = em.merge(idDetalleIngresoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleventa.getIdDetalleVenta();
                if (findDetalleventa(id) == null) {
                    throw new NonexistentEntityException("The detalleventa with id " + id + " no longer exists.");
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
            Detalleventa detalleventa;
            try {
                detalleventa = em.getReference(Detalleventa.class, id);
                detalleventa.getIdDetalleVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleventa with id " + id + " no longer exists.", enfe);
            }
            Venta idVenta = detalleventa.getIdVenta();
            if (idVenta != null) {
                idVenta.getDetalleventaList().remove(detalleventa);
                idVenta = em.merge(idVenta);
            }
            Detalleingreso idDetalleIngreso = detalleventa.getIdDetalleIngreso();
            if (idDetalleIngreso != null) {
                idDetalleIngreso.getDetalleventaList().remove(detalleventa);
                idDetalleIngreso = em.merge(idDetalleIngreso);
            }
            em.remove(detalleventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleventa> findDetalleventaEntities() {
        return findDetalleventaEntities(true, -1, -1);
    }

    public List<Detalleventa> findDetalleventaEntities(int maxResults, int firstResult) {
        return findDetalleventaEntities(false, maxResults, firstResult);
    }

    private List<Detalleventa> findDetalleventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleventa.class));
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

    public Detalleventa findDetalleventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleventa> rt = cq.from(Detalleventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
