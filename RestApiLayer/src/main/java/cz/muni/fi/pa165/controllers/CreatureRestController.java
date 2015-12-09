package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.hateoas.CreatureResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     *
     *
     * @return list of creatures
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<CreatureResource>> categories() {
        return null;
    }
}
