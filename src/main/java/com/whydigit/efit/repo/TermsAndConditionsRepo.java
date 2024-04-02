package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.TermsAndConditionsVO;

public interface TermsAndConditionsRepo extends JpaRepository<TermsAndConditionsVO, Long> {

	@Query(nativeQuery = true, value = "select * from terms where orgid=?")
	List<TermsAndConditionsVO> getAllTermsByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from terms where termsId=?")
	List<TermsAndConditionsVO> getAllTermsByTermsId(Long termsId);

}
