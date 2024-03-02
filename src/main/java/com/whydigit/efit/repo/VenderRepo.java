
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.VenderVO;

@Repository
public interface VenderRepo extends JpaRepository<VenderVO, Integer> {
	@Query(value = "select a from VenderVO a Where a.orgId=?1")
	List<VenderVO> getAllVenderByOrgId(Long orgId);

	

}
