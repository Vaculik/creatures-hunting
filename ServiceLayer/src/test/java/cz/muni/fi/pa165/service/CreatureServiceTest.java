package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.CreatureDao;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
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

import static org.mockito.Mockito.*;

/**
 * Tests for all methods of the CreatureService interface.
 *
 * @author Karel Vaculik
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class CreatureServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CreatureDao creatureDao;
    @Autowired
    @InjectMocks
    private CreatureService creatureService;
    private List<Creature> creatures;
    private Creature creature;

    private Area area;

    @BeforeMethod
    public void init() {
        creature = createCreature(1, 1, CreatureType.BEAST);
        creatures = new LinkedList<>();
        reset(creatureDao);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCreatureByIdTest() {
        Long id = 1l;
        Long wrongId = 0l;
        creature.setId(id);

        when(creatureDao.getById(id)).thenReturn(creature);

        Assert.assertEquals(creatureService.getCreatureById(id), creature);
        Assert.assertNull(creatureService.getCreatureById(wrongId));

        verify(creatureDao).getById(id);
        verify(creatureDao).getById(wrongId);
    }

    @Test
    public void getCreatureByNameTest() {
        String name = "creature";
        String wrongName = "wrong-name";
        creature.setName(name);

        when(creatureDao.getByName(name)).thenReturn(creature);

        Assert.assertEquals(creatureService.getCreatureByName(name), creature);
        Assert.assertNull(creatureService.getCreatureByName(wrongName));

        verify(creatureDao).getByName(name);
        verify(creatureDao).getByName(wrongName);
    }

    @Test
    public void createCreatureTest() {
        doNothing().when(creatureDao).create(creature);

        creatureService.createCreature(creature);

        verify(creatureDao).create(creature);
    }

    @Test
    public void deleteCreatureTest() {
        doNothing().when(creatureDao).delete(creature);

        creatureService.deleteCreature(creature);

        verify(creatureDao).delete(creature);
    }

    @Test
    public void findAllCreaturesTest() {
        creatures.add(creature);

        when(creatureDao.findAll()).thenReturn(creatures);

        List<Creature> actual = creatureService.findAllCreatures();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual, creatures);

        verify(creatureDao).findAll();
    }

    @Test
    public void getCreaturesOfTypeTest() {
        Creature vampire1 = createCreature(1, 1, CreatureType.VAMPIRE);
        Creature vampire2 = createCreature(1, 2, CreatureType.VAMPIRE);
        Creature beast = createCreature(1, 3, CreatureType.BEAST);
        creatures.add(vampire1);
        creatures.add(vampire2);
        creatures.add(beast);

        when(creatureDao.findAll()).thenReturn(creatures);

        // vampire type test
        List<Creature> actual = creatureService.getCreaturesOfType(CreatureType.VAMPIRE);
        List<Creature> expected = new LinkedList<>();
        Assert.assertEquals(actual.size(), 2);

        expected.add(vampire1);
        expected.add(vampire2);
        Assert.assertEquals(actual, expected);

        verify(creatureDao).findAll();

        // beast type test
        actual = creatureService.getCreaturesOfType(CreatureType.BEAST);
        Assert.assertEquals(actual.size(), 1);

        expected.clear();
        expected.add(beast);
        Assert.assertEquals(actual, expected);

        verify(creatureDao, times(2)).findAll();

        // undead type test
        actual = creatureService.getCreaturesOfType(CreatureType.UNDEAD);
        Assert.assertEquals(actual.size(), 0);

        verify(creatureDao, times(3)).findAll();
    }

    @Test
    public void getCreaturesWithMaxHeightNoCreaturesTest() {
        when(creatureDao.findAll()).thenReturn(creatures);

        List<Creature> actual = creatureService.getCreaturesWithMaxHeight();
        Assert.assertEquals(actual.size(), 0);

        verify(creatureDao).findAll();
    }

    @Test
    public void getCreaturesWithMaxHeightTest() {
        Creature smallCreature = createCreature(1, 1, CreatureType.VAMPIRE);
        Creature creatureWithMaxHeight1 = createCreature(2, 1, CreatureType.UNDEAD);
        Creature creatureWithMaxHeight2 = createCreature(2, 2, CreatureType.BEAST);

        creatures.add(creatureWithMaxHeight1);
        creatures.add(creatureWithMaxHeight2);
        creatures.add(smallCreature);

        when(creatureDao.findAll()).thenReturn(creatures);

        List<Creature> actual = creatureService.getCreaturesWithMaxHeight();
        List<Creature> expected = new LinkedList<>();
        expected.add(creatureWithMaxHeight1);
        expected.add(creatureWithMaxHeight2);

        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual, expected);

        verify(creatureDao).findAll();
    }

    @Test
    public void getCreaturesWithMaxWeightNoCreaturesTest() {
        when(creatureDao.findAll()).thenReturn(creatures);

        List<Creature> actual = creatureService.getCreaturesWithMaxWeight();
        Assert.assertEquals(actual.size(), 0);

        verify(creatureDao).findAll();
    }

    @Test
    public void getCreaturesWithMaxWeightTest() {
        Creature smallCreature = createCreature(1, 1, CreatureType.VAMPIRE);
        Creature creatureWithMaxWeight1 = createCreature(2, 2, CreatureType.UNDEAD);
        Creature creatureWithMaxWeight2 = createCreature(1, 2, CreatureType.BEAST);

        creatures.add(creatureWithMaxWeight1);
        creatures.add(creatureWithMaxWeight2);
        creatures.add(smallCreature);

        when(creatureDao.findAll()).thenReturn(creatures);

        List<Creature> actual = creatureService.getCreaturesWithMaxWeight();
        List<Creature> expected = new LinkedList<>();
        expected.add(creatureWithMaxWeight1);
        expected.add(creatureWithMaxWeight2);

        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual, expected);

        verify(creatureDao).findAll();
    }

    @Test
    public void updateCreatureTest() {
        doNothing().when(creatureDao).update(creature);
        creatureService.updateCreature(creature);
        verify(creatureDao).update(creature);
    }

    @Test
    public void getCreaturesInNoAreaTest() {
        Creature smallCreature = createCreature(1, 1, CreatureType.VAMPIRE);
        Creature creatureWithMaxWeight1 = createCreature(2, 2, CreatureType.UNDEAD);
        Creature creatureWithMaxWeight2 = createCreature(1, 2, CreatureType.BEAST);
        area = createArea();
        area.addCreature(smallCreature);

        creatures.add(creatureWithMaxWeight1);
        creatures.add(creatureWithMaxWeight2);
        creatures.add(smallCreature);

        when(creatureDao.findAll()).thenReturn(creatures);

        List<Creature> actual = creatureService.getCreaturesInNoArea();
        Assert.assertEquals(actual.size(), 2);        

        verify(creatureDao).findAll();

    }

    @Test
    public void removeAreaOfCreatureTest() {
        area = createArea();
        area.addCreature(creature);
        Long id = 1l;
        creature.setId(id);
        when(creatureDao.getById(id)).thenReturn(creature);
        
        creatureService.removeAreaOfCreature(id);
        
        
        Assert.assertEquals(area.getCreatures().size(), 0);

        verify(creatureDao).getById(id);

        
    }
    
    
    private Creature createCreature(Integer height, Integer weight, CreatureType type) {
        Creature c = new Creature();
        c.setHeight(height);
        c.setWeight(weight);
        c.setType(type);
        c.setName("testing-creature");
        return c;
    }

    private Area createArea() {
        Area a = new Area();
        a.setDescription("Desc");
        a.setName("Noname");
        return a;
    }
}
