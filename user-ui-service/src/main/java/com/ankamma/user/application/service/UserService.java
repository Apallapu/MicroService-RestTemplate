package com.ankamma.user.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ankamma.user.application.exception.CustomException;
import com.ankamma.user.application.exception.CustomResponseErrorHandler;
import com.ankamma.user.application.exception.UserDetailsException;
import com.ankamma.user.application.model.UserExit;
import com.ankamma.user.application.model.UserList;
import com.ankamma.user.application.model.UserRequest;
import com.ankamma.user.application.model.UserResponse;

@Service
public class UserService {

	@LoadBalanced
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CustomResponseErrorHandler customResponseErrorHandler;

	@Value("${userdomainserviceUrl}")
	private String userdomainserviceUrl;

	public UserResponse createUser(UserRequest userRequest) {
		UserResponse response = null;
		try {
			restTemplate.setErrorHandler(customResponseErrorHandler);

			StringBuilder url = new StringBuilder();
			url.append(userdomainserviceUrl).append("/users");

			response = restTemplate.postForObject(url.toString(), userRequest, UserResponse.class);
		} catch (CustomException customException) {
			throw new UserDetailsException(customException.getMap().get("errorMessage").toString(),
					customException.getMap().get("httpStatusCode").toString(),
					customException.getMap().get("details").toString());
		}

		return response;
	}

	public UserExit existsByUsername(String username) {
		UserExit userExit = null;

		try {
			restTemplate.setErrorHandler(customResponseErrorHandler);
			StringBuilder url = new StringBuilder();
			url.append(userdomainserviceUrl).append("/user/checkUserExit?username=").append(username);
			userExit = restTemplate.getForObject(url.toString(), UserExit.class);
		} catch (CustomException customException) {
			throw new UserDetailsException(customException.getMap().get("errorMessage").toString(),
					customException.getMap().get("httpStatusCode").toString(),
					customException.getMap().get("details").toString());
		}
		return userExit;
	}

	public UserExit existsByEmail(String email) {
		UserExit userExit = null;
		try {
			restTemplate.setErrorHandler(customResponseErrorHandler);

			StringBuilder url = new StringBuilder();
			url.append(userdomainserviceUrl).append("/user/checkEmailExit?email=").append(email);
			userExit = restTemplate.getForObject(url.toString(), UserExit.class);
		} catch (CustomException customException) {
			throw new UserDetailsException(customException.getMap().get("errorMessage").toString(),
					customException.getMap().get("httpStatusCode").toString(),
					customException.getMap().get("details").toString());
		}
		return userExit;
	}

	public UserList getUserNames(String username) {
		UserList userList=null;
		try {
			restTemplate.setErrorHandler(customResponseErrorHandler);
		StringBuilder url = new StringBuilder();
		url.append(userdomainserviceUrl).append("/user/").append(username);
		userList= restTemplate.getForObject(url.toString(), UserList.class);
		} catch (CustomException customException) {
			throw new UserDetailsException(customException.getMap().get("errorMessage").toString(),
					customException.getMap().get("httpStatusCode").toString(),
					customException.getMap().get("details").toString());
		}
		return userList;

	}

	public List<UserList> getUserList() {
		List<UserList> userList=null;
		try {
			restTemplate.setErrorHandler(customResponseErrorHandler);
		
		StringBuilder url = new StringBuilder();
		url.append(userdomainserviceUrl).append("/users");
		userList= restTemplate.getForObject(url.toString(), List.class);
		} catch (CustomException customException) {
			throw new UserDetailsException(customException.getMap().get("errorMessage").toString(),
					customException.getMap().get("httpStatusCode").toString(),
					customException.getMap().get("details").toString());
		}
		return userList;
	}

}
