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
        return em.find(UserSystem.class, id);
    }

    @Override
    public UserSystem getByUserName(String name) {
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
        em.persist(user);
    }

    @Override
    public void update(UserSystem user) {
        em.merge(user);
    }

    @Override
    public void delete(UserSystem user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}
