package com.verisk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verisk.model.Account;
import com.verisk.model.User;
import com.verisk.service.AccountService;
import com.verisk.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

  private static final ObjectMapper om = new ObjectMapper();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private TransactionService transactionService;

  private Account account;

  private User user;

  @BeforeEach
  void init(){

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
  void findById() throws Exception {

    given(accountService.findById(1L)).willReturn(Optional.of(account));

    mvc.perform(MockMvcRequestBuilders
    .get("/account/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id",is(1)));

  }

  @Test
  void save() throws Exception {

    when(accountService.save(any(Account.class)))
            .thenReturn(account);

    mvc.perform(MockMvcRequestBuilders
            .post("/account/new")
            .content(om.writeValueAsString(account))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id",is(1)));

  }

  @Test
  void statusChange() throws Exception{

    when(accountService.save(any(Account.class)))
            .thenReturn(account);

    mvc.perform(MockMvcRequestBuilders
            .put("/account/status/{id}",1)
            .content(om.writeValueAsString(account))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status",is("open")));

  }
}