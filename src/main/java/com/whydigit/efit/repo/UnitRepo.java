package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.UnitVO;


@Repository
public interface UnitRepo extends JpaRepository<UnitVO, Long> {
	@Query(value = "select a from UnitVO a Where a.orgId=?1")
	List<UnitVO> getAllUnit(Long orgId);

	boolean existsByUnitAndOrgId(String unit, long orgId);

	Optional<UnitVO> findById(Long id);


}


