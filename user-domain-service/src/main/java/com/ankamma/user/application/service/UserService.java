package com.ankamma.user.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankamma.user.application.exception.UserNotFoundException;
import com.ankamma.user.application.model.UserList;
import com.ankamma.user.application.model.UserRequest;
import com.ankamma.user.application.model.UserResponse;
import com.ankamma.user.application.persistence.User;
import com.ankamma.user.application.respository.UserJpaRepository;
import com.ankamma.user.application.util.UserUtil;

@Service
public class UserService {

	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private UserUtil userUtil;

	public UserResponse createUser(UserRequest userRequest) {
		UserResponse response = new UserResponse();
		User user = userUtil.transformUser(userRequest);
		user = userJpaRepository.save(user);
		response.setId(user.getId());
		return response;
	}

	public boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		Boolean isAvailable = userJpaRepository.existsByUsername(username);
		if (isAvailable) {
			throw new UserNotFoundException("username already exit in data base");
		}
		return isAvailable;
	}

	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		Boolean isAvailable = userJpaRepository.existsByEmail(email);
		if (isAvailable) {
			throw new UserNotFoundException("user Email id already exit in data base");
		}
		return isAvailable;

	}

	public UserList getUserNames(String username) {
		User user = userJpaRepository.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException("user details not found ");
		}
		UserList userList = userUtil.tranfromEntityToModel(user);
		
		return userList;
	}

	public List<UserList> getUserList() {
		List<User> userEntitylist= userJpaRepository.findAll();
		if(userEntitylist.isEmpty()) {
			throw new UserNotFoundException("user details not found ");
		}
		return userUtil.getUserDeatailsList(userEntitylist);
	}

}
