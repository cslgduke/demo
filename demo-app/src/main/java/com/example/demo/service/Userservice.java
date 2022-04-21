package com.example.demo.service;

import com.example.demo.bo.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author i565244
 */
@Component
public class Userservice {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation=Propagation.REQUIRED)
    public User save(User user){
      return userRepository.save(user);
    }
}
