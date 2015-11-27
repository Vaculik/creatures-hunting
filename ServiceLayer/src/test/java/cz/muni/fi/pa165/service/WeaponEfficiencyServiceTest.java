package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.WeaponEfficiencyDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
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
 * Tests for all methods of the WeaponEfficiencyService interface.
 *
 * @author Karel Vaculik
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class WeaponEfficiencyServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private WeaponEfficiencyDao weaponEfficiencyDao;
    @Autowired
    @InjectMocks
    private WeaponEfficiencyService weaponEfficiencyService;
    private WeaponEfficiency weaponEfficiency;
    private List<WeaponEfficiency> weaponEfficiencies;

    @BeforeMethod
    public void initSingleTest() {
        reset(weaponEfficiencyDao);
        weaponEfficiency = createWeaponEfficiency(1);
        weaponEfficiencies = new LinkedList<>();
    }

    @BeforeClass
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWeaponEfficiencyByIdTest() {
        Long id = 1l;
        Long wrongId = 0l;
        weaponEfficiency.setId(id);

        when(weaponEfficiencyDao.getById(id)).thenReturn(weaponEfficiency);

        Assert.assertEquals(weaponEfficiencyService.getWeaponEfficiencyById(id), weaponEfficiency);
        Assert.assertNull(weaponEfficiencyService.getWeaponEfficiencyById(wrongId));

        verify(weaponEfficiencyDao).getById(id);
        verify(weaponEfficiencyDao).getById(wrongId);
    }

    @Test
    public void createWeaponEfficiencyTest() {
        doNothing().when(weaponEfficiencyDao).create(weaponEfficiency);

        weaponEfficiencyService.createWeaponEfficiency(weaponEfficiency);
        verify(weaponEfficiencyDao).create(weaponEfficiency);
    }

    @Test
    public void deleteWeaponEfficiencyTest() {
        doNothing().when(weaponEfficiencyDao).delete(weaponEfficiency);

        weaponEfficiencyService.deleteWeaponEfficiency(weaponEfficiency);
        verify(weaponEfficiencyDao).delete(weaponEfficiency);
    }

    @Test
    public void findAllWeaponEficienciesTest() {
        weaponEfficiencies.add(weaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<WeaponEfficiency> actual = weaponEfficiencyService.findAllWeaponEfficiencies();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual, weaponEfficiencies);

        verify(weaponEfficiencyDao).findAll();
    }

    @Test
    public void findMostEffectiveWeaponsAtCreatureNullCreatureTest() {
        weaponEfficiencies.add(weaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<Weapon> actual = weaponEfficiencyService.findMostEffectiveWeaponsAtCreature(null);
        Assert.assertEquals(actual.size(), 0);
    }

    @Test
    public void findMostEffectiveWeaponsAtCreatureEmptyResultsTest() {
        Creature creature = new Creature();
        Creature inputCreature = new Creature();
        creature.setName("different-creature");
        inputCreature.setName("input-creature");
        weaponEfficiency.setCreature(creature);
        weaponEfficiencies.add(weaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<Weapon> actual = weaponEfficiencyService.findMostEffectiveWeaponsAtCreature(inputCreature);
        Assert.assertEquals(actual.size(), 0);
        verify(weaponEfficiencyDao).findAll();
    }

    @Test
    public void findMostEffectiveWeaponsAtCreatureTest() {
        Creature creature = new Creature();
        creature.setName("weapon-eff-creature");
        Weapon resultWeapon1 = new Weapon();
        resultWeapon1.setName("result-weapon1");
        Weapon resultWeapon2 = new Weapon();
        resultWeapon2.setName("result-weapon2");
        Weapon weapon = new Weapon();
        weapon.setName("not-efficient-weapon");

        WeaponEfficiency matchingWeaponEfficiency1 = new WeaponEfficiency();
        matchingWeaponEfficiency1.setEfficiency(2);
        matchingWeaponEfficiency1.setCreature(creature);
        matchingWeaponEfficiency1.setWeapon(resultWeapon1);

        WeaponEfficiency matchingWeaponEfficiency2 = new WeaponEfficiency();
        matchingWeaponEfficiency2.setEfficiency(2);
        matchingWeaponEfficiency2.setCreature(creature);
        matchingWeaponEfficiency2.setWeapon(resultWeapon2);

        WeaponEfficiency weakWeaponEfficiency = new WeaponEfficiency();
        weakWeaponEfficiency.setEfficiency(1);
        weakWeaponEfficiency.setCreature(creature);
        weakWeaponEfficiency.setWeapon(weapon);

        weaponEfficiencies.add(weaponEfficiency);
        weaponEfficiencies.add(matchingWeaponEfficiency1);
        weaponEfficiencies.add(matchingWeaponEfficiency2);
        weaponEfficiencies.add(weakWeaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<Weapon> actual = weaponEfficiencyService.findMostEffectiveWeaponsAtCreature(creature);
        List<Weapon> expected = new LinkedList<>();
        expected.add(resultWeapon1);
        expected.add(resultWeapon2);

        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual, expected);
        verify(weaponEfficiencyDao).findAll();
    }

    @Test
    public void findMostVulnerableCreaturesToWeaponNullWeaponTest() {
        weaponEfficiencies.add(weaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<Creature> actual = weaponEfficiencyService.findMostVulnerableCreaturesToWeapon(null);
        Assert.assertEquals(actual.size(), 0);
    }

    @Test
    public void findMostVulnerableCreatureToWeaponEmptyResultsTest() {
        Weapon weapon = new Weapon();
        Weapon inputWeapon = new Weapon();
        weapon.setName("different-weapon");
        inputWeapon.setName("input-weapon");
        weaponEfficiency.setWeapon(weapon);
        weaponEfficiencies.add(weaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<Creature> actual = weaponEfficiencyService.findMostVulnerableCreaturesToWeapon(inputWeapon);
        Assert.assertEquals(actual.size(), 0);
        verify(weaponEfficiencyDao).findAll();
    }

    @Test
    public void findMostVulnerableCreaturesToWeaponTest() {
        Creature vulnerableCreature1 = new Creature();
        vulnerableCreature1.setName("vulnerable-creature1");
        Creature vulnerableCreature2 = new Creature();
        vulnerableCreature2.setName("vulnerable-creature2");
        Creature resilientCreature = new Creature();
        resilientCreature.setName("resilient-creature");
        Weapon weapon = new Weapon();
        weapon.setName("input-weapon");

        WeaponEfficiency matchingWeaponEfficiency1 = new WeaponEfficiency();
        matchingWeaponEfficiency1.setEfficiency(2);
        matchingWeaponEfficiency1.setCreature(vulnerableCreature1);
        matchingWeaponEfficiency1.setWeapon(weapon);

        WeaponEfficiency matchingWeaponEfficiency2 = new WeaponEfficiency();
        matchingWeaponEfficiency2.setEfficiency(2);
        matchingWeaponEfficiency2.setCreature(vulnerableCreature2);
        matchingWeaponEfficiency2.setWeapon(weapon);

        WeaponEfficiency weakWeaponEfficiency = new WeaponEfficiency();
        weakWeaponEfficiency.setEfficiency(1);
        weakWeaponEfficiency.setCreature(resilientCreature);
        weakWeaponEfficiency.setWeapon(weapon);

        weaponEfficiencies.add(weaponEfficiency);
        weaponEfficiencies.add(matchingWeaponEfficiency1);
        weaponEfficiencies.add(matchingWeaponEfficiency2);
        weaponEfficiencies.add(weakWeaponEfficiency);

        when(weaponEfficiencyDao.findAll()).thenReturn(weaponEfficiencies);

        List<Creature> actual = weaponEfficiencyService.findMostVulnerableCreaturesToWeapon(weapon);
        List<Creature> expected = new LinkedList<>();
        expected.add(vulnerableCreature1);
        expected.add(vulnerableCreature2);

        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual, expected);
        verify(weaponEfficiencyDao).findAll();
    }

    private WeaponEfficiency createWeaponEfficiency(Integer efficiency) {
        WeaponEfficiency weaponEfficiency = new WeaponEfficiency();
        weaponEfficiency.setEfficiency(efficiency);
        weaponEfficiency.setWeapon(new Weapon());
        weaponEfficiency.setCreature(new Creature());
        return weaponEfficiency;
    }
}
