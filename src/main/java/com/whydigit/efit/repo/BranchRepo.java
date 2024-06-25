package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.BranchVO;


@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long> {

	@Query(value = "select a from BranchVO a where a.orgId=?1")
	List<BranchVO> findAllBranchByOrgId(Long orgId);

	@Query(value = "select a from BranchVO a where a.orgId=?1 and a.active=true")
	List<BranchVO> findAllActiveBranch(Long orgId);

	boolean existsByBranchCodeAndOrgId(String branchCode, Long orgId);

	boolean existsByBranchNameAndOrgId(String branchName, Long orgId);


}


