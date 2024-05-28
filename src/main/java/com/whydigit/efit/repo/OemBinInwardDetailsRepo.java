package com.whydigit.efit.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.OemBinInwardDetailsVO;

public interface OemBinInwardDetailsRepo extends JpaRepository<OemBinInwardDetailsVO, Long> {

	@Query(value = "SELECT c.flowid,c.flow\r\n" + "FROM binoutward a\r\n"
			+ "JOIN flow c ON a.flow = c.flowid\r\n"
			+ "JOIN users b ON FIND_IN_SET(a.flow, b.access_flow_id) AND b.user_id = ?1 and b.org_id=?2 GROUP BY c.flowid,c.flow", nativeQuery = true)
	Set<Object[]> findFlowFromOemByOrgId(Long userId, Long orgId);
	

	@Query(value = "SELECT a.docid,a.outwardkitqty,a.kit,a.docdate\r\n" + "FROM binoutward a\r\n"
			+ "JOIN flow c ON a.flow = c.flowid\r\n"
			+ "JOIN users b ON FIND_IN_SET(a.flow, b.access_flow_id) AND a.flow = ?1 and b.org_id=?2 GROUP BY a.docid,a.outwardkitqty,a.kit,a.docdate", nativeQuery = true)
	Set<Object[]> findgetOutwardDetailsByFlow(Long flowId, Long orgId);

}



