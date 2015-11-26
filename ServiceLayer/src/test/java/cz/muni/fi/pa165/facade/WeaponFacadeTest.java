package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.util.EntityMapper;
import java.util.LinkedList;
import java.util.List;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Tests all methods of WeaponFacade interface
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class WeaponFacadeTest extends AbstractTestNGSpringContextTests {
   
    @Autowired
    private WeaponFacade weaponFacade;
    
    @Autowired
    private WeaponService weaponService;
    
    @Autowired
    private EntityMapper entityMapper;
    
    List<Weapon> weapons;
    List<Weapon> actual;
    List<Weapon> expected;

    @BeforeMethod
    public void init() {
        weapons = new LinkedList<>();
        actual = new LinkedList<>();
        expected = new LinkedList<>();
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    //TODO Tests
    
    private WeaponDTO createDefaultWeaponDTO(Weapon weapon) {
        WeaponDTO w = new WeaponDTO();
        w.setName(weapon.getName());
        w.setRange(weapon.getRange());
        w.setType(weapon.getType());
        w.setAmmoType(weapon.getAmmotype());
        w.setDescription(weapon.getDescription());
        return w;
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
