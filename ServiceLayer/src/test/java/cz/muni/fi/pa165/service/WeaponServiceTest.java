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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
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
 * @author Pavel Vesely <448290@mail.muni.cz>
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class WeaponServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private WeaponDao weaponDao;
    @Autowired
    @InjectMocks
    private WeaponService weaponService;
    Weapon weapon;
    List<Weapon> weapons;
    List<Weapon> actual;
    List<Weapon> expected;

    @BeforeMethod
    public void init() {
        weapon = createDefaultWeapon("weapon");
        weapons = new LinkedList<>();
        actual = new LinkedList<>();
        expected = new LinkedList<>();
        reset(weaponDao);
        when(weaponDao.findAll()).thenReturn(weapons);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWeaponByIdTest() {
        Long id = 0l;
        Long diffId = 10l;
        weapon.setId(id);

        when(weaponDao.getById(id)).thenReturn(weapon);

        Assert.assertEquals(weaponService.getWeaponById(id), weapon);
        Assert.assertNull(weaponService.getWeaponById(diffId));

        verify(weaponDao).getById(id);
        verify(weaponDao).getById(diffId);
    }

    @Test
    public void getWeaponByNameTest() {
        String name0 = "weapon0";
        String name1 = "weapon1";
        weapon = createDefaultWeapon(name0);
        weapons.add(weapon);

        when(weaponDao.getByName(name0)).thenReturn(weapon);

        Assert.assertEquals(weaponService.getWeaponByName(name0), weapon);
        Assert.assertNull(weaponService.getWeaponByName(name1));

        verify(weaponDao).getByName(name0);
        verify(weaponDao).getByName(name1);
    }

    @Test
    public void getAllWeaponsTest() {
        Weapon weapon0 = createDefaultWeapon("weapon0");
        Weapon weapon1 = createDefaultWeapon("weapon1");
        Weapon weapon2 = createDefaultWeapon("weapon2");
        weapons.add(weapon0);
        weapons.add(weapon1);
        weapons.add(weapon2);

        Assert.assertEquals(weaponService.getAllWeapons(), weapons);

        verify(weaponDao).findAll();
    }

    @Test
    public void createWeaponTest() {
        doNothing().when(weaponDao).create(weapon);

        weaponService.createWeapon(weapon);
        verify(weaponDao).create(weapon);
    }

    @Test
    public void updateWeaponTest() {
        doNothing().when(weaponDao).update(weapon);

        weaponService.updateWeapon(weapon);

        verify(weaponDao).update(weapon);
    }

    @Test
    public void deleteWeaponTest() {
        doNothing().when(weaponDao).delete(weapon);

        weaponService.deleteWeapon(weapon);

        verify(weaponDao).delete(weapon);
    }

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

    @Test
    public void getWeaponsOfAmmoTypeTest() {
        // Initialization
        Weapon pistol0 = createWeapon("pistol0", WeaponType.GUN, 200, AmmoType.BULLET_9MM);
        Weapon pistol1 = createWeapon("pistol1", WeaponType.GUN, 200, AmmoType.BULLET_9MM);
        Weapon rifle = createWeapon("rifle", WeaponType.GUN, 500, AmmoType.BULLET_NATO);
        weapons.add(pistol0);
        weapons.add(pistol1);
        weapons.add(rifle);

        // Test BULLET_9MM type
        actual = weaponService.getWeaponsOfAmmoType(AmmoType.BULLET_9MM);
        expected.clear();
        expected.add(pistol0);
        expected.add(pistol1);
        Assert.assertEquals(actual, expected);

        // Test BULLET_NATO type
        actual = weaponService.getWeaponsOfAmmoType(AmmoType.BULLET_NATO);
        expected.clear();
        expected.add(rifle);
        Assert.assertEquals(actual, expected);

        // Test BATTERY type - empty
        actual = weaponService.getWeaponsOfAmmoType(AmmoType.BATTERY);
        expected.clear();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getWeaponsOfRange() {
        // Initialization
        Weapon pistol0 = createWeapon("pistol0", WeaponType.GUN, 200, AmmoType.BULLET_9MM);
        Weapon pistol1 = createWeapon("pistol1", WeaponType.GUN, 200, AmmoType.BULLET_9MM);
        Weapon rifle = createWeapon("rifle", WeaponType.GUN, 500, AmmoType.BULLET_NATO);
        Weapon laser = createWeapon("laser", WeaponType.ENERGY, 300, AmmoType.BATTERY);
        weapons.add(pistol0);
        weapons.add(pistol1);
        weapons.add(rifle);
        weapons.add(laser);

        // Test 0-400
        actual = weaponService.getWeaponsOfRange(0, 400);
        expected.clear();
        expected.add(pistol0);
        expected.add(pistol1);
        expected.add(laser);
        Assert.assertEquals(actual, expected);

        // Test 250-400
        actual = weaponService.getWeaponsOfRange(250, 400);
        expected.clear();
        expected.add(laser);
        Assert.assertEquals(actual, expected);

        // Test 550+ - empty
        actual = weaponService.getWeaponsOfRange(550, Integer.MAX_VALUE);
        expected.clear();
        Assert.assertEquals(actual, expected);
    }

    private Weapon createDefaultWeapon(String name) {
        return createWeapon(name, WeaponType.GUN, 200, AmmoType.BULLET_9MM);
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
