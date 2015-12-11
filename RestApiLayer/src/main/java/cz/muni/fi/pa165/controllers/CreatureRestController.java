package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.hateoas.CreatureResource;
import cz.muni.fi.pa165.hateoas.CreatureResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private CreatureResourceAssembler creatureResourceAssembler;

    /**
     * @return list of creatures
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> getAllcreatures() {
        logger.debug("GET all creatures.");
        List<CreatureDTO> creatureDTOs = creatureFacade.getAllCreatures();
        logger.debug(creatureDTOs.toString());
        System.out.println(creatureDTOs);
        Resources<CreatureResource> creatureResources = new Resources<>(
                creatureResourceAssembler.toResources(creatureDTOs),
                linkTo(CreatureRestController.class).withSelfRel());

        return new ResponseEntity<>(creatureResources, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<CreatureResource> getCreature(@PathVariable long id) {
        logger.debug("GET creature with id=" + id);
        CreatureDTO creatureDTO = creatureFacade.getCreatureById(id);
        CreatureResource creatureResource = creatureResourceAssembler.toResource(creatureDTO);

        return new ResponseEntity<>(creatureResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCreature(@PathVariable long id) {
    }
}
