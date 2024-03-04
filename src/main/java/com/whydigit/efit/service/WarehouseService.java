package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.whydigit.efit.entity.WarehouseVO;

public interface WarehouseService {

	List<WarehouseVO>getAllWarehouse();
	
	Optional<WarehouseVO>getById(int id);
	
	WarehouseVO createWarehouseVO(WarehouseVO warehousevo);
	
	Optional<WarehouseVO>updateWarehouseVo(WarehouseVO warehouse);
	
	void deleteWarehouse(int id);

	Set<Object[]> getWarehouseLocationByOrgID(Long orgId);
	
	
	
	
}
