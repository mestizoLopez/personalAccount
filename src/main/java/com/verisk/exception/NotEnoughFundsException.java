package com.verisk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotEnoughFundsException extends RuntimeException{

  public NotEnoughFundsException(){
    super();
  }

  public NotEnoughFundsException(String message) {
    super(message);
  }

}
