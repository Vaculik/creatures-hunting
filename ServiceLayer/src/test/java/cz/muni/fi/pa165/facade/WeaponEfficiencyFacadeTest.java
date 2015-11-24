package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyCreateDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.WeaponEfficiencyService;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.util.EntityMapper;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for all methods of the WeaponEfficiencyFacade interface.
 *
 * @author Karel Vaculik
 */
@PrepareForTest(WeaponEfficiencyFacade.class)
@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class WeaponEfficiencyFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private WeaponEfficiencyService weaponEfficiencyService;

    @Autowired
    private CreatureService creatureService;

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private WeaponEfficiencyFacade weaponEfficiencyFacade;

    private WeaponEfficiency weaponEfficiency;
    private WeaponEfficiencyDTO weaponEfficiencyDTO;

    @BeforeMethod
    public void initSingleTest() {
        reset(weaponEfficiencyService, creatureService, weaponService, entityMapper);
        weaponEfficiency = createWeaponEfficiency();
        weaponEfficiencyDTO = createWeaponEfficiencyDTO(weaponEfficiency);
    }

    @BeforeClass
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

//    Doens't work and don't know why, probably because of the application contexts

//    @ObjectFactory
//    public IObjectFactory getObjectFactory() {
//        return new org.powermock.modules.testng.PowerMockObjectFactory();
//    }

    @Test
    public void getWeaponEfficiencyByIdTest() {
        Long id = 1l;
        weaponEfficiency.setId(id);
        weaponEfficiencyDTO.setId(id);

        when(entityMapper.map(weaponEfficiency, WeaponEfficiencyDTO.class)).thenReturn(weaponEfficiencyDTO);
        when(weaponEfficiencyService.getWeaponEfficiencyById(id)).thenReturn(weaponEfficiency);

        Assert.assertEquals(weaponEfficiencyFacade.getWeaponEfficiencyById(id), weaponEfficiencyDTO);
        Assert.assertNull(weaponEfficiencyFacade.getWeaponEfficiencyById(0l));

        verify(entityMapper).map(weaponEfficiency, WeaponEfficiencyDTO.class);
        verify(weaponEfficiencyService).getWeaponEfficiencyById(id);
    }

    @Test
    public void createWeaponEfficiencyTest() {
        Long creatureId = 1l;
        Long weaponId = 1l;

        Weapon weapon = new Weapon();
        weapon.setId(weaponId);
        Creature creature = new Creature();
        creature.setId(creatureId);

        WeaponEfficiencyCreateDTO weaponEfficiencyCreateDTO = new WeaponEfficiencyCreateDTO();
        weaponEfficiencyCreateDTO.setEfficiency(1);
        weaponEfficiencyCreateDTO.setCreatureId(creatureId);
        weaponEfficiencyCreateDTO.setWeaponId(weaponId);

        when(creatureService.getCreatureById(creatureId)).thenReturn(creature);
        when(weaponService.getWeaponById(weaponId)).thenReturn(weapon);
        doNothing().when(weaponEfficiencyService).createWeaponEfficiency(any(WeaponEfficiency.class));

        weaponEfficiencyFacade.createWeaponEfficiency(weaponEfficiencyCreateDTO);

        verify(creatureService).getCreatureById(creatureId);
        verify(weaponService).getWeaponById(weaponId);
        verify(weaponEfficiencyService).createWeaponEfficiency(any(WeaponEfficiency.class));
    }


//    PowerMock version of a test for creation a new WeaponEfficiency object

