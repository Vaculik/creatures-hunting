package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.WeaponEfficiency;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The class implements basic operations on WeaponEfficiency entity.
 *
 * @author Karel Vaculik
 */
@Repository
@Transactional
public class WeaponEfficiencyDaoImpl implements WeaponEfficiencyDao {

    @PersistenceContext
    private EntityManager em;

    public WeaponEfficiency getById(Long id) {
        return em.find(WeaponEfficiency.class, id);
    }

    public void create(WeaponEfficiency weaponEfficiency) {
        em.persist(weaponEfficiency);
    }

    public void delete(WeaponEfficiency weaponEfficiency) {
        em.remove(em.contains(weaponEfficiency) ? weaponEfficiency : em.merge(weaponEfficiency));
    }

    public void update(WeaponEfficiency weaponEfficiency) {
        em.merge(weaponEfficiency);
    }

    public List<WeaponEfficiency> findAll() {
        TypedQuery<WeaponEfficiency> query = em.createQuery("SELECT we FROM WeaponEfficiency AS we",
                WeaponEfficiency.class);
        return query.getResultList();
    }
}
