package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinAllotmentNewVO;

public interface BinAllotmentNewRepo extends JpaRepository<BinAllotmentNewVO, Long> {

	boolean existsByBinReqNo(String binReqNo);

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int getFinyr();

	@Query(nativeQuery = true, value = "select allotcode from binallotmentseq")
	int finddocid();

	@Query(nativeQuery = true, value = "CALL next_allotcode()")
	void nextDocseq();

	@Query(nativeQuery = true, value = "select a.docid,a.docdate reqDate,a.emitter,a.emitterid,b.kitcode,b.kitqty reqKitQty,b.partno,b.partname,a.flow,a.flowid from issuerequest a, issuerequest2 b where a.issuerequestid=b.issuerequestid and a.orgid=?1 and a.docid=?2")
	Set<Object[]> findReqDetailsByOrgId(Long orgId,String reqno);

	@Query(nativeQuery = true, value = "select * from binallotment where orgid=?1")
	List<BinAllotmentNewVO> getAllBinAllotment(Long orgId);

	@Query(nativeQuery = true, value = "SELECT docid\r\n"
			+ "FROM binallotment\r\n"
			+ "WHERE emitterid = ?2 \r\n"
			+ "    AND orgid = ?1 \r\n"
			+ "    AND docid NOT IN (SELECT allotmentno FROM bininward)")
	Set<Object[]> getAllotmentNoByEmitterIdAndOrgId(Long orgId, Long emitterId);

	@Query(nativeQuery = true, value = "select a.docdate binallotdate,a.binreqno,a.binreqdate,b.flow,a.kitcode,a.allotkitqty,a.reqkitqty,a.part,a.partcode from binallotment a, issuerequest b \r\n"
			+ "where a.binreqno=b.docid and a.orgid=?1  and a.docid=?2")
	Set<Object[]> getAllotmentDetailsByAllotmentNoAndOrgId(Long orgId, String docid);

	@Query(nativeQuery = true, value = "select b.asset,b.assetcode,b.rfid,b.tagcode,b.skuqty from binallotment a , binallotment1 b where a.binallotmentid=b.binallotmentid and a.docid=?2 and a.orgid=?1")
	Set<Object[]> getAllotmentAssetDetailsByAllotmentNoAndOrgId(Long orgId, String docid);

	@Query(nativeQuery =true,value = "select * from binallotment where docid=?1")
	List<BinAllotmentNewVO> getAllAssetByOrgId(String docId);
	
