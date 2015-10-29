/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.UserSystemDao;
import cz.muni.fi.pa165.dao.UserSystemDaoImpl;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * This class test functionality of methods of UserSystem Data Access Object class.
 *
 * @author Martin Zboril
 */
@ContextConfiguration(classes = InMemoryDatabaseApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserSystemDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserSystemDao userDao;

    @Test
    public void getByIdTest() {
        UserSystem user = createUser("Ned Stark");
        userDao.create(user);
        UserSystem tmp = userDao.getById(user.getId());
        Assert.assertEquals(tmp, user);
    }

    @Test
    public void getByWrongIdTest() {
        UserSystem user = createUser("Ned Stark");
        userDao.create(user);
        Long tmp_id = new Long(123);
        while (tmp_id == user.getId()) {
            tmp_id += 1;
        }
        UserSystem tmp = userDao.getById(tmp_id);
        Assert.assertNotEquals(tmp, user);
        Assert.assertNull(tmp);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getByNullIdTest() {
        UserSystem user = createUser("Ned Stark");
        userDao.create(user);
        userDao.getById(null);
    }
    
    @Test
    public void getByNameTest() {
        UserSystem user = createUser("Tyrion");
        userDao.create(user);
        UserSystem tmp = userDao.getByName(user.getName());
        Assert.assertEquals(tmp, user);
    }

    @Test
    public void getByNameNotEqualsTest() {
        UserSystem user = createUser("Tyrion");
        userDao.create(user);
        UserSystem tmp = userDao.getByName("Sansa Stark");
        Assert.assertNotEquals(tmp, user);
        Assert.assertNotEquals(null, user);
        Assert.assertNull(tmp);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getByNullNameTest() {
        UserSystem user = createUser("Ned Stark");
        userDao.create(user);
        userDao.getByName(null);
    }
    
        @Test(expectedExceptions = NullPointerException.class)
    public void createNullUserTest() {//       
        userDao.create(null);
    }
        
    @Test
    public void deleteUserTest() {
        UserSystem user = createUser("Jamie Lannister");
        userDao.create(user);
        Assert.assertEquals(1, userDao.findAll().size());
        userDao.delete(user);
        Assert.assertEquals(0, userDao.findAll().size());
        UserSystem tmp = userDao.getByName(user.getName());
        Assert.assertNotEquals(tmp, user);
        Assert.assertNull(tmp);
    }

    @Test
    public void deleteWrongUserTest() {
        UserSystem user = createUser("Jamie Lannister");
        UserSystem user2 = createUser("Tyrion");
        userDao.create(user);

        Assert.assertFalse(userDao.findAll().contains(user2));
        userDao.delete(user2);
        Assert.assertFalse(userDao.findAll().contains(user2));
        Assert.assertTrue(userDao.findAll().contains(user));

        UserSystem tmp = userDao.getByName(user.getName());
        Assert.assertEquals(tmp, user);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullUserTest() {
        userDao.delete(null);
    }
    
    @Test
    public void updateUserTest() {
        UserSystem user = createUser("Jamie Lannister");
        userDao.create(user);

        user.setName("Arya");
        user.setSex(SexType.FEMALE);
        user.setType(UserType.ADMIN);
        user.setUserName("Arya the blind");
        user.setPassword(new Integer(654321));

        userDao.update(user);

        UserSystem tmp = userDao.getByName(user.getName());

        Assert.assertEquals(tmp, user);
        Assert.assertEquals(user.getId(), tmp.getId());
        Assert.assertEquals(user.getName(), tmp.getName());
        Assert.assertEquals(user.getUserName(), tmp.getUserName());
        Assert.assertEquals(user.getPassword(), tmp.getPassword());
        Assert.assertEquals(user.getSex(), tmp.getSex());
        Assert.assertEquals(user.getType(), tmp.getType());
    }

    @Test
    public void updateWrongUserTest() {
        UserSystem user = createUser("Jamie Lannister");
        UserSystem user2 = createUser("Tywin");
        userDao.create(user);
        user2.setName("Arya");

        Assert.assertFalse(userDao.findAll().contains(user2));
        userDao.update(user2);
        Assert.assertFalse(userDao.findAll().contains(user2));
        Assert.assertTrue(userDao.findAll().contains(user));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullUserTest() {
        userDao.delete(null);
    }

    @Test
    public void findAllTest() {
        UserSystem user = createUser("Jamie Lannister");
        UserSystem user2 = createUser("The Mountain");
        UserSystem user3 = createUser("Forest children");
        UserSystem user4 = createUser("Sun of my stars");

        userDao.create(user);
        userDao.create(user2);
        userDao.create(user3);

        Assert.assertEquals(3, userDao.findAll().size());
        Assert.assertFalse(userDao.findAll().contains(user4));

        userDao.create(user4);

        Assert.assertNotEquals(3, userDao.findAll().size());
        Assert.assertEquals(4, userDao.findAll().size());
        Assert.assertTrue(userDao.findAll().contains(user4));
    }

    @Test
    public void findAllNoneContainsTest() {
        Assert.assertEquals(0, userDao.findAll().size());
    }

    private UserSystem createUser(String name) {
        UserSystem user = new UserSystem();
        user.setName(name);
        user.setPassword(new Integer(123456));
        user.setSex(SexType.MALE);
        user.setType(UserType.ORDINARY);
        user.setUserName("nick " + name);
        return user;
    }        
}
