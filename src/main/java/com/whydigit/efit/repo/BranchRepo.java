package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.BranchVO;


@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long> {

	boolean existsByBranchNameAndBranchCodeAndOrgId(String branchName, String branchCode, Long orgId);

	boolean existsByBranchCodeAndOrgId(String branchCode, Long orgId);

	boolean existsByBranchNameAndOrgId(String branchName, Long orgId);

	boolean existsByIdAndBranchNameAndOrgId(Long id, String branchName, Long orgId);

	boolean existsByIdAndBranchCodeAndOrgId(Long id, String branchCode, Long orgId);

	boolean existsByIdAndBranchNameAndBranchCodeAndOrgId(Long id, String branchName, String branchCode, Long orgId);

}


