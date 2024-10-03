
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CustomersVO;

@Repository
public interface CustomersRepo extends JpaRepository<CustomersVO, Long> {
	@Query(value = "select a from CustomersVO a Where a.orgId=?1 and a.active=true")
	List<CustomersVO> getAllActiveCustomersByOrgId(Long orgId);
	
	@Query(value = "select a from CustomersVO a Where a.orgId=?1")
	List<CustomersVO> getAllCustomersByOrgId(Long orgId);
	
	

	List<CustomersVO> findByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select code from customer where customerid=?1")
	String findcustomercodeByEmitterId(long emitterId);

	boolean existsByEntityLegalNameAndDisplayNameAndOrgId(String entityLegalName, String displayName, long orgId);

	@Query(nativeQuery = true, value = "select custcode from customercodeseq")
	int getCustomerCodeSeq();

	@Query(nativeQuery = true, value = "CALL next_custcode()")
	void nextCustomerCode();

	@Query(nativeQuery = true, value = "select reccode from receivercodeseq")
	int getRecCodeSeq();

	@Query(nativeQuery = true, value = "CALL next_reccode()")
	void nextRecCode();

	@Query(nativeQuery = true, value = "select displayname from customer where customerid =?1")
	String findCustomerLegalnameByEmitterId(long emitterId);

	boolean existsByEntityLegalNameAndOrgId(String entityLegalName, long orgId);

	boolean existsByDisplayNameAndOrgId(String displayName, long orgId);

	boolean existsByOrgIdAndEntityLegalNameIgnoreCase(long orgId, String entityLegalName);


	
	}

	


