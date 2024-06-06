package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.DispatchVO;

public interface DispatchRepository extends JpaRepository<DispatchVO, Long>{

	@Query("select a from DispatchVO a where a.emitterId=?1")
	List<DispatchVO> findAllByEmitterId(Long emitterId);

}
