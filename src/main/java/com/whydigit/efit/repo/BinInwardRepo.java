package com.whydigit.efit.repo;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinInwardVO;

public interface BinInwardRepo extends JpaRepository<BinInwardVO, Long>{

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int getFinyr();

	@Query(nativeQuery = true, value = "select inwarddocid from bininwarddocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_inwardid()")
	void nextDocseq();

	@Query(nativeQuery = true, value ="select a.docid,a.docdate,a.allotmentno,a.allotdate,a.flow,a.kitcode,a.reqkitqty,a.allotedqty,a.partname,a.partCode from bininward a where orgid=?2 and emitterid=?1")
	Set<Object[]> findAllByEmitterIdAndOrgId(Long emitterid, Long orgId);

	boolean existsByDocid(String docid);

	 
	Optional<BinInwardVO> findAllByDocid(String docid);

	BinInwardVO findByAllotmentNo(String allotNo);
	@Query(value ="select k.emitterid, k.orgid, k.displayname, k.flow, f.flowid, k.kitcode, " +
            "sum(k.invQty) - sum(k.outqty) as availKitQty " +
            "from ( " +
            "select a.emitterid, a.orgid, b.displayname, a.flow, a.kitcode, sum(a.allotedqty) as invQty, 0 as outqty " +
            "from bininward a join customer b on a.emitterid = b.customerid " +
            "group by a.emitterid, a.orgid, b.displayname, a.flow, a.kitcode " +
            "union " +
            "select a.emitterid, a.orgid, b.displayname, a.flow, a.kitno as kitcode, 0 as invQty, sum(a.outwardkitqty) as outqty " +
            "from binoutward a join customer b on a.emitterid = b.customerid " +
            "group by a.emitterid, a.orgid, b.displayname, a.flow, a.kitno " +
            ") k " +
            "join flow f on k.flow = f.flow " +
            "where f.flowid in ( " +
            "select a.flowid from flow a, users b " +
            "where find_in_set(a.flowid, b.access_flow_id) > 0 and b.user_id = ?2 and b.org_id = ?1 " +
            "group by a.flowid) " +
            "group by k.emitterid, k.orgid, k.displayname, k.flow, f.flowid, k.kitcode",
            nativeQuery = true)
	Set<Object[]> getKitAvilQtyDetails(Long orgId, Long userId);

	@Query(value ="select a.category from assetcategory a where orgid=?1 and active=1 and assettype=?2",nativeQuery =true )
	Set<Object[]> getActiveCategory(Long orgId, String assetCategory);

}
