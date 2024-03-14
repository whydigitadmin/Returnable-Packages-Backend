package com.whydigit.efit.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.IssueRequestVO;

public interface IssueRequestRepo
		extends JpaRepository<IssueRequestVO, Long>, JpaSpecificationExecutor<IssueRequestVO> {

	List<IssueRequestVO> findAll(Specification<IssueRequestVO> specification);

	List<IssueRequestVO> findByOrgId(Long orgId);

	@Modifying
	@Transactional
	@Query(value = "update IssueRequestVO ir set ir.issueStatus=?2 where ir.id=?1")
	int cancelIssueRequestByIssueRequestId(Long issueRequestId, int issueRequestStatusCancelled);

	@Query(value = "select b.warehouse_id from issue_request a , flow b where a.flow_to=b.id and a.flow_to=?1 group BY  b.warehouse_id",nativeQuery = true)
	long findWarehouseLocationId(Long flowTo);

	@Query(value = "select b.warehouse_location from issue_request a , flow b where a.flow_to=b.id and a.flow_to=?1 group BY  b.warehouse_location",nativeQuery = true)
	String findWarehouseLocation(Long flowTo);
	
}
