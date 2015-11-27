/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.exception.DatabaseCreatureException;
import cz.muni.fi.pa165.entity.Area;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * This class represents an implementation of Area Data Access Object interface.
 * It contains several basic methods.
 *
 * @author Martin Zboril
 */
@Repository
public class AreaDaoImpl implements AreaDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Area area) {
        if (area == null) {
            throw new NullPointerException("Input Are is null");
        }
        try {
            em.persist(area);
        } catch (Exception ex) {
            throw new DatabaseCreatureException("Error while creating area");
        }
    }

    @Override
    public void delete(Area area) {
        if (area == null) {
            throw new NullPointerException("Input Are is null");
        }
        try {
            em.remove(area);
        } catch (Exception ex) {
            throw new DatabaseCreatureException("Error while deleting area.  Error: " + ex.getMessage());
        }
    }

    @Override
    public void update(Area area) {
        if (area == null) {
            throw new NullPointerException("Input Are is null");
        }
        try {
            em.merge(area);
        } catch (Exception ex) {
            throw new DatabaseCreatureException("Error while updating area. Error: " + ex.getMessage());
        }

    }

    @Override
    public List<Area> findAll() {
        try {
            TypedQuery<Area> qr = em.createQuery("SELECT ar FROM Area AS ar", Area.class);
            return qr.getResultList();
        } catch (Exception ex) {
            throw new DatabaseCreatureException("Error while getting areas. Error: " + ex.getMessage());
        }
    }

    @Override
    public Area getById(Long id) {
        if (id == null) {
            throw new NullPointerException("Input Id is null");
        }
        return em.find(Area.class, id);
    }

    @Override
    public Area getByName(String name) {
        if (name == null) {
            throw new NullPointerException("Input Name is null");
        }
        try {
            return em.createQuery("SELECT area FROM Area as area WHERE area.name = :parName", Area.class).setParameter("parName", name).getSingleResult();
        } catch (Exception ex) {
            throw new NoResultException("Error while getting areas. Error: " + ex.getMessage());
        }
    }
}