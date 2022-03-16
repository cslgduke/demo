package com.example.demo.jpa;

import com.example.demo.bo.User;
import com.example.demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author i565244
 */
@SpringBootTest
@Slf4j
public class JpaTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testBatchInsert(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            users.add(user);
        }
        userRepository.saveAll(users);
    }
}
