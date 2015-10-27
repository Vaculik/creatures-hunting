package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.WeaponDao;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for basic operations on entity Weapon defined in interface WeaponDao.
 *
 * Created by vaculik on 26.10.15.
 */

@ContextConfiguration(classes=InMemoryDatabaseApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WeaponDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WeaponDao weaponDao;

    @Test
    public void getById() {
        Weapon w = createDefaultWeapon();
        weaponDao.create(w);
        Weapon result = weaponDao.getById(w.getId());

        Assert.assertEquals(result, w);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void nullId() {
        weaponDao.getById(null);
    }

    @Test
    public void getByName() {
        Weapon w = createDefaultWeapon();
        weaponDao.create(w);
        Weapon result = weaponDao.getByName(w.getName());

        Assert.assertEquals(result, w);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void nullName() {
        weaponDao.getByName(null);
    }

    @Test
    public void getByNameNoResult() {
        Weapon w = createDefaultWeapon();
        weaponDao.create(w);
        Weapon result = weaponDao.getByName("A");

        Assert.assertNull(result);
    }

    @Test
    public void findAll() {
        Weapon w1 = createDefaultWeapon("W1");
        Weapon w2 = createDefaultWeapon("W2");
        Weapon w3 = createDefaultWeapon("W3");
        weaponDao.create(w1);
        weaponDao.create(w2);
        weaponDao.create(w3);
        List<Weapon> result = weaponDao.findAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertTrue(result.contains(w1));
        Assert.assertTrue(result.contains(w2));
        Assert.assertTrue(result.contains(w3));
    }

    @Test
    public void findAllEmptyResult() {
        Assert.assertEquals(0, weaponDao.findAll().size());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNull() {
        weaponDao.create(null);
    }

    @Test
    public void update() {
        Weapon w = createDefaultWeapon();
        weaponDao.create(w);
        w.setName("A");
        weaponDao.update(w);
        Weapon result = weaponDao.getById(w.getId());

        Assert.assertEquals(result, w);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNull() {
        weaponDao.update(null);
    }

    @Test
    public void remove() {
        Weapon w1 = createDefaultWeapon("W1");
        Weapon w2 = createDefaultWeapon("W2");
        weaponDao.create(w1);
        weaponDao.create(w2);
        weaponDao.remove(w2);
        List<Weapon> result = weaponDao.findAll();

        Assert.assertEquals(result.size(), 1);
        Assert.assertTrue(result.contains(w1));
        Assert.assertFalse(result.contains(w2));

        weaponDao.remove(w1);
        result = weaponDao.findAll();

        Assert.assertEquals(result.size(), 0);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void removeNull() {
        weaponDao.remove(null);
    }

    private Weapon createDefaultWeapon() {
        return createDefaultWeapon("W");
    }

    private Weapon createDefaultWeapon(String name) {
        Weapon w = new Weapon();
        w.setName(name);
        w.setRange(10);
        w.setType(WeaponType.GUN);
        w.setAmmoType(AmmoType.BULLET_9MM);
        w.setDescription("Default weapon");
        return w;
    }
}

