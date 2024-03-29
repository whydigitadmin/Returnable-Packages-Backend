
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.ManufacturerProductVO;


@Repository
public interface ManufacturerProductRepo extends JpaRepository<ManufacturerProductVO, Integer> {
	@Query(value = "select a from ManufacturerProductVO a Where a.orgId=?1")
	List<ManufacturerProductVO> getAllManufacturerProduct(Long orgId);

}
