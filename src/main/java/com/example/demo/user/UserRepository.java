package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // @Query("{'email': ?0}")
    Optional<User> findStudentByEmail(String email);

    public User findStudentByName(String name);
}
