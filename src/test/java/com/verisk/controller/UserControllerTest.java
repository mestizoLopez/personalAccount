package com.verisk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verisk.model.User;
import com.verisk.service.UserService;
import org.aspectj.lang.annotation.Before;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

  private static final ObjectMapper om = new ObjectMapper();

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  private User user;

  @BeforeEach
  public void init(){
     user = User.builder()
            .name("Javier")
            .lastName("Lopez")
            .ssn("1234-56-789")
            .voteCard("123456789")
            .build();
  }

  @Test
  void findById() throws Exception{

    when(userService.findById(1L)).thenReturn(Optional.of(user));

    mvc.perform(MockMvcRequestBuilders
            .get("/user/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{'name':'Javier','lastName':'Lopez','ssn':'1234-56-789','voteCard':'123456789'}"));
  }

  @Test
  void findAll() throws Exception {

    List<User> userList = Arrays.asList(user);
    given(userService.findAll()).willReturn(userList);

    mvc.perform(MockMvcRequestBuilders
            .get("/user/all")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("[{'name':'Javier','lastName':'Lopez','ssn':'1234-56-789','voteCard':'123456789'}]"));

  }

  @Test
  void newUser() throws Exception {

    given(userService.save(any(User.class))).willReturn(user);

    mvc.perform(MockMvcRequestBuilders
            .post("/user/new")
            .content(om.writeValueAsString(user))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'name':'Javier','lastName':'Lopez','ssn':'1234-56-789','voteCard':'123456789'}"));

  }

}