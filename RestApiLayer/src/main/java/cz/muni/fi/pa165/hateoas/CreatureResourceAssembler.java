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
        try {
            Link self = entityLinks.linkToSingleResource(CreatureDTO.class, creatureDTO.getId()).withSelfRel();
            creatureResource.add(self);
        } catch (Exception ex) {
            logger.warn("Failed to create links.");
        }
        return creatureResource;
    }
}
