package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.EmitterOutwardVO;

public interface EmitterOutwardRepo extends JpaRepository<EmitterOutwardVO, Integer> {

	@Query(value = "select eo from EmitterOutwardVO eo Where eo.orgId=?1")
	List<EmitterOutwardVO> getAllEmitterOutwardByOrgId(Long orgId);

}