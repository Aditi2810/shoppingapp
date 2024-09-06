package com.shoppingapp.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shoppingapp.exceptions.EmailAlreadyExistException;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public static final Logger log = LogManager.getLogger(UserService.class);

	public User findByEmail(User user) throws EmailAlreadyExistException {
		log.info("Saving user with email : {}", user.getEmail());
		Optional<User> userMailExist = userRepository.findByEmail(user.getEmail());
		if (null != userMailExist && !userMailExist.isEmpty()) {
			log.error("Exception occured : Mail already exist {}", user.getEmail());
			throw new EmailAlreadyExistException("Email already exists " + user.getEmail());
		}
		return userRepository.save(user);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Optional<User> validateUser(String emailId, String password) {
		log.info("Validating user with emailId: {}", emailId);
        Optional<User> user = userRepository.findByEmailAndPassword(emailId, password);
        if (user.isPresent()) {
        	log.info("User with emailId: {} validated successfully", emailId);
            return user;
        } else {
            return Optional.empty();
        }
    }

	public Optional<User> findByFistName(String firstName){
		return userRepository.findByFirstName(firstName);
	}
	
	public void updatePassword(String firstName, String newPassword) {
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where("firstName").is(firstName));
			
			List<User> result = mongoTemplate.find(query, User.class);
			System.out.println(result);
			
			Update update = new Update();
			update.set("password", newPassword);
			update.set("confirmPassword", newPassword);
			
			mongoTemplate.findAndModify(query, update, User.class);
		}catch(Exception e) {
			log.error("Exception in updatePassword() in UserService");
			throw e;
		}
	}
	
}
