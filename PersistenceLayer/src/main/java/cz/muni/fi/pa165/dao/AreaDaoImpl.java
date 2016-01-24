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
import org.springframework.transaction.annotation.Transactional;

/**
 * This class represents an implementation of Area Data Access Object interface.
 * It contains several basic methods.
 *
 * @author Martin Zboril
 */
@Repository
@Transactional
public class AreaDaoImpl implements AreaDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Area area) {
        em.persist(area);
    }

    @Override
    public void delete(Area area) {
        em.remove(em.contains(area) ? area : em.merge(area));
    }

    @Override
    public void update(Area area) {
        em.merge(area);
    }

    @Override
    public List<Area> findAll() {
            TypedQuery<Area> qr = em.createQuery("SELECT ar FROM Area AS ar", Area.class);
            return qr.getResultList();
    }

    @Override
    public Area getById(Long id) {
        return em.find(Area.class, id);
    }

    @Override
    public Area getByName(String name) {
            TypedQuery<Area> query = em.createQuery("SELECT area FROM Area as area WHERE area.name = :parName", Area.class)
                    .setParameter("parName", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}