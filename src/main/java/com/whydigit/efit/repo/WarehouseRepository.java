package com.whydigit.efit.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.WarehouseVO;

public interface WarehouseRepository extends JpaRepository<WarehouseVO, Long>{
@Query(value = "select w.warehouseLocation  from WarehouseVO w where w.orgId=?1")
	Set<Object[]> getWarehouseLocationByOrgID(Long orgId);

}
