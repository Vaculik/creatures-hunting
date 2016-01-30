package cz.muni.fi.pa165.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.security.InvalidParameterException;
import java.util.List;

import javax.validation.Valid;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.exceptions.*;
import cz.muni.fi.pa165.hateoas.*;

import cz.muni.fi.pa165.security.TokenAuthenticationService;
import cz.muni.fi.pa165.security.TokenHandler;
import cz.muni.fi.pa165.security.UserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.facade.UserSystemFacade;

import static cz.muni.fi.pa165.security.TokenAuthenticationService.AUTH_NAME_HEADER;


@RestController
@RequestMapping("/users")
@ExposesResourceFor(UserSystemDTO.class)
public class UserSystemRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserSystemRestController.class);

    @Autowired
    private UserSystemFacade userFacade;

    @Autowired
    private UserSystemResourceAssembler userResourceAssembler;

    @Autowired
    private UserSystemVerifiedResourceAssembler userVerifiedResourceAssembler;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    //permission:ANYONE
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<UserSystemResource>> getAllUsers(@RequestParam(value = "view", defaultValue = "all") String viewType) {
        List<UserSystemDTO> usersDTO;
        usersDTO = userFacade.getAllUsers();

        Link createLink = linkTo(UserSystemRestController.class).slash("create").withRel("create");

        Resources<UserSystemResource> resources = new Resources<>(
                userResourceAssembler.toResources(usersDTO),
                linkTo(UserSystemRestController.class).withSelfRel(),
                createLink
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    //permission:ANYONE
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

    //permission:ANYONE
    @RequestMapping(value = "/login-name/{name}", method = RequestMethod.GET)
    public HttpEntity<UserSystemResource> getCreature(@PathVariable String name) {
        logger.debug("GET user with name=" + name);
        UserSystemDTO userDTO = userFacade.getUserByUserName(name);
        if (userDTO == null) {
            String msg = "User with userName=" + name + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }
        UserSystemResource userResource = userResourceAssembler.toResource(userDTO);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    //permission:ANYONE
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

    //permission:ANYONE
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserSystemVerifiedResource> loginUser(@RequestBody @Valid UserSystemLoginDTO userLoginDTO,
                                                            BindingResult bindingResult) {
        logger.debug("Authenticate user with login name={}", userLoginDTO.getLoginName());
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed when authenticate user with login name=" + userLoginDTO.getLoginName();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }
        UserSystemVerifiedDTO userVerifiedDTO = userFacade.login(userLoginDTO);
        if (userVerifiedDTO == null) {
            String msg = "Authentication of user with login name=" + userLoginDTO.getLoginName() + " has failed.";
            logger.debug(msg);
            throw new AuthenticationFailedException(msg);
        }

        UserSystemVerifiedResource userVerifiedResource = userVerifiedResourceAssembler.toResource(userVerifiedDTO);

        UserSystemDTO user = userFacade.getUserById(userVerifiedDTO.getUserId());
        UserAuthentication userAuthentication = new UserAuthentication(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        tokenAuthenticationService.addAuthentication(httpHeaders, userAuthentication);

        return new ResponseEntity<>(userVerifiedResource, httpHeaders, HttpStatus.OK);
    }

    //permission:USER
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

    //permission:ANYONE
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public HttpEntity<Resources<UserSystemResource>> getUsersOfType(@PathVariable UserType type) {
        logger.debug("GET user of type=" + type);
        List<UserSystemDTO> usersDTO = userFacade.getUsersOfType(type);
        Resources<UserSystemResource> userResources = new Resources<>(
                userResourceAssembler.toResources(usersDTO),
                linkTo(this.getClass()).slash("type").slash(type).withSelfRel());

        return new ResponseEntity<>(userResources, HttpStatus.OK);
    }

    //permission:ANYONE
    @RequestMapping(value = "/sex/{sex}", method = RequestMethod.GET)
    public HttpEntity<Resources<UserSystemResource>> getUsersOfSex(@PathVariable SexType sex) {
        logger.debug("GET user of sex=" + sex);
        List<UserSystemDTO> usersDTO = userFacade.getUsersOfSex(sex);
        Resources<UserSystemResource> userResources = new Resources<>(
                userResourceAssembler.toResources(usersDTO),
                linkTo(this.getClass()).slash("sex").slash(sex).withSelfRel());

        return new ResponseEntity<>(userResources, HttpStatus.OK);
    }

    //permission:AUTHENTICATED_USER
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editUser(@PathVariable long id, @RequestBody @Valid UserSystemUpdateDTO userUpdateDTO,
                         @RequestHeader(AUTH_NAME_HEADER) String token, BindingResult bindingResult) {
        if (userUpdateDTO.getId() != id) {
            String msg = "User edit id mismatch.";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        logger.debug("POST edit user id=" + userUpdateDTO.getId().toString());
        if (bindingResult.hasErrors()) {
            String msg = "Validation failed: editing user id=" + userUpdateDTO.getId().toString() + ": " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }

        if (!TokenHandler.validateUsername(userUpdateDTO.getUserName(), token)) {
            String msg = "Access is denied for user with id=" + id;
            logger.debug(msg);
            throw new AccessDeniedException(msg);
        }

        userFacade.updateUser(userUpdateDTO);
    }

    //permission:ADMIN
    @RequestMapping(value = "/make-admin/{id}", method = RequestMethod.POST)
    public void promoteToAdmin(@PathVariable long id) {
        logger.debug("POST promote user with id={} to admin", id);
        UserSystemDTO userDTO = userFacade.getUserById(id);

        if (userDTO == null) {
            String msg = "User with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }

        userFacade.promoteToAdmin(userDTO);
    }

    //permission:AUTHENTICATED_USER
    @RequestMapping(value = "/make-user/{id}", method = RequestMethod.POST)
    public void degradeToUser(@PathVariable long id, @RequestHeader(AUTH_NAME_HEADER) String token) {
        logger.debug("POST degrade user with id={} from admin to user", id);
        UserSystemDTO userDTO = userFacade.getUserById(id);

        if (userDTO == null) {
            String msg = "User with id=" + id + " not found.";
            logger.debug(msg);
            throw new ResourceNotFoundException(msg);
        }

        if (!TokenHandler.validateUsername(userDTO.getUserName(), token)) {
            String msg = "Access is denied for user with id=" + id;
            logger.debug(msg);
            throw new AccessDeniedException(msg);
        }

        userFacade.degradeToUser(userDTO);
    }

    //permission:AUTHENTICATED_USER
    @RequestMapping(value = "/change-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@RequestBody @Valid UserSystemChangePasswordDTO changePasswordDTO,
                               BindingResult bindingResult, @RequestHeader(AUTH_NAME_HEADER) String token) {
        logger.debug("POST change user password");

        if (bindingResult.hasErrors()) {
            String msg = "Validation failed: " + bindingResult.toString();
            logger.error(msg);
            throw new InvalidRequestFormatException(msg);
        }

        if (!TokenHandler.validateUsername(changePasswordDTO.getUserName(), token)) {
            String msg = "Access is denied for user with id=" + changePasswordDTO.getUserId();
            logger.debug(msg);
            throw new AccessDeniedException(msg);
        }

        if (!userFacade.changePassword(changePasswordDTO)) {
            String msg = "Password change is denied, invalid original password.";
            logger.debug(msg);
            throw new AuthenticationFailedException(msg);
        }
    }

}