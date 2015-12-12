package cz.muni.fi.pa165.hateoas;

import cz.muni.fi.pa165.controllers.WeaponEfficiencyRestController;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;
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
public class WeaponEfficiencyResourceAssembler
        extends ResourceAssemblerSupport<WeaponEfficiencyDTO, WeaponEfficiencyResource> {

    private static final Logger logger = LoggerFactory.getLogger(WeaponEfficiencyResourceAssembler.class);

    @Autowired
    private EntityLinks entityLinks;

    public WeaponEfficiencyResourceAssembler() {
        super(WeaponEfficiencyRestController.class, WeaponEfficiencyResource.class);
    }

    @Override
    public WeaponEfficiencyResource toResource(WeaponEfficiencyDTO weaponEfficiencyDTO) {
        WeaponEfficiencyResource resource = new WeaponEfficiencyResource(weaponEfficiencyDTO);
        Long id = weaponEfficiencyDTO.getId();
        try {
            Link self = entityLinks.linkToSingleResource(WeaponEfficiencyDTO.class, id).withSelfRel();
            resource.add(self);
        } catch (Exception ex) {
            logger.error("Failed to create links.");
        }
        return resource;
    }
}
