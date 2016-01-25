package cz.muni.fi.pa165.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.UserSystemDao;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.util.PasswordUtil;

/**
 * Tests for all methods of the UserSystemService interface.
 *
 * @author Jakub Miculka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class UserSystemServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserSystemDao userSystemDao;
    @Autowired
    @InjectMocks
    private UserSystemService userSystemService;
    private List<UserSystem> users;
    private UserSystem user;

    @BeforeMethod
    public void init() {
        user = createUser("Boss", SexType.MALE, UserType.ADMIN);
        users = new LinkedList<>();
        reset(userSystemDao);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserByIdTest() {
        Long id = 1l;
        Long wrongId = 0l;
        user.setId(id);

        when(userSystemDao.getById(id)).thenReturn(user);

        Assert.assertEquals(userSystemService.getUserById(id), user);
        Assert.assertNull(userSystemService.getUserById(wrongId));

        verify(userSystemDao).getById(id);
        verify(userSystemDao).getById(wrongId);
    }

    @Test
    public void getUserByUserNameTest() {
        String name = "Ice-B";
        String wrongName = "Doktor z hor";
        user.setUserName(name);

        when(userSystemDao.getByUserName(name)).thenReturn(user);

        Assert.assertEquals(userSystemService.getUserByUserName(name), user);
        Assert.assertNull(userSystemService.getUserByUserName(wrongName));

        verify(userSystemDao).getByUserName(name);
        verify(userSystemDao).getByUserName(wrongName);
    }

    @Test
    public void createUserTest() {
        doNothing().when(userSystemDao).create(user);
        userSystemService.createUser(user);
        verify(userSystemDao).create(user);
    }

    @Test
    public void deleteUserTest() {
        doNothing().when(userSystemDao).delete(user);
        userSystemService.deleteUser(user);
        verify(userSystemDao).delete(user);
    }

    @Test
    public void updateUserTest() {
        doNothing().when(userSystemDao).update(user);
        userSystemService.updateUser(user);
        verify(userSystemDao).update(user);
    }

    @Test
    public void getAllUsersTest() {
        UserSystem user1 = createUser("Bob Dylan", SexType.MALE, UserType.ORDINARY);
        UserSystem user2 = createUser("Bob Marley", SexType.MALE, UserType.ORDINARY);
        UserSystem user3 = createUser("Bohus Matus", SexType.MALE, UserType.ADMIN);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userSystemDao.findAll()).thenReturn(users);
        List<UserSystem> actual = userSystemService.getAllUsers();
        Assert.assertEquals(actual.size(), 3);
        Assert.assertEquals(actual, users);

        verify(userSystemDao).findAll();
    }
    
    @Test
    public void loginTest() {
        UserSystem user1 = createUser("Bob", SexType.MALE, UserType.ORDINARY);
        user1.setUserName("admin");
        String password = PasswordUtil.hashPassword("123456");
        user1.setPassword(password);
        Long id = 1l;
        user1.setId(id);

        when(userSystemDao.getByUserName("admin")).thenReturn(user1);
        
        Assert.assertEquals(userSystemService.login("admin", "123456").getId(),id);        

        verify(userSystemDao).getByUserName("admin");
    }    
    
    @Test
    public void getUsersOfTypeTest() {
        UserSystem user1 = createUser("Bob Dylan", SexType.MALE, UserType.ORDINARY);
        UserSystem user2 = createUser("Bob Marley", SexType.MALE, UserType.ORDINARY);
        UserSystem user3 = createUser("Bohus Matus", SexType.MALE, UserType.ADMIN);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userSystemDao.findAll()).thenReturn(users);

        //ordinary user type test
        List<UserSystem> actual = userSystemService.getUsersOfType(UserType.ORDINARY);
        Assert.assertEquals(actual.size(), 2);

        List<UserSystem> expected = new ArrayList<UserSystem>();
        expected.add(user1);
        expected.add(user2);
        Assert.assertEquals(actual, expected);

        // admin type test
        actual = userSystemService.getUsersOfType(UserType.ADMIN);
        Assert.assertEquals(actual.size(), 1);

        expected.clear();
        expected.add(user3);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getUsersOfSexTest() {
        UserSystem user1 = createUser("Bob Dylan", SexType.MALE, UserType.ORDINARY);
        UserSystem user2 = createUser("Bob Marley", SexType.MALE, UserType.ORDINARY);
        UserSystem user3 = createUser("Luiza Lian", SexType.FEMALE, UserType.ORDINARY);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userSystemDao.findAll()).thenReturn(users);

        //ordinary user type test
        List<UserSystem> actual = userSystemService.getUsersOfSex(SexType.MALE);
        Assert.assertEquals(actual.size(), 2);

        List<UserSystem> expected = new ArrayList<UserSystem>();
        expected.add(user1);
        expected.add(user2);
        Assert.assertEquals(actual, expected);

        // admin type test
        actual = userSystemService.getUsersOfSex(SexType.FEMALE);
        Assert.assertEquals(actual.size(), 1);

        expected.clear();
        expected.add(user3);
        Assert.assertEquals(actual, expected);
    }

    private UserSystem createUser(String name, SexType sex, UserType type) {
        UserSystem user = new UserSystem();
        user.setName(name);
        user.setPassword("abcd");
        user.setSex(sex);
        user.setType(type);
        user.setUserName("nick " + name);

        return user;
    }
}
