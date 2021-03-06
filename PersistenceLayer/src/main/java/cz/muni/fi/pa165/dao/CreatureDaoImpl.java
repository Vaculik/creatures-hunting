package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Creature;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The class implements basic operations on Creature entity.
 *
 * @author Karel Vaculik
 */
@Repository
@Transactional
public class CreatureDaoImpl implements CreatureDao {

    @PersistenceContext
    private EntityManager em;

    public Creature getById(Long id) {
        return em.find(Creature.class, id);
    }

    public Creature getByName(String name) {
        TypedQuery<Creature> query = em.createQuery("SELECT c FROM Creature AS c WHERE c.name=:n",
                Creature.class).setParameter("n", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void create(Creature creature) {
        em.persist(creature);
    }

    public void delete(Creature creature) {
        em.remove(em.contains(creature) ? creature : em.merge(creature));
    }

    public void update(Creature creature) {
        em.merge(creature);
    }

    public List<Creature> findAll() {
        TypedQuery<Creature> query = em.createQuery("SELECT c FROM Creature AS c", Creature.class);
        return query.getResultList();
    }
}
