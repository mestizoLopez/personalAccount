package com.verisk.controller;

import com.verisk.model.Account;
import com.verisk.model.Transaction;
import com.verisk.model.User;
import com.verisk.repository.TransactionRepo;
import com.verisk.service.AccountService;
import com.verisk.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

  private Transaction transaction;

  private User user;

  private Account account;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private TransactionRepo transactionRepo;

  @MockBean
  private TransactionService transactionService;

  @MockBean
  private AccountService accountService;

  @BeforeEach
  public void init(){

    user = User.builder()
            .id(1L).build();

    transaction = Transaction.builder()
            .id(1L)
            .accountId(1L)
            .amount(new BigDecimal(1000))
            .description("ATM deposit")
            .type("deposit")
            .user(user).build();

    account = Account.builder()
            .amount(new BigDecimal(500)).build();

  }

  void findById() throws Exception {
    when(transactionRepo.findById(anyLong())).thenReturn(Optional.of(transaction));

    mvc.perform(MockMvcRequestBuilders
            .get("/transaction/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id",is(1)));

  }

  @Test
  void create() {

    BigDecimal newAmount = account.getAmount().add(transaction.getAmount());

    when(transactionService.create(any(Transaction.class),any(Account.class)))
            .thenReturn(newAmount);

    BigDecimal created = transactionService.create(transaction,account);

    assertEquals(created,newAmount);

  }
}