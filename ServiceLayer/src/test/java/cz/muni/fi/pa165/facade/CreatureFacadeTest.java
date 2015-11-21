package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.service.CreatureService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * Created by vaculik on 21.11.15.
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class CreatureFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CreatureService creatureService;

    @Autowired
    @InjectMocks
    private CreatureFacade creatureFacade;


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCreatureByIdTest() {
        Creature creature = createCreature("testing-creature");
        Long id = 1l;
        creature.setId(id);
        when(creatureService.getCreatureById(id)).thenReturn(creature);

        System.out.println(creatureService.getCreatureById(id));

        CreatureDTO expected = createCreatureDTO(creature);
        Assert.assertEquals(creatureFacade.getCreatureById(id), expected);

        Assert.assertNull(creatureFacade.getCreatureById(0l));
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
}
