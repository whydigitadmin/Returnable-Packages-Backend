package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinOutwardVO;

public interface BinOutwardRepo extends JpaRepository<BinOutwardVO, Long> {

	@Query(nativeQuery = true, value = "select sequence_value from binoutwardseq")
	String finddocid();

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int findfinyr();

	@Query(nativeQuery = true,value="CALL next_binoutward_sequence_value()")
	void nextseq();

}
