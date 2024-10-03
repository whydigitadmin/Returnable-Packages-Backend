
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.VendorVO;

@Repository
public interface VendorRepo extends JpaRepository<VendorVO, Long> {
	@Query(value = "select a from VendorVO a Where a.orgId=?1 and a.active=true")
	List<VendorVO> getAllActiveVenderByOrgId(Long orgId);
	
	@Query(value = "select a from VendorVO a Where a.orgId=?1")
	List<VendorVO> getAllVenderByOrgId(Long orgId);

	boolean existsByEntityLegalNameAndDisplyNameAndOrgId(String entityLegalName, String displyName, long orgId);

	boolean existsByDisplyNameAndOrgId(String displyName, Long orgId);

	boolean existsByEntityLegalNameAndOrgId(String entityLegalName, Long orgId);
	
	
	List<VendorVO> getAllActiveVendorsByOrgId(Long orgId);

	 @Query("select a from VendorVO a where a.venderType='TRANSPORT' and a.active=true")
	List<VendorVO> getAllActiveTransportVendorsByOrgId(Long orgId);
	 
    @Query("select a from VendorVO a where a.venderType='SUPPLIER' and a.active=true")
	List<VendorVO> getAllActiveSupplierVendorsByOrgId(Long orgId);

	boolean existsByOrgIdAndEntityLegalNameIgnoreCase(long orgId, String entityLegalName);

	

	

}
