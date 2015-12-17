package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.hateoas.AreaResource;
import cz.muni.fi.pa165.hateoas.AreaResourceAssembler;
import java.util.List;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *  The contoller for the rest layer of Creature informations system
 * 
 * @author Martin Zboril
 */
@RestController
@RequestMapping("/areas")
@ExposesResourceFor(AreaDTO.class)
public class AreaRestController {

    private static final Logger logger = LoggerFactory.getLogger(AreaRestController.class);

    @Autowired
    private AreaFacade areaFacade;

    @Autowired
    private CreatureFacade creatureFacade;

    @Autowired
    private AreaResourceAssembler areaResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<AreaResource>> getAllAreas() {
        logger.debug("GET all areas.");
        List<AreaDTO> areaDTOs = areaFacade.getAllAreas();
        Link createLink = linkTo(AreaRestController.class).slash("create").withRel("create");
        Link noCreatureLink = linkTo(AreaRestController.class).slash("no-creature").withRel("no-creature");
        Link anyCreatureLink = linkTo(AreaRestController.class).slash("any-creature").withRel("any-creature");
        Link mostCreatureLink = linkTo(AreaRestController.class).slash("most-creatures").withRel("most-creatures");
        Link fewestCreatureLink = linkTo(AreaRestController.class).slash("fewest-creatures").withRel("fewest-creatures");
        logger.debug(areaDTOs.toString());
        Resources<AreaResource> resources = new Resources<>(
                areaResourceAssembler.toResources(areaDTOs),
                linkTo(AreaRestController.class).withSelfRel(),
                createLink,
                noCreatureLink,
                anyCreatureLink,
                mostCreatureLink,
                fewestCreatureLink
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<AreaResource> getArea(@PathVariable long id) {
        logger.debug("GET area with id=" + id);
        AreaDTO areaDTO = areaFacade.getById(id);
        logger.debug(areaDTO.toString());
        if (areaDTO == null) {
            String msg = "Area with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        AreaResource areaResource = areaResourceAssembler.toResource(areaDTO);
        return new ResponseEntity<>(areaResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AreaResource> createArea(@RequestBody @Valid AreaDTO areaDTO,
            BindingResult bindingResult) {
        logger.debug("POST new area");
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed when create new area: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }

        Long id = areaFacade.createArea(areaDTO);
        AreaDTO newAreaDTO = areaFacade.getById(id);
        AreaResource areaResource = areaResourceAssembler.toResource(newAreaDTO);
        return new ResponseEntity<>(areaResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteArea(@PathVariable long id) {
        logger.debug("DELETE area with id=" + id);
        AreaDTO areaDTO = areaFacade.getById(id);

        if (areaDTO == null) {
            String msg = "Area with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }

        areaFacade.deleteArea(areaDTO);
    }

    @RequestMapping(value = "/no-creature", method = RequestMethod.GET)
    public HttpEntity<Resources<AreaResource>> getAreasWithNoCreature() {
        logger.debug("GET areas with no creature");
        List<AreaDTO> areaDTOs = areaFacade.getAreasWithNoCreature();
        Resources<AreaResource> areaResources = new Resources<>(
                areaResourceAssembler.toResources(areaDTOs),
                linkTo(this.getClass()).slash("no-creature").withSelfRel());

        return new ResponseEntity<>(areaResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/any-creature", method = RequestMethod.GET)
    public HttpEntity<Resources<AreaResource>> getAreasWithAnyCreature() {
        logger.debug("GET areas with any creature");
        List<AreaDTO> areaDTOs = areaFacade.getAreasWithAnyCreature();
        Resources<AreaResource> areaResources = new Resources<>(
                areaResourceAssembler.toResources(areaDTOs),
                linkTo(this.getClass()).slash("any-creature").withSelfRel());

        return new ResponseEntity<>(areaResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/most-creatures", method = RequestMethod.GET)
    public HttpEntity<Resources<AreaResource>> getAreasMostCreatures() {
        logger.debug("GET areas with most creatures");
        List<AreaDTO> areaDTOs = areaFacade.getAreasMostCreatures();
        Resources<AreaResource> areaResources = new Resources<>(
                areaResourceAssembler.toResources(areaDTOs),
                linkTo(this.getClass()).slash("most-creatures").withSelfRel());

        return new ResponseEntity<>(areaResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/fewest-creatures", method = RequestMethod.GET)
    public HttpEntity<Resources<AreaResource>> getAreasFewestCreatures() {
        logger.debug("GET areas with fewest creatures");
        List<AreaDTO> areaDTOs = areaFacade.getAreasFewestCreatures();
        Resources<AreaResource> areaResources = new Resources<>(
                areaResourceAssembler.toResources(areaDTOs),
                linkTo(this.getClass()).slash("fewest-creatures").withSelfRel());

        return new ResponseEntity<>(areaResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public HttpEntity<AreaResource> addCreature(@PathVariable("id") long id, @RequestBody CreatureDTO creatureDTO) {

        logger.debug("POST add new Creature with ID " + creatureDTO.getId());

        AreaDTO areaDTO = areaFacade.getById(id);
        areaFacade.addCreature(areaDTO, creatureDTO);

        AreaResource areaResource = areaResourceAssembler.toResource(areaDTO);
        return new ResponseEntity<>(areaResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<AreaResource> moveCreature(@PathVariable("id") long id, long id2, long id3) {

        AreaDTO areaDTO = areaFacade.getById(id);
        AreaDTO areaDTO2 = areaFacade.getById(id2);
        CreatureDTO creatureDTO = creatureFacade.getCreatureById(id3);
        logger.debug("POST add new Creature with ID " + creatureDTO.getId());
        areaFacade.moveCreature(creatureDTO, areaDTO, areaDTO2);

        AreaResource areaResource = areaResourceAssembler.toResource(areaDTO);
        return new ResponseEntity<>(areaResource, HttpStatus.OK);
    }

}
