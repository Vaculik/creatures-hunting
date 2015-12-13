package cz.muni.fi.pa165.hateoas;

import cz.muni.fi.pa165.controllers.CreatureRestController;
import cz.muni.fi.pa165.dto.CreatureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.lang.reflect.Method;

/**
 * @author Karel Vaculik
 */

@Component
public class CreatureResourceAssembler extends ResourceAssemblerSupport<CreatureDTO, CreatureResource> {

    private static final Logger logger = LoggerFactory.getLogger(CreatureResourceAssembler.class);

    @Autowired
    private EntityLinks entityLinks;

    public CreatureResourceAssembler() {
        super(CreatureRestController.class, CreatureResource.class);
    }

    @Override
    public CreatureResource toResource(CreatureDTO creatureDTO) {
        CreatureResource creatureResource = new CreatureResource(creatureDTO);
        Long id = creatureDTO.getId();
        try {
            Link self = entityLinks.linkToSingleResource(CreatureDTO.class, id).withSelfRel();
            creatureResource.add(self);
            Method delete = CreatureRestController.class.getMethod("deleteCreature", long.class);
            creatureResource.add(linkTo(CreatureRestController.class, delete, id).withRel("delete"));
        } catch (Exception ex) {
            logger.error("Failed to create links.");
        }
        return creatureResource;
    }
}
