package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.service.CreatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Karel Vaculik
 */

@Component
@Transactional
public class InitialDataLoaderImpl implements InitialDataLoader {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoaderImpl.class);

    @Autowired
    private CreatureService creatureService;

    @Override
    public void LoadData() {
        logger.debug("Loading data");
        loadCreature("Dracula", 180, 90, CreatureType.VAMPIRE, "Lord of Transylvania.");
        loadCreature("Jacob Black", 175, 80, CreatureType.BEAST, "Werewolf from Twilight.");
        loadCreature("Frankenstein", 190, 100, CreatureType.UNDEAD, "Created from scientific experiment.");
    }


    private void loadCreature(String name, Integer height, Integer weight, CreatureType type, String description) {
        logger.debug("Create creature with name=" + name);
        Creature creature = new Creature();
        creature.setName(name);
        creature.setHeight(height);
        creature.setWeight(weight);
        creature.setType(type);
        creature.setDescription(description);
        creatureService.createCreature(creature);
    }
}
