package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.TransportPickupVO;

public interface TransportPickupRepo extends JpaRepository<TransportPickupVO, Long> {

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from tranportdocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_tranportdocid_sequence_value()")
	void nextSeq();

	@Query(nativeQuery = true, value = "SELECT docid,docdate,drivername,driverphoneno,transportdocno,vechicleno,fromstockbranch,tostockbranch FROM rp_dev.transportpickup where orgid=?1 and docid not in(\r\n"
			+ "select pickupdocid from binretrieval where orgid=?1 group by pickupdocid) and tostockbranch=?2 group by docid,docdate,drivername,driverphoneno,transportdocno,vechicleno,fromstockbranch,tostockbranch")
	Set<Object[]> getPendingPickupDetails(Long orgId, String retrievalWarehosue);

	@Query(nativeQuery = true, value = "select b.category,b.assetcode,b.asset,b.pickqty from transportpickup a,transportpickupdetails b where a.transportpickupid=b.transportpickupid and a.orgid=?1 and a.docid=?2")
	Set<Object[]> getPickupDetails(Long orgId, String pickupDocId);

	@Query(nativeQuery = true, value = "select * from transportpickup where receiverid=?1")
	List<TransportPickupVO> getAllTranportPickupByReceiverId(Long receiverId);
}
