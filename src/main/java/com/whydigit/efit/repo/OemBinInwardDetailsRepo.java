package com.whydigit.efit.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.OemBinInwardDetailsVO;

public interface OemBinInwardDetailsRepo extends JpaRepository<OemBinInwardDetailsVO, Long> {

	@Query(value = "SELECT c.flowid, c.flow\r\n"
			+ "FROM flow c\r\n"
			+ "JOIN users b ON FIND_IN_SET(c.flowid, b.access_flow_id) AND b.user_id = ?1 AND b.org_id = ?2\r\n"
			+ "GROUP BY c.flowid, c.flow", nativeQuery = true)
	Set<Object[]> findFlowFromOemByOrgId(Long userId, Long orgId);
	

	@Query(value = "SELECT a.docid, a.outwardkitqty, a.kitno, a.docdate ,d.partname,d.partno   \r\n"
			+ "FROM binoutward a JOIN flow c ON a.flowid = c.flowid\r\n"
			+ "JOIN users b ON FIND_IN_SET(a.flowid, b.access_flow_id)\r\n"
			+ "JOIN flow1 d ON d.flowid = c.flowid WHERE a.flowid = ?1 AND a.kitno=d.kitno and b.org_id = ?2 GROUP BY \r\n"
			+ " a.docid, a.outwardkitqty, a.kitno,a.docdate,d.partname,d.partno", nativeQuery = true)
	Set<Object[]> findgetOutwardDetailsByFlow(Long flowId, Long orgId);
  
}



