package com.whydigit.efit.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.RetreivalVO;

public interface RetreivalRepo extends JpaRepository<RetreivalVO, Long> {
	
	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from retreivaldocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_retreivaldocid_sequence_value()")
	void nextSeq();
	
	@Query(nativeQuery = true, value = "select docid,docdate,fromstockbranch,tostockbranch from retreival where orgid=?1 and receiverid=?2 and pickup='Pending'")
	Set<Object[]> getRetrievalDeatils(Long orgId, Long receiverId);

	@Query(nativeQuery = true, value = "select b.category,b.assetcode,b.assetname,sum(b.outqty)outqty from oembinoutward a,oembinoutwarddetails b \r\n"
			+ "where a.oembinoutwardid=b.oembinoutwardid  and a.orgid=?1 \r\n"
			+ "and a.docid in(SELECT b.outwarddocid FROM retreival a,retreivaldetails b where a.retreivalid=b.retreivalid and a.docid=?2 and a.orgid=?1)\r\n"
			+ "group by b.category,b.assetcode,b.assetname")
	Set<Object[]> getRetrievalDeatilsforPickupFillgrid(Long orgId, String rmNo);

	@Query("select a from RetreivalVO a where a.docId=?1 and a.orgId=?2")
	RetreivalVO findByDocidAndOrgId(String rmNo, Long orgId);


}
