package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.aspectj.weaver.tools.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinInwardVO;

public interface BinAllotmentNewRepo extends JpaRepository<BinAllotmentNewVO, Long> {

	boolean existsByBinReqNo(String binReqNo);

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int getFinyr();

	@Query(nativeQuery = true, value = "select allotcode from binallotmentseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_allotcode()")
	void nextDocseq();

	@Query(nativeQuery = true, value = "select a.docid reqNo,a.docdate reqDate,a.emitter,a.emitterid,b.kitcode,b.kitqty reqKitQty,b.partno,b.partname,a.flow,a.flowid from issuerequest a, issuerequest2 b where a.issuerequestid=b.issuerequestid and a.orgid=?1")
	Set<Object[]> findReqDetailsByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from binallotment where orgid=?1")
	List<BinAllotmentNewVO> getAllBinAllotment(Long orgId);

	@Query(nativeQuery = true, value = "SELECT docid\r\n"
			+ "FROM binallotment\r\n"
			+ "WHERE emitterid = ?2 \r\n"
			+ "    AND orgid = ?1 \r\n"
			+ "    AND docid NOT IN (SELECT allotmentno FROM bininward)")
	Set<Object[]> getAllotmentNoByEmitterIdAndOrgId(Long orgId, Long emitterId);

	@Query(nativeQuery = true, value = "select a.docdate binallotdate,a.binreqno,a.binreqdate,b.flow,a.kitcode,a.allotkitqty from binallotment a, issuerequest b where a.binreqno=b.docid and a.orgid=?1 and a.docid=?2")
	Set<Object[]> getAllotmentDetailsByAllotmentNoAndOrgId(Long orgId, String docid);

	@Query(nativeQuery = true, value = "select b.asset,b.assetcode,b.rfid,b.tagcode,b.skuqty from binallotment a , binallotment1 b where a.binallotmentid=b.binallotmentid and a.docid=?2 and a.orgid=?1")
	Set<Object[]> getAllotmentAssetDetailsByAllotmentNoAndOrgId(Long orgId, String docid);

	@Query(nativeQuery =true,value = "select * from binallotment where docid=?1")
	List<BinAllotmentNewVO> getAllAssetByOrgId(String docId);
	
	@Query(nativeQuery =true,value = "SELECT a.docid, a.docdate, b.flow, a.kitcode, a.allotkitqty, a.reqkitqty,a.emitterid,a.orgid FROM binallotment a INNER JOIN issuerequest b ON a.binreqno = b.docid\r\n"
			+ "WHERE a.docid NOT IN (SELECT allotmentno FROM bininward) and a.orgid=?1 and a.emitterid=?2")
	Set<Object[]> getWaitingforBinInwardDetailsByEmitterAndOrgId(Long orgId, Long emitterid);
	
	

}
