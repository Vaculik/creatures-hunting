package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.WeaponEfficiencyFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.hateoas.AreaResource;
import cz.muni.fi.pa165.hateoas.AreaResourceAssembler;
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
    @Autowired
    private AreaResourceAssembler areaResourceAssembler;


    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> getAllCreatures(
            @RequestParam(value="view", defaultValue = "all") String viewType) {
        List<CreatureDTO> creatureDTOs;
        switch(viewType) {
            case "all": {
                logger.debug("GET all creatures.");
                creatureDTOs = creatureFacade.getAllCreatures();
                break;
            }
            case "highest": {
                logger.debug("GET creatures with max height.");
                creatureDTOs = creatureFacade.getCreaturesWithMaxHeight();
                break;
            }
            case "heaviest": {
                logger.debug("GET creatures with max weight.");
                creatureDTOs = creatureFacade.getCreaturesWithMaxWeight();
                break;
            }
            case "no-area": {
                logger.debug("GET creatures in no area.");
                creatureDTOs = creatureFacade.getCreaturesInNoArea();
                break;
            }
            default: {
                String msg = "View request parameter value=" + viewType + " is invalid.";
                logger.error(msg);
                throw new InvalidRequestFormatException(msg);
            }
        }
        Link createLink = linkTo(CreatureRestController.class).slash("create").withRel("create");

        Resources<CreatureResource> creatureResources = new Resources<>(
                creatureResourceAssembler.toResources(creatureDTOs),
                linkTo(CreatureRestController.class).withSelfRel(),
                createLink);

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


    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCreature(@RequestBody @Valid CreatureDTO creatureDTO, BindingResult bindingResult) {
        logger.debug("POST update creature");
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed when update creature: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }
        creatureFacade.updateCreature(creatureDTO);
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


    @RequestMapping(value = "/{id}/area", method = RequestMethod.GET)
    public HttpEntity<AreaResource> getAreaOfCreature(@PathVariable Long id) {
        logger.debug("GET area of creature with id={}", id);
        AreaDTO area = creatureFacade.getAreaOfCreature(id);
        AreaResource areaResource = areaResourceAssembler.toResource(area);

        return new ResponseEntity<>(areaResource, HttpStatus.OK);
    }
}
