package com.cloudappi.apiusers.models.service;

import com.cloudappi.apiusers.models.dao.AddressDao;
import com.cloudappi.apiusers.models.dao.UserDao;
import com.cloudappi.apiusers.models.entity.User;
import com.cloudappi.apiusers.models.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AddressDao addressDao;

	@Override
	public List<User> getUsers() {
		return userDao.findAll();
	}

	@Override
	public User createUser(@Valid User newUser) {
		newUser.setId(null);
		// Address Saving
		if (newUser.getAddress() != null) {
			newUser.getAddress().setId(null);
			newUser.getAddress().setId(addressDao.save(newUser.getAddress()).getId());
		}
		return userDao.save(newUser);
	}

	@Override
	public Optional<User> getUsersById(Long userId) {
		return userDao.findById(userId);
	}

	@Override
	public User updateUsersById(Long userId, User user) {
		userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
		// Address update
		if (user.getAddress() != null) {
			user.getAddress().setId(null);
			user.getAddress().setId(addressDao.save(user.getAddress()).getId());
		}
		user.setId(userId);
		return userDao.save(user);
	}

	@Override
	public void deleteUsersById(Long userId) {
		User userInDb = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(
				"User not found"));
		userDao.delete(userInDb);
	}
}
