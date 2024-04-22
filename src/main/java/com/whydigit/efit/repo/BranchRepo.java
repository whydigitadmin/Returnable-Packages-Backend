package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.BranchVO;


@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long> {

	boolean existsByBranchNameAndBranchCodeAndOrgId(String branchName, String branchCode, String orgId);

	boolean existsByBranchCodeAndOrgId(String branchCode, String orgId);

	boolean existsByBranchNameAndOrgId(String branchName, String orgId);

}


