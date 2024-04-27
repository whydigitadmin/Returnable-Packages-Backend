
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetGroupVO;

@Repository
public interface AssetGroupRepo extends JpaRepository<AssetGroupVO, String> {
	
	@Query(value = "select a from AssetGroupVO a Where a.orgId=?1")
	List<AssetGroupVO> getAllAssetGroupByOrgId(Long orgId);

	List<AssetGroupVO> findByAssetCategory(String assetCategory);

	List<AssetGroupVO> findByOrgIdAndAssetCategory(Long orgId, String assetCategory);

	List<AssetGroupVO> findAll(Specification<AssetGroupVO> specification);

	@Query("select a from AssetGroupVO a where a.orgId=?1 and a.assetCodeId=?2 ")
	AssetGroupVO findAssetByAssetCodeId(Long orgId, String assetCodeId);

	
	
}
