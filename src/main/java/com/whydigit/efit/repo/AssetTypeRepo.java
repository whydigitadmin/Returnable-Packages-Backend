
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetTypeVO;

@Repository
public interface AssetTypeRepo extends JpaRepository<AssetTypeVO, Long> {
	@Query(value = "select a from AssetCategoryVO a Where a.orgId=?1")
	List<AssetTypeVO> getAllAssetType(Long orgId);

	List<AssetTypeVO> findByAssetType(String assetCategoryName);

	List<AssetTypeVO> findByAssetTypeAndOrgId(String assetCategoryName, Long orgId);

	boolean existsByAssetTypeAndOrgId(String assetCategory, Long orgId);

	boolean existsByTypeCodeAndOrgId(String assetCategoryId, Long orgId);

	AssetTypeVO findByOrgIdAndAssetType(Long orgId, String assetType);

}
