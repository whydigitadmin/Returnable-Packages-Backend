package com.whydigit.efit.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetTaggingVO;

public interface AssetTaggingRepo extends JpaRepository<AssetTaggingVO, Long> {
	
	@Query(nativeQuery = true,value = "WITH RECURSIVE NumberSequence AS (\r\n"
			+ "   SELECT ?3 AS level\r\n"
			+ "   UNION ALL\r\n"
			+ "   SELECT level + 1\r\n"
			+ "   FROM NumberSequence\r\n"
			+ "   WHERE level < ?4\r\n"
			+ " )\r\n"
			+ " SELECT ?1 assetcode, ?2 asset,\r\n"
			+ "   CASE \r\n"
			+ "     WHEN CHAR_LENGTH(level) = 1 THEN CONCAT('AI',24,?1, LPAD(level, 4, '0'))\r\n"
			+ "     ELSE CONCAT('AI',24,?1, LPAD(level, 4, '0'))\r\n"
			+ "   END AS num\r\n"
			+ " FROM NumberSequence\r\n"
			+ " WHERE level BETWEEN ?3 AND ?4")
	Set<Object[]> getTagCodeByAsset(String assetcode, String asset, int startno, int endno);

}
