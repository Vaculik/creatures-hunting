package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.util.EntityMapper;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for all methods of the CreatureFacade interface.
 *
 * @author Karel Vaculik
 */
@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class CreatureFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CreatureService creatureService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private CreatureFacade creatureFacade;
    private Creature creature;
    private CreatureDTO creatureDTO;
    private String name;

    @BeforeMethod
    public void initSingleTest() {
        reset(creatureService, entityMapper);
        name = "testing-creature";
        creature = createCreature(name);
        creatureDTO = createCreatureDTO(creature);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCreatureByIdTest() {
        Long id = 1l;
        creature.setId(id);
        creatureDTO.setId(id);

        when(entityMapper.map(creature, CreatureDTO.class)).thenReturn(creatureDTO);
        when(creatureService.getCreatureById(id)).thenReturn(creature);

        Assert.assertEquals(creatureFacade.getCreatureById(id), creatureDTO);
        Assert.assertNull(creatureFacade.getCreatureById(0l));

        verify(entityMapper).map(creature, CreatureDTO.class);
        verify(creatureService).getCreatureById(id);
    }

    @Test
    public void getCreatureByNameTest() {
        when(entityMapper.map(creature, CreatureDTO.class)).thenReturn(creatureDTO);
        when(creatureService.getCreatureByName(name)).thenReturn(creature);

        Assert.assertEquals(creatureFacade.getCreatureByName(name), creatureDTO);
        Assert.assertNull(creatureFacade.getCreatureByName("wrong-name"));

        verify(entityMapper).map(creature, CreatureDTO.class);
        verify(creatureService).getCreatureByName(name);
    }

    @Test
    public void createCreatureTest() {
        final Long id = 1l;

        when(entityMapper.map(creatureDTO, Creature.class)).thenReturn(creature);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                creature.setId(id);
                return null;
            }
        }).when(creatureService).createCreature(creature);

        Assert.assertEquals(creatureFacade.createCreature(creatureDTO), id);

        verify(entityMapper).map(creatureDTO, Creature.class);
        verify(creatureService).createCreature(creature);
    }

    @Test
    public void deleteCreatureTest() {
        when(entityMapper.map(creatureDTO, Creature.class)).thenReturn(creature);
        doNothing().when(creatureService).deleteCreature(creature);

        creatureFacade.deleteCreature(creatureDTO);
        verify(entityMapper).map(creatureDTO, Creature.class);
        verify(creatureService).deleteCreature(creature);
    }

    @Test
    public void getAllCreaturesTest() {
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();
        creatures.add(creature);
        creatureDTOs.add(creatureDTO);

        when(entityMapper.map(creatures, CreatureDTO.class)).thenReturn(creatureDTOs);
        when(creatureService.findAllCreatures()).thenReturn(creatures);

        Assert.assertEquals(creatureFacade.getAllCreatures(), creatureDTOs);

        verify(entityMapper).map(creatures, CreatureDTO.class);
        verify(creatureService).findAllCreatures();
    }

    @Test
    public void getCreaturesWithMaxWeight() {
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();
        creatures.add(creature);
        creatureDTOs.add(creatureDTO);

        when(entityMapper.map(creatures, CreatureDTO.class)).thenReturn(creatureDTOs);
        when(creatureService.getCreaturesWithMaxWeight()).thenReturn(creatures);

        Assert.assertEquals(creatureFacade.getCreaturesWithMaxWeight(), creatureDTOs);

        verify(entityMapper).map(creatures, CreatureDTO.class);
        verify(creatureService).getCreaturesWithMaxWeight();
    }

    @Test
    public void getCreaturesOfTypeTest() {
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();
        creatures.add(creature);
        creatureDTOs.add(creatureDTO);
        CreatureType type = creature.getType();

        when(entityMapper.map(creatures, CreatureDTO.class)).thenReturn(creatureDTOs);
        when(creatureService.getCreaturesOfType(type)).thenReturn(creatures);

        Assert.assertEquals(creatureFacade.getCreaturesOfType(type), creatureDTOs);

        verify(entityMapper).map(creatures, CreatureDTO.class);
        verify(creatureService).getCreaturesOfType(type);
    }

    @Test
    public void getCreaturesWithMaxHeightTest() {
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();
        creatures.add(creature);
        creatureDTOs.add(creatureDTO);

        when(entityMapper.map(creatures, CreatureDTO.class)).thenReturn(creatureDTOs);
        when(creatureService.getCreaturesWithMaxHeight()).thenReturn(creatures);

        Assert.assertEquals(creatureFacade.getCreaturesWithMaxHeight(), creatureDTOs);

        verify(entityMapper).map(creatures, CreatureDTO.class);
        verify(creatureService).getCreaturesWithMaxHeight();
    }

    @Test
    public void updateAreaTest() {
        doNothing().when(creatureService).updateCreature(creature);
        when(entityMapper.map(creatureDTO, Creature.class)).thenReturn(creature);
        creatureFacade.updateCreature(creatureDTO);
        verify(creatureService).updateCreature(creature);
        verify(entityMapper).map(creatureDTO, Creature.class);
    }
    
    @Test
    public void removeAreaOfCreatureTest() {
        Long id = 1l;
        creature.setId(id);
        creatureDTO.setId(id);
        doNothing().when(creatureService).updateCreature(creature);
        creatureFacade.removeAreaOfCreature(id);
        verify(creatureService).removeAreaOfCreature(id);        
    }
    
    @Test
    public void getCreaturesInNoAreaTest() {
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();
        creatures.add(creature);
        creatureDTOs.add(creatureDTO);

        when(entityMapper.map(creatures, CreatureDTO.class)).thenReturn(creatureDTOs);
        when(creatureService.getCreaturesInNoArea()).thenReturn(creatures);

        Assert.assertEquals(creatureFacade.getCreaturesInNoArea(), creatureDTOs);

        verify(entityMapper).map(creatures, CreatureDTO.class);
        verify(creatureService).getCreaturesInNoArea();
    }
    
    @Test
    public void getAreaOfCreatureTest() {
        Long id = 1l;
        creature.setId(id);
        creatureDTO.setId(id);
        Area ar = createArea();
        AreaDTO arDTO = createAreaDTO(ar);
        ar.addCreature(creature);
        arDTO.addCreature(creatureDTO);
        
        when(entityMapper.map(ar, AreaDTO.class)).thenReturn(arDTO);
        when(creatureService.getCreatureById(id)).thenReturn(creature);

        Assert.assertEquals(creatureFacade.getAreaOfCreature(id), arDTO);
        
        verify(entityMapper).map(ar, AreaDTO.class);
        verify(creatureService).getCreatureById(id);
    }
    
    private Creature createCreature(String name, Integer height, Integer weight, CreatureType type) {
        Creature c = new Creature();
        c.setName(name);
        c.setHeight(height);
        c.setWeight(weight);
        c.setType(type);
        return c;
    }

    private Creature createCreature(String name) {
        return createCreature(name, 1, 1, CreatureType.BEAST);
    }

    private CreatureDTO createCreatureDTO(Creature creature) {
        CreatureDTO creatureDTO = new CreatureDTO();
        creatureDTO.setId(creature.getId());
        creatureDTO.setName(creature.getName());
        creatureDTO.setHeight(creature.getHeight());
        creatureDTO.setWeight(creature.getWeight());
        creatureDTO.setType(creature.getType());
        return creatureDTO;
    }
    
    private Area createArea() {
        Area a = new Area();
        a.setDescription("Desc");
        a.setName("Noname");
        return a;
    }
    
    private AreaDTO createAreaDTO(Area area) {
        AreaDTO a = new AreaDTO();
        a.setDescription(area.getDescription());
        a.setName(area.getName());
        return a;
    }
}
