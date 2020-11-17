/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Loguusuario;
import java.util.ArrayList;
import java.util.List;
import com.entities.Rolusuario;
import com.entities.Empleado;
import com.entities.Usuario;
import com.views.FrmPrincipal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *  Nombre de la clase: UsuarioJpaController
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getLoguusuarioList() == null) {
            usuario.setLoguusuarioList(new ArrayList<Loguusuario>());
        }
        if (usuario.getRolusuarioList() == null) {
            usuario.setRolusuarioList(new ArrayList<Rolusuario>());
        }
        if (usuario.getEmpleadoList() == null) {
            usuario.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Loguusuario> attachedLoguusuarioList = new ArrayList<Loguusuario>();
            for (Loguusuario loguusuarioListLoguusuarioToAttach : usuario.getLoguusuarioList()) {
                loguusuarioListLoguusuarioToAttach = em.getReference(loguusuarioListLoguusuarioToAttach.getClass(), loguusuarioListLoguusuarioToAttach.getCodigoLog());
                attachedLoguusuarioList.add(loguusuarioListLoguusuarioToAttach);
            }
            usuario.setLoguusuarioList(attachedLoguusuarioList);
            List<Rolusuario> attachedRolusuarioList = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioListRolusuarioToAttach : usuario.getRolusuarioList()) {
                rolusuarioListRolusuarioToAttach = em.getReference(rolusuarioListRolusuarioToAttach.getClass(), rolusuarioListRolusuarioToAttach.getCodigoRolUsuario());
                attachedRolusuarioList.add(rolusuarioListRolusuarioToAttach);
            }
            usuario.setRolusuarioList(attachedRolusuarioList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : usuario.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getCodigoEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            usuario.setEmpleadoList(attachedEmpleadoList);
            em.persist(usuario);
            for (Loguusuario loguusuarioListLoguusuario : usuario.getLoguusuarioList()) {
                Usuario oldCodigoUsuarioOfLoguusuarioListLoguusuario = loguusuarioListLoguusuario.getCodigoUsuario();
                loguusuarioListLoguusuario.setCodigoUsuario(usuario);
                loguusuarioListLoguusuario = em.merge(loguusuarioListLoguusuario);
                if (oldCodigoUsuarioOfLoguusuarioListLoguusuario != null) {
                    oldCodigoUsuarioOfLoguusuarioListLoguusuario.getLoguusuarioList().remove(loguusuarioListLoguusuario);
                    oldCodigoUsuarioOfLoguusuarioListLoguusuario = em.merge(oldCodigoUsuarioOfLoguusuarioListLoguusuario);
                }
            }
            for (Rolusuario rolusuarioListRolusuario : usuario.getRolusuarioList()) {
                Usuario oldCodigoUsuarioOfRolusuarioListRolusuario = rolusuarioListRolusuario.getCodigoUsuario();
                rolusuarioListRolusuario.setCodigoUsuario(usuario);
                rolusuarioListRolusuario = em.merge(rolusuarioListRolusuario);
                if (oldCodigoUsuarioOfRolusuarioListRolusuario != null) {
                    oldCodigoUsuarioOfRolusuarioListRolusuario.getRolusuarioList().remove(rolusuarioListRolusuario);
                    oldCodigoUsuarioOfRolusuarioListRolusuario = em.merge(oldCodigoUsuarioOfRolusuarioListRolusuario);
                }
            }
            for (Empleado empleadoListEmpleado : usuario.getEmpleadoList()) {
                Usuario oldCodigoUsuarioOfEmpleadoListEmpleado = empleadoListEmpleado.getCodigoUsuario();
                empleadoListEmpleado.setCodigoUsuario(usuario);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldCodigoUsuarioOfEmpleadoListEmpleado != null) {
                    oldCodigoUsuarioOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldCodigoUsuarioOfEmpleadoListEmpleado = em.merge(oldCodigoUsuarioOfEmpleadoListEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCodigoUsuario());
            List<Loguusuario> loguusuarioListOld = persistentUsuario.getLoguusuarioList();
            List<Loguusuario> loguusuarioListNew = usuario.getLoguusuarioList();
            List<Rolusuario> rolusuarioListOld = persistentUsuario.getRolusuarioList();
            List<Rolusuario> rolusuarioListNew = usuario.getRolusuarioList();
            List<Empleado> empleadoListOld = persistentUsuario.getEmpleadoList();
            List<Empleado> empleadoListNew = usuario.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Rolusuario rolusuarioListOldRolusuario : rolusuarioListOld) {
                if (!rolusuarioListNew.contains(rolusuarioListOldRolusuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolusuario " + rolusuarioListOldRolusuario + " since its codigoUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Loguusuario> attachedLoguusuarioListNew = new ArrayList<Loguusuario>();
            for (Loguusuario loguusuarioListNewLoguusuarioToAttach : loguusuarioListNew) {
                loguusuarioListNewLoguusuarioToAttach = em.getReference(loguusuarioListNewLoguusuarioToAttach.getClass(), loguusuarioListNewLoguusuarioToAttach.getCodigoLog());
                attachedLoguusuarioListNew.add(loguusuarioListNewLoguusuarioToAttach);
            }
            loguusuarioListNew = attachedLoguusuarioListNew;
            usuario.setLoguusuarioList(loguusuarioListNew);
            List<Rolusuario> attachedRolusuarioListNew = new ArrayList<Rolusuario>();
            for (Rolusuario rolusuarioListNewRolusuarioToAttach : rolusuarioListNew) {
                rolusuarioListNewRolusuarioToAttach = em.getReference(rolusuarioListNewRolusuarioToAttach.getClass(), rolusuarioListNewRolusuarioToAttach.getCodigoRolUsuario());
                attachedRolusuarioListNew.add(rolusuarioListNewRolusuarioToAttach);
            }
            rolusuarioListNew = attachedRolusuarioListNew;
            usuario.setRolusuarioList(rolusuarioListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getCodigoEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            usuario.setEmpleadoList(empleadoListNew);
            usuario = em.merge(usuario);
            for (Loguusuario loguusuarioListOldLoguusuario : loguusuarioListOld) {
                if (!loguusuarioListNew.contains(loguusuarioListOldLoguusuario)) {
                    loguusuarioListOldLoguusuario.setCodigoUsuario(null);
                    loguusuarioListOldLoguusuario = em.merge(loguusuarioListOldLoguusuario);
                }
            }
            for (Loguusuario loguusuarioListNewLoguusuario : loguusuarioListNew) {
                if (!loguusuarioListOld.contains(loguusuarioListNewLoguusuario)) {
                    Usuario oldCodigoUsuarioOfLoguusuarioListNewLoguusuario = loguusuarioListNewLoguusuario.getCodigoUsuario();
                    loguusuarioListNewLoguusuario.setCodigoUsuario(usuario);
                    loguusuarioListNewLoguusuario = em.merge(loguusuarioListNewLoguusuario);
                    if (oldCodigoUsuarioOfLoguusuarioListNewLoguusuario != null && !oldCodigoUsuarioOfLoguusuarioListNewLoguusuario.equals(usuario)) {
                        oldCodigoUsuarioOfLoguusuarioListNewLoguusuario.getLoguusuarioList().remove(loguusuarioListNewLoguusuario);
                        oldCodigoUsuarioOfLoguusuarioListNewLoguusuario = em.merge(oldCodigoUsuarioOfLoguusuarioListNewLoguusuario);
                    }
                }
            }
            for (Rolusuario rolusuarioListNewRolusuario : rolusuarioListNew) {
                if (!rolusuarioListOld.contains(rolusuarioListNewRolusuario)) {
                    Usuario oldCodigoUsuarioOfRolusuarioListNewRolusuario = rolusuarioListNewRolusuario.getCodigoUsuario();
                    rolusuarioListNewRolusuario.setCodigoUsuario(usuario);
                    rolusuarioListNewRolusuario = em.merge(rolusuarioListNewRolusuario);
                    if (oldCodigoUsuarioOfRolusuarioListNewRolusuario != null && !oldCodigoUsuarioOfRolusuarioListNewRolusuario.equals(usuario)) {
                        oldCodigoUsuarioOfRolusuarioListNewRolusuario.getRolusuarioList().remove(rolusuarioListNewRolusuario);
                        oldCodigoUsuarioOfRolusuarioListNewRolusuario = em.merge(oldCodigoUsuarioOfRolusuarioListNewRolusuario);
                    }
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.setCodigoUsuario(null);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Usuario oldCodigoUsuarioOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getCodigoUsuario();
                    empleadoListNewEmpleado.setCodigoUsuario(usuario);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldCodigoUsuarioOfEmpleadoListNewEmpleado != null && !oldCodigoUsuarioOfEmpleadoListNewEmpleado.equals(usuario)) {
                        oldCodigoUsuarioOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldCodigoUsuarioOfEmpleadoListNewEmpleado = em.merge(oldCodigoUsuarioOfEmpleadoListNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getCodigoUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCodigoUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rolusuario> rolusuarioListOrphanCheck = usuario.getRolusuarioList();
            for (Rolusuario rolusuarioListOrphanCheckRolusuario : rolusuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Rolusuario " + rolusuarioListOrphanCheckRolusuario + " in its rolusuarioList field has a non-nullable codigoUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Loguusuario> loguusuarioList = usuario.getLoguusuarioList();
            for (Loguusuario loguusuarioListLoguusuario : loguusuarioList) {
                loguusuarioListLoguusuario.setCodigoUsuario(null);
                loguusuarioListLoguusuario = em.merge(loguusuarioListLoguusuario);
            }
            List<Empleado> empleadoList = usuario.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.setCodigoUsuario(null);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public Object getCodigoUsuario(String usuario, String pass)
    {
        try
        {
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
            EntityManager entitymanager = emfactory.createEntityManager();

            Query query = entitymanager.createNamedQuery("Usuario.findUser");
            query.setParameter("nombreUsuario",usuario);
            query.setParameter("contraUsuario", pass);
            Object user=query.getSingleResult();
            return user;
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
    
    
    public List getPermisosUsuario(int codigoUsuario)
    {
        try
        {
            List listaPermisos = new ArrayList();
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
            EntityManager entitymanager = emfactory.createEntityManager();

            Query query = entitymanager.createNamedQuery("Usuario.findPermisos");
            query.setParameter("codigoUsuario",codigoUsuario);
            listaPermisos=query.getResultList();
            return listaPermisos;
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
    
    public boolean acceder(String user, String pass)
    {
        Object us;
        int codigoUser;
        List listaPermisos= new ArrayList();
        us=getCodigoUsuario(user,pass);
        
        if(us!=null)
        {
            codigoUser=Integer.parseInt(us.toString());
            
            listaPermisos=getPermisosUsuario(codigoUser);

            FrmPrincipal ingreso = new FrmPrincipal(listaPermisos);
            ingreso.setVisible(true);
            ingreso.pack();
            return true;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Usuario o Contraseña Incorrectas");
            return false;
        }
    }
    
    
    public Object getExistUser(String usuario, String pass)
    {
        try
        {
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
            EntityManager entitymanager = emfactory.createEntityManager();

            Query query = entitymanager.createNamedQuery("Usuario.existUser");
            query.setParameter("nombreUsuario",usuario);
            query.setParameter("contraUsuario", pass);
            Object user=query.getSingleResult();
            return user;
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
    
    
    public Object getDependUser(int rolusuario)
    {
        try
        {
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
            EntityManager entitymanager = emfactory.createEntityManager();

            Query query = entitymanager.createNamedQuery("Usuario.dependUser");
            query.setParameter("codigoUsuario",rolusuario);
            Object user=query.getSingleResult();
            return user;
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
}
