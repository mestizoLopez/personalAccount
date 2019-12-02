package com.verisk.service;

import com.verisk.exception.ResourceNotFoundException;
import com.verisk.model.Account;
import com.verisk.model.Transaction;
import com.verisk.repository.AccountRepo;
import com.verisk.repository.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  Logger log = LoggerFactory.getLogger(AccountService.class);

  @Autowired
  private AccountRepo accRepo;

  @Autowired
  private TransactionRepo transactionRepo;

  public Account save(Account account){
    return accRepo.save(account);
  }

  public Optional<Account> findById(Long id){
    return accRepo.findById(id);
  }

}
