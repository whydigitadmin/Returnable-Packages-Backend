package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.WarehouseVO;
import com.whydigit.efit.repo.WarehouseRepository;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	@Autowired
	WarehouseRepository warehouseRepo;

	@Override
	public List<WarehouseVO> getAllWarehouse() {
		
		return warehouseRepo.findAll();
	}

	@Override
	public Optional<WarehouseVO> getById(int id) {
		
		return warehouseRepo.findById(id);
	}

	@Override
	public WarehouseVO createWarehouseVO(WarehouseVO warehousevo) {
		
		return warehouseRepo.save(warehousevo);
	}

	@Override
	public Optional<WarehouseVO> updateWarehouseVo(WarehouseVO warehouse) {
		if(warehouseRepo.existsById(warehouse.getWarehouse_id()))
		{
			return Optional.of(warehouseRepo.save(warehouse));
		}
		else {
			return Optional.empty();
		}
		
	}

	@Override
	public void deleteWarehouse(int id) {
		warehouseRepo.deleteById(id);
		
	}

}
