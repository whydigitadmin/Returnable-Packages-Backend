package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.StockBranchVO;

public interface StockBranchRepo extends JpaRepository<StockBranchVO, Long> {

	List<StockBranchVO> findByOrgId(Long orgId);

}
