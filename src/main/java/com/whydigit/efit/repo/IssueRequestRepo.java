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

	@Query(nativeQuery =true,value = "SELECT a.docid, a.docdate AS reqDate, a.emitter, a.emitterid, b.kitcode, b.kitqty AS reqKitQty, b.partno, b.partname, a.flow, a.flowid \n"
			+ "FROM issuerequest a JOIN issuerequest2 b ON a.issuerequestid = b.issuerequestid \n"
			+ "JOIN users u ON FIND_IN_SET(a.whlocationid, u.access_warehouse) > 0 \n"
			+ "WHERE concat(a.docid,b.kitcode ) NOT IN (SELECT concat(binreqno,kitcode) FROM binallotment) AND a.orgid = ?1 AND u.user_id = ?2 \n"
			+ "GROUP BY a.docid, a.docdate, a.emitter, a.emitterid, b.kitcode, b.kitqty, b.partno, b.partname, a.flow, a.flowid")
	Set<Object[]> getIssueRequestByOrgId(Long orgId, Long userId);

	@Query(nativeQuery =true,value = "select 'Completed'Status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty from issuerequest a,issuerequest2 b \r\n"
			+ "where concat(a.docid,b.kitcode ) IN (SELECT concat(binreqno,kitcode) FROM binallotment) and  a.issuerequestid=b.issuerequestid and a.emitterid=?1 and a.orgid=?2  \r\n"
			+ "group by status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty\r\n"
			+ "union\r\n"
			+ "select 'Pending'Status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty from issuerequest a,issuerequest2 b \r\n"
			+ "where concat(a.docid,b.kitcode ) NOT IN (SELECT concat(binreqno,kitcode) FROM binallotment) and  a.issuerequestid=b.issuerequestid and a.emitterid=?1 and a.orgid=?2  \r\n"
			+ "group by status,a.emitterid,a.emitter,a.docid,a.docdate,a.flow,b.kitcode,b.kitqty")
	Set<Object[]> getBinRequestStatusCount(Long emitterId, Long orgId);
	
	@Query(value = "select a.docid,a.docdate,a.binreqno,a.binreqdate, a.flow,c.partno ,c.partname,a.kitcode,a.allotkitqty,'Completed'status from binallotment a, bininward b, flow1 c,flow d\r\n"
			+ " where concat(a.docid,a.binreqno,a.kitcode) in (select concat(allotmentno,reqno,kitcode) from bininward) and a.emitterid=?1 and c.emitterid=?1 and a.kitcode=c.kitno and  c.flowid= d.flowid and a.orgid=?2 and a.flow=d.flow\r\n"
			+ " group by a.docid,a.docdate,a.flow,a.kitcode,a.allotkitqty,c.partno ,c.partname,a.binreqno,a.binreqdate \r\n"
			+ "union\r\n"
			+ "select a.docid,a.docdate,a.binreqno,a.binreqdate, a.flow,c.partno ,c.partname,a.kitcode,a.allotkitqty,'Pending'status from binallotment a, bininward b, flow1 c,flow d\r\n"
			+ " where concat(a.docid,a.binreqno,a.kitcode) not in (select concat(allotmentno,reqno,kitcode) from bininward) and a.emitterid=?1 and c.emitterid=?1 and a.kitcode=c.kitno and a.orgid=?2 and c.flowid= d.flowid  and a.flow=d.flow\r\n"
			+ " group by a.docid,a.docdate,a.flow,a.kitcode,a.allotkitqty,c.partno ,c.partname,a.binreqno,a.binreqdate", nativeQuery = true)
	Set<Object[]> getBinInward(Long emitterId, Long orgId);

	@Query(value = "select a from IssueRequestVO a where a.docId=?1 and a.orgId=?2")
	IssueRequestVO findByDocIdAndOrgId(String transactionNo, Long orgId);

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    a.docid, \r\n"
			+ "    a.docdate AS reqDate, \r\n"
			+ "    a.emitter, \r\n"
			+ "    a.emitterid, \r\n"
			+ "    b.kitcode, \r\n"
			+ "    b.kitqty AS reqKitQty, \r\n"
			+ "    b.partno, \r\n"
			+ "    b.partname, \r\n"
			+ "    a.flow, \r\n"
			+ "    a.flowid\r\n"
			+ "FROM \r\n"
			+ "    issuerequest a \r\n"
			+ "JOIN \r\n"
			+ "    issuerequest2 b \r\n"
			+ "    ON a.issuerequestid = b.issuerequestid\r\n"
			+ "JOIN \r\n"
			+ "    users u \r\n"
			+ "    ON FIND_IN_SET(a.whlocationid, u.access_warehouse) > 0 \r\n"
			+ "WHERE \r\n"
			+ "    CONCAT(a.docid, b.kitcode) NOT IN (SELECT CONCAT(binreqno, kitcode) FROM binallotment) \r\n"
			+ "    AND a.orgid = ?1 and a.docid in (SELECT docid \r\n"
			+ "FROM issuerequest \r\n"
			+ "WHERE orgid = ?1 \r\n"
			+ "  AND docID IN (\r\n"
			+ "    SELECT transactionno \r\n"
			+ "    FROM mim \r\n"
			+ "    WHERE orgid = ?1 \r\n"
			+ "    GROUP BY transactionno\r\n"
			+ "  ) \r\n"
			+ "  AND docid NOT IN (\r\n"
			+ "    SELECT docid \r\n"
			+ "    FROM binallotment \r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "  )\r\n"
			+ "GROUP BY docid)\r\n"
			+ "GROUP BY \r\n"
			+ "    a.docid, a.docdate, a.emitter, a.emitterid, b.kitcode, b.kitqty, b.partno, b.partname, a.flow, a.flowid")
	Set<Object[]> getIssueRequestReportFromMIM(Long orgId);
	
}
