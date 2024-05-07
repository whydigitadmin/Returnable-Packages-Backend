package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetStockDetailsVO;

public interface AssetStockDetailsRepo extends JpaRepository<AssetStockDetailsVO, Long>{

	
	@Query(nativeQuery = true,value = "select * from availableasset where orgid=?1")
	List<Object[]> getAvailableAssetDetails(Long orgId);

}
