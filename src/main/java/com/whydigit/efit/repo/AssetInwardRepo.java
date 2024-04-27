package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetInwardVO;

public interface AssetInwardRepo extends JpaRepository<AssetInwardVO, Long>{
	
	@Query(value = "select * from assetinward where orgid=?1",nativeQuery = true)

	List<AssetInwardVO> findAssetInwardByOrgId(Long orgId);

}
