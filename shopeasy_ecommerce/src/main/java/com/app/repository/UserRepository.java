package com.app.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.collections.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
	
	User findByResetPasswordToken(String token);
	

}
