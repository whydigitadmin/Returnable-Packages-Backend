package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.GatheringEmptyVO;
@Repository
public interface GatheringEmptyRepo extends JpaRepository<GatheringEmptyVO, Long> {
	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from gatheringemptydocidseq")
	String finddocid();

	@Query(nativeQuery = true, value = "CALL next_gatheringempty_sequence_value()")
	void nextseq();

	
	@Query(nativeQuery = true,value = "select * from gatheringempty where orgid=?1 ")
	List<GatheringEmptyVO> getAllGatheringEmptyByOrgId(Long orgId);

}
