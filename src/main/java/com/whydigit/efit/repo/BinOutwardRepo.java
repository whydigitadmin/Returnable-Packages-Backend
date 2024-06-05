package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinOutwardVO;

public interface BinOutwardRepo extends JpaRepository<BinOutwardVO, Long> {

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from binoutwarddocidseq")
	String finddocid();

	@Query(nativeQuery = true, value = "CALL next_binoutward_sequence_value()")
	void nextseq();

	@Query(value = "select a from BinOutwardVO a where a.docId=?1")
	BinOutwardVO findByDocId(String outwardDocId);

	@Query(value = "select * from binoutward where orgid=?1 and emitterid=?2 ", nativeQuery = true)
	List<BinOutwardVO> getAllBinOutwardByOrgId(Long orgId,Long emitterId);

	@Query(value = "select * from binoutward where docid=?1", nativeQuery = true)
	List<BinOutwardVO> getAllBinOutwardByDocId(String docId);
	
	@Query(nativeQuery = true,value = "select b.docid,b.docdate,b.outwardkitqty,f.partno,b.kitno,f.partname from binoutward b , flow g, flow1 f where b.emitterid=f.emitterid and g.flowid=f.flowid and f.orgid=?1 and b.kitno=f.kitno  and b.emitterid=?3 and b.flow=?2 and invoiceno is null group by b.docid,b.docdate,b.outwardkitqty,f.partno,b.kitno,f.partname")
	List<Object[]> findEmitterDispatchByFlowId(Long orgId, Long flowId, Long emitterId);


}
