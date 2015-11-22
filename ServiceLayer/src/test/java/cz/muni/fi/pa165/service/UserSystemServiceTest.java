package cz.muni.fi.pa165.service;

import static org.mockito.Mockito.when;

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
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;

@ContextConfiguration(classes = ServiceApplicationContext.class)
public class UserSystemServiceTest extends AbstractTestNGSpringContextTests {
	
	  	@Mock
	    private UserSystemDao userSystemDao;

	    @Autowired
	    @InjectMocks
	    private UserSystemService userSystemService;

	    private List<UserSystem> users = new LinkedList<>();
	    private List<UserSystem> actual;
	    private List<UserSystem> expected = new LinkedList<>();

	    @BeforeMethod
	    public void init() {
	    	users.clear();
	        expected.clear();
	        when(userSystemDao.findAll()).thenReturn(users);
	    }

	    @BeforeClass
	    public void setup() {
	        MockitoAnnotations.initMocks(this);
	    }

	    @Test
	    public void getUsersByTypeTest() {
	        UserSystem user1 = createUser("Bob Dylan", SexType.MALE, UserType.ORDINARY);
	        UserSystem user2 = createUser("Bob Marley", SexType.MALE, UserType.ORDINARY);
	        UserSystem user3 = createUser("Bohus Matus", SexType.MALE, UserType.ADMIN);
	        users.add(user1);
	        users.add(user2);
	        users.add(user3);

	        //ordinary user type test
	        actual = userSystemService.getUsersOfType(UserType.ORDINARY);
	        Assert.assertEquals(actual.size(), 2);

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
	    public void getUsersBySexTest() {
	    	UserSystem user1 = createUser("Bob Dylan", SexType.MALE, UserType.ORDINARY);
	        UserSystem user2 = createUser("Bob Marley", SexType.MALE, UserType.ORDINARY);
	        UserSystem user3 = createUser("Luiza Lian", SexType.FEMALE, UserType.ORDINARY);
	        users.add(user1);
	        users.add(user2);
	        users.add(user3);

	        //ordinary user type test
	        actual = userSystemService.getUsersOfSex(SexType.MALE);
	        Assert.assertEquals(actual.size(), 2);

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
	        user.setPassword(new Integer(123456));
	        user.setSex(sex);
	        user.setType(type);
	        user.setUserName("nick " + name);
	        
	        return user;
	    }
}
