
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.BasicDetailVO;

@Repository
public interface BasicDetailRepo extends JpaRepository<BasicDetailVO, Long> {
	@Query(value = "select a from BasicDetailVO a Where a.orgId=?1")
	List<BasicDetailVO> getAllBasicDetailByOrgId(Long orgId);

	List<BasicDetailVO> findAll(Specification<BasicDetailVO> specification);

	@Query(nativeQuery = true,value = "select sequence_value from basicdetailseq")
	String finddocid();

	@Query(nativeQuery = true,value = "CALL next_basicdetail_sequence_value()")
	void updatesequence();

	@Query(value = "select a from BasicDetailVO a Where a.orgId=?1 and a.active=true")
	List<BasicDetailVO> getAllActiveBasicDetailByOrgId(Long orgId);

	@Query(value = "select a from BasicDetailVO a Where a.orgId=?2 and a.partNumber=?1")
	BasicDetailVO findByPartNumberAndOrgId(String partNumber,Long orgId);

	
}
