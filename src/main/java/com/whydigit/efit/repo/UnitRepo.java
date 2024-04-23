package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.UnitVO;


@Repository
public interface UnitRepo extends JpaRepository<UnitVO, Integer> {
	@Query(value = "select a from UnitVO a Where a.orgId=?1")
	List<UnitVO> getAllUnit(Long orgId);


}


