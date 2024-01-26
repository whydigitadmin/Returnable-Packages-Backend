
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.FlowVO;

@Repository
public interface FlowRepo extends JpaRepository<FlowVO, Integer> {
	@Query(value = "select a from FlowVO a Where a.orgId=?1")
	List<FlowVO> getAllFlowByOrgId(Long orgId);


}


