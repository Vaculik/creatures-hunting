package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CreatureDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The implementation of the CreatureService interface.
 *
 * @author Karel Vaculik
 */
@Service
public class CreatureServiceImpl implements CreatureService {

    @Autowired
    private CreatureDao creatureDao;

    @Override
    public Creature getCreatureById(Long id) {
        return creatureDao.getById(id);
    }

    @Override
    public Creature getCreatureByName(String name) {
        return creatureDao.getByName(name);
    }

    @Override
    public void createCreature(Creature creature) {
        System.out.println("Create creature with name=" + creature.getName());
        creatureDao.create(creature);
    }



    @Override
    public void deleteCreature(Creature creature) {
        creatureDao.delete(creature);
    }

    @Override
    public void updateCreature(Creature creature) {
        creatureDao.update(creature);
    }

    @Override
    public List<Creature> findAllCreatures() {
        return creatureDao.findAll();
    }

    @Override
    public List<Creature> getCreaturesOfType(CreatureType type) {
        List<Creature> results = creatureDao.findAll()
                .stream()
                .filter(c -> c.getType() == type)
                .collect(Collectors.toList());
        return results;
    }

    @Override
    public List<Creature> getCreaturesWithMaxHeight() {
        List<Creature> results = new LinkedList<>();
        final Integer[] maxHeight = {Integer.MIN_VALUE};

        creatureDao.findAll()
                .stream()
                .filter(c -> c.getHeight() != null)
                .forEach(c -> {
                    Integer height = c.getHeight();
                    if (height > maxHeight[0]) {
                        maxHeight[0] = height;
                        results.clear();
                        results.add(c);
                    } else if (height.equals(maxHeight[0])) {
                        results.add(c);
                    }
                });
        return results;
    }

    @Override
    public List<Creature> getCreaturesWithMaxWeight() {
        List<Creature> results = new LinkedList<>();
        final Integer[] maxWeight = {Integer.MIN_VALUE};

        creatureDao.findAll()
                .stream()
                .filter(c -> c.getWeight() != null)
                .forEach(c -> {
                    Integer weight = c.getWeight();
                    if (weight > maxWeight[0]) {
                        maxWeight[0] = weight;
                        results.clear();
                        results.add(c);
                    } else if (weight.equals(maxWeight[0])) {
                        results.add(c);
                    }
                });
        return results;
    }

    @Override
    public List<Creature> getCreaturesInNoArea() {
        return creatureDao.findAll()
                .stream()
                .filter(c -> c.getArea() == null)
                .collect(Collectors.toList());
    }
}
