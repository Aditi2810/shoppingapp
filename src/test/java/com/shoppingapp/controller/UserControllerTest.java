package com.shoppingapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.shoppingapp.exceptions.EmailAlreadyExistException;
import com.shoppingapp.model.User;
import com.shoppingapp.security.JwtService;
import com.shoppingapp.service.AuthenticationService;
import com.shoppingapp.service.KafkaProducerService;
import com.shoppingapp.service.UserService;

class UserControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private JwtService mockJwtService;
    @Mock
    private AuthenticationManager mockManager;
    @Mock
    private AuthenticationService mockAuthenticationService;
    @Mock
    private UserDetailsService mockUserDetailsService;
    @Mock
    private KafkaProducerService mockKafkaProducerService;

    @InjectMocks
    private UserController userControllerUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testResigterUser() throws EmailAlreadyExistException {
        // Setup
        final User user = new User();
        user.setLoginId("loginId");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setConfirmPassword("confirmPassword");
        user.setContactNumber("contactNumber");

        // Configure UserService.findByEmail(...).
        final User user1 = new User();
        user1.setLoginId("loginId");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setConfirmPassword("confirmPassword");
        user1.setContactNumber("contactNumber");
        when(mockUserService.findByEmail(any(User.class))).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.resigterUser(user);

        // Verify the results
        verify(mockUserService).findByEmail(any(User.class));
    }

    @Test
    void testResigterUser_UserServiceThrowsEmailAlreadyExistException() throws EmailAlreadyExistException {
        // Setup
        final User user = new User();
        user.setLoginId("loginId");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setConfirmPassword("confirmPassword");
        user.setContactNumber("contactNumber");

        when(mockUserService.findByEmail(any(User.class))).thenThrow(EmailAlreadyExistException.class);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.resigterUser(user);

    }

    @Test
    void testLogin() {
        // Setup
        // Configure UserService.validateUser(...).
        final User user1 = new User();
        user1.setLoginId("loginId");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setConfirmPassword("confirmPassword");
        user1.setContactNumber("contactNumber");
        final Optional<User> user = Optional.of(user1);
        when(mockUserService.validateUser("emailId", "password")).thenReturn(user);

        when(mockJwtService.generateToken(any(UserDetails.class))).thenReturn("result");

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.login("username", "password");

        // Verify the results
        verify(mockKafkaProducerService).sendMessageToTopic("message");
    }

    @Test
    void testLogin_UserServiceReturnsAbsent() {
        // Setup
        when(mockUserService.validateUser("emailId", "password")).thenReturn(Optional.empty());
        when(mockJwtService.generateToken(any(UserDetails.class))).thenReturn("result");

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.login("username", "password");

        // Verify the results
        verify(mockKafkaProducerService).sendMessageToTopic("message");
    }

    @Test
    void testUpdatepassowrd() {
        // Setup
        // Configure UserService.findByFistName(...).
        final User user1 = new User();
        user1.setLoginId("loginId");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setConfirmPassword("confirmPassword");
        user1.setContactNumber("contactNumber");
        final Optional<User> user = Optional.of(user1);
        when(mockUserService.findByFistName("firstName")).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.updatepassowrd("firstName", "newPassword");

        // Verify the results
        verify(mockUserService).updatePassword("firstName", "newPassword");
  
    }

    @Test
    void testUpdatepassowrd_UserServiceFindByFistNameReturnsAbsent() {
        // Setup
        when(mockUserService.findByFistName("firstName")).thenReturn(Optional.empty());

        // Run the test
        final ResponseEntity<?> result = userControllerUnderTest.updatepassowrd("firstName", "newPassword");

        // Verify the results
        verify(mockUserService).updatePassword("firstName", "newPassword");
    }
}
