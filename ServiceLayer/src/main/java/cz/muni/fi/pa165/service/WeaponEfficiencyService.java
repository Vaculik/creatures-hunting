package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;

import java.util.List;

/**
 * The interface represents service layer operations on objects of the class
 * WeaponEfficiency.
 *
 * @author Karel Vaculik
 */
public interface WeaponEfficiencyService {

    /**
     * Find a WeaponEfficiency by id.
     *
     * @param id id of the WeaponEfficiency
     * @return the found WeaponEfficiency or null if doesn't exist
     */
    WeaponEfficiency getWeaponEfficiencyById(Long id);

    /**
     * Create the WeaponEfficiency.
     *
     * @param weaponEfficiency the WeaponEfficiency to be created
     */
    void createWeaponEfficiency(WeaponEfficiency weaponEfficiency);

    /**
     * Delete the WeaponEfficiency.
     *
     * @param weaponEfficiency the WeaponEfficiency to be deleted
     */
    void deleteWeaponEfficiency(WeaponEfficiency weaponEfficiency);

    /**
     * Update the WeaponEfficiency.
     *
     * @param weaponEfficiency the WeaponEfficiency to be updated
     */
    void updateWeaponEfficiency(WeaponEfficiency weaponEfficiency);

    /**
     * Find all WeaponEfficiencies.
     *
     * @return list of the results
     */
    List<WeaponEfficiency> findAllWeaponEfficiencies();

    /**
     * Find all Weapons, which are the most effective at the given Creature.
     *
     * @param creature the Creature
     * @return list of the most effective Weapons
     */
    List<Weapon> findMostEffectiveWeaponsAtCreature(Creature creature);

    /**
     * Find all Creatures, which are the most vulnerable to the given Weapon.
     *
     * @param weapon the Weapon
     * @return list of the most vulnerable Creatures
     */
    List<Creature> findMostVulnerableCreaturesToWeapon(Weapon weapon);

    /**
     * Find all WeaponEfficiencies, which belongs to the given Weapon.
     *
     * @param weapon the Weapon
     * @return list of the WeaponEfficiencies
     */
    List<WeaponEfficiency> findAllWeaponEfficienciesOfWeapon(Weapon weapon);

    /**
     * Find a WeaponEfficiency of the specified Weapon against the specified Creature.
     *
     * @param weapon the Weapon
     * @param creature the Creature
     * @return the WeaponEfficiency, if doesn't exist, then return null
     */
    WeaponEfficiency findWeaponEfficiency(Weapon weapon, Creature creature);
}
