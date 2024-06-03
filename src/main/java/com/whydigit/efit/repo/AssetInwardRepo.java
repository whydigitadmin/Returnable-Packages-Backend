package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetInwardVO;

public interface AssetInwardRepo extends JpaRepository<AssetInwardVO, Long>{
	
	@Query(value = "select * from assetinward where orgid=?1",nativeQuery = true)
	List<AssetInwardVO> findAssetInwardByOrgId(Long orgId);

	@Query(nativeQuery = true,value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int findfinyr();

	@Query(nativeQuery = true,value = "select sequence_value from assetinwarddocidseq")
	int finddocid();

	@Query(nativeQuery = true,value ="CALL next_assetinward_sequence_value()" )
	void nextseq();

	AssetInwardVO findAssetInwardByDocId(String docId);

}
