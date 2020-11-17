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
import com.entities.Ingreso;
import com.entities.Articulo;
import com.entities.Detalleingreso;
import com.entities.Detalleventa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: DetalleingresoJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class DetalleingresoJpaController implements Serializable {

    public DetalleingresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public DetalleingresoJpaController() {
        this.emf = this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalleingreso detalleingreso) {
        if (detalleingreso.getDetalleventaList() == null) {
            detalleingreso.setDetalleventaList(new ArrayList<Detalleventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingreso idIngreso = detalleingreso.getIdIngreso();
            if (idIngreso != null) {
                idIngreso = em.getReference(idIngreso.getClass(), idIngreso.getIdIngreso());
                detalleingreso.setIdIngreso(idIngreso);
            }
            Articulo idArticulo = detalleingreso.getIdArticulo();
            if (idArticulo != null) {
                idArticulo = em.getReference(idArticulo.getClass(), idArticulo.getIdArticulo());
                detalleingreso.setIdArticulo(idArticulo);
            }
            List<Detalleventa> attachedDetalleventaList = new ArrayList<Detalleventa>();
            for (Detalleventa detalleventaListDetalleventaToAttach : detalleingreso.getDetalleventaList()) {
                detalleventaListDetalleventaToAttach = em.getReference(detalleventaListDetalleventaToAttach.getClass(), detalleventaListDetalleventaToAttach.getIdDetalleVenta());
                attachedDetalleventaList.add(detalleventaListDetalleventaToAttach);
            }
            detalleingreso.setDetalleventaList(attachedDetalleventaList);
            em.persist(detalleingreso);
            if (idIngreso != null) {
                idIngreso.getDetalleingresoList().add(detalleingreso);
                idIngreso = em.merge(idIngreso);
            }
            if (idArticulo != null) {
                idArticulo.getDetalleingresoList().add(detalleingreso);
                idArticulo = em.merge(idArticulo);
            }
            for (Detalleventa detalleventaListDetalleventa : detalleingreso.getDetalleventaList()) {
                Detalleingreso oldIdDetalleIngresoOfDetalleventaListDetalleventa = detalleventaListDetalleventa.getIdDetalleIngreso();
                detalleventaListDetalleventa.setIdDetalleIngreso(detalleingreso);
                detalleventaListDetalleventa = em.merge(detalleventaListDetalleventa);
                if (oldIdDetalleIngresoOfDetalleventaListDetalleventa != null) {
                    oldIdDetalleIngresoOfDetalleventaListDetalleventa.getDetalleventaList().remove(detalleventaListDetalleventa);
                    oldIdDetalleIngresoOfDetalleventaListDetalleventa = em.merge(oldIdDetalleIngresoOfDetalleventaListDetalleventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleingreso detalleingreso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleingreso persistentDetalleingreso = em.find(Detalleingreso.class, detalleingreso.getIdDetalleIngreso());
            Ingreso idIngresoOld = persistentDetalleingreso.getIdIngreso();
            Ingreso idIngresoNew = detalleingreso.getIdIngreso();
            Articulo idArticuloOld = persistentDetalleingreso.getIdArticulo();
            Articulo idArticuloNew = detalleingreso.getIdArticulo();
            List<Detalleventa> detalleventaListOld = persistentDetalleingreso.getDetalleventaList();
            List<Detalleventa> detalleventaListNew = detalleingreso.getDetalleventaList();
            if (idIngresoNew != null) {
                idIngresoNew = em.getReference(idIngresoNew.getClass(), idIngresoNew.getIdIngreso());
                detalleingreso.setIdIngreso(idIngresoNew);
            }
            if (idArticuloNew != null) {
                idArticuloNew = em.getReference(idArticuloNew.getClass(), idArticuloNew.getIdArticulo());
                detalleingreso.setIdArticulo(idArticuloNew);
            }
            List<Detalleventa> attachedDetalleventaListNew = new ArrayList<Detalleventa>();
            for (Detalleventa detalleventaListNewDetalleventaToAttach : detalleventaListNew) {
                detalleventaListNewDetalleventaToAttach = em.getReference(detalleventaListNewDetalleventaToAttach.getClass(), detalleventaListNewDetalleventaToAttach.getIdDetalleVenta());
                attachedDetalleventaListNew.add(detalleventaListNewDetalleventaToAttach);
            }
            detalleventaListNew = attachedDetalleventaListNew;
            detalleingreso.setDetalleventaList(detalleventaListNew);
            detalleingreso = em.merge(detalleingreso);
            if (idIngresoOld != null && !idIngresoOld.equals(idIngresoNew)) {
                idIngresoOld.getDetalleingresoList().remove(detalleingreso);
                idIngresoOld = em.merge(idIngresoOld);
            }
            if (idIngresoNew != null && !idIngresoNew.equals(idIngresoOld)) {
                idIngresoNew.getDetalleingresoList().add(detalleingreso);
                idIngresoNew = em.merge(idIngresoNew);
            }
            if (idArticuloOld != null && !idArticuloOld.equals(idArticuloNew)) {
                idArticuloOld.getDetalleingresoList().remove(detalleingreso);
                idArticuloOld = em.merge(idArticuloOld);
            }
            if (idArticuloNew != null && !idArticuloNew.equals(idArticuloOld)) {
                idArticuloNew.getDetalleingresoList().add(detalleingreso);
                idArticuloNew = em.merge(idArticuloNew);
            }
            for (Detalleventa detalleventaListOldDetalleventa : detalleventaListOld) {
                if (!detalleventaListNew.contains(detalleventaListOldDetalleventa)) {
                    detalleventaListOldDetalleventa.setIdDetalleIngreso(null);
                    detalleventaListOldDetalleventa = em.merge(detalleventaListOldDetalleventa);
                }
            }
            for (Detalleventa detalleventaListNewDetalleventa : detalleventaListNew) {
                if (!detalleventaListOld.contains(detalleventaListNewDetalleventa)) {
                    Detalleingreso oldIdDetalleIngresoOfDetalleventaListNewDetalleventa = detalleventaListNewDetalleventa.getIdDetalleIngreso();
                    detalleventaListNewDetalleventa.setIdDetalleIngreso(detalleingreso);
                    detalleventaListNewDetalleventa = em.merge(detalleventaListNewDetalleventa);
                    if (oldIdDetalleIngresoOfDetalleventaListNewDetalleventa != null && !oldIdDetalleIngresoOfDetalleventaListNewDetalleventa.equals(detalleingreso)) {
                        oldIdDetalleIngresoOfDetalleventaListNewDetalleventa.getDetalleventaList().remove(detalleventaListNewDetalleventa);
                        oldIdDetalleIngresoOfDetalleventaListNewDetalleventa = em.merge(oldIdDetalleIngresoOfDetalleventaListNewDetalleventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleingreso.getIdDetalleIngreso();
                if (findDetalleingreso(id) == null) {
                    throw new NonexistentEntityException("The detalleingreso with id " + id + " no longer exists.");
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
            Detalleingreso detalleingreso;
            try {
                detalleingreso = em.getReference(Detalleingreso.class, id);
                detalleingreso.getIdDetalleIngreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleingreso with id " + id + " no longer exists.", enfe);
            }
            Ingreso idIngreso = detalleingreso.getIdIngreso();
            if (idIngreso != null) {
                idIngreso.getDetalleingresoList().remove(detalleingreso);
                idIngreso = em.merge(idIngreso);
            }
            Articulo idArticulo = detalleingreso.getIdArticulo();
            if (idArticulo != null) {
                idArticulo.getDetalleingresoList().remove(detalleingreso);
                idArticulo = em.merge(idArticulo);
            }
            List<Detalleventa> detalleventaList = detalleingreso.getDetalleventaList();
            for (Detalleventa detalleventaListDetalleventa : detalleventaList) {
                detalleventaListDetalleventa.setIdDetalleIngreso(null);
                detalleventaListDetalleventa = em.merge(detalleventaListDetalleventa);
            }
            em.remove(detalleingreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleingreso> findDetalleingresoEntities() {
        return findDetalleingresoEntities(true, -1, -1);
    }

    public List<Detalleingreso> findDetalleingresoEntities(int maxResults, int firstResult) {
        return findDetalleingresoEntities(false, maxResults, firstResult);
    }

    private List<Detalleingreso> findDetalleingresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleingreso.class));
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

    public Detalleingreso findDetalleingreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleingreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleingresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleingreso> rt = cq.from(Detalleingreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
