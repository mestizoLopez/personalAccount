package com.verisk.controller;

import com.verisk.exception.ResourceNotFoundException;
import com.verisk.model.Account;
import com.verisk.model.Transaction;
import com.verisk.service.AccountService;
import com.verisk.service.TransactionService;
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
@RequestMapping(value = "/transaction")
public class TransactionController {

  Logger log = LoggerFactory.getLogger(TransactionController.class);

  @Autowired
  private TransactionService tranService;

  @Autowired
  private AccountService accService;

  @GetMapping(value = "/account/{id}")
  public ResponseEntity findByAccountId(@Valid @PathVariable Long id){

    if(!accService.findById(id).isPresent()){
      log.error("Id " + id + " is not existed");
      throw new  ResourceNotFoundException("Id " + id + " is not existed");
    }

    return ResponseEntity.ok(tranService.findByAccountId(id));

  }

  @PostMapping(value = "/account/{id}")
  public ResponseEntity create(@Valid @PathVariable Long id, @RequestBody Transaction transaction){

    if(!accService.findById(id).isPresent()){
      log.error("Id " + id + " is not existed");
      throw new  ResourceNotFoundException("Id " + id + " is not existed");
    }
  Account account = accService.findById(id).get();

  return ResponseEntity.ok("Current balance is "+tranService.create(transaction,account));

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
