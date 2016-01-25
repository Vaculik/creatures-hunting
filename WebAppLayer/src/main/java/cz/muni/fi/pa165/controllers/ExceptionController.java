package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.exceptions.AccessDeniedException;
import cz.muni.fi.pa165.exceptions.AuthenticationFailedException;
import cz.muni.fi.pa165.exceptions.InvalidRequestFormatException;
import cz.muni.fi.pa165.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.hateoas.ExceptionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Karel Vaculik
 */

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResource> handleExcpetion(Exception e) {
        String exceptionName = e.getClass().getSimpleName();
        ExceptionResource resource = new ExceptionResource(exceptionName, e.getMessage());

        HttpStatus statusCode;
        if (e instanceof AuthenticationFailedException || e instanceof AccessDeniedException) {
            statusCode = HttpStatus.UNAUTHORIZED;
        } else if(e instanceof InvalidRequestFormatException) {
            statusCode = HttpStatus.BAD_REQUEST;
        } else if(e instanceof ResourceNotFoundException) {
            statusCode = HttpStatus.NOT_FOUND;
        } else {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        logger.debug("Handle excpetion (name={}; message={}), HttpStatus={}",
                exceptionName, e.getMessage(), statusCode);
        logger.debug(e.getStackTrace().toString());

        return new ResponseEntity<>(resource, statusCode);
    }
}
