package com.cloudappi.apiusers.models.iservice;

import com.cloudappi.apiusers.models.entity.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface IUserService {

	public List<User> getUsers();

	public User createUser(User newUser);

	public Optional<User> getUsersById(Long userId);

	public User updateUsersById(Long userId, @Valid User user);

	public void deleteUsersById(Long userId);

}
