package com.shoppingapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shoppingapp.exceptions.EmailAlreadyExistException;
import com.shoppingapp.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByEmailAndPassword(String emailId, String password);
	
	public Optional<User> findByFirstName(String firstName);
}
