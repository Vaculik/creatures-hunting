package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.util.EntityMapper;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests all methods of WeaponFacade interface
 *
 * @author Pavel Vesely <448290@mail.muni.cz>
 */
@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class WeaponFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WeaponFacade weaponFacade;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private EntityMapper entityMapper;
    Weapon weapon;
    WeaponDTO weaponDTO;
    List<Weapon> weapons;
    List<WeaponDTO> dtos;

    @BeforeMethod
    public void init() {
        weapon = createDefaultWeapon("weapon");
        weaponDTO = weaponToWeaponDTO(weapon);
        weapons = new LinkedList<>();
        dtos = new LinkedList<>();
        reset(weaponService, entityMapper);
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
        weaponDTO.setId(id);

        when(weaponService.getWeaponById(id)).thenReturn(weapon);
        when(entityMapper.map(weapon, WeaponDTO.class)).thenReturn(weaponDTO);

        Assert.assertEquals(weaponDTO, weaponFacade.getWeaponById(id));
        Assert.assertNull(weaponFacade.getWeaponById(diffId));

        verify(weaponService).getWeaponById(id);
        verify(entityMapper).map(weapon, WeaponDTO.class);
    }

    @Test
    public void getWeaponByNameTest() {
        String name0 = "weapon0";
        String name1 = "weapon1";
        weapon.setName(name0);
        weaponDTO.setName(name0);

        when(weaponService.getWeaponByName(name0)).thenReturn(weapon);
        when(entityMapper.map(weapon, WeaponDTO.class)).thenReturn(weaponDTO);

        Assert.assertEquals(weaponDTO, weaponFacade.getWeaponByName(name0));
        Assert.assertNull(weaponFacade.getWeaponByName(name1));

        verify(weaponService).getWeaponByName(name0);
        verify(entityMapper).map(weapon, WeaponDTO.class);
    }

    @Test
    public void getAllWeaponsTest() {
        Weapon weapon1 = createDefaultWeapon("weapon1");
        WeaponDTO weaponDTO1 = weaponToWeaponDTO(weapon1);
        weapons.add(weapon);
        weapons.add(weapon1);
        dtos.add(weaponDTO);
        dtos.add(weaponDTO1);

        when(weaponService.getAllWeapons()).thenReturn(weapons);
        when(entityMapper.map(weapons, WeaponDTO.class)).thenReturn(dtos);

        Assert.assertEquals(dtos, weaponFacade.getAllWeapons());

        verify(weaponService).getAllWeapons();
        verify(entityMapper).map(weapons, WeaponDTO.class);
    }

    @Test
    public void createWeaponTest() {
        final Long id = 0l;
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                weapon.setId(id);
                return null;
            }
        }).when(weaponService).createWeapon(weapon);
        when(entityMapper.map(weaponDTO, Weapon.class)).thenReturn(weapon);

        Assert.assertEquals(id, weaponFacade.createWeapon(weaponDTO));

        verify(weaponService).createWeapon(weapon);
        verify(entityMapper).map(weaponDTO, Weapon.class);
    }

    @Test
    public void updateWeaponTest() {
        doNothing().when(weaponService).updateWeapon(weapon);
        when(entityMapper.map(weaponDTO, Weapon.class)).thenReturn(weapon);

        weaponFacade.updateWeapon(weaponDTO);

        verify(weaponService).updateWeapon(weapon);
        verify(entityMapper).map(weaponDTO, Weapon.class);
    }

    @Test
    public void deleteWeaponTest() {
        doNothing().when(weaponService).deleteWeapon(weapon);
        when(entityMapper.map(weaponDTO, Weapon.class)).thenReturn(weapon);

        weaponFacade.deleteWeapon(weaponDTO);

        verify(weaponService).deleteWeapon(weapon);
        verify(entityMapper).map(weaponDTO, Weapon.class);
    }

    @Test
    public void getWeaponsOfTypeTest() {
        Weapon weapon1 = createDefaultWeapon("weapon1");
        WeaponDTO weaponDTO1 = weaponToWeaponDTO(weapon1);
        weapons.add(weapon);
        weapons.add(weapon1);
        dtos.add(weaponDTO);
        dtos.add(weaponDTO1);

        when(weaponService.getWeaponsOfType(WeaponType.GUN)).thenReturn(weapons);
        when(entityMapper.map(weapons, WeaponDTO.class)).thenReturn(dtos);

        Assert.assertEquals(dtos, weaponFacade.getWeaponsOfType(WeaponType.GUN));

        verify(weaponService).getWeaponsOfType(WeaponType.GUN);
        verify(entityMapper).map(weapons, WeaponDTO.class);
    }

    @Test
    public void getWeaponsOfAmmoTypeTest() {
        Weapon weapon1 = createDefaultWeapon("weapon1");
        WeaponDTO weaponDTO1 = weaponToWeaponDTO(weapon1);
        weapons.add(weapon);
        weapons.add(weapon1);
        dtos.add(weaponDTO);
        dtos.add(weaponDTO1);

        when(weaponService.getWeaponsOfAmmoType(AmmoType.BULLET_9MM)).thenReturn(weapons);
        when(entityMapper.map(weapons, WeaponDTO.class)).thenReturn(dtos);

        Assert.assertEquals(dtos, weaponFacade.getWeaponsOfAmmoType(AmmoType.BULLET_9MM));

        verify(weaponService).getWeaponsOfAmmoType(AmmoType.BULLET_9MM);
        verify(entityMapper).map(weapons, WeaponDTO.class);
    }

    @Test
    public void getWeaponsOfRangeTest() {
        Weapon weapon1 = createDefaultWeapon("weapon1");
        WeaponDTO weaponDTO1 = weaponToWeaponDTO(weapon1);
        weapons.add(weapon);
        weapons.add(weapon1);
        dtos.add(weaponDTO);
        dtos.add(weaponDTO1);

        when(weaponService.getWeaponsOfRange(100, 300)).thenReturn(weapons);
        when(entityMapper.map(weapons, WeaponDTO.class)).thenReturn(dtos);

        Assert.assertEquals(dtos, weaponFacade.getWeaponsOfRange(100, 300));

        verify(weaponService).getWeaponsOfRange(100, 300);
        verify(entityMapper).map(weapons, WeaponDTO.class);
    }

    private WeaponDTO weaponToWeaponDTO(Weapon weapon) {
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
