package cz.muni.fi.pa165.hateoas;

import cz.muni.fi.pa165.controllers.UserSystemRestController;
import cz.muni.fi.pa165.dto.UserSystemVerifiedDTO;
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
public class UserSystemVerifiedResourceAssembler
        extends ResourceAssemblerSupport<UserSystemVerifiedDTO, UserSystemVerifiedResource> {

    @Autowired
    private EntityLinks entityLinks;

    private static final Logger logger = LoggerFactory.getLogger(UserSystemVerifiedResourceAssembler.class);

    public UserSystemVerifiedResourceAssembler() {
        super(UserSystemRestController.class, UserSystemVerifiedResource.class);
    }

    @Override
    public UserSystemVerifiedResource toResource(UserSystemVerifiedDTO userSystemVerifiedDTO) {
        UserSystemVerifiedResource resource = new UserSystemVerifiedResource(userSystemVerifiedDTO);
        Long id = userSystemVerifiedDTO.getUserId();
        try {
            Link self = entityLinks.linkToSingleResource(UserSystemVerifiedDTO.class, id).withSelfRel();
            resource.add(self);
        } catch (Exception ex) {
            logger.error("Failed to create links.");
        }
        return resource;
    }
}
