package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyCreateDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import cz.muni.fi.pa165.service.CreatureService;
import cz.muni.fi.pa165.service.WeaponEfficiencyService;
import cz.muni.fi.pa165.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The implementation of the WeaponEfficiencyFacade interface.
 *
 * @author Karel Vaculik
 */
@Service
@Transactional
public class WeaponEfficiencyFacadeImpl implements WeaponEfficiencyFacade {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private WeaponEfficiencyService weaponEfficiencyService;

    @Autowired
    private CreatureService creatureService;

//    @Autowired
//    private WeaponService weaponService;

    @Override
    public WeaponEfficiencyDTO getWeaponEfficiencyById(Long id) {
        return entityMapper.map(weaponEfficiencyService.getWeaponEfficiencyById(id), WeaponEfficiencyDTO.class);
    }

    @Override
    public Long createWeaponEfficiency(WeaponEfficiencyCreateDTO weaponEfficiencyCreateDTO) {
        WeaponEfficiency newWeaponEfficiency = new WeaponEfficiency();
        newWeaponEfficiency.setEfficiency(weaponEfficiencyCreateDTO.getEfficiency());
        Creature creature = creatureService.getCreatureById(weaponEfficiencyCreateDTO.getCreatureId());
        newWeaponEfficiency.setCreature(creature);
//        Weapon weapon = weaponService.getWeaponById(weaponEfficiencyCreateDTO.getWeaponId());
//        newWeaponEfficiency.setWeapon(weapon);
        weaponEfficiencyService.createWeaponEfficiency(newWeaponEfficiency);
        return newWeaponEfficiency.getId();
    }

    @Override
    public void deleteWeaponEfficiency(WeaponEfficiencyDTO weaponEfficiencyDTO) {
        weaponEfficiencyService.deleteWeaponEfficiency(entityMapper.map(weaponEfficiencyDTO, WeaponEfficiency.class));
    }

    @Override
    public List<WeaponEfficiencyDTO> findAllWeaponEfficiencies() {
        return entityMapper.map(weaponEfficiencyService.findAllWeaponEfficiencies(), WeaponEfficiencyDTO.class);
    }

    @Override
    public List<WeaponDTO> findMostEffectiveWeaponsAtCreature(CreatureDTO creatureDTO) {
        Creature creature = entityMapper.map(creatureDTO, Creature.class);
        return entityMapper.map(weaponEfficiencyService.
                findMostEffectiveWeaponsAtCreature(creature), WeaponDTO.class);
    }

    @Override
    public List<CreatureDTO> findMostVulnerableCreaturesToWeapon(WeaponDTO weaponDTO) {
        Weapon weapon = entityMapper.map(weaponDTO, Weapon.class);
        return entityMapper.map(weaponEfficiencyService.
                findMostVulnerableCreaturesToWeapon(weapon), CreatureDTO.class);
    }
}
