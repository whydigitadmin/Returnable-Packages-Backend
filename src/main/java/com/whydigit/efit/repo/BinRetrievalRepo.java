package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinRetrievalVO;

public interface BinRetrievalRepo  extends JpaRepository<BinRetrievalVO, Long>{

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	String findFinyr();

	@Query(nativeQuery = true, value = "select sequence_value from binretrievaldocidseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_binretrieval_sequence_value()")
	void nextSeq();

	
	@Query(nativeQuery=true,value = "select * from binretrieval where docid=?1")
	List<BinRetrievalVO> findByDocId(String docId);

	@Query(nativeQuery=true,value ="select * from binretrieval where binretrievalid=?1")
	List<BinRetrievalVO> getAllBinReterival(Long id);

	@Query(nativeQuery=true,value ="select * from binretrieval where orgid=?1")
	List<BinRetrievalVO> findByOrgId(Long orgId);

	BinRetrievalVO findByDocIdAndOrgId(String transactionNo, Long orgId);
}