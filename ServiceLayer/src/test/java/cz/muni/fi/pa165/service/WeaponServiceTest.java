package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.WeaponDao;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests all methods of WeaponService interface
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class WeaponServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private WeaponDao weaponDao;

    @Autowired
    @InjectMocks
    private WeaponService weaponService;

    List<Weapon> weapons;
    List<Weapon> actual;
    List<Weapon> expected;

    @BeforeMethod
    public void initTest() {
        weapons = new LinkedList<>();
        actual = new LinkedList<>();
        expected = new LinkedList<>();
        when(weaponDao.findAll()).thenReturn(weapons);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    //TODO test other methods
    
    @Test
    public void getWeaponsOfTypeTest() {
        // Initialization
        Weapon pistol = createWeapon("pistol", WeaponType.GUN, 200, AmmoType.BULLET_9MM);
        Weapon rifle = createWeapon("rifle", WeaponType.GUN, 500, AmmoType.BULLET_NATO);
        Weapon laser = createWeapon("laser", WeaponType.ENERGY, 300, AmmoType.BATTERY);
        weapons.add(pistol);
        weapons.add(rifle);
        weapons.add(laser);

        // Test Gun type
        actual = weaponService.getWeaponsOfType(WeaponType.GUN);
        expected.clear();
        expected.add(pistol);
        expected.add(rifle);
        Assert.assertEquals(actual, expected);
        
        // Test Energy type
        actual = weaponService.getWeaponsOfType(WeaponType.ENERGY);
        expected.clear();
        expected.add(laser);
        Assert.assertEquals(actual, expected);
        
        // Test Explosive type - empty
        actual = weaponService.getWeaponsOfType(WeaponType.EXPLOSIVE);
        expected.clear();
        Assert.assertEquals(actual, expected);
    }

    private Weapon createDefaultWeapon(String name) {
        Weapon w = new Weapon();
        w.setName(name);
        w.setRange(10);
        w.setType(WeaponType.GUN);
        w.setAmmoType(AmmoType.BULLET_9MM);
        w.setDescription("Default testing weapon");
        return w;
    }

    private Weapon createWeapon(String name, WeaponType type, int range, AmmoType ammo) {
        Weapon w = new Weapon();
        w.setName(name);
        w.setRange(range);
        w.setType(type);
        w.setAmmoType(ammo);
        w.setDescription("Default testing weapon");
        return w;
    }
}
