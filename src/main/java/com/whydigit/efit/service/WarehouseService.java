package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.WarehouseDTO;
import com.whydigit.efit.entity.WarehouseVO;
import com.whydigit.efit.exception.ApplicationException;

public interface WarehouseService {

	List<WarehouseVO> getAllWarehouse(Long orgId);
	
	List<WarehouseVO> getAllActiveWarehouse(Long orgId);

	Optional<WarehouseVO> getById(Long id);

	Optional<WarehouseVO> updateWarehouseVo(WarehouseVO warehouse);

	void deleteWarehouse(Long id);

	Set<Object[]> getWarehouseLocationByOrgID(Long orgId);

	WarehouseVO updateCreateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException;

	Optional<WarehouseVO> getWarehouseById(Long id);

	List<WarehouseVO> getWarehouseByUserID(long userId) throws ApplicationException;

	void ExcelUploadForWarehouse(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy) throws ApplicationException;

	int getTotalRows();

	int getSuccessfulUploads();

	Optional<WarehouseVO> getOrginWarehouseByUserId(Long userId, Long orgId);

}