//    @Test
//    public void createWeaponEfficiencyTest() {
//        final Long id = 1l;
//        Long creatureId = 1l;
//        Long weaponId = 1l;
//        final WeaponEfficiency newWeaponEfficiency = new WeaponEfficiency();
//
//        WeaponEfficiencyCreateDTO weaponEfficiencyCreateDTO = new WeaponEfficiencyCreateDTO();
//        weaponEfficiencyCreateDTO.setEfficiency(1);
//        weaponEfficiencyCreateDTO.setCreatureId(creatureId);
//        weaponEfficiencyCreateDTO.setWeaponId(weaponId);
//
//        Weapon weapon = new Weapon();
//        weapon.setId(weaponId);
//        Creature creature = new Creature();
//        creature.setId(creatureId);
//
////        newWeaponEfficiency.setWeapon(weapon);
////        newWeaponEfficiency.setCreature(creature);
//
//        when(creatureService.getCreatureById(creatureId)).thenReturn(creature);
//        when(weaponService.getWeaponById(weaponId)).thenReturn(weapon);
//        // TODO: resolve problem of mocking creation of new WeaponEfficiency
//        try {
//            PowerMockito.whenNew(WeaponEfficiency.class).withNoArguments().thenReturn(newWeaponEfficiency);
//        } catch (Exception e) {
//            Assert.fail("Exception thrown when trying to mock creation of new WeaponEfficiency object with no arguments.");
//        }
//        doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
//                newWeaponEfficiency.setId(id);
//                return null;
//            }
//        }).when(weaponEfficiencyService).createWeaponEfficiency(newWeaponEfficiency);
//
//        weaponEfficiencyFacade.createWeaponEfficiency(weaponEfficiencyCreateDTO);
//        //Assert.assertEquals(weaponEfficiencyFacade.createWeaponEfficiency(weaponEfficiencyCreateDTO), id);
//
//        verify(creatureService).getCreatureById(creatureId);
//        verify(weaponService).getWeaponById(weaponId);
//        verify(weaponEfficiencyService).createWeaponEfficiency(any(WeaponEfficiency.class));
//        try {
//            PowerMockito.verifyNew(WeaponEfficiency.class).withNoArguments();
//        } catch (Exception e) {
//            Assert.fail("Exception thrown when verifying creation of new WeaponEfficiency object with no arguments.");
//        }
//    }

    @Test
    public void deleteWeaponEfficiencyTest() {
        when(entityMapper.map(weaponEfficiencyDTO, WeaponEfficiency.class)).thenReturn(weaponEfficiency);
        doNothing().when(weaponEfficiencyService).deleteWeaponEfficiency(weaponEfficiency);

        weaponEfficiencyFacade.deleteWeaponEfficiency(weaponEfficiencyDTO);
        verify(entityMapper).map(weaponEfficiencyDTO, WeaponEfficiency.class);
        verify(weaponEfficiencyService).deleteWeaponEfficiency(weaponEfficiency);
    }

    @Test
    public void findAllWeaponEfficienciesTest() {
        List<WeaponEfficiency> weaponEfficiencies = new LinkedList<>();
        List<WeaponEfficiencyDTO> weaponEfficiencyDTOs = new LinkedList<>();
        weaponEfficiencies.add(weaponEfficiency);
        weaponEfficiencyDTOs.add(weaponEfficiencyDTO);

        when(entityMapper.map(weaponEfficiencies, WeaponEfficiencyDTO.class)).thenReturn(weaponEfficiencyDTOs);
        when(weaponEfficiencyService.findAllWeaponEfficiencies()).thenReturn(weaponEfficiencies);

        Assert.assertEquals(weaponEfficiencyFacade.findAllWeaponEfficiencies(), weaponEfficiencyDTOs);

        verify(entityMapper).map(weaponEfficiencies, WeaponEfficiencyDTO.class);
        verify(weaponEfficiencyService).findAllWeaponEfficiencies();
    }

    @Test
    public void findMostEffectiveWeaponsAtCreatureTest() {
        CreatureDTO creatureDTO = new CreatureDTO();
        Creature creature = new Creature();
        List<Weapon> weapons = new LinkedList<>();
        List<WeaponDTO> weaponDTOs = new LinkedList<>();

        when(entityMapper.map(creatureDTO, Creature.class)).thenReturn(creature);
        when(entityMapper.map(weapons, WeaponDTO.class)).thenReturn(weaponDTOs);
        when(weaponEfficiencyService.findMostEffectiveWeaponsAtCreature(creature)).thenReturn(weapons);

        Assert.assertEquals(weaponEfficiencyFacade.findMostEffectiveWeaponsAtCreature(creatureDTO), weaponDTOs);

        verify(entityMapper).map(creatureDTO, Creature.class);
        verify(entityMapper).map(weapons, WeaponDTO.class);
        verify(weaponEfficiencyService).findMostEffectiveWeaponsAtCreature(creature);
    }

    @Test
    public void findMostVulnerableCreaturesToWeapon() {
        WeaponDTO weaponDTO = new WeaponDTO();
        Weapon weapon = new Weapon();
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();

        when(entityMapper.map(weaponDTO, Weapon.class)).thenReturn(weapon);
        when(entityMapper.map(creatures, CreatureDTO.class)).thenReturn(creatureDTOs);
        when(weaponEfficiencyService.findMostVulnerableCreaturesToWeapon(weapon)).thenReturn(creatures);

        Assert.assertEquals(weaponEfficiencyFacade.findMostVulnerableCreaturesToWeapon(weaponDTO), creatureDTOs);

        verify(entityMapper).map(weaponDTO, Weapon.class);
        verify(entityMapper).map(creatures, CreatureDTO.class);
        verify(weaponEfficiencyService).findMostVulnerableCreaturesToWeapon(weapon);
    }

    private WeaponEfficiency createWeaponEfficiency() {
        WeaponEfficiency eff = new WeaponEfficiency();
        eff.setEfficiency(1);
        eff.setCreature(new Creature());
        eff.setWeapon(new Weapon());
        return eff;
    }

    private WeaponEfficiencyDTO createWeaponEfficiencyDTO(WeaponEfficiency weaponEfficiency) {
        WeaponEfficiencyDTO effDTO = new WeaponEfficiencyDTO();
        effDTO.setEfficiency(weaponEfficiency.getEfficiency());
        effDTO.setCreature(new CreatureDTO());
        effDTO.setWeapon(new WeaponDTO());
        return effDTO;
    }
}
