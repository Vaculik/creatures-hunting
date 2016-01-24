package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.WeaponEfficiency;

import java.util.List;

/**
 * The interface represents basic operations on WeaponEfficiency entity.
 *
 * @author Karel Vaculik
 */
public interface WeaponEfficiencyDao {

    /**
     * Find a WeaponEfficiency entity by id.
     *
     * @param id id of a WeaponEfficiency entity
     * @return the found WeaponEfficiency entity or null if the entity does not
     * exist
     */
    WeaponEfficiency getById(Long id);

    /**
     * Create the WeaponEfficiency entity.
     *
     * @param weaponEfficiency WeaponEfficiency entity
     */
    void create(WeaponEfficiency weaponEfficiency);

    /**
     * Remove the WeaponEfficiency entity.
     *
     * @param weaponEfficiency WeaponEfficiency entity
     */
    void delete(WeaponEfficiency weaponEfficiency);

    /**
     * Update the WeaponEfficiency entity.
     *
     * @param weaponEfficiency WeaponEfficiency entity
     */
    void update(WeaponEfficiency weaponEfficiency);

    /**
     * Find all WeaponEfficiency entities.
     *
     * @return list of the results
     */
    List<WeaponEfficiency> findAll();
}
