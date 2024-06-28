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
	


	@Query(nativeQuery = true, value = " SELECT docid,docdate,drivername,driverphoneno,transportdocno,vechicleno,fromstockbranch,tostockbranch FROM transportpickup \r\n"
			+ " where orgid=1 and docid not in\r\n"
			+ "			(select pickupdocid from binretrieval where orgid=1 group by pickupdocid) and  tostockbranch in(SELECT a.whlocation FROM warehouse a, users b \r\n"
			+ "WHERE FIND_IN_SET(a.warehouseid, b.access_warehouse) > 0 and b.user_id=12 and b.org_id=1 group by whlocation)\r\n"
			+ "            group by docid,docdate,drivername,driverphoneno,transportdocno,vechicleno,fromstockbranch,tostockbranch")
	Set<Object[]> getPendingPickupDetails(Long orgId, Long userId);

	@Query(nativeQuery = true, value = "select b.category,b.assetcode,b.asset,b.pickqty from transportpickup a,transportpickupdetails b where a.transportpickupid=b.transportpickupid and a.orgid=?1 and a.docid=?2")
	Set<Object[]> getPickupDetails(Long orgId, String pickupDocId);

	@Query(nativeQuery = true, value = "select * from transportpickup where receiverid=?1")
	List<TransportPickupVO> getAllTranportPickupByReceiverId(Long receiverId);

	@Query(value = "select a.docid as TransactionNo ,a.docdate as TransactionDate,a.docdate as DispatchDate,b.displayname as sender,\r\n"
    		+ "concat(c.street1,' ,',c.street2,' ,',c.state) as Address,c.gstin as SenderGst,d.name as Receiver,e.address As Address,e.gst as ReceiverGST\r\n"
    		+ "from retreival a join customer b join customer1 c join organization d join warehouse e on a.receiverid=b.customerid and b.customerid=c.customerid and\r\n"
    		+ " a.orgid=d.organizationid  and a.tostockbranch=e.whlocation where a.docid=?1 and a.orgid=?2",nativeQuery =true)
	Set<Object[]> getManifestHeader(Long docId, Long orgId);
	
	

}
