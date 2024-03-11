package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.VwEmitterInwardVO;

public interface VwEmitterInwardRepo extends JpaRepository<VwEmitterInwardVO, Long>{
	
   @Query(nativeQuery = true,value= "select a.* from vw_emiter_inward a where a.org_id=?1 And a.emitter_id=?2")
	List<VwEmitterInwardVO> findAllByOrgId(Long orgId, Long emitterId);


   @Query(nativeQuery = true,value= "select a.* from vw_emiter_inward a where a.org_id=?1 And a.emitter_id=?2 And a.flow_id=?3")
List<VwEmitterInwardVO> findAllByOrgIdFlow(Long orgId, Long emitterId, Long flowid);


   @Query(nativeQuery = true,value= "select a.* from vw_emiter_inward a where a.org_id=?1 And a.warehouse_location_id=?2")
List<VwEmitterInwardVO> findAllByOrgIdAndWarehosue(Long orgId, Long warehouseid);




List<VwEmitterInwardVO> findAll(Specification<VwEmitterInwardVO> specification);

}
