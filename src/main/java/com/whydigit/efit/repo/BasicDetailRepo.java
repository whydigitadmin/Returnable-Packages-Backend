
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.BasicDetailVO;

@Repository
public interface BasicDetailRepo extends JpaRepository<BasicDetailVO, Integer> {
	@Query(value = "select a from BasicDetailVO a Where a.orgId=?1")
	List<BasicDetailVO> getAllBasicDetailByOrgId(Long orgId);
}
