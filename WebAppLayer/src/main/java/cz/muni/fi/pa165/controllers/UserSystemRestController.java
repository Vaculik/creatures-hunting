package cz.muni.fi.pa165.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.facade.UserSystemFacade;
import cz.muni.fi.pa165.hateoas.CreatureResource;
import cz.muni.fi.pa165.hateoas.UserSystemResource;
import cz.muni.fi.pa165.hateoas.UserSystemResourceAssembler;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(UserSystemDTO.class)
public class UserSystemRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserSystemRestController.class);
	
	@Autowired
	private UserSystemFacade userFacade;
	
	@Autowired
	private UserSystemResourceAssembler userResourceAssembler;
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<Resources<UserSystemResource>> getAllUsers(@RequestParam(value="view", defaultValue = "all") String viewType) {
		List<UserSystemDTO> usersDTO;
		usersDTO = userFacade.getAllUsers();

		Link createLink = linkTo(UserSystemRestController.class).slash("create").withRel("create");
		
		Resources<UserSystemResource> resources = new Resources<> (
				userResourceAssembler.toResources(usersDTO),
				linkTo(UserSystemRestController.class).withSelfRel(),
				createLink
		);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<UserSystemResource> getUser(@PathVariable long id) {
    	logger.debug("GET user with id=" + id);
    	UserSystemDTO userDTO = userFacade.getUserById(id);
    	if (userDTO == null) {
            String msg = "User with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
    	}
    	UserSystemResource userResource = userResourceAssembler.toResource(userDTO);
    	return new ResponseEntity<>(userResource, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody @Valid UserSystemDTO userDTO,
    												BindingResult bindingResult) {
    	logger.debug("POST new user");
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed when create new user: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }
        
        userFacade.createUser(userDTO);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long id) {
    	logger.debug("DELETE user with id=" + id);
    	UserSystemDTO userDTO = userFacade.getUserById(id);
    	
    	if (userDTO == null) {
            String msg = "User with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
    	}
    	
    	userFacade.deleteUser(userDTO);
    }
    
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public HttpEntity<Resources<UserSystemResource>> getUsersOfType(@PathVariable UserType type) {
    	logger.debug("GET user of type=" + type);
    	List<UserSystemDTO> usersDTO = userFacade.getUsersOfType(type);
        Resources<UserSystemResource> userResources = new Resources<>(
        		userResourceAssembler.toResources(usersDTO),
                linkTo(this.getClass()).slash("type").slash(type).withSelfRel());

    	return new ResponseEntity<>(userResources, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/sex/{sex}", method = RequestMethod.GET)
    public HttpEntity<Resources<UserSystemResource>> getUsersOfSex(@PathVariable SexType sex) {
    	logger.debug("GET user of sex=" + sex);
    	List<UserSystemDTO> usersDTO = userFacade.getUsersOfSex(sex);
        Resources<UserSystemResource> userResources = new Resources<>(
        		userResourceAssembler.toResources(usersDTO),
                linkTo(this.getClass()).slash("sex").slash(sex).withSelfRel());

    	return new ResponseEntity<>(userResources, HttpStatus.OK);
    }
}
