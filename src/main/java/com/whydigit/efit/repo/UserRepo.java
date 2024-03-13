package com.whydigit.efit.repo;

import java.time.LocalDateTime;
import java.util.List;

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

	@Query(value = "SELECT u2.user_id, ua.id,u2.first_name,u2.last_name, ua.location,ua.address1,ua.address2,ua.country,ua.state,ua.pin FROM users u2 LEFT JOIN user_address ua ON u2.address_id = ua.id WHERE u2.org_id = ?1 AND u2.role = ?2",nativeQuery = true)
	List<Object[]> findByOrgIdAndRole(Long orgId,String string);

	@Query(value = "select u.accessFlowId from UserVO u where u.id =?1")
	String getFlowIdsByUserId(long userId);

	@Query(value = "select u.accessWarehouse from UserVO u where u.id =?1")
	String getWarehouseByUserID(long userId);

	@Query(value = "select u.* from users u where u.org_id=?1",nativeQuery = true)
	List<UserVO> findByOrgId(Long orgId);

}