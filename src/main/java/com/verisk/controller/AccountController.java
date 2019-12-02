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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

  Logger log = LoggerFactory.getLogger(AccountController.class);

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  @GetMapping(value = "/{id}")
  public ResponseEntity findById(@PathVariable Long id){

    if(!accountService.findById(id).isPresent()){
      log.error("Id " + id + " is not existed");
      throw new  ResourceNotFoundException("Id " + id + " is not existed");
    }

    Account accountOptional = accountService.findById(id).get();

    List<Transaction> transactionList = transactionService.findTop5ByAccountIdOrderByDateDesc(id);
    accountOptional.setTransactions(transactionList);

    return ResponseEntity.ok(accountOptional);

  }

  @PostMapping(value = "/new")
  public ResponseEntity save(@Valid @RequestBody Account account){
    return ResponseEntity.ok(accountService.save(account));
  }

  @PutMapping(value = "/status/{id}")
  public ResponseEntity statusChange(@Valid @PathVariable Long id, @RequestBody Account account ){

    if(!accountService.findById(id).isPresent()){
      log.error("Id " + id + " is not existed");
      throw new  ResourceNotFoundException("Id " + id + " is not existed");
    }

    Account accountOptional = accountService.findById(id).get();

    accountOptional.setStatus(account.getStatus());

    return ResponseEntity.ok(accountService.save(account));
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
