package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.WeaponEfficiencyDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The implementation of the WeaponEfficiencyService interface.
 *
 * @author Karel Vaculik
 */
@Service
public class WeaponEfficiencyServiceImpl implements WeaponEfficiencyService {

    @Autowired
    private WeaponEfficiencyDao weaponEfficiencyDao;

    @Override
    public WeaponEfficiency getWeaponEfficiencyById(Long id) {
        return weaponEfficiencyDao.getById(id);
    }

    @Override
    public void createWeaponEfficiency(WeaponEfficiency weaponEfficiency) {
        weaponEfficiencyDao.create(weaponEfficiency);
    }

    @Override
    public void deleteWeaponEfficiency(WeaponEfficiency weaponEfficiency) {
        weaponEfficiencyDao.delete(weaponEfficiency);
    }

    @Override
    public void updateWeaponEfficiency(WeaponEfficiency weaponEfficiency) {
        weaponEfficiencyDao.update(weaponEfficiency);
    }

    @Override
    public List<WeaponEfficiency> findAllWeaponEfficiencies() {
        return weaponEfficiencyDao.findAll();
    }

    @Override
    public List<Weapon> findMostEffectiveWeaponsAtCreature(Creature creature) {
        List<Weapon> results = new LinkedList<>();
        if (creature == null) {
            return results;
        }
        final Integer[] maxEfficiency = {Integer.MIN_VALUE};

        weaponEfficiencyDao.findAll()
                .stream()
                .filter(w -> creature.equals(w.getCreature()))
                .filter(w -> w.getEfficiency() != null)
                .forEach(w -> {
                    Integer efficiency = w.getEfficiency();
                    if (maxEfficiency[0] < efficiency) {
                        results.clear();
                        maxEfficiency[0] = efficiency;
                        results.add(w.getWeapon());
                    } else if (maxEfficiency[0].equals(efficiency)) {
                        results.add(w.getWeapon());
                    }
        });
        return results;
    }

    @Override
    public List<Creature> findMostVulnerableCreaturesToWeapon(Weapon weapon) {
        List<Creature> results = new LinkedList<>();
        if (weapon == null) {
            return results;
        }
        final Integer[] maxEfficiency = {Integer.MIN_VALUE};

        weaponEfficiencyDao.findAll()
                .stream()
                .filter(w -> weapon.equals(w.getWeapon()))
                .filter(w -> w.getEfficiency() != null)
                .forEach(w -> {
                    Integer efficiency = w.getEfficiency();
                    if (maxEfficiency[0] < efficiency) {
                        results.clear();
                        maxEfficiency[0] = efficiency;
                        results.add(w.getCreature());
                    } else if (maxEfficiency[0].equals(efficiency)) {
                        results.add(w.getCreature());
                    }
                });
        return results;
    }

    @Override
    public List<WeaponEfficiency> findAllWeaponEfficienciesOfWeapon(Weapon weapon) {
        if (weapon == null) {
            return new LinkedList<>();
        }
        return weaponEfficiencyDao.findAll()
                .stream()
                .filter(w -> weapon.equals(w.getWeapon()))
                .collect(Collectors.toList());
    }

    @Override
    public WeaponEfficiency findWeaponEfficiency(Weapon weapon, Creature creature) {
        if (weapon == null || creature == null) {
            return null;
        }
        Optional<WeaponEfficiency> optional = weaponEfficiencyDao.findAll()
                .stream()
                .filter(w -> weapon.equals(w.getWeapon()) && creature.equals(w.getCreature()))
                .findFirst();

        return optional.isPresent() ? optional.get() : null;
    }
}
