package cz.muni.fi.pa165.hateoas;

import cz.muni.fi.pa165.controllers.WeaponRestController;
import cz.muni.fi.pa165.dto.WeaponDTO;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class WeaponResourceAssembler extends ResourceAssemblerSupport<WeaponDTO, WeaponResource>{

    public WeaponResourceAssembler() {
        super(WeaponRestController.class, WeaponResource.class);
    }

    @Override
    public WeaponResource toResource(WeaponDTO weaponDTO) {
        // TODO
        return null;
    }
}
