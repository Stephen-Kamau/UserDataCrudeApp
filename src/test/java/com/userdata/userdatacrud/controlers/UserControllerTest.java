package com.userdata.userdatacrud.controlers;
import com.userdata.userdatacrud.models.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers() {
        // Arrange
        List<UserData> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserData> result = userController.getAllUsers();

        // Assert
        assertEquals(userList, result);
    }

    @Test
    void addUser() {
        // Arrange
        UserData user = new UserData();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        ResponseEntity<UserData> responseEntity = userController.addUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void getUserById_UserExists() {
        // Arrange
        Long userId = 1L;
        UserData user = new UserData();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<UserData> responseEntity = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void getUserById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserData> responseEntity = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteUser_UserExists() {
        // Arrange
        Long userId = 1L;
        UserData user = new UserData();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<HttpStatus> responseEntity = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<HttpStatus> responseEntity = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(userRepository, never()).delete(any(UserData.class));
    }

    @Test
    void updateUserById_UserExists() {
        // Arrange
        Long userId = 1L;
        UserData existingUser = new UserData();
        UserData updatedUser = new UserData();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // Act
        ResponseEntity<UserData> responseEntity = userController.updateUserById(userId, updatedUser);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void updateUserById_UserNotFound() {
        // Arrange
        Long userId = 1L;
        UserData updatedUser = new UserData();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserData> responseEntity = userController.updateUserById(userId, updatedUser);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

