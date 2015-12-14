/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hateoas;

import cz.muni.fi.pa165.controllers.AreaRestController;
import cz.muni.fi.pa165.dto.AreaDTO;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martin Zboril
 */

@Component
public class AreaResourceAssembler extends ResourceAssemblerSupport<AreaDTO, AreaResource> {
        private static final Logger logger = LoggerFactory.getLogger(AreaResourceAssembler.class);

    @Autowired
    private EntityLinks entityLinks;

    public AreaResourceAssembler() {
        super(AreaRestController.class, AreaResource.class);
    }
    
    @Override
    public AreaResource toResource(AreaDTO areaDTO) {
        AreaResource areaResource = new AreaResource(areaDTO);
        Long id = areaDTO.getId();
        try {
            Link self = entityLinks.linkToSingleResource(AreaDTO.class, id).withSelfRel();
            areaResource.add(self);
            Method delete = AreaRestController.class.getMethod("deleteArea", long.class);
            areaResource.add(linkTo(AreaRestController.class, delete, id).withRel("delete"));
        } catch (Exception ex) {
            logger.error("Failed to create links.");
        }
        return areaResource;
    }
    
    
    

}
