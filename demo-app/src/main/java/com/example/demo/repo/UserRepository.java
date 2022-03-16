package com.example.demo.repo;

import com.example.demo.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author i565244
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
