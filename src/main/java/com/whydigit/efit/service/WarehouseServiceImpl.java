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
import com.whydigit.efit.entity.VendorVO;
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
		if (warehouseDTO.getWarehouseId() != 0) {
			warehouseVO = warehouseRepo.findById(warehouseDTO.getWarehouseId())
					.orElseThrow(() -> new ApplicationException("Warehouse not found"));
			warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());
		}
		getWarehouseVOFromWarehouseDTO(warehouseDTO, warehouseVO);
		return warehouseRepo.save(warehouseVO);
	}

	private void getWarehouseVOFromWarehouseDTO(WarehouseDTO warehouseDTO, WarehouseVO warehouseVO)
			throws ApplicationException {
		if (warehouseDTO.getWarehouseId() != 0) {
			WarehouseVO existingWarehosue = warehouseRepo.findById(warehouseDTO.getWarehouseId()).orElseThrow(
					() -> new ApplicationException("Warehouse " + warehouseDTO.getWarehouseId() + " not found"));
			if (!existingWarehosue.getLocationName().equals(warehouseDTO.getLocationName())) {
					if (warehouseRepo.existsByLocationNameAndUnitAndOrgId(warehouseDTO.getLocationName(),
							warehouseDTO.getUnit(), existingWarehosue.getOrgId())) {
						throw new ApplicationException("LocationName And Unit Already Exists");
					}
				warehouseVO.setLocationName(warehouseDTO.getLocationName());
			}
			// Update Entity Legal Name if there's no duplicate
//			warehouseVO.setUnit(warehouseDTO.getUnit());
			warehouseVO.setOrgId(warehouseDTO.getOrgId());
			warehouseVO.setAddress(warehouseDTO.getAddress());
			warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());
			warehouseVO.setState(warehouseDTO.getState());
			warehouseVO.setPincode(warehouseDTO.getPincode());
			warehouseVO.setCode(warehouseDTO.getCode());
			warehouseVO.setStockBranch(warehouseDTO.getStockBranch());
			warehouseVO.setCity(warehouseDTO.getCity());
			warehouseVO.setCountry(warehouseDTO.getCountry());
			warehouseVO.setGst(warehouseDTO.getGst());
			warehouseVO.setActive(warehouseDTO.isActive());
			warehouseVO.setWarehouseLocation(new StringBuilder(warehouseDTO.getLocationName().toUpperCase()).append("-")
					.append(warehouseDTO.getUnit().toUpperCase()).toString());
		}
		if (warehouseRepo.existsByLocationNameAndUnitAndOrgId(warehouseDTO.getLocationName(), warehouseDTO.getUnit(),
				warehouseDTO.getOrgId())) {
			throw new ApplicationException("LocationName And Unit Already Exists");
		}
		warehouseVO.setOrgId(warehouseDTO.getOrgId());
		warehouseVO.setUnit(warehouseDTO.getUnit());
		warehouseVO.setLocationName(warehouseDTO.getLocationName());
		warehouseVO.setAddress(warehouseDTO.getAddress());
		warehouseVO.setCreatedby(warehouseDTO.getCreatedBy());
		warehouseVO.setState(warehouseDTO.getState());
		warehouseVO.setPincode(warehouseDTO.getPincode());
		warehouseVO.setCode(warehouseDTO.getCode());
		warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());
		warehouseVO.setStockBranch(warehouseDTO.getStockBranch());
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
