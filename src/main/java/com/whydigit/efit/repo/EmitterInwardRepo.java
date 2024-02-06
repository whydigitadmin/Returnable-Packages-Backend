package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.EmitterInwardVO;

public interface EmitterInwardRepo extends JpaRepository<EmitterInwardVO, Integer> {

	@Query(value = "select ei from EmitterInwardVO ei Where ei.orgId=?1")
	List<EmitterInwardVO> getAllEmitterInwardByOrgId(Long orgId);

}
