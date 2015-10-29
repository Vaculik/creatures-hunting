package cz.muni.fi.pa165.dao;

import java.util.List;
import cz.muni.fi.pa165.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager em;
    
	@Override
	public User getById(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public User getByName(String name) {
        TypedQuery<User> query = em.createQuery("SELECT user FROM User as user WHERE user.name=:n",
                User.class).setParameter("n", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = em.createQuery("SELECT user FROM User AS c", User.class);
	     return query.getResultList();
	}

	@Override
	public void create(User user) {
		em.persist(user);
	}

	@Override
	public void update(User user) {
		em.merge(user);
	}

	@Override
	public void remove(User user) {
		em.remove(user);
	}

}
