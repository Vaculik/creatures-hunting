package cz.muni.fi.pa165.hateoas;

import cz.muni.fi.pa165.controllers.WeaponRestController;
import cz.muni.fi.pa165.dto.WeaponDTO;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

/**
 * 
 * @author Pavel Vesely <448290@mail.muni.cz>
 */
@Component
public class WeaponResourceAssembler extends ResourceAssemblerSupport<WeaponDTO, WeaponResource> {

    private static final Logger logger = LoggerFactory.getLogger(WeaponResourceAssembler.class);
    @Autowired
    private EntityLinks entityLinks;

    public WeaponResourceAssembler() {
        super(WeaponRestController.class, WeaponResource.class);
    }

    @Override
    public WeaponResource toResource(WeaponDTO weaponDTO) {
        WeaponResource weaponResource = new WeaponResource(weaponDTO);
        Long id = weaponDTO.getId();
        try {
            weaponResource.add(entityLinks.linkToSingleResource(WeaponDTO.class, id).withSelfRel());
            Method delete = WeaponRestController.class.getMethod("deleteWeapon", long.class);
            weaponResource.add(ControllerLinkBuilder.linkTo(WeaponRestController.class, delete, id).withRel("delete"));
        } catch (Exception e){
            logger.error("Failed to create links");
        }
        return weaponResource   ;
    }
}
