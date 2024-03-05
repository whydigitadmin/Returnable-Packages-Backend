package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.whydigit.efit.dto.WarehouseDTO;
import com.whydigit.efit.entity.WarehouseVO;
import com.whydigit.efit.exception.ApplicationException;

public interface WarehouseService {

	List<WarehouseVO> getAllWarehouse();

	Optional<WarehouseVO> getById(Long id);

//	WarehouseVO createWarehouseVO(WarehouseVO warehousevo);

	Optional<WarehouseVO> updateWarehouseVo(WarehouseVO warehouse);

	void deleteWarehouse(Long id);

	Set<Object[]> getWarehouseLocationByOrgID(Long orgId);

	WarehouseVO updateCreateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException;

	WarehouseVO createWarehouseVO(WarehouseVO warehousevo);

}
