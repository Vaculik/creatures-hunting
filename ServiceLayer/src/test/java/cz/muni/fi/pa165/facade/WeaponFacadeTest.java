package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Tests all methods of WeaponFacade interface
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class WeaponFacadeTest extends AbstractTestNGSpringContextTests {
   
    //TODO Tests
    
    private WeaponDTO createDefaultWeaponDTO(String name) {
        WeaponDTO w = new WeaponDTO();
        w.setName(name);
        w.setRange(10);
        w.setType(WeaponType.GUN);
        w.setAmmoType(AmmoType.BULLET_9MM);
        w.setDescription("Default weapon");
        return w;
    }
    
    private Weapon createDefaultWeapon(String name) {
        Weapon w = new Weapon();
        w.setName(name);
        w.setRange(10);
        w.setType(WeaponType.GUN);
        w.setAmmoType(AmmoType.BULLET_9MM);
        w.setDescription("Default weapon");
        return w;
    }
}
