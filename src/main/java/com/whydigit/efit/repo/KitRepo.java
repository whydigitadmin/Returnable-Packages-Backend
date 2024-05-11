package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.KitVO;

@Repository
public interface KitRepo extends JpaRepository<KitVO, Long> {

	List<KitVO> findByOrgId(Long orgId);

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

	@Query(nativeQuery = true, value = "SELECT kit, availkitqty FROM availablekitemitter WHERE orgid =?1 AND emitterid =?2 AND kit =?3 AND flowid =?4")
	List<Object[]> findByavaliableKitQtyByEmitter(Long orgId, Long emitterId, String kitId, Long flowId);

	boolean existsByKitNoAndOrgId(String kitNo, long orgId);

	boolean existsByKitDescAndOrgId(String kitDesc, long orgId);

	KitVO findAllByKitNoAndOrgId(String kitName, long orgId);

	

}
