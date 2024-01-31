
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.PackingDetailVO;

@Repository
public interface PackingDetailRepo extends JpaRepository<PackingDetailVO, Integer> {
	@Query(value = "select a from PackingDetailVO a Where a.orgId=?1")
	List<PackingDetailVO> getpackingDetailByOrgId(Long orgId);
}