	@Query(nativeQuery =true,value = "SELECT a.docid, a.docdate, b.flow, a.kitcode, a.allotkitqty, a.reqkitqty,a.emitterid,a.orgid,a.part as partname,a.partcode FROM binallotment a \r\n"
			+ "INNER JOIN issuerequest b ON a.binreqno = b.docid WHERE a.docid NOT IN (SELECT allotmentno FROM bininward) and a.orgid=?1 and a.emitterid=?2")
	Set<Object[]> getWaitingforBinInwardDetailsByEmitterAndOrgId(Long orgId, Long emitterid);

	
	@Query(nativeQuery = true,value="SELECT  \r\n"
			+ "                        ss.stockbranch,\r\n"
			+ "                        ss.tagcode,\r\n"
			+ "                        ss.sku,\r\n"
			+ "                        ss.rfid,\r\n"
			+ "                        ss.skucode,\r\n"
			+ "                        ss.tn,\r\n"
			+ "                        ss.skuqty,\r\n"
			+ "                        bp.quantity,bp.asset\r\n"
			+ "                    FROM (\r\n"
			+ "                        SELECT  \r\n"
			+ "                            stockbranch,\r\n"
			+ "                            tagcode,\r\n"
			+ "                            sku,\r\n"
			+ "                            rfid,\r\n"
			+ "                            skucode,\r\n"
			+ "                            skuqty,\r\n"
			+ "                            tn\r\n"
			+ "                        FROM (\r\n"
			+ "                            SELECT  \r\n"
			+ "                                stockbranch,\r\n"
			+ "                                tagcode,\r\n"
			+ "                                sku,\r\n"
			+ "                                rfid,\r\n"
			+ "                                skucode,\r\n"
			+ "                                ROW_NUMBER() OVER (PARTITION BY skucode) AS tn,\r\n"
			+ "                                skuqty\r\n"
			+ "                            FROM (\r\n"
			+ "                                SELECT  \r\n"
			+ "                                    stockbranch,\r\n"
			+ "                                    tagcode,\r\n"
			+ "                                    sku,\r\n"
			+ "                                    skucode,\r\n"
			+ "                                    rfid,\r\n"
			+ "                                    SUM(skuqty) AS skuqty\r\n"
			+ "                                FROM \r\n"
			+ "                                    stockdetails\r\n"
			+ "                                WHERE \r\n"
			+ "                                    status = 'S'  and stockbranch=?3\r\n"
			+ "                                GROUP BY \r\n"
			+ "                                    stockbranch,\r\n"
			+ "                                    tagcode,\r\n"
			+ "                                    sku,\r\n"
			+ "                                    rfid,\r\n"
			+ "                                    skucode\r\n"
			+ "                                HAVING \r\n"
			+ "                                    skuqty > 0  \r\n"
			+ "                                    order by tagcode\r\n"
			+ "                            ) AS sub\r\n"
			+ "                            GROUP BY  \r\n"
			+ "                                stockbranch,\r\n"
			+ "                                tagcode,\r\n"
			+ "                                sku,\r\n"
			+ "                                rfid,\r\n"
			+ "                                skucode\r\n"
			+ "                            HAVING \r\n"
			+ "                                SUM(skuqty) > 0\r\n"
			+ "                            ORDER BY \r\n"
			+ "                            tagcode,\r\n"
			+ "                                ROW_NUMBER() OVER (PARTITION BY stockbranch, tagcode, sku, skucode)\r\n"
			+ "                    )  AS s )ss\r\n"
			+ "                     JOIN (\r\n"
			+ "                        SELECT  \r\n"
			+ "                            asset,\r\n"
			+ "                            SUM(quantity) AS quantity\r\n"
			+ "                        FROM (\r\n"
			+ "                            SELECT  \r\n"
			+ "                                asset,\r\n"
			+ "                                quantity * ?2  as quantity\r\n"
			+ "                            FROM \r\n"
			+ "                                kit2 a\r\n"
			+ "                            INNER JOIN \r\n"
			+ "                                kit b ON a.kitid = b.kitid\r\n"
			+ "                            WHERE \r\n"
			+ "                                kitno = ?1\r\n"
			+ "                      ) AS sub\r\n"
			+ "                        GROUP BY \r\n"
			+ "                            asset\r\n"
			+ "                    ) AS bp ON ss.sku = bp.asset and ss.tn <= bp.quantity\r\n"
			+ "                    group by \r\n"
			+ "                    ss.stockbranch,\r\n"
			+ "                        ss.tagcode,\r\n"
			+ "                        ss.sku,\r\n"
			+ "                        ss.skucode,\r\n"
			+ "                        ss.tn,\r\n"
			+ "                        ss.skuqty,\r\n"
			+ "                        ss.rfid,\r\n"
			+ "                        bp.quantity  ")
	List<Object[]> RandomAssetDetailsByKitCodeAndAllotQty(String kitCode, int qty,String stockbranch);

	List<BinAllotmentNewVO> findAll(Specification<BinAllotmentNewVO> specification);

	List<BinAllotmentNewVO> findAllBinAllotmentByEmitterId(Specification<BinAllotmentNewVO> specification);

	@Query(nativeQuery = true, value = "select a.docid allotno,a.docdate allotdate,a.binreqno,a.binreqdate,c.address sender_address,c.city sender_city,c.state sender_state,c.gst sender_gst,d.name sender_name,e.legalname receiver_name,c.pincode from binallotment a, issuerequest b,warehouse c ,organization d,customer e where a.orgid=d.organizationid and a.binreqno=b.docid and b.whlocationid=c.warehouseid and b.emitterid=e.customerid and a.docid=?1")
	Set<Object[]> getBinAllotmentHeader(String docid);

	@Query(nativeQuery = true, value = "select a.kitcode,a.allotkitqty,b.asset product_name,b.assetcode product_code,sum(b.skuqty) product_qty from binallotment a, binallotment1 b where a.binallotmentid=b.binallotmentid and a.docid=?1 group by a.kitcode,a.allotkitqty,b.asset,b.assetcode")
	List<Object[]> getBinAllotmentGrid(String docid);

	
	

}
