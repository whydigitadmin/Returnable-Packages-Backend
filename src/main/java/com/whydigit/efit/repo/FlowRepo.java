
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.FlowVO;

@Repository
public interface FlowRepo extends JpaRepository<FlowVO, Long> {

	@Query(value = "select a from FlowVO a Where a.orgId=?1")
	List<FlowVO> getAllFlow(Long orgId);

	List<FlowVO> findByOrgId(Long orgId);

	List<FlowVO> findByEmitterId(Long emitterId);

	List<FlowVO> findByOrgIdAndEmitterId(Long orgId, Long emitterId);

	List<FlowVO> findById(String flowId);

}
