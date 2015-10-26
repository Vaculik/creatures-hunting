package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.WeaponEfficiency;

import java.util.List;

/**
 * The interface represents basic operations on WeaponEfficiency entity.
 *
 * Created by vaculik on 23.10.15.
 */
public interface WeaponEfficiencyDao {

    WeaponEfficiency findById(Long id);

    void create(WeaponEfficiency weaponEfficiency);

    void delete(WeaponEfficiency weaponEfficiency);

    void update(WeaponEfficiency weaponEfficiency);

    List<WeaponEfficiency> findAll();
}
