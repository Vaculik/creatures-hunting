package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Weapon;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
@Repository
public class WeaponDaoImpl implements WeaponDao {
    @PersistenceContext
    private EntityManager em;
    
    public Weapon getById(Long id) {
        if (id == null)
            throw new NullPointerException("Argument id is null");
        return em.find(Weapon.class, id);
    }

    public Weapon getByName(String name) {
        if (name == null)
            throw new NullPointerException("Argument name is null");
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
        if (weapon == null)
            throw new NullPointerException("Argument weapon is null");
        em.persist(weapon);
    }

    public void update(Weapon weapon) {
        if (weapon == null)
            throw new NullPointerException("Argument weapon is null");
        em.merge(weapon);
    }

    public void delete(Weapon weapon) {
        
        if (weapon == null)
            throw new NullPointerException("Argument weapon is null");
        em.remove(weapon);
    }
    
}
