/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public Area findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Input Id is null");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id is less then 0");
        }
        return em.find(Area.class, id);
    }

    @Override
    public Area findByName(String name) {
        if (name == null) {
            throw new NullPointerException("Input Name is null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("Name has no length");
        }
        if (name.length() < 50) {
            throw new IllegalArgumentException("Name is too long");
        }
        return em.find(Area.class, name);
    }

    @Override
    public List<String> retrieveAreasNames() {
        try {
            TypedQuery<Area> qr = em.createQuery("select ar.name from Area ar", Area.class);
            List<String> names = new ArrayList<String>();
            for (Area ar : qr.getResultList()) {
                names.add(ar.getName());
            }
            return names;
        } catch (Exception ex) {
            throw new NoResultException("Error while getting names. Error: " + ex.getMessage());
        }
    }

    @Override
    public List<Area> retrieveAreasWithNoCreature() {
        try {
            List<Area> results = new ArrayList<Area>();
            for (Area ar : findAll()) {
                if (ar.getCreatures().isEmpty()) {
                    results.add(ar);
                }
            }
            return results;
        } catch (Exception ex) {
            throw new NoResultException("Error while getting areas. Error: " + ex.getMessage());
        }
    }

    @Override
    public List<Area> retrieveAreasWithAnyCreature() {
        try {
            List<Area> results = new ArrayList<Area>();
            for (Area ar : findAll()) {
                if (!ar.getCreatures().isEmpty()) {
                    results.add(ar);
                }
            }
            return results;
        } catch (Exception ex) {
            throw new NoResultException("Error while getting areas. Error: " + ex.getMessage());
        }
    }

    @Override
    public List<Area> retrieveAreasMostCreatures() {
        try {
            List<Area> results = new ArrayList<Area>();
            for (Area ar : findAll()) {
                if (results.isEmpty()) {
                    results.add(ar);
                }
                if (retrieveCreaturesAmount(results.get(0)) < retrieveCreaturesAmount(ar)) {
                    results.clear();
                    results.add(ar);
                } else if (retrieveCreaturesAmount(results.get(0)) == retrieveCreaturesAmount(ar)) {
                    results.add(ar);
                }
            }
            return results;
        } catch (Exception ex) {
            throw new NoResultException("Error while getting area. Error: " + ex.getMessage());
        }
    }

    @Override
    public List<Area> retrieveAreasFewestCreatures() {
        try {
            List<Area> results = new ArrayList<Area>();
            for (Area ar : findAll()) {
                if (results.isEmpty()) {
                    results.add(ar);
                }
                if (retrieveCreaturesAmount(results.get(0)) > retrieveCreaturesAmount(ar)) {
                    results.clear();
                    results.add(ar);
                } else if (retrieveCreaturesAmount(results.get(0)) == retrieveCreaturesAmount(ar)) {
                    results.add(ar);
                }
            }
            return results;
        } catch (Exception ex) {
            throw new NoResultException("Error while getting area. Error: " + ex.getMessage());
        }
    }

    @Override
    public void addCreature(Area ar, Creature cr) {
        try {
            em.detach(ar);
            Set<Creature> creatures = ar.getCreatures();
            creatures.add(cr);
            ar.setCreatures(creatures);
            em.merge(ar);
        } catch (Exception ex) {
            throw new NoResultException("Error while getting area. Error: " + ex.getMessage());
        }
    }

    @Override
    public boolean removeCreature(Area ar, Creature cr) {
        try {
            em.detach(ar);
            Set<Creature> creatures = ar.getCreatures();
            if (!creatures.contains(cr)) {
                return false;
            }
            creatures.remove(cr);
            ar.setCreatures(creatures);
            em.merge(ar);
            return true;
        } catch (Exception ex) {
            throw new NoResultException("Error while getting area. Error: " + ex.getMessage());
        }
    }

    @Override
    public int retrieveCreaturesAmount(Area ar) {
        if (ar == null) {
            throw new NullPointerException("Input Are is null");
        }
        if (ar.getName() == null || ar.getId() == null) {
            throw new IllegalArgumentException("Area does not exist");
        }

        try {
            return findById(ar.getId()).getCreatures().size();
        } catch (Exception ex) {
            throw new NoResultException("Error while getting area. Error: " + ex.getMessage());
        }
    }

    @Override
    public boolean containAreaCreature(Area ar, Creature cr) {
        List<Area> areas = findAll();
        if (!areas.contains(ar)) {
            System.err.println("Area does not exists in database");
            return false;
        }
        return ar.getCreatures().contains(cr);
    }
}
