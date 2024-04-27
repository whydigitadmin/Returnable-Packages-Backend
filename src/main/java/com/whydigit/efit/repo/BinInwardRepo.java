package com.whydigit.efit.repo;

import java.util.List;
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

	@Query(nativeQuery = true, value ="select docid,docdate,allotmentno,allotdate,flow,kitcode,reqkitqty,allotedqty from bininward where orgid=?2 and emitterid=?1")
	Set<Object[]> findAllByEmitterIdAndOrgId(Long emitterid, Long orgId);

	boolean existsByDocid(String docid);

	
	Optional<BinInwardVO> findAllByDocid(String docid);

}
