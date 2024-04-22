package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.StockBranchVO;

public interface StockBranchRepo extends JpaRepository<StockBranchVO, Long> {

	List<StockBranchVO> findByOrgId(Long orgId);

	
	@Query(nativeQuery = true,value="select b.* from warehouse a, availablekit b where a.stockbranch=b.stockbranch and a.warehouseid=?1 and b.kitcode=?2")
	Set<Object[]> findAvalKitQty(Long warehouseId, String kitname);

}
