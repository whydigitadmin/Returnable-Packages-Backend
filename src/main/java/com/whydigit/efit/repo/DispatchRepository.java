package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.DispatchVO;

public interface DispatchRepository extends JpaRepository<DispatchVO, Long> {

	@Query("select a from DispatchVO a where a.emitterId=?1")
	List<DispatchVO> findAllByEmitterId(Long emitterId);

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from oembininwarddocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_oembininward_sequence_value()")
	void nextseq();

	@Query(nativeQuery = true, value = " select a.binoutwarddocid,a.binoutdocdate,a.partname,a.partno,a.kitno,a.qty from \r\n"
			+ "              dispatchdetails a inner join dispatch b on a.dispatchid=b.dispatchid where b.docid=?1 and a.invoiceno is null")
	Set<Object[]> findEmitterInwardListByDocId(String docId);

}
