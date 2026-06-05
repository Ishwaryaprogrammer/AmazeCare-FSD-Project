package com.hiretrack.config;


import com.hiretrack.exception.ResourceNotFoundException;
import com.hiretrack.utility.ResponseUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    private ResponseUtility responseUtility;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtility> handlerResourceNotFoundException(ResourceNotFoundException e){
        ResponseUtility responseUtility = new ResponseUtility();
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
         }

        return ResponseEntity
                .badRequest()
                .body(map);
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseUtility> handlerRuntimeException(RuntimeException e ){
        ResponseUtility responseUtility = new ResponseUtility();
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtility);
    }






}
