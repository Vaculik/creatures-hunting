package cz.muni.fi.pa165.dao;

import java.util.List;
import cz.muni.fi.pa165.entity.User;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;

>>>>>>> 674858bc57090555c6b17463b05edc5d0bff742d
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
=======
>>>>>>> 674858bc57090555c6b17463b05edc5d0bff742d

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
