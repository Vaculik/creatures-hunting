package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.WeaponEfficiency;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The class implements basic operations on WeaponEfficiency entity.
 *
 * Created by vaculik on 23.10.15.
 */
@Repository
public class WeaponEfficiencyDaoImpl implements WeaponEfficiencyDao {

    @PersistenceContext
    private EntityManager em;

    public WeaponEfficiency getById(Long id) {
        if (id == null) {
            throw new NullPointerException("Argument id is null");
        }
        return em.find(WeaponEfficiency.class, id);
    }

    public void create(WeaponEfficiency weaponEfficiency) {
        if (weaponEfficiency == null) {
            throw new NullPointerException("Argument weaponEfficiency is null");
        }
        em.persist(weaponEfficiency);
    }

    public void delete(WeaponEfficiency weaponEfficiency) {
        if (weaponEfficiency == null) {
            throw new NullPointerException("Argument weaponEfficiency is null");
        }
        em.remove(weaponEfficiency);
    }

    public void update(WeaponEfficiency weaponEfficiency) {
        if (weaponEfficiency == null) {
            throw new NullPointerException("Argument weaponEfficiency is null");
        }
        em.merge(weaponEfficiency);
    }

    public List<WeaponEfficiency> findAll() {
        TypedQuery<WeaponEfficiency> query = em.createQuery("SELECT we FROM WeaponEfficiency AS we",
                WeaponEfficiency.class);
        return query.getResultList();
    }
}
