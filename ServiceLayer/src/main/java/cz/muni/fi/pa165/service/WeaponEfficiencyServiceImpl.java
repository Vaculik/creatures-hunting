package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.WeaponEfficiencyDao;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
    public void updateWeaponEfficiency(WeaponEfficiency weaponEfficiency) {
        weaponEfficiencyDao.update(weaponEfficiency);
    }

    @Override
    public void deleteWeaponEfficiency(WeaponEfficiency weaponEfficiency) {
        weaponEfficiencyDao.delete(weaponEfficiency);
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

        Integer maxEfficiency = null;
        Integer efficiency;
        for (WeaponEfficiency weaponEfficiency : weaponEfficiencyDao.findAll()) {
            if (creature.equals(weaponEfficiency.getCreature())) {
                efficiency = weaponEfficiency.getEfficiency();
                if (efficiency == null) {
                    continue;
                }
                if (maxEfficiency == null || maxEfficiency < efficiency) {
                    results.clear();
                    maxEfficiency = efficiency;
                    results.add(weaponEfficiency.getWeapon());
                } else if (maxEfficiency == efficiency) {
                    results.add(weaponEfficiency.getWeapon());
                }
            }
        }
        return results;
    }

    @Override
    public List<Creature> findMostVulnerableCreaturesToWeapon(Weapon weapon) {
        List<Creature> results = new LinkedList<>();
        if (weapon == null) {
            return results;
        }

        Integer maxEfficiency = null;
        Integer efficiency;
        for (WeaponEfficiency weaponEfficiency : weaponEfficiencyDao.findAll()) {
            if (weapon.equals(weaponEfficiency.getWeapon())) {
                efficiency = weaponEfficiency.getEfficiency();
                if (efficiency == null) {
                    continue;
                }
                if (maxEfficiency == null || maxEfficiency < efficiency) {
                    results.clear();
                    maxEfficiency = efficiency;
                    results.add(weaponEfficiency.getCreature());
                } else if (maxEfficiency == efficiency) {
                    results.add(weaponEfficiency.getCreature());
                }
            }
        }
        return results;
    }
}
