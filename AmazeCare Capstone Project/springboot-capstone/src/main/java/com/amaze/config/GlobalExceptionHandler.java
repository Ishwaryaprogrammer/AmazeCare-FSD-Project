package com.amaze.config;


import com.amaze.exception.*;
import com.amaze.utility.ResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private ResponseUtility responseUtility;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtility> handlerResourceNotFoundException(ResourceNotFoundException e){
        responseUtility = new ResponseUtility(); // without this line, it throws 500 internal server error for resourcenotfoundexception
        logger.error("Error:{}", e.getMessage());
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, Principal principal
    ){
        BindingResult bindingResult =  e.getBindingResult();
        List<FieldError> errors =  bindingResult.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for (FieldError error : errors) {
            map.put(error.getField(), error.getDefaultMessage());
            logger.error("Field {} - message: {}", error.getField(), error.getDefaultMessage());
            }

        return ResponseEntity
                .badRequest()
                .body(map);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ResponseUtility> handlerProfileNotFoundException(
            ProfileNotFoundException e,Principal principal){
        responseUtility = new ResponseUtility(); // without this line, it throws 500 internal server error for profilenotfoundexception

        responseUtility.setMessage(e.getMessage());
         return ResponseEntity.badRequest().body(responseUtility);
    }

    @ExceptionHandler(TimeOverlapException.class)
    public ResponseEntity<ResponseUtility> handlerTimeOverlapException(TimeOverlapException e, Principal principal){
        responseUtility = new ResponseUtility(); // without this line, it throws 500 internal server error for timeoverlapexception

        responseUtility.setMessage(e.getMessage());
          return ResponseEntity.badRequest().body(responseUtility);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseUtility> handlerRuntimeException(RuntimeException e ){
        responseUtility = new ResponseUtility(); // without this line, it throws 500 internal server error for any runtimeException
        responseUtility.setMessage(e.getMessage());
          return ResponseEntity.badRequest().body(responseUtility);
    }


    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseUtility> handleFileNotFoundException(
            FileNotFoundException e
    ){
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtility);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseUtility> handleIOException(
            IOException e
    ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtility);
    }

    @ExceptionHandler(FileInvalidExtensionException.class)
    public ResponseEntity<ResponseUtility> handleFileInvalidExtensionException(
            FileInvalidExtensionException e
    ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtility);
    }


    public ResponseEntity<ResponseUtility> handleMalformedURLException(
            MalformedURLException e
    ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtility);
    }

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity<ResponseUtility> handlerUserNameExistsException(UserNameExistsException e ){
        responseUtility = new ResponseUtility(); // without this line, it throws 500 internal server error for any runtimeException
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<ResponseUtility> handlerUserEmailAlreadyExistsException(UserEmailAlreadyExistsException e ){
        responseUtility = new ResponseUtility(); // without this line, it throws 500 internal server error for any runtimeException
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }

    @ExceptionHandler(ConcurrentSlotBookingException.class)
    public ResponseEntity<ResponseUtility> handlerConcurrentSlotBookingException(ConcurrentSlotBookingException e ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }
    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<ResponseUtility> handlerAppointmentException(AppointmentException e ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }
    @ExceptionHandler(EndTimeInvalidException.class)
    public ResponseEntity<ResponseUtility> handlerEndTimeInvalidException(EndTimeInvalidException e ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }
    @ExceptionHandler(ConsultationException.class)
    public ResponseEntity<ResponseUtility> handlerConsultationException(ConsultationException e ){
        responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }






}
