package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.List;

/**
 * Interface of service layer operations over Weapon class objects.
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
public interface WeaponService {
    //TODO JavaDoc
    public Weapon getWeaponById(Long id);
    public Weapon getWeaponByName(String name);
    public List<Weapon> getAllWeapons();
    
    public void createWeapon(Weapon weapon);
    public void updateWeapon(Weapon weapon);
    public void deleteWeapon(Weapon weapon);
    
    public List<Weapon> getWeaponsOfType(WeaponType type);
    public List<Weapon> getWeaponsOfAmmoType(AmmoType ammoType);
    public List<Weapon> getWeaponsOfRange(int min, int max);
}
