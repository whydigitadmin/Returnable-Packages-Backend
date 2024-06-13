package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardVO;

public interface OemBinInwardRepo extends JpaRepository<OemBinInwardVO, Long> {

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select oeminwarddocid from oembinoutwarddocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_oembininwarddocid_sequence_value()")
	void nextseq();

	@Query(value = "select * from oembininward where orgid=?1", nativeQuery = true)
	List<OemBinInwardVO> findAllOemBinInwardByOrgIdAndUserId(Long orgId);

	@Query(value = "select a from OemBinInwardVO a where a.docId=?1")
	OemBinInwardVO findOemInwardByDocId(String docId);
	

}
