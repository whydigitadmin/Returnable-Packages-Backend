
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetVO;

@Repository
public interface AssetRepo extends JpaRepository<AssetVO, Integer> {
	@Query(value = "select a from AssetVO a Where a.orgId=?1")
	List<AssetVO> getAllAssetByOrgId(Long orgId);

}
