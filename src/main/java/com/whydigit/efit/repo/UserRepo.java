package com.whydigit.efit.repo;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.UserVO;

public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUserNameOrEmail(String userName, String email);

	UserVO findByUserName(String userName);

	@Query(value = "select u from UserVO u where u.id =?1")
	UserVO getUserById(Long userId);

	UserVO findByUserNameAndUserId(String userName, Long userId);

	boolean existsByEmail(String email);

	@Modifying
	@Transactional
	@Query(value= "update UserVO u set u.lastLogin=?2 where u.userId=?1" )
	void updateLastLoginByUserId(Long userId, LocalDateTime now);

}