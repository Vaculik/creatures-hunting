package cz.muni.fi.pa165.facade;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.service.UserSystemService;
import cz.muni.fi.pa165.util.EntityMapper;

/**
 * Tests for all methods of the UserSystemFacade interface.
 *
 * @author Jakub Miculka
 */
@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class UserSystemFacadeTest extends AbstractTestNGSpringContextTests {
	
    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private UserSystemFacade userSystemFacade;
    
    private UserSystem user;
    private UserSystemDTO userDTO;
    
    @BeforeMethod
    public void initSingleTest() {
        reset(userSystemService, entityMapper);
        user = createUser("Boss", SexType.MALE, UserType.ADMIN);
        userDTO = createUserDTO(user);
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
        userDTO.setId(id);

        when(entityMapper.map(user, UserSystemDTO.class)).thenReturn(userDTO);
        when(userSystemService.getUserById(id)).thenReturn(user);

        Assert.assertEquals(userSystemFacade.getUserById(id), userDTO);
        Assert.assertNull(userSystemFacade.getUserById(wrongId));

        verify(entityMapper).map(user, UserSystemDTO.class);
        verify(userSystemService).getUserById(id);
    }
    
    @Test
    public void getUserByName() {
    	String name = "Ice-B";
        String wrongName = "Doktor z hor";
        user.setName(name);
        userDTO.setName(name);

        when(entityMapper.map(user, UserSystemDTO.class)).thenReturn(userDTO);
        when(userSystemService.getUserByName(name)).thenReturn(user);

        Assert.assertEquals(userSystemFacade.getUserByName(name), userDTO);
        Assert.assertNull(userSystemFacade.getUserByName(wrongName));

        verify(entityMapper).map(user, UserSystemDTO.class);
        verify(userSystemService).getUserByName(name);
    }
    
    @Test
    public void createUserTest() {
    	when(entityMapper.map(userDTO, UserSystem.class)).thenReturn(user);
    	doNothing().when(userSystemService).createUser(user);
    	
    	userSystemFacade.createUser(userDTO);
        verify(entityMapper).map(userDTO, UserSystem.class);
        verify(userSystemService).createUser(user);
    }
    
    @Test
    public void deleteUserTest() {
    	when(entityMapper.map(userDTO, UserSystem.class)).thenReturn(user);
    	doNothing().when(userSystemService).deleteUser(user);
    	
    	userSystemFacade.deleteUser(userDTO);
        verify(entityMapper).map(userDTO, UserSystem.class);
        verify(userSystemService).deleteUser(user);
    }
    
    @Test
    public void updateUserTest() {
    	when(entityMapper.map(userDTO, UserSystem.class)).thenReturn(user);
    	doNothing().when(userSystemService).updateUser(user);
    	
    	userSystemFacade.updateUser(userDTO);
        verify(entityMapper).map(userDTO, UserSystem.class);
        verify(userSystemService).updateUser(user);
    }
    
    @Test
    public void getAllUsersTest() {
        UserSystem user1 = createUser("Bob Dylan", SexType.MALE, UserType.ORDINARY);
        UserSystem user2 = createUser("Bob Marley", SexType.MALE, UserType.ORDINARY);
        UserSystem user3 = createUser("Bohus Matus", SexType.MALE, UserType.ADMIN);
        UserSystemDTO userDTO1 = createUserDTO(user1);
        UserSystemDTO userDTO2 = createUserDTO(user2);
        UserSystemDTO userDTO3 = createUserDTO(user3);
        
        List<UserSystem> users = new ArrayList<UserSystem>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        
        List<UserSystemDTO> usersDTO = new ArrayList<UserSystemDTO>();
        usersDTO.add(userDTO1);
        usersDTO.add(userDTO2);
        usersDTO.add(userDTO3);
        
        when(entityMapper.map(users, UserSystemDTO.class)).thenReturn(usersDTO);
        when(userSystemService.getAllUsers()).thenReturn(users);

        Assert.assertEquals(userSystemFacade.getAllUsers(), usersDTO);

        verify(entityMapper).map(users, UserSystemDTO.class);
        verify(userSystemService).getAllUsers();
    }
    
    @Test
    public void getUsersOfTypeTest() {
        List<UserSystem> users = new ArrayList<UserSystem>();
        List<UserSystemDTO> usersDTO = new ArrayList<UserSystemDTO>();
        users.add(user);
        usersDTO.add(userDTO);
        UserType type = user.getType();

        when(entityMapper.map(users, UserSystemDTO.class)).thenReturn(usersDTO);
        when(userSystemService.getUsersOfType(type)).thenReturn(users);

        Assert.assertEquals(userSystemFacade.getUsersOfType(type), usersDTO);

        verify(entityMapper).map(users, UserSystemDTO.class);
        verify(userSystemService).getUsersOfType(type);
    }
    
    @Test
    public void getUsersOfSexTest() {
        List<UserSystem> users = new ArrayList<UserSystem>();
        List<UserSystemDTO> usersDTO = new ArrayList<UserSystemDTO>();
        users.add(user);
        usersDTO.add(userDTO);
        SexType sex = user.getSex();

        when(entityMapper.map(users, UserSystemDTO.class)).thenReturn(usersDTO);
        when(userSystemService.getUsersOfSex(sex)).thenReturn(users);

        Assert.assertEquals(userSystemFacade.getUsersOfSex(sex), usersDTO);

        verify(entityMapper).map(users, UserSystemDTO.class);
        verify(userSystemService).getUsersOfSex(sex);
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
    
    private UserSystemDTO createUserDTO(UserSystem user) {
    	UserSystemDTO userDTO = new UserSystemDTO();
    	userDTO.setId(user.getId());
    	userDTO.setName(user.getName());
    	userDTO.setPassword(user.getPassword());
    	userDTO.setSex(user.getSex());
    	userDTO.setType(user.getType());
    	userDTO.setUserName(user.getUserName());
        
        return userDTO;
    }
}
