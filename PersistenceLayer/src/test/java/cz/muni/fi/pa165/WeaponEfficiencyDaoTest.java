package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.CreatureDao;
import cz.muni.fi.pa165.dao.WeaponDao;
import cz.muni.fi.pa165.dao.WeaponEfficiencyDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.enums.WeaponType;
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
 * @author Pavel Veselý <448290@mail.muni.cz>
 */

@ContextConfiguration(classes=InMemoryDatabaseApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WeaponEfficiencyDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private CreatureDao creatureDao;
    @Autowired
    private WeaponDao weaponDao;
    @Autowired
    private WeaponEfficiencyDao efficiencyDao;
    
    @Test
    public void getById_standard() {
        Creature creature = makeCreature();
        Weapon weapon = makeWeapon();
        WeaponEfficiency efficiency = makeEfficiency(weapon, creature);
        creatureDao.create(creature);
        weaponDao.create(weapon);
        efficiencyDao.create(efficiency);
        
        WeaponEfficiency result = efficiencyDao.getById(efficiency.getId());
        
        Assert.assertEquals(efficiency, result);
    }
    
    @Test
    public void getById_noResult() {
        WeaponEfficiency result = efficiencyDao.getById(Long.MAX_VALUE);
        Assert.assertNull(result);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void getById_nullArg() {
        efficiencyDao.getById(null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void create_nullArg() {
        efficiencyDao.create(null);
    }
        
    @Test
    public void delete_standard() {
        Creature creature = makeCreature();
        Weapon weapon = makeWeapon();
        WeaponEfficiency efficiency = makeEfficiency(weapon, creature);
        creatureDao.create(creature);
        weaponDao.create(weapon);
        efficiencyDao.create(efficiency);
        
        efficiencyDao.delete(efficiency);
        WeaponEfficiency result = efficiencyDao.getById(efficiency.getId());
        
        Assert.assertNull(result);
    }
    
    @Test
    public void delete_noSuchEfficiency() {
        Creature creature = makeCreature();
        Weapon weapon = makeWeapon();
        Weapon weapon2 = makeWeapon("weapon2");
        WeaponEfficiency efficiency = makeEfficiency(weapon, creature);
        WeaponEfficiency efficiency2 = makeEfficiency(weapon2, creature);
        creatureDao.create(creature);
        weaponDao.create(weapon);
        weaponDao.create(weapon2);
        efficiencyDao.create(efficiency);
        
        efficiencyDao.delete(efficiency2);
        WeaponEfficiency result = efficiencyDao.getById(efficiency.getId());
        
        Assert.assertEquals(efficiency, result);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void delete_nullArg() {
        efficiencyDao.delete(null);
    }
    
    @Test
    public void update_standard() {
        Creature creature = makeCreature();
        Weapon weapon = makeWeapon();
        WeaponEfficiency efficiency = makeEfficiency(weapon, creature);
        creatureDao.create(creature);
        weaponDao.create(weapon);
        efficiencyDao.create(efficiency);
        
        efficiency.setEfficiency(9);
        efficiencyDao.update(efficiency);
        WeaponEfficiency result = efficiencyDao.getById(efficiency.getId());
        
        Assert.assertEquals(efficiency, result);
    }
    
    @Test
    public void update_noSuchEfficiency() {
        Creature creature = makeCreature();
        Weapon weapon = makeWeapon();
        Weapon weapon2 = makeWeapon("weapon2");
        WeaponEfficiency efficiency = makeEfficiency(weapon, creature);
        WeaponEfficiency efficiency2 = makeEfficiency(weapon2, creature);
        creatureDao.create(creature);
        weaponDao.create(weapon);
        weaponDao.create(weapon2);
        efficiencyDao.create(efficiency);
        
        efficiencyDao.update(efficiency2);
        WeaponEfficiency result = efficiencyDao.getById(efficiency.getId());
        
        Assert.assertEquals(efficiency, result);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void update_nullArg() {
        efficiencyDao.update(null);
    }
    
    @Test
    public void findAll_standard() {
        Creature creature = makeCreature();
        Weapon weapon = makeWeapon();
        Weapon weapon2 = makeWeapon("weapon2");
        WeaponEfficiency efficiency = makeEfficiency(weapon, creature);
        WeaponEfficiency efficiency2 = makeEfficiency(weapon2, creature);
        creatureDao.create(creature);
        weaponDao.create(weapon);
        weaponDao.create(weapon2);
        efficiencyDao.create(efficiency);
        efficiencyDao.create(efficiency2);
        
        List<WeaponEfficiency> result = efficiencyDao.findAll();
        
        Assert.assertEquals(result.size(), 2);
        Assert.assertTrue(result.contains(efficiency));
        Assert.assertTrue(result.contains(efficiency2));
    }
    
    @Test
    public void findAll_empty() {
        List<WeaponEfficiency> result = efficiencyDao.findAll();
        Assert.assertEquals(result.size(), 0);
    }
    
    private WeaponEfficiency makeEfficiency(Weapon weapon, Creature creature) {
        return makeEfficiency(weapon, creature, 5);
    }
    
    private WeaponEfficiency makeEfficiency(Weapon weapon, Creature creature, int effCoeficient) {
        WeaponEfficiency efficiency = new WeaponEfficiency();
        efficiency.setWeapon(weapon);
        efficiency.setCreature(creature);
        efficiency.setEfficiency(effCoeficient);
        return efficiency;
    }
    
    private Weapon makeWeapon() {
        return makeWeapon("weapon");
    }

    private Weapon makeWeapon(String name) {
        Weapon w = new Weapon();
        w.setName(name);
        w.setRange(10);
        w.setType(WeaponType.GUN);
        w.setAmmoType(AmmoType.BULLET_9MM);
        w.setDescription("Test weapon");
        return w;
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
