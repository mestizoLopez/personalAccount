package com.verisk.service;

import com.verisk.exception.NotEnoughFundsException;
import com.verisk.model.Account;
import com.verisk.model.Transaction;
import com.verisk.repository.AccountRepo;
import com.verisk.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepo tranRepo;

  @Autowired
  private AccountRepo accountRepo;

  public Optional<Transaction> findById(Long id){
    return tranRepo.findById(id);
  }

  public BigDecimal create(Transaction transaction, Account account){

      BigDecimal currentAmount = account.getAmount();
      BigDecimal tranAmount = transaction.getAmount();
    if(transaction.getType().equalsIgnoreCase("deposit")){
      currentAmount =  currentAmount.add(tranAmount);
    }else if(transaction.getType().equalsIgnoreCase("withdrawal")){
        if(currentAmount.compareTo(tranAmount) == -1){
          throw new NotEnoughFundsException("You don't have enough funds to complete this transaction");
        }

      currentAmount = currentAmount.subtract(tranAmount);
    }

    transaction.setAmount(tranAmount);
    transaction.setAccountId(account.getId());
    account.setAmount(currentAmount);

    tranRepo.save(transaction);
    accountRepo.save(account);
    return account.getAmount();
  }

  public List<Transaction> findTop5ByAccountIdOrderByDateDesc(Long id){
    return tranRepo.findTop5ByAccountIdOrderByDateDesc(id);
  }

  public Transaction save(Transaction transaction){
    return tranRepo.save(transaction);
  }

  public Optional<Transaction> findByAccountId(Long id){
    return tranRepo.findByAccountId(id);
  }

}
