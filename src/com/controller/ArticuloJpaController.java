/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Articulo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Categoria;
import com.entities.Presentacion;
import com.entities.Detalleingreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: ArticuloJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ArticuloJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) {
        if (articulo.getDetalleingresoList() == null) {
            articulo.setDetalleingresoList(new ArrayList<Detalleingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = articulo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                articulo.setIdCategoria(idCategoria);
            }
            Presentacion idPresentacion = articulo.getIdPresentacion();
            if (idPresentacion != null) {
                idPresentacion = em.getReference(idPresentacion.getClass(), idPresentacion.getIdPresentacion());
                articulo.setIdPresentacion(idPresentacion);
            }
            List<Detalleingreso> attachedDetalleingresoList = new ArrayList<Detalleingreso>();
            for (Detalleingreso detalleingresoListDetalleingresoToAttach : articulo.getDetalleingresoList()) {
                detalleingresoListDetalleingresoToAttach = em.getReference(detalleingresoListDetalleingresoToAttach.getClass(), detalleingresoListDetalleingresoToAttach.getIdDetalleIngreso());
                attachedDetalleingresoList.add(detalleingresoListDetalleingresoToAttach);
            }
            articulo.setDetalleingresoList(attachedDetalleingresoList);
            em.persist(articulo);
            if (idCategoria != null) {
                idCategoria.getArticuloList().add(articulo);
                idCategoria = em.merge(idCategoria);
            }
            if (idPresentacion != null) {
                idPresentacion.getArticuloList().add(articulo);
                idPresentacion = em.merge(idPresentacion);
            }
            for (Detalleingreso detalleingresoListDetalleingreso : articulo.getDetalleingresoList()) {
                Articulo oldIdArticuloOfDetalleingresoListDetalleingreso = detalleingresoListDetalleingreso.getIdArticulo();
                detalleingresoListDetalleingreso.setIdArticulo(articulo);
                detalleingresoListDetalleingreso = em.merge(detalleingresoListDetalleingreso);
                if (oldIdArticuloOfDetalleingresoListDetalleingreso != null) {
                    oldIdArticuloOfDetalleingresoListDetalleingreso.getDetalleingresoList().remove(detalleingresoListDetalleingreso);
                    oldIdArticuloOfDetalleingresoListDetalleingreso = em.merge(oldIdArticuloOfDetalleingresoListDetalleingreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulo articulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulo persistentArticulo = em.find(Articulo.class, articulo.getIdArticulo());
            Categoria idCategoriaOld = persistentArticulo.getIdCategoria();
            Categoria idCategoriaNew = articulo.getIdCategoria();
            Presentacion idPresentacionOld = persistentArticulo.getIdPresentacion();
            Presentacion idPresentacionNew = articulo.getIdPresentacion();
            List<Detalleingreso> detalleingresoListOld = persistentArticulo.getDetalleingresoList();
            List<Detalleingreso> detalleingresoListNew = articulo.getDetalleingresoList();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                articulo.setIdCategoria(idCategoriaNew);
            }
            if (idPresentacionNew != null) {
                idPresentacionNew = em.getReference(idPresentacionNew.getClass(), idPresentacionNew.getIdPresentacion());
                articulo.setIdPresentacion(idPresentacionNew);
            }
            List<Detalleingreso> attachedDetalleingresoListNew = new ArrayList<Detalleingreso>();
            for (Detalleingreso detalleingresoListNewDetalleingresoToAttach : detalleingresoListNew) {
                detalleingresoListNewDetalleingresoToAttach = em.getReference(detalleingresoListNewDetalleingresoToAttach.getClass(), detalleingresoListNewDetalleingresoToAttach.getIdDetalleIngreso());
                attachedDetalleingresoListNew.add(detalleingresoListNewDetalleingresoToAttach);
            }
            detalleingresoListNew = attachedDetalleingresoListNew;
            articulo.setDetalleingresoList(detalleingresoListNew);
            articulo = em.merge(articulo);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getArticuloList().remove(articulo);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getArticuloList().add(articulo);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idPresentacionOld != null && !idPresentacionOld.equals(idPresentacionNew)) {
                idPresentacionOld.getArticuloList().remove(articulo);
                idPresentacionOld = em.merge(idPresentacionOld);
            }
            if (idPresentacionNew != null && !idPresentacionNew.equals(idPresentacionOld)) {
                idPresentacionNew.getArticuloList().add(articulo);
                idPresentacionNew = em.merge(idPresentacionNew);
            }
            for (Detalleingreso detalleingresoListOldDetalleingreso : detalleingresoListOld) {
                if (!detalleingresoListNew.contains(detalleingresoListOldDetalleingreso)) {
                    detalleingresoListOldDetalleingreso.setIdArticulo(null);
                    detalleingresoListOldDetalleingreso = em.merge(detalleingresoListOldDetalleingreso);
                }
            }
            for (Detalleingreso detalleingresoListNewDetalleingreso : detalleingresoListNew) {
                if (!detalleingresoListOld.contains(detalleingresoListNewDetalleingreso)) {
                    Articulo oldIdArticuloOfDetalleingresoListNewDetalleingreso = detalleingresoListNewDetalleingreso.getIdArticulo();
                    detalleingresoListNewDetalleingreso.setIdArticulo(articulo);
                    detalleingresoListNewDetalleingreso = em.merge(detalleingresoListNewDetalleingreso);
                    if (oldIdArticuloOfDetalleingresoListNewDetalleingreso != null && !oldIdArticuloOfDetalleingresoListNewDetalleingreso.equals(articulo)) {
                        oldIdArticuloOfDetalleingresoListNewDetalleingreso.getDetalleingresoList().remove(detalleingresoListNewDetalleingreso);
                        oldIdArticuloOfDetalleingresoListNewDetalleingreso = em.merge(oldIdArticuloOfDetalleingresoListNewDetalleingreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articulo.getIdArticulo();
                if (findArticulo(id) == null) {
                    throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.");
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
            Articulo articulo;
            try {
                articulo = em.getReference(Articulo.class, id);
                articulo.getIdArticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategoria = articulo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getArticuloList().remove(articulo);
                idCategoria = em.merge(idCategoria);
            }
            Presentacion idPresentacion = articulo.getIdPresentacion();
            if (idPresentacion != null) {
                idPresentacion.getArticuloList().remove(articulo);
                idPresentacion = em.merge(idPresentacion);
            }
            List<Detalleingreso> detalleingresoList = articulo.getDetalleingresoList();
            for (Detalleingreso detalleingresoListDetalleingreso : detalleingresoList) {
                detalleingresoListDetalleingreso.setIdArticulo(null);
                detalleingresoListDetalleingreso = em.merge(detalleingresoListDetalleingreso);
            }
            em.remove(articulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulo> findArticuloEntities() {
        return findArticuloEntities(true, -1, -1);
    }

    public List<Articulo> findArticuloEntities(int maxResults, int firstResult) {
        return findArticuloEntities(false, maxResults, firstResult);
    }

    private List<Articulo> findArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulo.class));
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

    public Articulo findArticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulo> rt = cq.from(Articulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
