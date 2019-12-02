package com.verisk.service;

import com.verisk.model.User;
import com.verisk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Optional<User> findById(Long id){
    return userRepository.findById(id);
  }

  public List<User> findAll(){
    return userRepository.findAll();
  }

  public User save(User user){
    return userRepository.save(user);
  }

}