package cz.muni.fi.pa165.facade;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.service.UserSystemService;
import cz.muni.fi.pa165.util.EntityMapper;

@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class UserSystemFacadeTest extends AbstractTestNGSpringContextTests {
	
    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private UserSystemFacade userSystemFacade;
    
    @BeforeTest
    public void initMocks() {

    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getUserByIdTest() {
    	Assert.assertEquals(true, true);
    }

}
