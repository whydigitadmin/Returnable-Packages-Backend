
package com.whydigit.efit.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.LogisticsVO;

@Repository
public interface LogisticsRepo extends JpaRepository<LogisticsVO, UUID> {
	@Query(value = "select a from LogisticsVO a Where a.orgId=?1")
	List<LogisticsVO> getLogisticsByOrgId(Long orgId);
}
