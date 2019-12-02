package com.verisk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verisk.model.Account;
import com.verisk.model.Transaction;
import com.verisk.model.User;
import com.verisk.repository.AccountRepo;
import com.verisk.repository.TransactionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

  private static final ObjectMapper om = new ObjectMapper();

  @Mock
  private TransactionRepo transactionRepo;

  @Mock
  private AccountRepo accountRepo;

  @InjectMocks
  private TransactionService transactionService;

  private Transaction transaction;
  private Account account;
  private User user;

  @BeforeEach

  public void init(){
    user = User.builder()
            .id(1L).build();

    account = Account.builder()
            .id(1L)
            .user(user)
            .amount(new BigDecimal(1000))
            .pin("1234")
            .status("open")
            .type("debit").build();

     transaction = Transaction.builder()
            .id(1L)
            .accountId(1L)
            .amount(new BigDecimal(500))
            .description("ATM deposit")
            .type("deposit")
            .user(user).build();

    MockitoAnnotations.initMocks(this);
  }

  @Test
  void findById() throws Exception {

    when(transactionRepo.findById(anyLong()))
            .thenReturn(Optional.of(transaction));
    Optional<Transaction> transactionOptional = transactionService.findById(anyLong());

    assertEquals(1,transactionOptional.get().getAccountId());

  }

  @Test
  void addAmount() throws Exception {


    assertEquals(transaction.getType(),"deposit");
    BigDecimal newAmount = account.getAmount().add(transaction.getAmount());
    assertEquals(newAmount.compareTo(account.getAmount()),1);

    when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);
    when(accountRepo.save(any(Account.class))).thenReturn(account);

  }

  @Test
  void substractAmount() throws Exception {

    transaction.setType("withdrawal");

    assertEquals(transaction.getType(),"withdrawal");
    BigDecimal newAmount = account.getAmount().subtract(transaction.getAmount());
    assertEquals(newAmount.compareTo(account.getAmount()),-1);

    when(transactionRepo.save(any(Transaction.class))).thenReturn(transaction);
    when(accountRepo.save(any(Account.class))).thenReturn(account);

  }

  @Test
  void findTop5ByAccountIdOrderByDateDesc() {

    List<Transaction> transactionList = Arrays.asList(transaction);
    when(transactionRepo.findTop5ByAccountIdOrderByDateDesc(anyLong()))
            .thenReturn(transactionList);

    List<Transaction> tranList = transactionService.findTop5ByAccountIdOrderByDateDesc(anyLong());
    assertEquals(tranList.size(),transactionList.size());

  }

  @Test
  void save(){

    when(transactionRepo.save(any(Transaction.class)))
            .thenReturn(transaction);

    Transaction created = transactionService.save(transaction);

    assertEquals(created.getId(),transaction.getId());

  }

  @Test
  public void findByAccountId(){

    when(transactionRepo.findByAccountId(anyLong())).thenReturn(Optional.of(transaction));

    Transaction founded = transactionService.findByAccountId(1L).get();

    assertEquals(founded.getId(),transaction.getId());
  }

}