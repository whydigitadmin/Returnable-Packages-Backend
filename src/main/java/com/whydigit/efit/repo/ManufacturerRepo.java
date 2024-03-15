
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.ManufacturerVO;


@Repository
public interface ManufacturerRepo extends JpaRepository<ManufacturerVO, Integer> {
	@Query(value = "select a from ManufacturerVO a Where a.orgId=?1")
	List<ManufacturerVO> getAllManufacturerByOrgId(Long orgId);
}
