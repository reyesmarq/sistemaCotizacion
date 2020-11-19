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
import com.entities.Empleado;
import com.entities.Proveedor;
import com.entities.Detalleingreso;
import com.entities.Ingreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: IngresoJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class IngresoJpaController implements Serializable {

    public IngresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public IngresoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingreso ingreso) {
        if (ingreso.getDetalleingresoList() == null) {
            ingreso.setDetalleingresoList(new ArrayList<Detalleingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado idEmpleado = ingreso.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                ingreso.setIdEmpleado(idEmpleado);
            }
            Proveedor idProveedor = ingreso.getIdProveedor();
            if (idProveedor != null) {
                idProveedor = em.getReference(idProveedor.getClass(), idProveedor.getIdProveedor());
                ingreso.setIdProveedor(idProveedor);
            }
            List<Detalleingreso> attachedDetalleingresoList = new ArrayList<Detalleingreso>();
            for (Detalleingreso detalleingresoListDetalleingresoToAttach : ingreso.getDetalleingresoList()) {
                detalleingresoListDetalleingresoToAttach = em.getReference(detalleingresoListDetalleingresoToAttach.getClass(), detalleingresoListDetalleingresoToAttach.getIdDetalleIngreso());
                attachedDetalleingresoList.add(detalleingresoListDetalleingresoToAttach);
            }
            ingreso.setDetalleingresoList(attachedDetalleingresoList);
            em.persist(ingreso);
            if (idEmpleado != null) {
                idEmpleado.getIngresoList().add(ingreso);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idProveedor != null) {
                idProveedor.getIngresoList().add(ingreso);
                idProveedor = em.merge(idProveedor);
            }
            for (Detalleingreso detalleingresoListDetalleingreso : ingreso.getDetalleingresoList()) {
                Ingreso oldIdIngresoOfDetalleingresoListDetalleingreso = detalleingresoListDetalleingreso.getIdIngreso();
                detalleingresoListDetalleingreso.setIdIngreso(ingreso);
                detalleingresoListDetalleingreso = em.merge(detalleingresoListDetalleingreso);
                if (oldIdIngresoOfDetalleingresoListDetalleingreso != null) {
                    oldIdIngresoOfDetalleingresoListDetalleingreso.getDetalleingresoList().remove(detalleingresoListDetalleingreso);
                    oldIdIngresoOfDetalleingresoListDetalleingreso = em.merge(oldIdIngresoOfDetalleingresoListDetalleingreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingreso ingreso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingreso persistentIngreso = em.find(Ingreso.class, ingreso.getIdIngreso());
            Empleado idEmpleadoOld = persistentIngreso.getIdEmpleado();
            Empleado idEmpleadoNew = ingreso.getIdEmpleado();
            Proveedor idProveedorOld = persistentIngreso.getIdProveedor();
            Proveedor idProveedorNew = ingreso.getIdProveedor();
            List<Detalleingreso> detalleingresoListOld = persistentIngreso.getDetalleingresoList();
            List<Detalleingreso> detalleingresoListNew = ingreso.getDetalleingresoList();
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                ingreso.setIdEmpleado(idEmpleadoNew);
            }
            if (idProveedorNew != null) {
                idProveedorNew = em.getReference(idProveedorNew.getClass(), idProveedorNew.getIdProveedor());
                ingreso.setIdProveedor(idProveedorNew);
            }
            List<Detalleingreso> attachedDetalleingresoListNew = new ArrayList<Detalleingreso>();
            for (Detalleingreso detalleingresoListNewDetalleingresoToAttach : detalleingresoListNew) {
                detalleingresoListNewDetalleingresoToAttach = em.getReference(detalleingresoListNewDetalleingresoToAttach.getClass(), detalleingresoListNewDetalleingresoToAttach.getIdDetalleIngreso());
                attachedDetalleingresoListNew.add(detalleingresoListNewDetalleingresoToAttach);
            }
            detalleingresoListNew = attachedDetalleingresoListNew;
            ingreso.setDetalleingresoList(detalleingresoListNew);
            ingreso = em.merge(ingreso);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getIngresoList().remove(ingreso);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getIngresoList().add(ingreso);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idProveedorOld != null && !idProveedorOld.equals(idProveedorNew)) {
                idProveedorOld.getIngresoList().remove(ingreso);
                idProveedorOld = em.merge(idProveedorOld);
            }
            if (idProveedorNew != null && !idProveedorNew.equals(idProveedorOld)) {
                idProveedorNew.getIngresoList().add(ingreso);
                idProveedorNew = em.merge(idProveedorNew);
            }
            for (Detalleingreso detalleingresoListOldDetalleingreso : detalleingresoListOld) {
                if (!detalleingresoListNew.contains(detalleingresoListOldDetalleingreso)) {
                    detalleingresoListOldDetalleingreso.setIdIngreso(null);
                    detalleingresoListOldDetalleingreso = em.merge(detalleingresoListOldDetalleingreso);
                }
            }
            for (Detalleingreso detalleingresoListNewDetalleingreso : detalleingresoListNew) {
                if (!detalleingresoListOld.contains(detalleingresoListNewDetalleingreso)) {
                    Ingreso oldIdIngresoOfDetalleingresoListNewDetalleingreso = detalleingresoListNewDetalleingreso.getIdIngreso();
                    detalleingresoListNewDetalleingreso.setIdIngreso(ingreso);
                    detalleingresoListNewDetalleingreso = em.merge(detalleingresoListNewDetalleingreso);
                    if (oldIdIngresoOfDetalleingresoListNewDetalleingreso != null && !oldIdIngresoOfDetalleingresoListNewDetalleingreso.equals(ingreso)) {
                        oldIdIngresoOfDetalleingresoListNewDetalleingreso.getDetalleingresoList().remove(detalleingresoListNewDetalleingreso);
                        oldIdIngresoOfDetalleingresoListNewDetalleingreso = em.merge(oldIdIngresoOfDetalleingresoListNewDetalleingreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingreso.getIdIngreso();
                if (findIngreso(id) == null) {
                    throw new NonexistentEntityException("The ingreso with id " + id + " no longer exists.");
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
            Ingreso ingreso;
            try {
                ingreso = em.getReference(Ingreso.class, id);
                ingreso.getIdIngreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingreso with id " + id + " no longer exists.", enfe);
            }
            Empleado idEmpleado = ingreso.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getIngresoList().remove(ingreso);
                idEmpleado = em.merge(idEmpleado);
            }
            Proveedor idProveedor = ingreso.getIdProveedor();
            if (idProveedor != null) {
                idProveedor.getIngresoList().remove(ingreso);
                idProveedor = em.merge(idProveedor);
            }
            List<Detalleingreso> detalleingresoList = ingreso.getDetalleingresoList();
            for (Detalleingreso detalleingresoListDetalleingreso : detalleingresoList) {
                detalleingresoListDetalleingreso.setIdIngreso(null);
                detalleingresoListDetalleingreso = em.merge(detalleingresoListDetalleingreso);
            }
            em.remove(ingreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingreso> findIngresoEntities() {
        return findIngresoEntities(true, -1, -1);
    }

    public List<Ingreso> findIngresoEntities(int maxResults, int firstResult) {
        return findIngresoEntities(false, maxResults, firstResult);
    }

    private List<Ingreso> findIngresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingreso.class));
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

    public Ingreso findIngreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingreso> rt = cq.from(Ingreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
