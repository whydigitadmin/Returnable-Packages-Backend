
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetCategoryVO;

@Repository
public interface AssetCategoryRepo extends JpaRepository<AssetCategoryVO, Long> {
	
	@Query(value = "select a from AssetCategoryVO a Where a.orgId=?1")
	List<AssetCategoryVO> getAllCategoryByOrgId(Long orgId);

	List<AssetCategoryVO> findByCategory(String assetCategory);

	List<AssetCategoryVO> findByOrgIdAndCategory(Long orgId, String assetCategory);

	List<AssetCategoryVO> findAll(Specification<AssetCategoryVO> specification);

	@Query("select a from AssetCategoryVO a where a.orgId=?1 and a.categoryCode=?2 ")
	AssetCategoryVO findCategoryByCategoryCode(Long orgId, String categoryCode);

	AssetCategoryVO findAllByCategoryCode(String assetCodeId);

	boolean existsByCategoryCode(String categoryCode);

	boolean existsByCategoryCodeAndOrgId(String categoryCode, Long orgId);

	boolean existsById(String string);

	AssetCategoryVO findByCategoryAndAssetTypeAndOrgId(String category, String assetType, Long orgId);


	boolean existsByAssetTypeAndCategoryAndCategoryCodeAndOrgId(String assetType, String category, String categoryCode,
			Long orgId);

	boolean existsByCategoryAndOrgId(String upperCase, Long orgId);


	
	

	

	

	

	
	
}
