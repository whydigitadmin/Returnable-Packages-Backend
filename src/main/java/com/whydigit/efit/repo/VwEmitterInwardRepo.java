package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.VwEmitterInwardVO;

public interface VwEmitterInwardRepo extends JpaRepository<VwEmitterInwardVO, Long>{
	
@Query(name = "select * from VwEmitterInwardVO where orgId=?1 And emitterId=?2")
	List<VwEmitterInwardVO> findByOrgIdAndEmitter(long orgId, long emitterId);

}
