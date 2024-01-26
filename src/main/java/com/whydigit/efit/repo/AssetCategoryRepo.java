
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetCategoryVO;


@Repository
public interface AssetCategoryRepo extends JpaRepository<AssetCategoryVO, Integer> {
	@Query(value = "select a from AssetCategoryVO a Where a.orgId=?1")
	List<AssetCategoryVO> getAllAssetCategory(Long orgId);

}
