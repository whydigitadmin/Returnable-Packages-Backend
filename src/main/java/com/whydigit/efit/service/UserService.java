package com.whydigit.efit.service;

import org.springframework.stereotype.Service;

import com.whydigit.efit.controller.RefreshTokenDTO;
import com.whydigit.efit.entity.UserVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface UserService {

	public UserVO getUserById(Long userId);
	public UserVO getUserByUserName(String userName);
	public void createUserAction(String userName, long userId, String actionType);
	public void removeUser(String userName);
	public void updateUserLogOutInformation(UserVO userVO);
	
}
