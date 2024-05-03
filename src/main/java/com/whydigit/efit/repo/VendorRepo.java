
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.VendorVO;

@Repository
public interface VendorRepo extends JpaRepository<VendorVO, Long> {
	@Query(value = "select a from VendorVO a Where a.orgId=?1")
	List<VendorVO> getAllVenderByOrgId(Long orgId);

	boolean existsByEntityLegalNameAndDisplyNameAndOrgId(String entityLegalName, String displyName, long orgId);

	

}
