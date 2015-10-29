package cz.muni.fi.pa165;

import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.entity.Weapon;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) throws SQLException {
        // The following line is here just to start up a in-memory database
        new AnnotationConfigApplicationContext(InMemoryDatabaseApplicationContext.class);

        System.out.println("pv_dev branch!");

        emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Weapon weapon = new Weapon();
        weapon.setName("Zweihandler");
        Creature creature = new Creature();
        creature.setName("Rabbit");
        UserSystem user = new UserSystem();
        user.setName("JohnSnow");

        em.persist(weapon);
        em.persist(creature);
        em.persist(user);
        em.getTransaction().commit();
        em.close();

        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Weapon weaponFound = em2.find(Weapon.class, weapon.getId());
        Creature creatureFound = em2.find(Creature.class, creature.getId());
        UserSystem userFound = em2.find(UserSystem.class, user.getId());

        em2.getTransaction().commit();
        em2.close();;
        assert "Zweihandler".equals(weaponFound.getName());
        assert "Rabbit".equals(creatureFound.getName());
        assert "John Snow".equals(userFound.getName());
        System.out.print("Juchu!");
        emf.close();
    }
}
