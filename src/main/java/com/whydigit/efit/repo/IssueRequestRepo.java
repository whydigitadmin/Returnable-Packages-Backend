package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.IssueRequestVO;

public interface IssueRequestRepo
		extends JpaRepository<IssueRequestVO, Long>, JpaSpecificationExecutor<IssueRequestVO> {

	List<IssueRequestVO> findAll(Specification<IssueRequestVO> specification);

	List<IssueRequestVO> findByOrgId(Long orgId);

	@Modifying
	@Transactional
	@Query(value = "update IssueRequestVO ir set ir.issueStatus=?2 where ir.id=?1")
	int cancelIssueRequestByIssueRequestId(Long issueRequestId, int issueRequestStatusCancelled);

	@Query(value = "select b.warehouseid from  flow b where b.flowid=?1 group BY  b.warehouseid",nativeQuery = true)
	long findWarehouseLocationId(Long flowTo); 

	@Query(value = "select b.whlocation from  flow b where b.flowid=?1 group BY  b.whlocation",nativeQuery = true)
	String findWarehouseLocation(Long flowTo);

	@Query(nativeQuery = true,value="select binid from binrqseq")
	Long finddocid();

	@Query(nativeQuery = true,value="CALL bin_next_value()")
	void updatesequence();

	@Query(nativeQuery = true,value ="select a.stockbranch from warehouse a, issuerequest b where a.warehouseid=b.whlocationid and b.issuerequestid=?1")
	String findStockBranchByIssurequestid(Long issueRequestId);
	
	@Query("select a.docId from IssueRequestVO a where a.id=?1")
	String findDocid(Long issueRequestId);

	@Query(nativeQuery = true,value="SELECT RIGHT(\r\n"
			+ "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n"
			+ "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n"
			+ "    ), \r\n"
			+ "    2\r\n"
			+ ") AS finyr")
	int getFinyr();
	
	@Query(nativeQuery = true,value="select a.flowid from issuerequest a where a.docid=?1")
	Long getFlowIdByrequestId(String binReqNo);

	@Query(nativeQuery =true,value = "SELECT a.docid, a.docdate AS reqDate, a.emitter, a.emitterid, b.kitcode, b.kitqty AS reqKitQty, b.partno, b.partname, a.flow, a.flowid FROM issuerequest a JOIN issuerequest2 b ON a.issuerequestid = b.issuerequestid JOIN users u ON FIND_IN_SET(a.whlocationid, u.access_warehouse) > 0 WHERE a.docid NOT IN (SELECT binreqno FROM binallotment) AND a.orgid = ?1 AND u.user_id = ?2 GROUP BY a.docid, a.docdate, a.emitter, a.emitterid, b.kitcode, b.kitqty, b.partno, b.partname, a.flow, a.flowid;")
	Set<Object[]> getIssueRequestByOrgId(Long orgId, Long userId);

	@Query(nativeQuery =true,value = "select 'Completed'Status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty from issuerequest a,issuerequest2 b \r\n"
			+ "where a.docid  in(select binreqno from binallotment) and  a.issuerequestid=b.issuerequestid and a.emitterid=?1 and a.orgid=?2  \r\n"
			+ "group by status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty\r\n"
			+ "union\r\n"
			+ "select 'Pending'Status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty from issuerequest a,issuerequest2 b \r\n"
			+ "where a.docid not in(select binreqno from binallotment) and  a.issuerequestid=b.issuerequestid and a.emitterid=?1 and a.orgid=?2  \r\n"
			+ "group by status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty")
	Set<Object[]> getBinRequestStatusCount(Long emitterId, Long orgId);
	
}
