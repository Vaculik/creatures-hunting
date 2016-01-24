package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.WeaponEfficiencyFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.hateoas.WeaponResource;
import cz.muni.fi.pa165.hateoas.WeaponResourceAssembler;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pavel Vesely <448290@mail.muni.cz>
 */
@RestController
@RequestMapping("/weapons")
@ExposesResourceFor(WeaponDTO.class)
public class WeaponRestController {

    private static final Logger logger = LoggerFactory.getLogger(WeaponRestController.class);

    @Autowired
    private WeaponFacade weaponFacade;
    @Autowired
    private WeaponEfficiencyFacade efficiencyFacade;
    @Autowired
    private WeaponResourceAssembler weaponResourceAssembler;

    //permission:ANYONE
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<WeaponResource>> getAllWeapons() {
        List<WeaponDTO> weaponDTOs;

        logger.debug("GET all creatures.");
        weaponDTOs = weaponFacade.getAllWeapons();

        Link createLink = linkTo(WeaponRestController.class).slash("create").withRel("create");
        Resources<WeaponResource> weaponResources = new Resources<>(
                weaponResourceAssembler.toResources(weaponDTOs),
                linkTo(WeaponRestController.class).withSelfRel(),
                createLink
        );
        Arrays.stream(WeaponType.values()).forEach((type -> weaponResources.
                add(linkTo(WeaponRestController.class).slash("type").slash(type).withRel(type.name()))));
        Arrays.stream(AmmoType.values()).forEach((type -> weaponResources.
                add(linkTo(WeaponRestController.class).slash("type").slash(type).withRel(type.name()))));

        return new ResponseEntity<>(weaponResources, HttpStatus.OK);
    }

    //permission:ANYONE
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public HttpEntity<Resources<WeaponResource>> getAllWeaponsOfType(@PathVariable WeaponType type) {
        List<WeaponDTO> weaponDTOs;

        logger.debug("GET all creatures of type " + type + ".");
        weaponDTOs = weaponFacade.getWeaponsOfType(type);

        Resources<WeaponResource> weaponResources = new Resources<>(
                weaponResourceAssembler.toResources(weaponDTOs),
                linkTo(WeaponRestController.class).withSelfRel()
        );
        return new ResponseEntity<>(weaponResources, HttpStatus.OK);
    }

    //permission:ANYONE
    @RequestMapping(value = "/ammotype/{ammoType}", method = RequestMethod.GET)
    public HttpEntity<Resources<WeaponResource>> getAllWeaponsOfAmmoType(@PathVariable AmmoType ammoType) {
        List<WeaponDTO> weaponDTOs;

        logger.debug("GET all creatures of type " + ammoType + ".");
        weaponDTOs = weaponFacade.getWeaponsOfAmmoType(ammoType);

        Resources<WeaponResource> weaponResources = new Resources<>(
                weaponResourceAssembler.toResources(weaponDTOs),
                linkTo(WeaponRestController.class).withSelfRel()
        );
        return new ResponseEntity<>(weaponResources, HttpStatus.OK);
    }

    //permission:ANYONE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<WeaponResource> getWeapon(@PathVariable long id) {
        logger.debug("GET weapon by id=" + id);
        WeaponDTO weaponDTO = weaponFacade.getWeaponById(id);
        if (weaponDTO == null) {
            String msg = "Weapon by id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        WeaponResource weaponResource = weaponResourceAssembler.toResource(weaponDTO);

        return new ResponseEntity<>(weaponResource, HttpStatus.OK);
    }

    //permission:USER
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<WeaponResource> createWeapon(@RequestBody @Valid WeaponDTO weaponDTO,
            BindingResult bindingResult) {
        logger.debug("POST new weapon");
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed: creating new weapon: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }

        Long id = weaponFacade.createWeapon(weaponDTO);
        WeaponDTO newWeaponDTO = weaponFacade.getWeaponById(id);
        WeaponResource weaponResource = weaponResourceAssembler.toResource(newWeaponDTO);

        return new ResponseEntity<>(weaponResource, HttpStatus.OK);
    }

    //permission:ADMIN
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteWeapon(@PathVariable long id) {
        logger.debug("DELETE weapon with id=" + id);
        WeaponDTO weaponDTO = weaponFacade.getWeaponById(id);
        if (weaponDTO == null) {
            String msg = "Weapon by id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        for (WeaponEfficiencyDTO efficiency : efficiencyFacade.findAllWeaponEfficienciesOfWeapon(weaponDTO)) {
            efficiencyFacade.deleteWeaponEfficiency(efficiency);
        }
        weaponFacade.deleteWeapon(weaponDTO);
    }

    //permission:USER
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editWeapon(@PathVariable long id, @RequestBody @Valid WeaponDTO weaponDTO,
            BindingResult bindingResult) {
        if (weaponDTO.getId() != id) {
            String msg = "Weapon edit id mismatch.";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        logger.debug("POST edit weapon id=" + weaponDTO.getId().toString());
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed: editing weapon id=" + weaponDTO.getId().toString() + ": " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }

        weaponFacade.updateWeapon(weaponDTO);
    }
}
