package com.verisk.controller;

import com.verisk.exception.ResourceNotFoundException;
import com.verisk.model.User;
import com.verisk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  UserService userService;

  @GetMapping(value = "/{id}")
  public ResponseEntity findById(@Valid @PathVariable Long id){

    if(!userService.findById(id).isPresent()){
      log.error("Id " + id + " is not existed");
      throw new ResourceNotFoundException("Id " + id + " is not existed");
    }
    User user = userService.findById(id).get();

    return ResponseEntity.ok(user);

  }

  @GetMapping(value = "/all")
  public ResponseEntity findAll(){
    return ResponseEntity.ok(userService.findAll());
  }

  @PostMapping(value = "/new")
  public ResponseEntity newUser(@Valid @RequestBody User user){
    return ResponseEntity.ok(userService.save(user));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
          MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }


}
