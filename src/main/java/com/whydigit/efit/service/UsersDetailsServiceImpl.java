package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.UsersDetails;
import com.whydigit.efit.repo.UsersDetailsRepository;

@Service
public class UsersDetailsServiceImpl implements UsersDetailsService{
	
	@Autowired
	UsersDetailsRepository usersDetailsRepo;
	
	@Override
	public List<UsersDetails> getAllUsers() {
		
		return usersDetailsRepo.findAll();
	}

	@Override
	public Optional<UsersDetails> getUsersById(int id) {
		
		return usersDetailsRepo.findById(id);
	}

	@Override
	public UsersDetails createUsersDetails(UsersDetails usersdetails) {
		
		return usersDetailsRepo.save(usersdetails);
	}

	@Override
	public Optional<UsersDetails> updateUsersDetails(UsersDetails usersdetails) {
		if(usersDetailsRepo.existsById(usersdetails.getUserdetails_id())) {
			return Optional.of(usersDetailsRepo.save(usersdetails));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public void deleteUsersDetails(int id) {
		
		 usersDetailsRepo.deleteById(id);
		
	}

}
