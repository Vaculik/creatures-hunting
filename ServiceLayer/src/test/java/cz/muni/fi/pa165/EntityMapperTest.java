package cz.muni.fi.pa165;

import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vaculik on 21.11.15.
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class EntityMapperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    EntityMapper mapper;

    @Test
    public void singleObjectMapTest() {
        Creature creature = createCreature("testing-entity");
        CreatureDTO creatureDTO = createCreatureDTO(creature);

        Assert.assertEquals(mapper.map(creature, CreatureDTO.class), creatureDTO);
        Assert.assertEquals(mapper.map(creatureDTO, Creature.class), creature);
    }

    @Test
    public void collectionMapTest() {
        List<Creature> creatures = new LinkedList<>();
        List<CreatureDTO> creatureDTOs = new LinkedList<>();
        Creature c = createCreature("entity-1");
        creatures.add(c);
        creatureDTOs.add(createCreatureDTO(c));
        c = createCreature("entity-2");
        creatures.add(c);
        creatureDTOs.add(createCreatureDTO(c));

        Assert.assertEquals(mapper.map(creatures, CreatureDTO.class), creatureDTOs);
        Assert.assertEquals(mapper.map(creatureDTOs, Creature.class), creatures);
    }


    private Creature createCreature(String name) {
        Creature c = new Creature();
        c.setId(1l);
        c.setName(name);
        c.setHeight(1);
        c.setWeight(1);
        c.setType(CreatureType.BEAST);
        return c;
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
