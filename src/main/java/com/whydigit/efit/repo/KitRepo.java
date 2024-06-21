package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.KitVO;

@Repository
public interface KitRepo extends JpaRepository<KitVO, Long> {

	List<KitVO> findByOrgId(Long orgId);
	
	@Query("select a from KitVO a where  a.orgId=?1 and active=true")
	List<KitVO> findActiveByOrgId(Long orgId);

	@Query("select a from KitVO a where a.kitNo=?1 and a.orgId=?2")
	CharSequence findKitcode(String kitCode, long orgId);

	@Query("select a from KitVO a where a.kitNo=?1")
	Optional<KitVO> findByKitNo(String kitName);

	@Query(nativeQuery = true, value = "select sequence_value from kitnoseq")
	Long finddocid();

	@Query(nativeQuery = true, value = "CALL next_sequence_value()")
	void updatesequence();

	@Query("select a.partQty from KitVO a where a.kitNo=?1 ")
	int findPartqty(String kitName);

	@Query(nativeQuery = true, value = "SELECT RIGHT(\r\n" + "    IF(\r\n"
			+ "        DATE_FORMAT(CURDATE(), '%m%d') > '0331', \r\n" + "        DATE_FORMAT(CURDATE(), '%Y'), \r\n"
			+ "        DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y')\r\n" + "    ), \r\n" + "    2\r\n"
			+ ") AS finyr")
	int getFinyr();

	KitVO findAllByKitNo(String kitName);

//	@Query(nativeQuery = true,value="SELECT \r\n"
//			+ "    a.kitcode AS kitcode,\r\n"
//			+ "    COALESCE(FLOOR(MIN(a.avalqty)), 0) AS avalqty\r\n"
//			+ "FROM\r\n"
//			+ "    (\r\n"
//			+ "    SELECT \r\n"
//			+ "        a.kitno AS kitcode,\r\n"
//			+ "        b.asset AS asset,\r\n"
//			+ "        b.quantity AS quantity,\r\n"
//			+ "        COALESCE(SUM(c.skuqty), 0) AS SUM_skuqty,\r\n"
//			+ "        CASE\r\n"
//			+ "            WHEN b.quantity <> 0 THEN COALESCE(SUM(c.skuqty) / b.quantity, 0)\r\n"
//			+ "            ELSE 0\r\n"
//			+ "        END AS avalqty\r\n"
//			+ "    FROM\r\n"
//			+ "        kit a\r\n"
//			+ "    JOIN kit2 b ON a.kitid = b.kitid\r\n"
//			+ "    LEFT JOIN stockdetails c ON b.asset = c.sku\r\n"
//			+ "        AND c.status = 'S' and stockbranch=?2 and c.orgid=?1\r\n"
//			+ "    GROUP BY  a.kitno, b.asset, b.quantity\r\n"
//			+ "    ) a where a.kitcode=?3\r\n"
//			+ "GROUP BY a.kitcode")
//	List<Object[]> findByAvailableKitQtyByEmitter(Long orgId, String stockbranch, String kitId);

	boolean existsByKitNoAndOrgId(String kitNo, long orgId);

	boolean existsByKitDescAndOrgId(String kitDesc, long orgId);

	KitVO findAllByKitNoAndOrgId(String kitName, long orgId);

	@Query(nativeQuery = true,value = "select kitcode,avlqty from \r\n"
			+ "(select a.flow,b.flowid,a.emitterid,a.kitcode,a.orgid,sum(a.allotedqty)-b.boutwardqty avlqty from bininward a,\r\n"
			+ "(select flow,flowid,emitterid,kitno,sum(outwardkitqty)boutwardqty from binoutward \r\n"
			+ "group by flow,flowid,emitterid,kitno)b where a.kitcode = b.kitno\r\n"
			+ "and a.emitterid=b.emitterid and a.flow=b.flow\r\n"
			+ "group by a.orgid,b.flowid,a.flow,a.emitterid,a.kitcode\r\n"
			+ ") c where c.avlqty > 0 and c.orgid= ?1 and c.emitterid=?2  and  c.kitcode=?3 and c.flowid=?4")
	Set<Object[]> findByAvailableKitQtyByEmitter(Long orgId, Long emitterId, String kitId, Long flowId);


	

}
