package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.EmitterOutwardVO;

public interface EmitterOutwardRepo extends JpaRepository<EmitterOutwardVO, Long> {

	@Query(value = "select * from emitteroutward where orgid=?1",nativeQuery =true)
	List<EmitterOutwardVO> getAllEmitterOutwardByOrgId(Long orgId);

	@Query(value="select * from emitteroutward where issue_item_id=?1",nativeQuery = true)
	Optional<EmitterOutwardVO> findByIssueItemId(long issueItemId);
	
	@Query(value="select * from emitteroutward where issue_item_id=?1",nativeQuery = true)
	EmitterOutwardVO findOutwardByIssueItemId(long issueItemId);

	


}