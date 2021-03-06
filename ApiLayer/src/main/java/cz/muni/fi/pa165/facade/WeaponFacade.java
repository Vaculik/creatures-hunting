package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.List;

/**
 * Interface of operations over transfer objects of WeaponDTO class.
 *
 * @author Pavel Vesely - 448290@mail.muni.cz
 */
public interface WeaponFacade {

    /**
     * Finds weaponDTO of given id.
     *
     * @param id unique integer identification of Weapon entity.
     * @return found weaponDTO
     */
    WeaponDTO getWeaponById(Long id);

    /**
     * Finds weaponDTO of given name.
     *
     * @param name Unique string identification of Weapon entity.
     * @return Found weaponDTO
     */
    WeaponDTO getWeaponByName(String name);

    /**
     * Finds all weaponDTOs.
     *
     * @return List of all weaponDTOs in db
     */
    List<WeaponDTO> getAllWeapons();

    /**
     * Persists new weapon, corresponding to given DTO, into db.
     *
     * @param weapon Object of class WeaponDTO to be persisted.
     * @return id of newly created weapon
     */
    Long createWeapon(WeaponDTO weapon);

    /**
     * Persists changes on object of class WeaponDTO in db.
     *
     * @param weapon New state of object WeaponDTO to be persisted.
     */
    void updateWeapon(WeaponDTO weapon);

    /**
     * Deletes object of class Weapon from db.
     *
     * @param weapon WeaponDTO to be deleted.
     */
    void deleteWeapon(WeaponDTO weapon);

    /**
     * Finds all weaponDTOs of given WeaponType.
     *
     * @param type WeaponType, search key
     * @return List of all weaponDTOs of given type
     */
    List<WeaponDTO> getWeaponsOfType(WeaponType type);

    /**
     * Finds all weaponDTOs of given AmmoType.
     *
     * @param ammoType AmmoType, search key
     * @return List of all weaponDTOs of given ammoType
     */
    List<WeaponDTO> getWeaponsOfAmmoType(AmmoType ammoType);

    /**
     * Finds all weaponDTOs with range min &le; weapon.range &le; max.
     * @param min integer, lower threshold
     * @param max integer, upper threshold
     * @return List of weaponDTOs of corresponding range
     */
    List<WeaponDTO> getWeaponsOfRange(int min, int max);
}