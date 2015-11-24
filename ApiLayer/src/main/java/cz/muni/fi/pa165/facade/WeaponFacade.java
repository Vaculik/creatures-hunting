package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.List;

/**
 * Interface of operations over transfer objects of WeaponDTO class.
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
public interface WeaponFacade {
    //TODO JavaDoc
    public WeaponDTO getWeaponById(Long id);
    public WeaponDTO getWeaponByName(String name);
    public List<WeaponDTO> getAllWeapons();
    
    public Long createWeapon(WeaponDTO weapon);
    public void updateWeapon(WeaponDTO weapon);
    public void deleteWeapon(WeaponDTO weapon);
    
    public List<WeaponDTO> getWeaponsOfType(WeaponType type);
    public List<WeaponDTO> getWeaponsOfAmmoType(AmmoType ammoType);
    public List<WeaponDTO> getWeaponsOfRange(int min, int max);
}