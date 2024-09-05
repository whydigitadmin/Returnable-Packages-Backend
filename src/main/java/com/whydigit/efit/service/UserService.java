package com.whydigit.efit.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.UserVO;

@Service
public interface UserService {

	public UserVO getUserById(Long userId);
	public UserVO getUserByUserName(String userName);
	public void removeUser(String userName);
	public void updateUserLogOutInformation(UserVO userVO);
	void createUserAction(String userName, long userId, String actionType);
	public void createUserLoginAction(String userName, Long userId, HttpServletRequest request, String userActionTypeLogin);
	
}
