package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.WeaponDao;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of WeaponService interface.
 *
 * @author Pavel Vesely - 448290@mail.muni.cz
 */
@Service
public class WeaponServiceImpl implements WeaponService {

    @Autowired
    private WeaponDao weaponDao;

    @Override
    public Weapon getWeaponById(Long id) {
        return weaponDao.getById(id);
    }

    @Override
    public Weapon getWeaponByName(String name) {
        return weaponDao.getByName(name);
    }

    @Override
    public List<Weapon> getAllWeapons() {
        return weaponDao.findAll();
    }

    @Override
    public void createWeapon(Weapon weapon) {
        weaponDao.create(weapon);
    }

    @Override
    public void updateWeapon(Weapon weapon) {
        weaponDao.update(weapon);
    }

    @Override
    public void deleteWeapon(Weapon weapon) {
        weaponDao.delete(weapon);
    }

    @Override
    public List<Weapon> getWeaponsOfType(WeaponType type) {
        List<Weapon> allWeapons = weaponDao.findAll();
        List<Weapon> returnList = new LinkedList<>();
        for (Weapon weapon : allWeapons) {
            if (weapon.getType() == type) {
                returnList.add(weapon);
            }
        }
        return returnList;
    }

    @Override
    public List<Weapon> getWeaponsOfAmmoType(AmmoType ammoType) {
        List<Weapon> allWeapons = weaponDao.findAll();
        List<Weapon> returnList = new LinkedList<>();
        for (Weapon weapon : allWeapons) {
            if (weapon.getAmmoType() == ammoType) {
                returnList.add(weapon);
            }
        }
        return returnList;
    }

    @Override
    public List<Weapon> getWeaponsOfRange(int min, int max) {
        List<Weapon> allWeapons = weaponDao.findAll();
        List<Weapon> returnList = new LinkedList<>();
        for (Weapon weapon : allWeapons) {
            int range = weapon.getRange();
            if (range >= min && range <= max) {
                returnList.add(weapon);
            }
        }
        return returnList;
    }
}
