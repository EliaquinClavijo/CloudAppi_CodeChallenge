package com.cloudappi.apiusers.models.service;

import com.cloudappi.apiusers.models.dao.AddressDao;
import com.cloudappi.apiusers.models.dao.UserDao;
import com.cloudappi.apiusers.models.entity.Address;
import com.cloudappi.apiusers.models.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private Address address1 = new Address( 1L, "Urb Inca Garcilaso G-3", "Cusco", "Cusco", "Perú", "08801");
    private Address address2 = new Address( 2L, "Av. Los Incas SN", "San Sebastian", "Cusco", "Perú", "08800");


    private User user1 = new User( 1L, "Eliaquin Clavijo M", "eliaquinltm@gmail.com" , LocalDateTime.parse("1997-01-04T22:17"), address1);
    private User user2 = new User(2L, "Yuliza Palomino T", "yuli@gmail.com" , LocalDateTime.parse("1997-05-18T22:17"), address1);

    private List<User> userList = asList(user1, user2);

    @Mock
    private UserDao userDao;

    @Mock
    private AddressDao addressDao;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldGetAllUsers() {
        // in getUsers
        // Setup our mock repository
        Mockito.doReturn(userList).when(userDao).findAll();
        Object[] expected = userList.toArray();

        // Execute the service call  // Assert the response
        Assertions.assertArrayEquals(expected, userService.getUsers().toArray());
    }

    @Test
    void whenValidUserId_thenUserShouldbeFound() {
        // in GetUsersById
        // Setup our mock repository
        final Long userId = 1L;
        Mockito.doReturn(Optional.of(user1)).when(userDao).findById(1L);
        // Execute the service call
        final Optional<User> user = userService.getUsersById(userId);
        // Assert the response
        Assertions.assertTrue(user.isPresent(), "User was not found");
        Assertions.assertSame(user.get(), user1, "The user returned was not the same as the mock");
    }

    @Test
    void whenInvalidUserId_thenUserNotShouldbeFound() {
        // in GetUsersById
        // Setup our mock repository
        final Long userId = 3L;
        Mockito.doReturn(Optional.empty()).when(userDao).findById(3L);
        // Execute the service call
        final Optional<User> user = userService.getUsersById(userId);
        // Assert the response
        Assertions.assertFalse(user.isPresent(), "Used should not be found");
    }

    /***@Test
    public void whenInvalidUserId_thenUserShouldNotbeFound() {
        // Setup our mock repository
        final Long userId = (long) 2;
        // Execute the service call
        EntityNotFoundException entityNotFoundException = assertThrows( EntityNotFoundException.class,
                () ->  userService.GetUsersById(userId), "Expected return a Value");
        Assertions.assertTrue(entityNotFoundException.getMessage().contains("User not found"));
    }***/

    @Test
    void whenValidUser_thenUserShouldSave() {
        // in createUser
        // Setup our mock repository
        Mockito.doReturn(address1).when(addressDao).save(address1);
        User newUserMock = new User( 3L, "Richard Zanner C.", "richard@gmail.com" , LocalDateTime.parse("1997-03-10T22:17"), address1);
        // Execute the service call
        userService.createUser(newUserMock);
        // Assert the response
        verify(userDao, times(1)).save(newUserMock);
    }

    @Test
    void whenValidUser_thenUserShouldUpdate() {
        // in updateUsersById
        // Setup our mock repository
        Long userId = 1L;
        User User1ToUpdateMock = new User( 1L, "Eliaquin Clavijo M", "eliaquinltm@outlook.com" , LocalDateTime.parse("1997-01-04T22:17"), address2);
        Mockito.doReturn(Optional.of(user1)).when(userDao).findById(1L);
        Mockito.doReturn(address2).when(addressDao).save(address2);
        Mockito.doReturn(User1ToUpdateMock).when(userDao).save(User1ToUpdateMock);
        // Execute the service call
        User userUpdated = userService.updateUsersById(userId, User1ToUpdateMock);
        // Assert the response
        verify(userDao).save(any(User.class));
        Assertions.assertNotNull(userUpdated, "User was not found");
        Assertions.assertEquals(User1ToUpdateMock.getEmail(), userUpdated.getEmail(), "Its diferent");
    }

    @Test
    void whenInvalidUser_thenUpdateShouldThrowEx() {
        // in updateUsersById
        // Setup our mock repository
        Long userId = 4L;
        User User1ToUpdateMock = new User( 4L, "Eliaquin Clavijo M", "eliaquinltm@outlook.com" , LocalDateTime.parse("1997-01-04T22:17"), address2);
        Mockito.doReturn(Optional.empty()).when(userDao).findById(4L);
        // Execute the service call, // Assert the response
        EntityNotFoundException entityNotFoundException = assertThrows( EntityNotFoundException.class,
                () ->  userService.updateUsersById(userId, User1ToUpdateMock), "Expected not return a value");
        Assertions.assertTrue(entityNotFoundException.getMessage().contains("User not found"));
    }

    @Test
    void whenValidUser_ThenUserShouldBeDeleted() {
        // in DeleteUsersById
        // Setup our mock repository
        Long userId = 1L;
        Mockito.doReturn(Optional.of(user1)).when(userDao).findById(1L);
        // Execute the service call
        userService.deleteUsersById(userId);
        // Assert the response
        verify(userDao, times(1)).delete(user1);
    }

    @Test
    void whenInvalidUser_thenDeleteShouldThrowEx() {
        // in DeleteUsersById
        // Setup our mock repository
        Long userId = 1L;
        Mockito.doReturn(Optional.empty()).when(userDao).findById(1L);
        // Execute the service call, // Assert the response
        EntityNotFoundException entityNotFoundException = assertThrows( EntityNotFoundException.class,
                () ->  userService.deleteUsersById(userId), "Expected not return a value");
        Assertions.assertTrue(entityNotFoundException.getMessage().contains("User not found"));
    }
}
