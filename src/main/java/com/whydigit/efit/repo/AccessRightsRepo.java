package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.AccessRightsVO;

public interface AccessRightsRepo extends JpaRepository<AccessRightsVO, Long> {

	boolean existsByRoleAndOrgId(String role, long orgId);

	List<AccessRightsVO> findByOrgId(long orgId);

}
