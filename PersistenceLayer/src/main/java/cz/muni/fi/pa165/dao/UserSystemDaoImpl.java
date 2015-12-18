package cz.muni.fi.pa165.dao;

import java.util.List;
import cz.muni.fi.pa165.entity.UserSystem;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements basic operations on UserSystem entity.
 *
 * @author Jakub Miculka
 */
@Repository
@Transactional
public class UserSystemDaoImpl implements UserSystemDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserSystem getById(Long id) {
        if (id == null) {
            throw new NullPointerException("Argument id is null");
        }
        return em.find(UserSystem.class, id);
    }

    @Override
    public UserSystem getByUserName(String name) {
        if (name == null) {
            throw new NullPointerException("Argument name is null");
        }
        TypedQuery<UserSystem> query = em.createQuery("SELECT userSystem FROM UserSystem as userSystem WHERE userSystem.userName=:n",
                UserSystem.class).setParameter("n", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<UserSystem> findAll() {
        TypedQuery<UserSystem> query = em.createQuery("SELECT userSys FROM UserSystem AS userSys", UserSystem.class);
        return query.getResultList();
    }

    @Override
    public void create(UserSystem user) {
        if (user == null) {
            throw new NullPointerException("Argument user is null");
        }
        em.persist(user);
    }

    @Override
    public void update(UserSystem user) {
        if (user == null) {
            throw new NullPointerException("Argument user is null");
        }
        em.merge(user);
    }

    @Override
    public void delete(UserSystem user) {
        if (user == null) {
            throw new NullPointerException("Argument user is null");
        }
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}
