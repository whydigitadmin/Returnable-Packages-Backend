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
	
	@Query(nativeQuery = true,value = "select a.skucode,a.sku,sum(a.skuqty) from(\r\n"
			+ "select  skucode,sku,sum(skuqty)skuqty from stockdetails where stockbranch=?2 and status='S' and orgid=?1 and category=?3 group by skucode,sku having sum(skuqty)>0\r\n"
			+ "union\r\n"
			+ "select assetcode,asset,0 skuqty from asset where  orgid=?1 and assetcategory=?3) a group by a.skucode,a.sku") 
	Set<Object[]> getAvailAssetDetailsByBranch(Long orgId, String stockBranch,String category);

	@Query(nativeQuery = true,value = "select stockbranch,category,sku,skucode,sum(oqty)oqty,sum(rqty)rqty,abs(sum(dqty))dqty,sum(cqty)cqty from (\r\n"
			+ "select 1 sno,stockbranch,category,sku,skucode,sum(skuqty)oqty,0 rqty,0 dqty,0 cqty from stockdetails where stockbranch=?3 and status='S' and stockdate < ?1\r\n"
			+ "group by stockbranch,sku,category,skucode\r\n"
			+ "union\r\n"
			+ "select 2 sno,stockbranch,category,sku,skucode,0 oqty,sum(skuqty) rqty,0 dqty,0 cqty from stockdetails where stockbranch=?3 and status='S' and stockdate  between ?1\r\n"
			+ "and ?2 and skuqty > 0 \r\n"
			+ "group by stockbranch,category,sku,skucode\r\n"
			+ "union\r\n"
			+ "select 3 sno,stockbranch,category,sku,skucode,0 oqty,0 rqty,sum(skuqty) dqty,0 cqty from stockdetails where stockbranch=?3 and status='S' and stockdate  between ?1\r\n"
			+ "and ?2 and skuqty < 0 \r\n"
			+ "group by stockbranch,category,sku,skucode\r\n"
			+ "union\r\n"
			+ "select 4 sno,stockbranch,category,sku,skucode,0 oqty,0 rqty,0 dqty,sum(skuqty) cqty from stockdetails where stockbranch=?3 and status='S' and stockdate <= ?2\r\n"
			+ "group by stockbranch,category,sku,skucode) a\r\n"
			+ "group by stockbranch,category,sku,skucode")
	Set<Object[]> getStockLedgerDetailsForEmitter(String startDate, String endDate, String stockBranch);

	
	@Query(nativeQuery = true,value ="select stockbranch,category,sku,skucode,sum(skuqty) from stockdetails where status='E' and stockbranch=?1   group by stockbranch,category,sku,skucode having sum(skuqty)>0")
	Set<Object[]> getOemStockDetailsForOemBinOutward(String stockBranch);

	@Query(nativeQuery = true,value ="select stockbranch,category,sku,skucode,sum(skuqty) from stockdetails where status='S' and stockbranch=?1 and orgid=?2   group by stockbranch,category,sku,skucode having sum(skuqty)>0")
	Set<Object[]> getOemEmptyStockDetailsForGathering(String stockBranch,Long orgId);

	
	@Query(nativeQuery = true,value="select a.partno,a.partname,a.kitno,a.partqty from KITPART a where flowid=?1 and kitno=?2 and emitterid=?3")
	Set<Object[]> getPartNameAndPartNoDetails(Long flowId, String kitNo, Long emitterId);
	
@Query(nativeQuery =true,value = "select a.kitno,sum(a.oqty)oqty,sum(a.rqty)rqty,abs(sum(a.dqty))dqty,sum(a.cqty)cqty from(\r\n"
		+ "select 1 sno,flow,flowid,kitno,sum(sqty) oqty,0 rqty,0 dqty,0 cqty  from kitstockdetails where flowid=?3 and orgid=?4 and stockdate<?1 group by sno,flow,flowid,kitno \r\n"
		+ "union\r\n"
		+ "select 2 sno,flow,flowid,kitno,0 oqty,sum(sqty) rqty,0 dqty,0 cqty  from kitstockdetails where flowid=?3 and orgid=?4 and stockdate between ?1 and ?2 and  sqty>0 group by sno,flow,flowid,kitno\r\n"
		+ "union\r\n"
		+ "select 3 sno,flow,flowid,kitno,0 oqty,0 rqty,sum(sqty) dqty,0 cqty  from kitstockdetails where flowid=?3 and orgid=?4 and stockdate between ?1 and ?2 and sqty<0 group by sno,flow,flowid,kitno\r\n"
		+ "union\r\n"
		+ "select 4 sno,flow,flowid,kitno,0 oqty,0 rqty,0 dqty,sum(sqty) cqty  from kitstockdetails where flowid=?3 and orgid=?4 and stockdate <=?2 group by sno,flow,flowid,kitno ) a group by a.kitno")
	Set<Object[]> getKitLedger(String startDate, String endDate, Long flowId, Long orgId);

}
