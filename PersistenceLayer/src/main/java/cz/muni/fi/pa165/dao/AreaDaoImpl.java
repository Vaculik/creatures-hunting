/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Area;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martin Zboril
 */
@Repository
public class AreaDaoImpl implements AreaDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Area ar) {
        if (ar == null) {
            throw new NullPointerException("Input Are is null");
        }
        if (ar.getName() == null) {
            throw new IllegalArgumentException("Area has no name");
        }
        try {
            em.persist(ar);
        } catch (Exception ex) {
            throw new NoResultException("Error while creating area");
        }
    }

    @Override
    public void delete(Area ar) {
        if (ar == null) {
            throw new NullPointerException("Input Are is null");
        }
        if (ar.getName() == null || ar.getId() == null) {
            throw new IllegalArgumentException("Area does not exist");
        }
        try {
            em.remove(ar);
        } catch (Exception ex) {
            throw new NoResultException("Error while deleting area.  Error: " + ex.getMessage());
        }
    }

    @Override
    public void update(Area ar) {
        if (ar == null) {
            throw new NullPointerException("Input Are is null");
        }
        if (ar.getName() == null || ar.getId() == null) {
            throw new IllegalArgumentException("Area does not exist");
        }
        try {
            em.merge(ar);
        } catch (Exception ex) {
            throw new NoResultException("Error while updating area. Error: " + ex.getMessage());
        }

    }

    @Override
    public List<Area> findAll() {
        try {
            TypedQuery<Area> qr = em.createQuery("select ar from Area ar", Area.class);
            return qr.getResultList();
        } catch (Exception ex) {
            throw new NoResultException("Error while getting areas. Error: " + ex.getMessage());
        }
    }

    @Override
    public Area getById(Long id) {
        if (id == null) {
            throw new NullPointerException("Input Id is null");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id is less then 0");
        }
        return em.find(Area.class, id);
    }

    @Override
    public Area getByName(String name) {
        if (name == null) {
            throw new NullPointerException("Input Name is null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("Name has no length");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name is too long");
        }
        try {
            return em.createQuery("SELECT ar FROM Area ar WHERE name = :parName", Area.class).setParameter("parName", name).getSingleResult();
        } catch (Exception ex) {
            throw new NoResultException("Error while getting areas. Error: " + ex.getMessage());
        }
    }
}