package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.OutwardView;

public interface OutwardViewRepo extends JpaRepository<OutwardView, Long> {

	@Query(value = "select * from outward_view where org_id=?1", nativeQuery = true)
	List<OutwardView> getOutwardViewByOrgId(Long orgId);

	@Query(value = "select * from outward_view where org_id=?1 and flow_number=?2", nativeQuery = true)
	List<OutwardView> getOutwardViewByOrgIdAndFlowId(Long orgId, Long flowId);

}
