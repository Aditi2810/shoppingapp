package com.shoppingapp.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.exceptions.EmailAlreadyExistException;
import com.shoppingapp.model.User;
import com.shoppingapp.security.JwtService;
import com.shoppingapp.service.AuthenticationService;
import com.shoppingapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/api/v1.0/shoppingapp")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
    @Autowired
	private AuthenticationManager manager;
    
    @Autowired
    private AuthenticationService authenticationService;
   
	@Autowired
	private UserDetailsService userDetailsService;

	public static final Logger log = LogManager.getLogger(UserController.class);

	@PostMapping(value = "/register")
	public ResponseEntity<?> resigterUser(@Valid @RequestBody User user) {
		log.info("Inside resigterUser() in UserController");
		try {
			if (!user.getPassword().equals(user.getConfirmPassword())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password and Confirm Password must be same");
			}
			userService.findByEmail(user);

		} catch (EmailAlreadyExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			log.error("Exception in resigterUser() of UserController");
		}
		return ResponseEntity.ok("User Saved Successfully");
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
	try {
		Optional<User> validatedUser = userService.validateUser(username, password);
		if(validatedUser.isPresent()) {
			UserDetails user = userDetailsService.loadUserByUsername(username);
			System.out.println(user);
			return ResponseEntity.ok(jwtService.generateToken(user));
		}
	}catch(Exception e) {
		log.error("User with emailId: {} not found", username);
	}
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password"); 
  }
	
	@GetMapping(value = "{firstName}/forgot")
	public ResponseEntity<?> updatepassowrd(@PathVariable String firstName, @RequestParam("newPassword") String newPassword) {
		log.info("Inside updatepassowrd() in UserController");
		try {
			if (null != firstName && null != newPassword) {
				Optional<User> user = userService.findByFistName(firstName);
				if (user.isPresent()) {
					userService.updatePassword(firstName, newPassword);
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter correct firstname");
				}
			}
		} catch (Exception e) {
			log.error("User with firstname: {} not found", firstName);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
	}
}
