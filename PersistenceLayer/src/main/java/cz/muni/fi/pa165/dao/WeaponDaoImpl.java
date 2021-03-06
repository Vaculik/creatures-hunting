package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Weapon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * This class represents an implementation of Data Access Object of Weapon
 * Entity.
 *
 * @author Pavel Vesely - 448290@mail.muni.cz
 */
@Repository
@Transactional
public class WeaponDaoImpl implements WeaponDao {

    @PersistenceContext
    private EntityManager em;

    public Weapon getById(Long id) {
        return em.find(Weapon.class, id);
    }

    public Weapon getByName(String name) {
        TypedQuery<Weapon> query = em.createQuery("SELECT weapn FROM Weapon AS weapn WHERE weapn.name=:nam", Weapon.class).setParameter("nam", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Weapon> findAll() {
        TypedQuery<Weapon> query = em.createQuery("SELECT weapn FROM Weapon AS weapn", Weapon.class);
        return query.getResultList();
    }

    public void create(Weapon weapon) {
        em.persist(weapon);
    }

    public void update(Weapon weapon) {
        em.merge(weapon);
    }

    public void delete(Weapon weapon) {
        em.remove(em.contains(weapon) ? weapon : em.merge(weapon));
    }
}
