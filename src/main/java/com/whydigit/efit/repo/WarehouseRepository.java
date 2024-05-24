package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.WarehouseVO;

public interface WarehouseRepository extends JpaRepository<WarehouseVO, Long>{
	
@Query(value = "select w.warehouseLocation,w.warehouseId  from WarehouseVO w where w.orgId=?1")
	Set<Object[]> getWarehouseLocationByOrgID(Long orgId);

boolean existsByLocationNameAndUnitAndOrgId(String locationName, String unit, Long orgId);

boolean existsByWarehouseLocationAndOrgId(String whlocation, Long orgId);

@Query(value = "select a from WarehouseVO a where a.orgId=?1")
List<WarehouseVO> findAllWarehouse(Long orgId);

@Query(value = "select a from WarehouseVO a where a.orgId=?1 and a.active=true")
List<WarehouseVO> findAllActiveWarehouse(Long orgId);

}
