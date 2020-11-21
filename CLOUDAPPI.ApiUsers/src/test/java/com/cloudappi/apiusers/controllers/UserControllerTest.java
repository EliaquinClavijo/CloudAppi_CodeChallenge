package com.cloudappi.apiusers.controllers;

import com.cloudappi.apiusers.models.dao.AddressDao;
import com.cloudappi.apiusers.models.dao.UserDao;
import com.cloudappi.apiusers.models.entity.Address;
import com.cloudappi.apiusers.models.entity.User;
import com.cloudappi.apiusers.models.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@WebMvcTest(value = UserController.class)
class UserControllerTest {

    private Address address1 = new Address( 1L, "Urb Inca Garcilaso G-3", "Cusco", "Cusco", "Perú", "08801");

    private User user1 = new User( 1L, "Eliaquin Clavijo M", "eliaquinltm@gmail.com" , null, address1);
    private User user2 = new User( 2L, "Yuliza Palomino T", "yuli@gmail.com" , LocalDateTime.parse("1997-05-18T22:17"), address1);

    private List<User> userList = asList(user1, user2);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDao userDao;

    @MockBean
    private AddressDao addressDao;

    @Test
    void shouldGetUsers() throws Exception {
        // in GetUsers
        // Setup our mock repository
        Mockito.when(userService.getUsers()).thenReturn(userList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users/getUsers").accept(
                MediaType.APPLICATION_JSON);
        // Execute the GET request
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }

    @Test
    void whenValidUser_shouldCreateUser() throws Exception {
        // in CreateUsers
        // Setup our mock repository
        User userToPost = new User( "Eliaquin Clavijo M", "eliaquinltm@gmail.com" , null, address1);
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user1);
        String body = asJsonString(userToPost);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/users/createUsers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        // Execute the POST request
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)));
    }


    @Test
    void whenValidUserId_shouldGetUsersById() throws Exception {
        // in getUsersById
        // Setup our mock repository
        Mockito.doReturn(Optional.of(user1)).when(userService).getUsersById(1L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users/getUsersById/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);
        // Execute the GET request
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Eliaquin Clavijo M")));
    }

    @Test
    void whenInvalidUserIdInGet_shouldReturnNotFound() throws Exception {
        // in getUsersById
        // Setup our mock repository
        Mockito.doReturn(Optional.empty()).when(userService).getUsersById(5L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users/getUsersById/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON);
        // Execute the GET request
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isNotFound())
                // Validate the returned fields
                .andExpect(content().string("User not found"));
    }

    @Test
    void whenValidUserId_shouldUpdateUsersById() throws Exception {
        // in updateUsersById
        // Setup our mock repository
        User userUpdated = new User( 1L,"Eliaquin Clavijo M", "eliaquinltm@outlook.com" , null, address1);
        Mockito.when(userService.updateUsersById(eq(1L), any(User.class))).thenReturn(userUpdated);
        String body = asJsonString(user1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "/users/updateUsersById/{id}", 1L)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("eliaquinltm@outlook.com")));
    }

    @Test
    void whenInvalidUserIdInUpdate_shouldReturnNotFound() throws Exception {
        // in updateUsersById
        // Setup our mock repository
        User userUpdated = new User( 10L,"Eliaquin Clavijo M", "eliaquinltm@outlook.com" , null, address1);
        Mockito.when(userService.updateUsersById(eq(10L), any(User.class))).thenThrow(new EntityNotFoundException("User not found"));
        String body = asJsonString(userUpdated);
        // Execute the PUT request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "/users/updateUsersById/{id}", 10L)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isNotFound())
                // Validate the returned fields
                .andExpect(content().string("User not found"));
    }

    @Test
    void whenValidUserId_ShouldDeleteUsersById() throws Exception {
        // in deleteUsersById
        // Setup our mock repository
        Long userId = 1L;
        // Mockito.when(userService.deleteUsersById(1L)).thenThrow(new EntityNotFoundException("User not found"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/users/deleteUsersById/{id}", 1L);
        // Execute the DELETE request
        mockMvc.perform(requestBuilder)
                // Validate the response code and content type
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
