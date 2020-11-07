/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Usuario;
import com.entities.Venta;
import java.util.ArrayList;
import java.util.List;
import com.entities.Ingreso;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  Nombre de la clase: EmpleadoJpaController
 *  Fecha: 11-04-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EmpleadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getVentaList() == null) {
            empleado.setVentaList(new ArrayList<Venta>());
        }
        if (empleado.getIngresoList() == null) {
            empleado.setIngresoList(new ArrayList<Ingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario codigoUsuario = empleado.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario = em.getReference(codigoUsuario.getClass(), codigoUsuario.getCodigoUsuario());
                empleado.setCodigoUsuario(codigoUsuario);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : empleado.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdVenta());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            empleado.setVentaList(attachedVentaList);
            List<Ingreso> attachedIngresoList = new ArrayList<Ingreso>();
            for (Ingreso ingresoListIngresoToAttach : empleado.getIngresoList()) {
                ingresoListIngresoToAttach = em.getReference(ingresoListIngresoToAttach.getClass(), ingresoListIngresoToAttach.getIdIngreso());
                attachedIngresoList.add(ingresoListIngresoToAttach);
            }
            empleado.setIngresoList(attachedIngresoList);
            em.persist(empleado);
            if (codigoUsuario != null) {
                codigoUsuario.getEmpleadoList().add(empleado);
                codigoUsuario = em.merge(codigoUsuario);
            }
            for (Venta ventaListVenta : empleado.getVentaList()) {
                Empleado oldCodigoEmpleadoOfVentaListVenta = ventaListVenta.getCodigoEmpleado();
                ventaListVenta.setCodigoEmpleado(empleado);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldCodigoEmpleadoOfVentaListVenta != null) {
                    oldCodigoEmpleadoOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldCodigoEmpleadoOfVentaListVenta = em.merge(oldCodigoEmpleadoOfVentaListVenta);
                }
            }
            for (Ingreso ingresoListIngreso : empleado.getIngresoList()) {
                Empleado oldCodigoEmpleadoOfIngresoListIngreso = ingresoListIngreso.getCodigoEmpleado();
                ingresoListIngreso.setCodigoEmpleado(empleado);
                ingresoListIngreso = em.merge(ingresoListIngreso);
                if (oldCodigoEmpleadoOfIngresoListIngreso != null) {
                    oldCodigoEmpleadoOfIngresoListIngreso.getIngresoList().remove(ingresoListIngreso);
                    oldCodigoEmpleadoOfIngresoListIngreso = em.merge(oldCodigoEmpleadoOfIngresoListIngreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getCodigoEmpleado());
            Usuario codigoUsuarioOld = persistentEmpleado.getCodigoUsuario();
            Usuario codigoUsuarioNew = empleado.getCodigoUsuario();
            List<Venta> ventaListOld = persistentEmpleado.getVentaList();
            List<Venta> ventaListNew = empleado.getVentaList();
            List<Ingreso> ingresoListOld = persistentEmpleado.getIngresoList();
            List<Ingreso> ingresoListNew = empleado.getIngresoList();
            if (codigoUsuarioNew != null) {
                codigoUsuarioNew = em.getReference(codigoUsuarioNew.getClass(), codigoUsuarioNew.getCodigoUsuario());
                empleado.setCodigoUsuario(codigoUsuarioNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdVenta());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            empleado.setVentaList(ventaListNew);
            List<Ingreso> attachedIngresoListNew = new ArrayList<Ingreso>();
            for (Ingreso ingresoListNewIngresoToAttach : ingresoListNew) {
                ingresoListNewIngresoToAttach = em.getReference(ingresoListNewIngresoToAttach.getClass(), ingresoListNewIngresoToAttach.getIdIngreso());
                attachedIngresoListNew.add(ingresoListNewIngresoToAttach);
            }
            ingresoListNew = attachedIngresoListNew;
            empleado.setIngresoList(ingresoListNew);
            empleado = em.merge(empleado);
            if (codigoUsuarioOld != null && !codigoUsuarioOld.equals(codigoUsuarioNew)) {
                codigoUsuarioOld.getEmpleadoList().remove(empleado);
                codigoUsuarioOld = em.merge(codigoUsuarioOld);
            }
            if (codigoUsuarioNew != null && !codigoUsuarioNew.equals(codigoUsuarioOld)) {
                codigoUsuarioNew.getEmpleadoList().add(empleado);
                codigoUsuarioNew = em.merge(codigoUsuarioNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setCodigoEmpleado(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Empleado oldCodigoEmpleadoOfVentaListNewVenta = ventaListNewVenta.getCodigoEmpleado();
                    ventaListNewVenta.setCodigoEmpleado(empleado);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldCodigoEmpleadoOfVentaListNewVenta != null && !oldCodigoEmpleadoOfVentaListNewVenta.equals(empleado)) {
                        oldCodigoEmpleadoOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldCodigoEmpleadoOfVentaListNewVenta = em.merge(oldCodigoEmpleadoOfVentaListNewVenta);
                    }
                }
            }
            for (Ingreso ingresoListOldIngreso : ingresoListOld) {
                if (!ingresoListNew.contains(ingresoListOldIngreso)) {
                    ingresoListOldIngreso.setCodigoEmpleado(null);
                    ingresoListOldIngreso = em.merge(ingresoListOldIngreso);
                }
            }
            for (Ingreso ingresoListNewIngreso : ingresoListNew) {
                if (!ingresoListOld.contains(ingresoListNewIngreso)) {
                    Empleado oldCodigoEmpleadoOfIngresoListNewIngreso = ingresoListNewIngreso.getCodigoEmpleado();
                    ingresoListNewIngreso.setCodigoEmpleado(empleado);
                    ingresoListNewIngreso = em.merge(ingresoListNewIngreso);
                    if (oldCodigoEmpleadoOfIngresoListNewIngreso != null && !oldCodigoEmpleadoOfIngresoListNewIngreso.equals(empleado)) {
                        oldCodigoEmpleadoOfIngresoListNewIngreso.getIngresoList().remove(ingresoListNewIngreso);
                        oldCodigoEmpleadoOfIngresoListNewIngreso = em.merge(oldCodigoEmpleadoOfIngresoListNewIngreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getCodigoEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getCodigoEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Usuario codigoUsuario = empleado.getCodigoUsuario();
            if (codigoUsuario != null) {
                codigoUsuario.getEmpleadoList().remove(empleado);
                codigoUsuario = em.merge(codigoUsuario);
            }
            List<Venta> ventaList = empleado.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setCodigoEmpleado(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            List<Ingreso> ingresoList = empleado.getIngresoList();
            for (Ingreso ingresoListIngreso : ingresoList) {
                ingresoListIngreso.setCodigoEmpleado(null);
                ingresoListIngreso = em.merge(ingresoListIngreso);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
