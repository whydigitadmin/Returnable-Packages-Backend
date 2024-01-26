
package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.WarehouseLocationVO;

@Repository
public interface WarehouseLocationRepo extends JpaRepository<WarehouseLocationVO, Integer> {
	@Query(value = "select a from WarehouseLocationVO a Where a.orgId=?1")
	List<WarehouseLocationVO> getAllWarehouseLocation(Long orgId);
}
