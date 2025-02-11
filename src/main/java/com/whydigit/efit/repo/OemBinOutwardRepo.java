package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.OemBinOutwardVO;

@Service
public interface OemBinOutwardRepo extends JpaRepository<OemBinOutwardVO, Long> {

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from oembinoutwarddocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_oembinoutwarddocid_sequence_value()")
	void nextSeq();

	@Query(nativeQuery =true,value = "select * from oembinoutward where orgid=?1")
	List<OemBinOutwardVO> getAllOemBinOutwardByOrgId(Long orgId);

}
