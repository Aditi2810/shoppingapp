package com.shoppingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.shoppingapp.exceptions.EmailAlreadyExistException;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private MongoTemplate mockMongoTemplate;

    @InjectMocks
    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testFindByEmail1() throws Exception {
        // Setup
        final User user = new User();
        user.setLoginId("loginId");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setConfirmPassword("confirmPassword");
        user.setContactNumber("contactNumber");

        // Configure UserRepository.findByEmail(...).
        final User user2 = new User();
        user2.setLoginId("loginId");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setEmail("email");
        user2.setPassword("password");
        user2.setConfirmPassword("confirmPassword");
        user2.setContactNumber("contactNumber");
        final Optional<User> user1 = Optional.of(user2);
        when(mockUserRepository.findByEmail("email")).thenReturn(user1);

        // Run the test
        final User result = userServiceUnderTest.findByEmail(user);

        // Verify the results
    }

    @Test
    void testFindByEmail1_UserRepositoryReturnsAbsent() throws Exception {
        // Setup
        final User user = new User();
        user.setLoginId("loginId");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setConfirmPassword("confirmPassword");
        user.setContactNumber("contactNumber");

        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        final User result = userServiceUnderTest.findByEmail(user);

        // Verify the results
    }

    @Test
    void testFindByEmail1_ThrowsEmailAlreadyExistException() {
        // Setup
        final User user = new User();
        user.setLoginId("loginId");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setConfirmPassword("confirmPassword");
        user.setContactNumber("contactNumber");

        // Configure UserRepository.findByEmail(...).
        final User user2 = new User();
        user2.setLoginId("loginId");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setEmail("email");
        user2.setPassword("password");
        user2.setConfirmPassword("confirmPassword");
        user2.setContactNumber("contactNumber");
        final Optional<User> user1 = Optional.of(user2);
        when(mockUserRepository.findByEmail("email")).thenReturn(user1);

        // Run the test
        assertThrows(EmailAlreadyExistException.class, () -> userServiceUnderTest.findByEmail(user));
    }

    @Test
    void testFindByEmail2() {
        // Setup
        // Configure UserRepository.findByEmail(...).
        final User user1 = new User();
        user1.setLoginId("loginId");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setConfirmPassword("confirmPassword");
        user1.setContactNumber("contactNumber");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByEmail("email")).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.findByEmail("email");

        // Verify the results
    }

    @Test
    void testFindByEmail2_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.findByEmail("email");

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testValidateUser() {
        // Setup
        // Configure UserRepository.findByEmailAndPassword(...).
        final User user1 = new User();
        user1.setLoginId("loginId");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setConfirmPassword("confirmPassword");
        user1.setContactNumber("contactNumber");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByEmailAndPassword("emailId", "password")).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.validateUser("emailId", "password");

        // Verify the results
    }

    @Test
    void testValidateUser_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByEmailAndPassword("emailId", "password")).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.validateUser("emailId", "password");

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testFindByFistName() {
        // Setup
        // Configure UserRepository.findByFirstName(...).
        final User user1 = new User();
        user1.setLoginId("loginId");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setConfirmPassword("confirmPassword");
        user1.setContactNumber("contactNumber");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByFirstName("firstName")).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.findByFistName("firstName");

        // Verify the results
    }

    @Test
    void testFindByFistName_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByFirstName("firstName")).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.findByFistName("firstName");

        // Verify the results
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testUpdatePassword() {
        // Setup
        // Run the test
        userServiceUnderTest.updatePassword("firstName", "newPassword");

        // Verify the results
    }
}
