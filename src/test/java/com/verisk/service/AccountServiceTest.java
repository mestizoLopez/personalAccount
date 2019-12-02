package com.verisk.service;

import com.verisk.model.Account;
import com.verisk.model.User;
import com.verisk.repository.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AccountServiceTest {

  private Account account;
  private User user;

  @Mock
  private AccountRepo accountRepo;

  @InjectMocks
  private AccountService accountService;

  @BeforeEach
  public void init(){
    MockitoAnnotations.initMocks(this);

    user = User.builder()
            .id(1L).build();

    account = Account.builder()
            .id(1L)
            .user(user)
            .amount(new BigDecimal(1000))
            .pin("1234")
            .status("open")
            .type("debit").build();

  }

  @Test
  void save() throws Exception {

    when(accountRepo.save(any(Account.class)))
            .thenReturn(account);
    Account created = accountRepo.save(account);

    assertEquals(created.getId(),account.getId());

  }

  @Test
  void findById() {

    when(accountRepo.findById(anyLong()))
            .thenReturn(Optional.of(account));
    Account accountOptional = accountService.findById(anyLong()).get();
    assertEquals(accountOptional.getId(),1);

  }
}