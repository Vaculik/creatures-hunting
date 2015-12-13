package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyCreateDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;

import java.util.List;

/**
 * The interface represents operations on a transfer object of the class WeaponEfficiencyDTO.
 *
 * @author Karel Vaculik
 */
public interface WeaponEfficiencyFacade {

    /**
     * Find a WeaponEfficiencyDTO by id.
     *
     * @param id id of the WeaponEfficiencyDTO
     * @return the found WeaponEfficiencyDTO or null if doesn't exist
     */
    WeaponEfficiencyDTO getWeaponEfficiencyById(Long id);

    /**
     * Create the WeaponEfficiencyDTO.
     *
     * @param weaponEfficiencyCreateDTO the WeaponEfficiencyDTO to be created
     * @return id of the created WeaponEfficiencyDTO
     */
    Long createWeaponEfficiency(WeaponEfficiencyCreateDTO weaponEfficiencyCreateDTO);

    /**
     * Delete the WeaponEfficiencyDTO.
     *
     * @param weaponEfficiencyDTO the WeaponEfficiencyDTO to be deleted
     */
    void deleteWeaponEfficiency(WeaponEfficiencyDTO weaponEfficiencyDTO);

    /**
     * Find all WeaponEfficiencyDTOs.
     *
     * @return list of the results
     */
    List<WeaponEfficiencyDTO> findAllWeaponEfficiencies();

    /**
     * Find all WeaponDTOs, which are the most effective at the given CreatureDTO.
     *
     * @param creatureDTO the CreatureDTO
     * @return list of the most effective WeaponDTOs
     */
    List<WeaponDTO> findMostEffectiveWeaponsAtCreature(CreatureDTO creatureDTO);

    /**
     * Find all CreatureDTOs, which are the most vulnerable to the given WeaponDTO.
     *
     * @param weaponDTO the WeaponDTO
     * @return list of the most vulnerable CreatureDTOs
     */
    List<CreatureDTO> findMostVulnerableCreaturesToWeapon(WeaponDTO weaponDTO);

    /**
     * Find all WeaponEfficiencyDTOs, which belongs to the given WeaponDTO.
     *
     * @param weaponDTO the WeaponDTO
     * @return list of the WeaponEfficiencyDTOs
     */
    List<WeaponEfficiencyDTO> findAllWeaponEfficienciesOfWeapon(WeaponDTO weaponDTO);
}
