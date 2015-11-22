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
    private List<WeaponEfficiency> expected;
    private List<WeaponEfficiency> actual;


    @BeforeMethod
    public void initSingleTest() {
        reset(weaponEfficiencyDao);
        weaponEfficiency = createWeaponEfficiency(1);
        weaponEfficiencies = new LinkedList<>();
        expected = new LinkedList<>();
    }

    @BeforeClass
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWeaponEfficiencyByIdTest() {
        Long id = 1l;
        weaponEfficiency.setId(id);

        when(weaponEfficiencyDao.getById(id)).thenReturn(weaponEfficiency);

        Assert.assertEquals(weaponEfficiencyService.getWeaponEfficiencyById(id), weaponEfficiency);
        Assert.assertNull(weaponEfficiencyService.getWeaponEfficiencyById(0l));

        verify(weaponEfficiencyDao).getById(id);
    }

    @Test
    public void createWeaponEfficiencyTest() {
        doNothing().when(weaponEfficiencyDao).create(weaponEfficiency);

        weaponEfficiencyService.createWeaponEfficiency(weaponEfficiency);
        verify(weaponEfficiencyDao).create(weaponEfficiency);
    }

    @Test
    public void updateWeaponEfficiencyTest() {
        // TODO
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

        actual = weaponEfficiencyService.findAllWeaponEfficiencies();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual, weaponEfficiencies);

        verify(weaponEfficiencyDao).findAll();
    }

    @Test
    public void findMostEffectiveWeaponsAtCreatureTest() {
        // TODO
    }

    @Test
    public void findMostVulnerableCreaturesToWeaponTest() {
        // TODO
    }

    private WeaponEfficiency createWeaponEfficiency(Integer efficiency) {
        WeaponEfficiency weaponEfficiency = new WeaponEfficiency();
        weaponEfficiency.setEfficiency(efficiency);
        weaponEfficiency.setWeapon(new Weapon());
        weaponEfficiency.setCreature(new Creature());
        return weaponEfficiency;
    }
}
