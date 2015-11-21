package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.CreatureDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by vaculik on 21.11.15.
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class CreatureServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CreatureDao creatureDao;

    @Autowired
    @InjectMocks
    private CreatureService creatureService;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCreaturesOfTypeTest() {
        List<Creature> creatures = new LinkedList<>();
        Creature vampire1 = createCreature(1, 1, CreatureType.VAMPIRE);
        Creature vampire2 = createCreature(1, 2, CreatureType.VAMPIRE);
        Creature beast = createCreature(1, 3, CreatureType.BEAST);
        creatures.add(vampire1);
        creatures.add(vampire2);
        creatures.add(beast);
        when(creatureDao.findAll()).thenReturn(creatures);

        // vampire type test
        List<Creature> actual = creatureService.getCreaturesOfType(CreatureType.VAMPIRE);
        Assert.assertEquals(actual.size(), 2);

        List<Creature> expected = new LinkedList<>();
        expected.add(vampire1);
        expected.add(vampire2);
        Assert.assertEquals(actual, expected);

        // beast type test
        actual = creatureService.getCreaturesOfType(CreatureType.BEAST);
        Assert.assertEquals(actual.size(), 1);

        expected.clear();
        expected.add(beast);
        Assert.assertEquals(actual, expected);

        // undead type test
        actual = creatureService.getCreaturesOfType(CreatureType.UNDEAD);
        Assert.assertEquals(actual.size(), 0);
    }

    private Creature createCreature(Integer height, Integer weight, CreatureType type) {
        Creature c = new Creature();
        c.setHeight(height);
        c.setWeight(weight);
        c.setType(type);
        c.setName("testing-creature");
        return c;
    }

}
