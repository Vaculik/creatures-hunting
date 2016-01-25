package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.List;

/**
 * Interface of service layer operations over Weapon class objects.
 *
 * @author Pavel Vesely - 448290@mail.muni.cz
 */
public interface WeaponService {

    /**
     * Finds weapon of given id.
     *
     * @param id unique integer identification of Weapon entity.
     * @return found weapon
     */
    Weapon getWeaponById(Long id);

    /**
     * Finds weapon of given name.
     *
     * @param name Unique string identification of Weapon entity.
     * @return Found weapon
     */
    Weapon getWeaponByName(String name);

    /**
     * Finds all weapons.
     *
     * @return List of all weapons in db
     */
    List<Weapon> getAllWeapons();

    /**
     * Persists new weapon into db.
     *
     * @param weapon Object of class Weapon to be persisted.
     */
    void createWeapon(Weapon weapon);

    /**
     * Persists changes on object of class Weapon in db.
     *
     * @param weapon New state of object Weapon to be persisted.
     */
    void updateWeapon(Weapon weapon);

    /**
     * Deletes object of class Weapon from db.
     *
     * @param weapon Weapon to be deleted.
     */
    void deleteWeapon(Weapon weapon);

    /**
     * Finds all weapons of given WeaponType.
     *
     * @param type WeaponType, search key
     * @return List of all weapons of given type
     */
    List<Weapon> getWeaponsOfType(WeaponType type);

    /**
     * Finds all weapons of given AmmoType.
     *
     * @param ammoType AmmoType, search key
     * @return List of all weapons of given ammoType
     */
    List<Weapon> getWeaponsOfAmmoType(AmmoType ammoType);

    /**
     * Finds all weapons with range min <= weapon.range <= max.
     * @
     *
     * param min integer, lower threshold
     * @param max integer, upper threshold
     * @return List of weapons of corresponding range
     */
    List<Weapon> getWeaponsOfRange(int min, int max);
}
