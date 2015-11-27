package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import cz.muni.fi.pa165.dao.AreaDao;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;

/* This class test functionality of methods of Area Service, Area Data Access Object class.
 *
 * @author Martin Zboril
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class AreaServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AreaDao areaDao;
    @Autowired
    @InjectMocks
    private AreaService areaService;
    private Area area;
    private Area ar2;
    private Long id;
    private String name;
    private String description;
    private List<Area> areas;
    private Creature creature;

    @BeforeMethod
    public void init() {
        id = 1l;
        name = "Winterfell";
        description = "Big area";
        area = createArea(id, name, description);
        reset(areaDao);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private Area createArea(Long id, String name, String description) {
        Area tmp = new Area();
        tmp.setId(id);
        tmp.setName(name);
        tmp.setDescription(description);
        return tmp;
    }

    @Test
    public void getAreaByIdTest() {
        Long wrong = 2l;
        when(areaDao.getById(id)).thenReturn(area);
        Assert.assertEquals(areaService.getAreaById(id), area);
        Assert.assertNull(areaService.getAreaById(wrong));
        verify(areaDao).getById(id);
        verify(areaDao).getById(wrong);
    }

    @Test
    public void getAreaByNameTest() {
        String wrong = "Wall";
        when(areaDao.getByName(name)).thenReturn(area);
        Assert.assertEquals(areaService.getAreaByName(name), area);
        Assert.assertNull(areaService.getAreaByName(wrong));
        verify(areaDao).getByName(name);
        verify(areaDao).getByName(wrong);
    }

    @Test
    public void createAreaTest() {
        areaService.createArea(area);
        doReturn(area).when(areaDao).getById(id);
        verify(areaDao).create(area);
        Assert.assertEquals(areaService.getAreaById(id).getName(), name);
    }

    @Test
    public void updateAreaTest() {
        areaService.createArea(area);
        doReturn(area).when(areaDao).getById(id);
        area.setDescription("Bad");
        areaService.updateArea(area);
        verify(areaDao).update(area);
        Assert.assertEquals(areaService.getAreaById(id).getDescription(), "Bad");
    }

    @Test
    public void deleteAreaTest() {
        areaService.createArea(area);
        doReturn(area).when(areaDao).getById(id);
        areaService.deleteArea(area);
        verify(areaDao).delete(area);
        doThrow(new NullPointerException()).when(areaDao).getById(id);
    }

    @Test
    public void findAllTest() {
        areas = createAreasList();
        when(areaDao.findAll()).thenReturn(areas);
        Assert.assertEquals(5, areaService.findAllAreas().size());
        verify(areaDao, times(1)).findAll();
    }

    @Test
    public void getAreasWithNoCreatureTest() {
        areas = createAreasList();
        when(areaDao.findAll()).thenReturn(areas);
        Assert.assertEquals(0, areaService.getAreasWithNoCreature().size());

        Long id6 = 6l;
        areas.add(createArea(id6, "Dorn", "Very hot"));
        Assert.assertEquals(1, areaService.getAreasWithNoCreature().size());

        verify(areaDao, times(2)).findAll();
    }

    @Test
    public void getAreasWithAnyCreatureTest() {
        areas = createAreasList();
        when(areaDao.findAll()).thenReturn(areas);
        Assert.assertEquals(5, areaService.getAreasWithAnyCreature().size());

        Long id6 = 6l;
        areas.add(createArea(id6, "Dorn", "Very hot"));
        Assert.assertEquals(5, areaService.getAreasWithAnyCreature().size());

        verify(areaDao, times(2)).findAll();
    }

    @Test
    public void getAreasMostCreaturesTest() {
        areas = createAreasList();
        when(areaDao.findAll()).thenReturn(areas);
        Assert.assertEquals(1, areaService.getAreasMostCreatures().size());
        Long id6 = 6l;
        Area ar6 = createArea(id6, "Dorn", "Very hot");
        areas.add(ar6);
        Assert.assertEquals(1, areaService.getAreasMostCreatures().size());
        verify(areaDao, times(2)).findAll();
    }

    @Test
    public void getAreasFewestCreaturesTest() {
        areas = createAreasList();
        when(areaDao.findAll()).thenReturn(areas);
        Assert.assertEquals(2, areaService.getAreasFewestCreatures().size());

        verify(areaDao, times(1)).findAll();
    }

    @Test
    public void moveCreatureTest() {
        areas = createAreasList();
        doReturn(area).when(areaDao).getById(area.getId());
        doReturn(ar2).when(areaDao).getById(ar2.getId());
        Assert.assertTrue(area.getCreatures().contains(creature));
        Assert.assertFalse(ar2.getCreatures().contains(creature));
        areaService.moveCreature(creature, area, ar2);
        Assert.assertFalse(area.getCreatures().contains(creature));
        Assert.assertTrue(ar2.getCreatures().contains(creature));
    }

    private List<Area> createAreasList() {
        areas = new ArrayList<>();

        area = createArea(id, name, description);
        Long id2 = 2l;
        ar2 = createArea(id2, "Wall", "Cold one");
        Long id3 = 3l;
        Area ar3 = createArea(id3, "Kings landing", "Hot one");
        Long id4 = 4l;
        Area ar4 = createArea(id4, "Bravos", "On the south");
        Long id5 = 5l;
        Area ar5 = createArea(id5, "Meereen", "With the sea");

        creature = createCreature("Ned Stark");
        area.addCreature(creature);
        area.addCreature(createCreature("Arya Stark"));
        area.addCreature(createCreature("Robb Stark"));
        area.addCreature(createCreature("Hodor"));

        ar2.addCreature(createCreature("Jon Snow"));

        ar3.addCreature(createCreature("Jamie Lannister"));
        ar3.addCreature(createCreature("Tywin Lannister"));
        ar3.addCreature(createCreature("Tyrion Lannister"));

        ar4.addCreature(createCreature("Syrio"));

        ar5.addCreature(createCreature("Daenerys Targarien"));
        ar5.addCreature(createCreature("Jorah Mormont"));

        areas.add(area);
        areas.add(ar2);
        areas.add(ar3);
        areas.add(ar4);
        areas.add(ar5);

        int a1 = area.getCreatures().size();
        int a2 = ar2.getCreatures().size();
        int a3 = ar3.getCreatures().size();
        int a4 = ar4.getCreatures().size();
        int a5 = ar5.getCreatures().size();

        return areas;
    }

    private Creature createCreature(String name) {
        Creature cr = new Creature();
        cr.setName(name);
        cr.setType(CreatureType.BEAST);
        cr.setHeight(180);
        cr.setWeight(350);
        cr.setDescription("Test creature");
        return cr;
    }
}