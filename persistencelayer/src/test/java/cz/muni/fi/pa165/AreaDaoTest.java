package cz.muni.fi.pa165;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.dao.AreaDao;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.enums.WeaponType;


/**
 * This class test functionality of methods of Area Data Access Object class.
 *
 * @author Jakub Miculka
 */
@ContextConfiguration(classes = InMemoryDatabaseApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AreaDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void getById() {
		Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
		
		Area tmp = areaDao.getById(area.getId());
		Assert.assertEquals(tmp, area);
	}
	
	@Test
	public void getByIdWrong() {
		Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
		
		Area tmp = areaDao.getById(Long.MAX_VALUE);
		Assert.assertNotEquals(tmp, area);
		Assert.assertNull(tmp);
	}
	
    @Test(expectedExceptions = NullPointerException.class)
    public void getByNullIdTest() {
    	Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
        areaDao.getById(null);
    }
    
    @Test
    public void getByName() {
    	Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
		
		Area tmp = areaDao.getByName("Brno");
		Assert.assertEquals(tmp, area);
    }
    
	@Test
	public void getByNameWrong() {
		Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
		
		Area tmp = areaDao.getByName("Praha");
		Assert.assertNotEquals(tmp, area);
		Assert.assertNull(tmp);
	}
	
    @Test(expectedExceptions = NullPointerException.class)
    public void getByNullNameTest() {
    	Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
        areaDao.getByName(null);
    }
    
    @Test
    public void deleteAreaTest() {
    	Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
		
		Area tmp = areaDao.getByName("Brno");
		Assert.assertEquals(area, tmp);
		areaDao.delete(area);
		
		Area tmp2 = areaDao.getByName("Brno");
		Assert.assertNotEquals(area, tmp2);
		Assert.assertNull(tmp2);
    }
    
    @Test
    public void updateUserTest() {
    	Area area = createArea("Brno", "StudentCity");
		areaDao.create(area);
		
		Area tmp = areaDao.getById(area.getId());
		Assert.assertEquals(area.getName(), tmp.getName());
		Assert.assertEquals(area.getDescription(), tmp.getDescription());
		
		//Area newArea = areaDao.getByName(area.getName());
		area.setName("Praha");
		area.setDescription("Big city");
		
		Set<Creature> creatureList = new HashSet<Creature>();
		creatureList.add(makeCreature("Michael"));
		area.setCreatures(creatureList);
		
		areaDao.update(area);
		
		Area tmp2 = areaDao.getById(area.getId());
		Assert.assertNotEquals(tmp.getName(), tmp2.getName());
		Assert.assertNotEquals(tmp.getDescription(), tmp2.getDescription());
    }
    
    @Test
    public void findAllTest() {
    	Area area1 = createArea("Brno", "Student City");
    	Area area2 = createArea("Praha", "Big City");
    	Area area3 = createArea("Ostrava", "Shit City");
    	Area area4 = createArea("Karlovy Vary", "Russian City");
    	
    	areaDao.create(area1);
    	areaDao.create(area2);
    	areaDao.create(area3);
    	areaDao.create(area4);
    	
    	List<Area> areaList = areaDao.findAll();
    	Assert.assertEquals(4, areaList.size());
    	Assert.assertEquals(areaList.contains(area1), true);
    	Assert.assertEquals(areaList.contains(area2), true);
    	Assert.assertEquals(areaList.contains(area3), true);
    	Assert.assertEquals(areaList.contains(area4), true);
    }
    
    @Test
    public void findAllNoneContainsTest() {
        Assert.assertEquals(0, areaDao.findAll().size());
    }
	
	private Area createArea(String name, String description) {
		Area area = new Area();
		area.setName(name);
		area.setDescription(description);
		
		Set<Creature> creatureList = new HashSet<Creature>();
		creatureList.add(makeCreature("Bob"));
		creatureList.add(makeCreature("Tom"));
		area.setCreatures(creatureList);
		
		return area;	
	}
	
    private Creature makeCreature(String name) {
        Creature creature = new Creature();
        creature.setName(name);
        creature.setType(CreatureType.BEAST);
        creature.setHeight(180);
        creature.setWeight(350);
        creature.setDescription("Test creature");
        return creature;
    }
}
