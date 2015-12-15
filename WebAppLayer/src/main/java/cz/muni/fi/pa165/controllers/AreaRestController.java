/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.hateoas.AreaResource;
import cz.muni.fi.pa165.hateoas.AreaResourceAssembler;
import cz.muni.fi.pa165.hateoas.CreatureResource;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
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
    private CreatureFacade CreatureFacade;

    @Autowired
    private AreaResourceAssembler areaResourceAssembler;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<AreaResource> getArea(@PathVariable long id) {
//    public final AreaDTO getArea(@PathVariable("id") long id) {
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
//        return areaDTO;
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

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<AreaResource>> getAllAreas() {
        logger.debug("GET all areas.");
        List<AreaDTO> areaDTOs = areaFacade.getAllAreas();
        logger.debug(areaDTOs.toString());
        Resources<AreaResource> resources = new Resources<>(
                areaResourceAssembler.toResources(areaDTOs),
                linkTo(AreaRestController.class).withSelfRel());

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
//    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public final List<AreaDTO> getCategories() {
//
//        logger.debug("rest getCategories()");
//        return areaFacade.getAllAreas();
//    }

}
