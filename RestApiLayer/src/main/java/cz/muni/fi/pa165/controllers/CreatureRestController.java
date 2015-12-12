package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.WeaponEfficiencyFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.hateoas.CreatureResource;
import cz.muni.fi.pa165.hateoas.CreatureResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Karel Vaculik
 */

@RestController
@RequestMapping("/creatures")
@ExposesResourceFor(CreatureDTO.class)
public class CreatureRestController {

    private static final Logger logger = LoggerFactory.getLogger(CreatureRestController.class);

    @Autowired
    private CreatureFacade creatureFacade;
    @Autowired
    private WeaponFacade weaponFacade;
    @Autowired
    private WeaponEfficiencyFacade weaponEfficiencyFacade;

    @Autowired
    private CreatureResourceAssembler creatureResourceAssembler;


    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> getAllCreatures() {
        logger.debug("GET all creatures.");
        List<CreatureDTO> creatureDTOs = creatureFacade.getAllCreatures();
        Link createLink = linkTo(CreatureRestController.class).slash("create").withRel("create");
        Link maxHeightLink = linkTo(CreatureRestController.class).slash("max-height").withRel("max-height");
        Link maxWeightLink = linkTo(CreatureRestController.class).slash("max-weight").withRel("max-weight");

        Resources<CreatureResource> creatureResources = new Resources<>(
                creatureResourceAssembler.toResources(creatureDTOs),
                linkTo(CreatureRestController.class).withSelfRel(),
                createLink,
                maxHeightLink,
                maxWeightLink);
        Arrays.stream(CreatureType.values()).forEach( (type -> creatureResources.
                add(linkTo(CreatureRestController.class).slash("type").slash(type).withRel(type.name()))) );

        return new ResponseEntity<>(creatureResources, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<CreatureResource> getCreature(@PathVariable long id) {
        logger.debug("GET creature with id=" + id);
        CreatureDTO creatureDTO = creatureFacade.getCreatureById(id);
        if (creatureDTO == null) {
            String msg = "Creature with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        CreatureResource creatureResource = creatureResourceAssembler.toResource(creatureDTO);

        return new ResponseEntity<>(creatureResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCreature(@PathVariable long id) {
        logger.debug("DELETE creature with id=" + id);
        CreatureDTO creatureDTO = creatureFacade.getCreatureById(id);
        if (creatureDTO == null) {
            String msg = "Creature with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        creatureFacade.deleteCreature(creatureDTO);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CreatureResource> createCreature(@RequestBody @Valid CreatureDTO creatureDTO,
                                                       BindingResult bindingResult) {
        logger.debug("POST new creature");
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed when create new creature: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }

        Long id = creatureFacade.createCreature(creatureDTO);
        CreatureDTO newCreatureDTO = creatureFacade.getCreatureById(id);
        CreatureResource creatureResource = creatureResourceAssembler.toResource(newCreatureDTO);

        return new ResponseEntity<>(creatureResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> getCreaturesOfType(@PathVariable CreatureType type) {
        logger.debug("GET all creatures of type=" + type);
        List<CreatureDTO> creatureDTOs = creatureFacade.getCreaturesOfType(type);
        Resources<CreatureResource> creatureResources = new Resources<>(
                creatureResourceAssembler.toResources(creatureDTOs),
                linkTo(this.getClass()).slash("type").slash(type).withSelfRel());

        return new ResponseEntity<>(creatureResources, HttpStatus.OK);
    }


    @RequestMapping(value = "/max-height", method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> getMaxHeightCreatures() {
        logger.debug("GET max height creatures");
        List<CreatureDTO> creatureDTOs = creatureFacade.getCreaturesWithMaxHeight();
        Resources<CreatureResource> creatureResources = new Resources<>(
                creatureResourceAssembler.toResources(creatureDTOs),
                linkTo(this.getClass()).slash("max-height").withSelfRel());

        return new ResponseEntity<>(creatureResources, HttpStatus.OK);
    }


    @RequestMapping(value = "/max-weight", method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> getMaxWeightCreatures() {
        logger.debug("GET max weight creatures");
        List<CreatureDTO> creatureDTOs = creatureFacade.getCreaturesWithMaxWeight();
        Resources<CreatureResource> creatureResources = new Resources<>(
                creatureResourceAssembler.toResources(creatureDTOs),
                linkTo(this.getClass()).slash("max-weight").withSelfRel());

        return new ResponseEntity<>(creatureResources, HttpStatus.OK);
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
