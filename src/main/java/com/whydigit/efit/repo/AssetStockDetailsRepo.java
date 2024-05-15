package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetStockDetailsVO;

public interface AssetStockDetailsRepo extends JpaRepository<AssetStockDetailsVO, Long>{

	
	@Query(nativeQuery = true,value = "select * from availableasset where orgid=?1")
	List<Object[]> getAvailableAssetDetails(Long orgId);
	
	@Query(nativeQuery = true,value = "select a.stockbranch,a.tagcode,a.rfid,a.sku,a.skucode,a.skuqty,a.tn from(\r\n"
			+ "select stockbranch,tagcode,rfid,sku,skucode,sum(skuqty)skuqty,ROW_NUMBER() OVER (PARTITION BY skucode) AS tn from stockdetails where stockbranch=?2 and orgid=?1 and status='S' and skucode=?3 group by stockbranch,tagcode,rfid,sku,skucode having sum(skuqty)>0)a\r\n"
			+ "where a.tn between 1 and ?4")
	Set<Object[]> getAssetDetailsByAssetForAssetInward(Long orgId, String stockBranch, String assetCode,int qty);
	
	@Query(nativeQuery = true,value = "select  skucode,sku,sum(skuqty)skuqty from stockdetails where stockbranch=?2 and status='S' and orgid=?1 group by skucode,sku having sum(skuqty)>0") 
	Set<Object[]> getAvailAssetDetailsByBranch(Long orgId, String stockBranch);

}
