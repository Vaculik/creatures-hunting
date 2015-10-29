package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.CreatureDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.dao.WeaponDao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Pavel Vesely <448290@mail.muni.cz>
 */

@ContextConfiguration(classes=InMemoryDatabaseApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CreatureDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private CreatureDao creatureDao;    
    
    @Test
    public void getById_standard() {
        Creature creature = makeCreature();
        creatureDao.create(creature);
        Creature result = creatureDao.getById(creature.getId());
        
        Assert.assertEquals(creature, result);
    }
    
    @Test
    public void getById_noResult() {
        Creature result = creatureDao.getById(Long.MAX_VALUE);
        Assert.assertNull(result);
    }
    
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getById_null() {
        creatureDao.getById(null);
    }
    
    @Test
    public void getByName_standard() {
        Creature creature = makeCreature("c001");
        creatureDao.create(creature);
        Creature result = creatureDao.getByName(creature.getName());
        
        Assert.assertEquals(creature, result);
    }
    
    @Test
    public void getByName_noResult() {
        Creature creature = makeCreature("c001");
        creatureDao.create(creature);
        Creature result = creatureDao.getByName("no such creature");
        
        Assert.assertNull(result);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void create_nullArg() {
        creatureDao.create(null);
    }
    
    @Test
    public void delete_standard() {
        Creature creature = makeCreature();
        creatureDao.create(creature);
        creatureDao.delete(creature);
        Creature result = creatureDao.getById(creature.getId());
        
        Assert.assertNull(result);
    }
    
    @Test
    public void delete_noSuchCreature() {
        Creature creat0 = makeCreature("creat0");
        Creature creat1 = makeCreature("creat1");
        creatureDao.create(creat0);
        creatureDao.delete(creat1);
        
        Creature result = creatureDao.getById(creat0.getId());
        
        Assert.assertEquals(creat0, result);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void delete_nullArg() {
        creatureDao.delete(null);
    }
    
    @Test
    public void update_standard() {
        Creature creature = makeCreature("creat0");
        creatureDao.create(creature);
        creature.setName("creat1");
        creatureDao.update(creature);
        Creature result = creatureDao.getById(creature.getId());
        
        Assert.assertEquals(creature, result);
    }
    
    @Test
    public void update_noSuchCreature() {
        Creature creat0 = makeCreature("creat0");
        Creature creat1 = makeCreature("creat1");
        creatureDao.create(creat0);
        creatureDao.update(creat1);
        
        Creature result = creatureDao.getById(creat0.getId());
        
        Assert.assertEquals(creat0, result);
        Assert.assertNotEquals(creat1, result);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void update_nullArg() {
        creatureDao.update(null);
    }
    
    @Test
    public void findAll_standard() {
        Creature creat0 = makeCreature("creat0");
        Creature creat1 = makeCreature("creat1");
        creatureDao.create(creat0);
        creatureDao.create(creat1);
        
        List<Creature> result = creatureDao.findAll();
        
        Assert.assertEquals(result.size(), 2);
        Assert.assertTrue(result.contains(creat0));
        Assert.assertTrue(result.contains(creat1));
    }
    
    @Test
    public void findAll_empty() {
        List<Creature> result = creatureDao.findAll();
        Assert.assertEquals(result.size(), 0);
    }
    
    private Creature makeCreature() {
        return makeCreature("creature");
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
 