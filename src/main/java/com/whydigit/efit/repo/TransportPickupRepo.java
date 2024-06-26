package com.whydigit.efit.repo;

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

}
