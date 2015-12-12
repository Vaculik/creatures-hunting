package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyCreateDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.WeaponEfficiencyFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.hateoas.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;

/**
 * @author Karel Vaculik
 */

@RestController
@RequestMapping("/weapon-efficiencies")
@ExposesResourceFor(WeaponEfficiencyDTO.class)
public class WeaponEfficiencyRestController {

    private static final Logger logger = LoggerFactory.getLogger(WeaponEfficiencyRestController.class);

    @Autowired
    private WeaponEfficiencyFacade weaponEfficiencyFacade;
    @Autowired
    private CreatureFacade creatureFacade;
    @Autowired
    private WeaponFacade weaponFacade;

    @Autowired
    private WeaponEfficiencyResourceAssembler weaponEfficiencyResourceAssembler;
    @Autowired
    private WeaponResourceAssembler weaponResourceAssembler;
    @Autowired
    private CreatureResourceAssembler creatureResourceAssembler;


    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<WeaponEfficiencyResource>> getAllWeaponEfficiencies() {
        logger.debug("GET all weapon efficiencies");
        List<WeaponEfficiencyDTO> weaponEfficiencyDTOs = weaponEfficiencyFacade.findAllWeaponEfficiencies();

        Resources<WeaponEfficiencyResource> resources = new Resources<>(
                weaponEfficiencyResourceAssembler.toResources(weaponEfficiencyDTOs),
                linkTo(WeaponEfficiencyRestController.class).withSelfRel());

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<WeaponEfficiencyResource> getWeaponEfficiency(@PathVariable Long id) {
        logger.debug("GET weapon efficiency with id=" + id);
        WeaponEfficiencyDTO weaponEfficiencyDTO = weaponEfficiencyFacade.getWeaponEfficiencyById(id);
        if (weaponEfficiencyDTO == null) {
            String msg = "Weapon efficiency with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        WeaponEfficiencyResource resource = weaponEfficiencyResourceAssembler.toResource(weaponEfficiencyDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteWeaponEfficiency(@PathVariable Long id) {
        logger.debug("DELETE weapon efficiency with id=" + id);
        WeaponEfficiencyDTO weaponEfficiencyDTO = weaponEfficiencyFacade.getWeaponEfficiencyById(id);
        if (weaponEfficiencyDTO == null) {
            String msg = "Weapon efficiency with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        weaponEfficiencyFacade.deleteWeaponEfficiency(weaponEfficiencyDTO);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public HttpEntity<WeaponEfficiencyResource> createWeaponEfficiency(
            @RequestBody @Valid WeaponEfficiencyCreateDTO weaponEfficiencyCreateDTO,
            BindingResult bindingResult) {
        logger.debug("POST new weapon efficiency");
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed when create new weapon efficiency: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }
        Long id = weaponEfficiencyFacade.createWeaponEfficiency(weaponEfficiencyCreateDTO);
        WeaponEfficiencyDTO newWeaponEfficiencyDTO = weaponEfficiencyFacade.getWeaponEfficiencyById(id);
        WeaponEfficiencyResource resource = weaponEfficiencyResourceAssembler.toResource(newWeaponEfficiencyDTO);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/most-effective-to/creature/{id}", method = RequestMethod.GET)
    public HttpEntity<Resources<WeaponResource>> findMostEffectiveWeaponsAtCreature(@PathVariable Long id) {
        logger.debug("GET most effective weapons at creature with id=" + id);
        CreatureDTO creatureDTO = creatureFacade.getCreatureById(id);
        if (creatureDTO == null) {
            String msg = "Creature with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        List<WeaponDTO> resultWeapons = weaponEfficiencyFacade.findMostEffectiveWeaponsAtCreature(creatureDTO);
        logger.debug(resultWeapons.toString()); // to delete
        Resources<WeaponResource> resources = new Resources<>(weaponResourceAssembler.toResources(resultWeapons));

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @RequestMapping(value = "/most-vulnerable-to/weapon/{id}", method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> findMostVulnerableCreaturesToWeapon(@PathVariable Long id) {
        logger.debug("GET most vulnerable creatures to weapon with id=" + id);
        WeaponDTO weaponDTO = weaponFacade.getWeaponById(id);
        if (weaponDTO == null) {
            String msg = "Weapon with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }

        List<CreatureDTO> resultCreatures = weaponEfficiencyFacade.findMostVulnerableCreaturesToWeapon(weaponDTO);
        logger.debug(resultCreatures.toString()); // to delete
        Resources<CreatureResource> resources = new Resources<>(
                creatureResourceAssembler.toResources(resultCreatures));

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
