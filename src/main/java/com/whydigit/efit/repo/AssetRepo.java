
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetVO;

@Repository
public interface AssetRepo extends JpaRepository<AssetVO, Long> {
	@Query(value = "select a from AssetVO a Where a.orgId=?1")
	List<AssetVO> getAllAssetByOrgId(Long orgId);

	@Query(value = "SELECT IFNULL((SELECT sku_to FROM asset WHERE asset_code_id = ? ORDER BY id DESC LIMIT 1), 0) AS sku_to",nativeQuery = true)
	long getLatestSkuByAssetCodeId(String assetCodeId);

	@Query(nativeQuery =true,value ="select * from asset where orgid=?1 and assetid=?2")
	AssetVO getAssetByOrgId(Long orgId, String assetId);


	AssetVO findByAssetCodeIdAndOrgId(String assetCodeId, long orgId);

	@Query(value = "select a from AssetVO a Where a.orgId=?1 and a.category=?2 and a.active=true")
	List<AssetVO> getAllAssetByOrgIdAndCategory(Long orgId, String category);


	boolean existsByAssetNameAndOrgId(String assetName, Long orgId);

	boolean existsByAssetCodeIdAndOrgId(String assetCodeId, Long orgId);

	@Query("select a.category from AssetVO a where a.assetCodeId=?1")
	String getCategoryByAssetCodeId(String assetCode);

	@Query(value = "select a from AssetVO a Where a.orgId=?1 and a.active=true")
	List<AssetVO> getAllActiveAssetByOrgId(Long orgId);

}
