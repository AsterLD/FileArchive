package com.ld.filearchive.repos;

import com.ld.filearchive.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<User, String> {
    User findUserByUsername(String username);
    User findUserByUserId(String userId);
    List<User> findUsersByUsernameContains(String username);
}
