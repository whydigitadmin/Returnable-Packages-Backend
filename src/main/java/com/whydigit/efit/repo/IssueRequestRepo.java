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
	
	@Query(nativeQuery = true,value="select a.flowTo IssueRequestVO a where a.docId=?1")
	String getFlowIdByrequestId(String binReqNo);

	@Query(nativeQuery =true,value = "select a.docid,a.docdate reqDate,a.emitter,a.emitterid,b.kitcode,b.kitqty reqKitQty,b.partno,b.partname,a.flow,a.flowid from issuerequest a, issuerequest2 b where a.issuerequestid=b.issuerequestid and a.docid not in (select binreqno from binallotment) and a.orgid=?1")
	Set<Object[]> getIssueRequestByOrgId(Long orgId);
	
}
