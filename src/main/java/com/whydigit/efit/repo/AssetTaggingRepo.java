package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetTaggingVO;

public interface AssetTaggingRepo extends JpaRepository<AssetTaggingVO, Long> {
	
	@Query(nativeQuery = true,value="SELECT RIGHT(\r\n"
			+ "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n"
			+ "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n"
			+ "    ), \r\n"
			+ "    2\r\n"
			+ ") AS finyr")
	int getFinyr();
	
	@Query(nativeQuery = true,value = "WITH RECURSIVE NumberSequence AS (\r\n"
			+ "   SELECT ?3 AS level\r\n"
			+ "   UNION ALL\r\n"
			+ "   SELECT level + 1\r\n"
			+ "   FROM NumberSequence\r\n"
			+ "   WHERE level < ?4\r\n"
			+ " )\r\n"
			+ " SELECT ?1 assetcode, ?2 asset,\r\n"
			+ "   CASE \r\n"
			+ "     WHEN CHAR_LENGTH(level) = 1 THEN CONCAT('AI',?5,?1,'-', LPAD(level, 4, '0'))\r\n"
			+ "     ELSE CONCAT('AI',?5,?1,'-', LPAD(level, 4, '0'))\r\n"
			+ "   END AS num\r\n"
			+ " FROM NumberSequence\r\n"
			+ " WHERE level BETWEEN ?3 AND ?4")
	Set<Object[]> getTagCodeByAsset(String assetcode, String asset, int startno, int endno, int finyr);

	@Query(nativeQuery = true, value = "select sequence_value from assettaggingdocidseq")
	int findocid();

	@Query(nativeQuery = true, value = "CALL next_assettaggingdocid_value()")
	void nextDocid();

	List<AssetTaggingVO> findAllByOrgId(Long orgId);

}
