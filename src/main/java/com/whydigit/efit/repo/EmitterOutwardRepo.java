package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.EmitterOutwardVO;

public interface EmitterOutwardRepo extends JpaRepository<EmitterOutwardVO, Long> {

	@Query(value = "select eo from EmitterOutwardVO eo Where eo.orgId=?1")
	List<EmitterOutwardVO> getAllEmitterOutwardByOrgId(Long orgId);

	@Query(value="select * from emitter_outward where issue_item_id=?1",nativeQuery = true)
	Optional<EmitterOutwardVO> findByIssueItemId(long issueItemId);


}