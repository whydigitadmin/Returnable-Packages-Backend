package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;

public interface EmitterOutwardRepo extends JpaRepository<EmitterOutwardVO, Long> {

	@Query(value = "select * from emitteroutward where orgid=?1",nativeQuery =true)
	List<EmitterOutwardVO> getAllEmitterOutwardByOrgId(Long orgId);

	@Query(value="select * from emitteroutward where issue_item_id=?1",nativeQuery = true)
	Optional<EmitterOutwardVO> findByIssueItemId(long issueItemId);
	
	@Query(value="select * from emitteroutward where issue_item_id=?1",nativeQuery = true)
	EmitterOutwardVO findOutwardByIssueItemId(long issueItemId);

	@Query(nativeQuery = true,value = "select b.docid,b.docdate,b.allotedqty,f.partno,f.kitno from bininward b , flow1 f where b.emitterid=f.emitterid and f.orgid=?1 and f.emitterid=?2 and f.flow1id=?3")
	List<Object[]> findEmitterDispatchByFlowId(Long orgId, Long flowId, Long emitterId);

	


}