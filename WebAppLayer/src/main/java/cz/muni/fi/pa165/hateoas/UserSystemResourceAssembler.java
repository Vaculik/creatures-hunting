package cz.muni.fi.pa165.hateoas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import cz.muni.fi.pa165.controllers.UserSystemRestController;
import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.UserSystemDTO;

@Component
public class UserSystemResourceAssembler extends ResourceAssemblerSupport<UserSystemDTO, UserSystemResource> {

	private static final Logger logger = LoggerFactory.getLogger(AreaResourceAssembler.class);
	
	@Autowired
	private EntityLinks entityLinks;
	
	public UserSystemResourceAssembler() {
		super(UserSystemRestController.class, UserSystemResource.class);
	}

	@Override
	public UserSystemResource toResource(UserSystemDTO userDTO) {
		UserSystemResource userResource = new UserSystemResource(userDTO);
		long id = userDTO.getId();
		try{
			Link self = entityLinks.linkToSingleResource(UserSystemDTO.class, id).withSelfRel();
			userResource.add(self);
//			Method delete = UserSystemRestController.class.getMethod("deleteUser", long.class);
//			userResource.add(linkTo(UserSystemRestController.class, delete, id).withRel("delete"));
		}
		catch (Exception ex) {
			logger.error("Failed to create links.");
		}
		
		return userResource;
	}
	

}
