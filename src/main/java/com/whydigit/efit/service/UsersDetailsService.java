package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.entity.UsersDetails;

public interface UsersDetailsService {
	
	List<UsersDetails>getAllUsers();
	
	Optional<UsersDetails>getUsersById(int id);
	
	UsersDetails createUsersDetails(UsersDetails usersdetails);
	
	Optional<UsersDetails>updateUsersDetails(UsersDetails usersdetails);
	
	void deleteUsersDetails(int id);

}
