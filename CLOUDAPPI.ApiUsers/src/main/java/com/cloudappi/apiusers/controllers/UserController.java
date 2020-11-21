package com.cloudappi.apiusers.controllers;

import com.cloudappi.apiusers.models.entity.User;
import com.cloudappi.apiusers.models.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("getUsers")
	@Operation(summary = "Get all users")
	public List<User> getUsers() {
			return userService.getUsers();
	} 
	
	@PostMapping("createUsers")
	public ResponseEntity<User> createUser(@RequestBody @Valid User newUser) {
		return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.CREATED);
	}
	
	@GetMapping("getUsersById/{userId}")
	@Operation(summary = "Get one user")
	public ResponseEntity<User> getUsersById(@PathVariable Long userId) {
		return userService.getUsersById(userId).map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityNotFoundException(
						"User not found"));
	}

	@PutMapping("updateUsersById/{userId}")
	public User updateUsersById(@PathVariable Long userId, @RequestBody @Valid User user) {
		return userService.updateUsersById(userId, user);
	}
	
	@DeleteMapping("deleteUsersById/{userId}")
	public void deleteUsersById(@PathVariable Long userId) {
		userService.deleteUsersById(userId);
	}
}
