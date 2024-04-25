package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.KitAssetVO;

@Repository
public interface KitAssetRepo  extends JpaRepository<KitAssetVO,Long>{

	
//	@Query(nativeQuery = true,value="select * from kit2 where kitid=?1 and quantity=?2")
//	List<KitAssetVO> getAllAssetByOrgId(String kitId, int quantity);
	
	@Query(nativeQuery = true,value="select a.assetcategory, a.asset, a.assetcode,a.quantity*?2 from kit2 a ,kit b where a.kitid=b.kitid and b.kitcode=?1")
	Set<Object[]> getAssetDetails(String kitCode, int quantity);

}
