package com.verisk.service;

import com.verisk.model.User;
import com.verisk.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserServiceTest {

  private User user;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  public void init(){
    MockitoAnnotations.initMocks(this);

    user = User.builder()
            .id(1L)
            .name("Javier")
            .lastName("Lopez")
            .ssn("1234-56-789")
            .voteCard("123456789").build();

  }

  @Test
  void finById(){

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    User userOptional = userService.findById(anyLong()).get();

    assertEquals(userOptional.getId(),1);

  }

  @Test
  void findAll() {

    List<User> userList = Arrays.asList(user);

    when(userRepository.findAll()).thenReturn(userList);

    assertEquals(userService.findAll().size(),userList.size());

  }

  @Test
  void save() {
    when(userRepository.save(any(User.class))).thenReturn(user);
  }
}