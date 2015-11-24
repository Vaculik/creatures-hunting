package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.service.WeaponService;
import cz.muni.fi.pa165.util.EntityMapper;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of WeaponFacade interface.
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
@Service
@Transactional
public class WeaponFacadeImpl implements WeaponFacade {

    @Autowired
    private WeaponService weaponService;
    
    @Autowired
    private EntityMapper entityMapper;
    
    @Override
    public WeaponDTO getWeaponById(Long id) {
        return entityMapper.map(weaponService.getWeaponById(id), WeaponDTO.class);
    }

    @Override
    public WeaponDTO getWeaponByName(String name) {
        return entityMapper.map(weaponService.getWeaponByName(name), WeaponDTO.class);
    }

    @Override
    public List<WeaponDTO> getAllWeapons() {
        return entityMapper.map(weaponService.getAllWeapons(), WeaponDTO.class);
    }

    @Override
    public Long createWeapon(WeaponDTO weapon) {
        Weapon newWeapon = entityMapper.map(weapon, Weapon.class);
        weaponService.createWeapon(newWeapon);
        return newWeapon.getId();
    }

    @Override
    public void updateWeapon(WeaponDTO weapon) {
        Weapon newWeapon = entityMapper.map(weapon, Weapon.class);
        weaponService.updateWeapon(newWeapon);
    }

    @Override
    public void deleteWeapon(WeaponDTO weapon) {
        Weapon newWeapon = entityMapper.map(weapon, Weapon.class);
        weaponService.deleteWeapon(newWeapon);
    }

    @Override
    public List<WeaponDTO> getWeaponsOfType(WeaponType type) {
        return entityMapper.map(weaponService.getWeaponsOfType(type), WeaponDTO.class);
    }

    @Override
    public List<WeaponDTO> getWeaponsOfAmmoType(AmmoType ammoType) {
        return entityMapper.map(weaponService.getWeaponsOfAmmoType(ammoType), WeaponDTO.class);
    }

    @Override
    public List<WeaponDTO> getWeaponsOfRange(int min, int max) {
        return entityMapper.map(weaponService.getWeaponsOfRange(min, max), WeaponDTO.class);
    }
}
