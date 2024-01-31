
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.StockDetailVO;

@Repository
public interface StockDetailRepo extends JpaRepository<StockDetailVO, Integer> {
	@Query(value = "select a from StockDetailVO a Where a.orgId=?1")
	List<StockDetailVO> getStockDetailByOrgId(Long orgId);
}
