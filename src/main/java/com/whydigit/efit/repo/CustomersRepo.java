
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CustomersVO;


@Repository
public interface CustomersRepo extends JpaRepository<CustomersVO, Long> {
	@Query(value = "select a from CustomersVO a Where a.orgId=?1")
	List<CustomersVO> getAllCustomersByOrgId(Long orgId);

	List<CustomersVO> findByOrgId(Long orgId);

	@Query(nativeQuery = true , value ="select code from customer where customerid=?1")
	String findcustomercodeByEmitterId(long emitterId);

	boolean existsByEntityLegalNameAndDisplayNameAndOrgId(String entityLegalName, String displayName, long orgId);

	

}


