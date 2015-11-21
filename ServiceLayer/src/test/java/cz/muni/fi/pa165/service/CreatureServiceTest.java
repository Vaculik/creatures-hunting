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
import org.testng.annotations.BeforeMethod;
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

    private List<Creature> creatures = new LinkedList<>();
    private List<Creature> actual;
    private List<Creature> expected = new LinkedList<>();

    @BeforeMethod
    public void init() {
        creatures.clear();
        expected.clear();
        when(creatureDao.findAll()).thenReturn(creatures);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCreaturesOfTypeTest() {
        Creature vampire1 = createCreature(1, 1, CreatureType.VAMPIRE);
        Creature vampire2 = createCreature(1, 2, CreatureType.VAMPIRE);
        Creature beast = createCreature(1, 3, CreatureType.BEAST);
        creatures.add(vampire1);
        creatures.add(vampire2);
        creatures.add(beast);

        // vampire type test
        actual = creatureService.getCreaturesOfType(CreatureType.VAMPIRE);
        Assert.assertEquals(actual.size(), 2);

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

    @Test
    public void getCreaturesWithMaxHeightTest() {
        // no creatures test
        actual = creatureService.getCreaturesWithMaxHeight();
        Assert.assertEquals(actual.size(), 0);

        // creature with null height test
        creatures.add(createCreature(null, 1, CreatureType.BEAST));
        actual = creatureService.getCreaturesWithMaxHeight();
        Assert.assertEquals(actual.size(), 0);

        // single creature with max height test
        Creature creatureWithMaxHeight = createCreature(1, 1, CreatureType.UNDEAD);
        creatures.add(creatureWithMaxHeight);
        expected.add(creatureWithMaxHeight);
        actual = creatureService.getCreaturesWithMaxHeight();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual, expected);

        // multiple creatures with max height test
        expected.clear();
        creatureWithMaxHeight = createCreature(2,1,CreatureType.UNDEAD);
        creatures.add(creatureWithMaxHeight);
        expected.add(creatureWithMaxHeight);
        creatureWithMaxHeight = createCreature(2,2,CreatureType.BEAST);
        creatures.add(creatureWithMaxHeight);
        expected.add(creatureWithMaxHeight);
        actual = creatureService.getCreaturesWithMaxHeight();
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getCreaturesWithMaxWeightTest() {
        // no creatures test
        actual = creatureService.getCreaturesWithMaxWeight();
        Assert.assertEquals(actual.size(), 0);

        // creature with null weight test
        creatures.add(createCreature(1, null, CreatureType.VAMPIRE));
        actual = creatureService.getCreaturesWithMaxWeight();
        Assert.assertEquals(actual.size(), 0);

        // single creature with max weight test
        Creature creatureWithMaxWeight = createCreature(1, 1, CreatureType.BEAST);
        creatures.add(creatureWithMaxWeight);
        expected.add(creatureWithMaxWeight);
        actual = creatureService.getCreaturesWithMaxWeight();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual, expected);

        // multiple creatures with max weight test
        expected.clear();
        creatureWithMaxWeight = createCreature(1,2,CreatureType.BEAST);
        creatures.add(creatureWithMaxWeight);
        expected.add(creatureWithMaxWeight);
        creatureWithMaxWeight = createCreature(2,2,CreatureType.UNDEAD);
        creatures.add(creatureWithMaxWeight);
        expected.add(creatureWithMaxWeight);
        actual = creatureService.getCreaturesWithMaxWeight();
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual, expected);
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
