package com.whydigit.efit.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.WarehouseDTO;
import com.whydigit.efit.entity.WarehouseVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.UserRepo;
import com.whydigit.efit.repo.WarehouseRepository;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	WarehouseRepository warehouseRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public List<WarehouseVO> getAllWarehouse() {

		return warehouseRepo.findAll();
	}

	@Override
	public Optional<WarehouseVO> getById(Long id) {

		return warehouseRepo.findById(id);
	}

	@Override
	public Optional<WarehouseVO> updateWarehouseVo(WarehouseVO warehouse) {
		if (warehouseRepo.existsById(warehouse.getWarehouseId())) {
			return Optional.of(warehouseRepo.save(warehouse));
		} else {
			return Optional.empty();
		}

	}

	@Override
	public void deleteWarehouse(Long id) {
		warehouseRepo.deleteById(id);

	}

	@Override
	public Set<Object[]> getWarehouseLocationByOrgID(Long orgId) {

		return warehouseRepo.getWarehouseLocationByOrgID(orgId);
	}

	@Override
	public WarehouseVO updateCreateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException {
		WarehouseVO warehouseVO = new WarehouseVO();
		if (ObjectUtils.isNotEmpty(warehouseDTO.getWarehouseId())) {
			warehouseVO = warehouseRepo.findById(warehouseDTO.getWarehouseId())
					.orElseThrow(() -> new ApplicationException("Invalid warehouse details"));
		}
		getWarehouseVOFromWarehouseDTO(warehouseDTO, warehouseVO);
		return warehouseRepo.save(warehouseVO);
	}

	private void getWarehouseVOFromWarehouseDTO(WarehouseDTO warehouseDTO, WarehouseVO warehouseVO) {
		warehouseVO.setOrgId(warehouseDTO.getOrgId());
		warehouseVO.setLocationName(warehouseDTO.getLocationName());
		warehouseVO.setAddress(warehouseDTO.getAddress());
		warehouseVO.setState(warehouseDTO.getState());
		warehouseVO.setPincode(warehouseDTO.getPincode());
		warehouseVO.setUnit(warehouseDTO.getUnit());
		warehouseVO.setCode(warehouseDTO.getCode());
		warehouseVO.setCity(warehouseDTO.getCity());
		warehouseVO.setCountry(warehouseDTO.getCountry());
		warehouseVO.setGst(warehouseDTO.getGst());
		warehouseVO.setActive(warehouseDTO.isActive());
		warehouseVO.setWarehouseLocation(new StringBuilder(warehouseDTO.getLocationName().toUpperCase()).append("-")
				.append(warehouseDTO.getUnit().toUpperCase()).toString());
	}

	@Override
	public Optional<WarehouseVO> getWarehouseById(Long id) {
		return warehouseRepo.findById(id);
	}

	@Override
	public List<WarehouseVO> getWarehouseByUserID(long userId) throws ApplicationException {
		String warehouseId = userRepo.getWarehouseByUserID(userId);
		if (StringUtils.isBlank(warehouseId)) {
			throw new ApplicationException("Warehouse not found.");
		}
		return getWarehouseByUserID(warehouseId);
	}

	private List<WarehouseVO> getWarehouseByUserID(String warehouseId) throws ApplicationException {
		List<Long> warehouseIds = Arrays.stream(StringUtils.split(warehouseId, ",")).map(Long::parseLong)
				.collect(Collectors.toList());
		List<WarehouseVO> warehouseVO = warehouseRepo.findAllById(warehouseIds); 
		if (warehouseVO.isEmpty()) {
			throw new ApplicationException("Warehouse not found.");
		}
		return warehouseVO;
		
	}
	
	
	

	


}
