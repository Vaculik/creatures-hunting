package cz.muni.fi.pa165.dao;

import java.util.List;
import cz.muni.fi.pa165.entity.UserSystem;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository
public class UserSystemDaoImpl implements UserSystemDao{

    @PersistenceContext
    private EntityManager em;
    
	@Override
	public UserSystem getById(Long id) {
		return em.find(UserSystem.class, id);
	}

	@Override
	public UserSystem getByName(String name) {
        TypedQuery<UserSystem> query = em.createQuery("SELECT user FROM User as user WHERE user.name=:n",
                UserSystem.class).setParameter("n", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}

	@Override
	public List<UserSystem> findAll() {
		TypedQuery<UserSystem> query = em.createQuery("SELECT user FROM User AS c", UserSystem.class);
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
	public void remove(UserSystem user) {
		em.remove(user);
	}

}
