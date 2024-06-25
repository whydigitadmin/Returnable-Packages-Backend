package com.whydigit.efit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.WarehouseDTO;
import com.whydigit.efit.entity.CountryVO;
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
	public List<WarehouseVO> getAllWarehouse(Long orgId) {

		return warehouseRepo.findAllWarehouse(orgId);
	}

	@Override
	public List<WarehouseVO> getAllActiveWarehouse(Long orgId) {

		return warehouseRepo.findAllActiveWarehouse(orgId);
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

//	@Override
//	public WarehouseVO updateCreateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException {
//		WarehouseVO warehouseVO = new WarehouseVO();
//		if (warehouseDTO.getWarehouseId() != 0) {
//			warehouseVO = warehouseRepo.findById(warehouseDTO.getWarehouseId())
//					.orElseThrow(() -> new ApplicationException("Warehouse not found"));
//			warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());
//		}
//		getWarehouseVOFromWarehouseDTO(warehouseDTO, warehouseVO);
//		return warehouseRepo.save(warehouseVO);
//	}
//
//	private void getWarehouseVOFromWarehouseDTO(WarehouseDTO warehouseDTO, WarehouseVO warehouseVO)
//			throws ApplicationException {
//		if (warehouseDTO.getWarehouseId() != 0) {
//			WarehouseVO existingWarehosue = warehouseRepo.findById(warehouseDTO.getWarehouseId()).orElseThrow(
//					() -> new ApplicationException("Warehouse " + warehouseDTO.getWarehouseId() + " not found"));
//			
//			String whlocation=new StringBuilder(warehouseDTO.getLocationName().toUpperCase()).append("-")
//					.append(warehouseDTO.getUnit().toUpperCase()).toString();
//			if(whlocation!=existingWarehosue.getWarehouseLocation())
//			{
//				if (warehouseRepo.existsByWarehouseLocationAndOrgId(whlocation,existingWarehosue.getOrgId())) {
//					throw new ApplicationException("LocationName And Unit Already Exists");
//				}
//				warehouseVO.setLocationName(warehouseDTO.getLocationName());
//				warehouseVO.setUnit(warehouseDTO.getUnit());
//			}
//			
////			if (!existingWarehosue.getLocationName().equals(warehouseDTO.getLocationName())||!existingWarehosue.getUnit().equals(warehouseDTO.getUnit())) {
////				String whlocation=new StringBuilder(warehouseDTO.getLocationName().toUpperCase()).append("-")
////						.append(warehouseDTO.getUnit().toUpperCase()).toString();
////				
////					if (warehouseRepo.existsByWarehouseLocationAndOrgId(whlocation,existingWarehosue.getOrgId())) {
////						throw new ApplicationException("LocationName And Unit Already Exists");
////					}
////				warehouseVO.setLocationName(warehouseDTO.getLocationName());
////				warehouseVO.setUnit(warehouseDTO.getUnit());
////			}
//			// Update Entity Legal Name if there's no duplicate
//			
//			warehouseVO.setOrgId(warehouseDTO.getOrgId());
//			warehouseVO.setAddress(warehouseDTO.getAddress());
//			warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());
//			warehouseVO.setState(warehouseDTO.getState());
//			warehouseVO.setPincode(warehouseDTO.getPincode());
//			warehouseVO.setCode(warehouseDTO.getCode());
//			warehouseVO.setStockBranch(warehouseDTO.getStockBranch());
//			warehouseVO.setCity(warehouseDTO.getCity());
//			warehouseVO.setCountry(warehouseDTO.getCountry());
//			warehouseVO.setGst(warehouseDTO.getGst());
//			warehouseVO.setActive(warehouseDTO.isActive());
//			warehouseVO.setWarehouseLocation(new StringBuilder(warehouseDTO.getLocationName().toUpperCase()).append("-")
//					.append(warehouseDTO.getUnit().toUpperCase()).toString());
//		}
//		if (warehouseRepo.existsByLocationNameAndUnitAndOrgId(warehouseDTO.getLocationName(), warehouseDTO.getUnit(),
//				warehouseDTO.getOrgId())) {
//			throw new ApplicationException("LocationName And Unit Already Exists");
//		}
//		warehouseVO.setOrgId(warehouseDTO.getOrgId());
//		warehouseVO.setUnit(warehouseDTO.getUnit());
//		warehouseVO.setLocationName(warehouseDTO.getLocationName());
//		warehouseVO.setAddress(warehouseDTO.getAddress());
//		warehouseVO.setCreatedby(warehouseDTO.getCreatedBy());
//		warehouseVO.setState(warehouseDTO.getState());
//		warehouseVO.setPincode(warehouseDTO.getPincode());
//		warehouseVO.setCode(warehouseDTO.getCode());
//		warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());
//		warehouseVO.setStockBranch(warehouseDTO.getStockBranch());
//		warehouseVO.setCity(warehouseDTO.getCity());
//		warehouseVO.setCountry(warehouseDTO.getCountry());
//		warehouseVO.setGst(warehouseDTO.getGst());
//		warehouseVO.setActive(warehouseDTO.isActive());
//		warehouseVO.setWarehouseLocation(new StringBuilder(warehouseDTO.getLocationName().toUpperCase()).append("-")
//				.append(warehouseDTO.getUnit().toUpperCase()).toString());
//
//	}

	@Override
	public WarehouseVO updateCreateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException {
		WarehouseVO warehouseVO;

		if (warehouseDTO.getWarehouseId() != 0) {
			// Update existing warehouse
			warehouseVO = warehouseRepo.findById(warehouseDTO.getWarehouseId())
					.orElseThrow(() -> new ApplicationException("Warehouse not found"));

			// Update modified by field
			warehouseVO.setModifiedby(warehouseDTO.getCreatedBy());

			// Update other fields from DTO
			updateWarehouseVOFromWarehouseDTO(warehouseDTO, warehouseVO);
		} else {
			// Create new warehouse
			warehouseVO = new WarehouseVO();
			populateWarehouseVOFromWarehouseDTO(warehouseDTO, warehouseVO);

			// Perform duplicate check
			checkForDuplicateWarehouseLocation(warehouseDTO);
		}

		return warehouseRepo.save(warehouseVO);
	}

	private void updateWarehouseVOFromWarehouseDTO(WarehouseDTO warehouseDTO, WarehouseVO warehouseVO)
			throws ApplicationException {
		// Check if location or unit is changing
		String newWarehouseLocation = warehouseDTO.getLocationName().toUpperCase() + "-"
				+ warehouseDTO.getUnit().toUpperCase();
		if (!newWarehouseLocation.equals(warehouseVO.getWarehouseLocation())) {
			// Perform duplicate check
			checkForDuplicateWarehouseLocation(warehouseDTO);

			// Update location and unit
			warehouseVO.setLocationName(warehouseDTO.getLocationName());
			warehouseVO.setUnit(warehouseDTO.getUnit());
			warehouseVO.setWarehouseLocation(newWarehouseLocation);
		}

		// Update other fields
		warehouseVO.setOrgId(warehouseDTO.getOrgId());
		warehouseVO.setAddress(warehouseDTO.getAddress());
		warehouseVO.setState(warehouseDTO.getState());
		warehouseVO.setPincode(warehouseDTO.getPincode());
		warehouseVO.setCode(warehouseDTO.getCode());
		warehouseVO.setStockBranch(warehouseDTO.getStockBranch());
		warehouseVO.setCity(warehouseDTO.getCity());
		warehouseVO.setCountry(warehouseDTO.getCountry());
		warehouseVO.setGst(warehouseDTO.getGst());
		warehouseVO.setActive(warehouseDTO.isActive());
	}

	private void populateWarehouseVOFromWarehouseDTO(WarehouseDTO warehouseDTO, WarehouseVO warehouseVO) {
		// Populate all fields from DTO for a new warehouse
		warehouseVO.setOrgId(warehouseDTO.getOrgId());
		warehouseVO.setUnit(warehouseDTO.getUnit());
		warehouseVO.setLocationName(warehouseDTO.getLocationName());
		warehouseVO.setAddress(warehouseDTO.getAddress());
		warehouseVO.setCreatedby(warehouseDTO.getCreatedBy());
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

	private void checkForDuplicateWarehouseLocation(WarehouseDTO warehouseDTO) throws ApplicationException {
		String whLocation = warehouseDTO.getLocationName().toUpperCase() + "-" + warehouseDTO.getUnit().toUpperCase();
		if (warehouseRepo.existsByWarehouseLocationAndOrgId(whLocation, warehouseDTO.getOrgId())) {
			throw new ApplicationException("LocationName and Unit already exists.");
		}
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

	// WAREHOUSE EXCEL UPLOAD

	private int totalRows = 0; // Initialize totalRows
	private int successfulUploads = 0; // Initialize successfulUploads

	@Override
	@Transactional
	public void ExcelUploadForWarehouse(MultipartFile[] files, CustomerAttachmentType type, Long orgId,
			String CreatedBy) throws ApplicationException {
		List<WarehouseVO> WarehouseVOsToSave = new ArrayList<>();
		totalRows = 0; // Reset totalRows for each execution
		successfulUploads = 0; // Reset successfulUploads for each execution

		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
				List<String> errorMessages = new ArrayList<>();
				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement

				// Check all rows for validity first
				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty1(row)) {
						continue; // Skip header row and empty rows
					}

					totalRows++; // Increment totalRows
					System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

					// Retrieve cell values and handle different types
					String location = getStringCellValue(row.getCell(0)); // Adjusted to match column position
					String unit = getStringCellValue(row.getCell(1));
					String code = getStringCellValue(row.getCell(2));
					String address = getStringCellValue(row.getCell(3));
					String country = getStringCellValue(row.getCell(4));
					String state = getStringCellValue(row.getCell(5));
					String city = getStringCellValue(row.getCell(6));
					String pincode = getStringCellValue(row.getCell(7));
					String gst = getStringCellValue(row.getCell(8));
					String stockBranch = getStringCellValue(row.getCell(9));

					// Validate each row
					try {
						if (warehouseRepo.existsByWarehouseLocationAndCodeAndOrgId(location, code, orgId)) {
							errorMessages.add("location and code " + location.toUpperCase() + code.toUpperCase()
									+ " already exists for this organization. Row: " + (row.getRowNum() + 1));
						}

						if (warehouseRepo.existsByWarehouseLocationAndOrgId(location, orgId)) {
							errorMessages.add("location " + location.toUpperCase()
									+ " already exists for this organization. Row: " + (row.getRowNum() + 1));
						}
						if (warehouseRepo.existsByCodeAndOrgId(code, orgId)) {
							errorMessages.add("Code " + code.toUpperCase()
									+ " already exists for this organization. Row: " + (row.getRowNum() + 1));
						}

					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// If there are errors, throw ApplicationException and do not save any rows
				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

				// No errors found, now save all rows
				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty1(row)) {
						continue; // Skip header row and empty rows
					}

					System.out.println("Saving row: " + (row.getRowNum() + 1)); // Debug statement

					String location = getStringCellValue(row.getCell(0)); // Adjusted to match column position
					String unit = getStringCellValue(row.getCell(1));
					String code = getStringCellValue(row.getCell(2));
					String address = getStringCellValue(row.getCell(3));
					String country = getStringCellValue(row.getCell(4));
					String state = getStringCellValue(row.getCell(5));
					String city = getStringCellValue(row.getCell(6));
					String pincode = getStringCellValue(row.getCell(7));
					String gst = getStringCellValue(row.getCell(8));
					String stockBranch = getStringCellValue(row.getCell(9));

					// Create BranchVO and add to list for batch saving
					WarehouseVO warehouseVO = new WarehouseVO();
					warehouseVO.setOrgId(orgId);
					warehouseVO.setCode(code.toUpperCase());
					warehouseVO.setCountry(country.toUpperCase());
					warehouseVO.setState(state.toUpperCase());
					warehouseVO.setActive(true);
					warehouseVO.setAddress(address);
					warehouseVO.setCreatedby(CreatedBy);
					warehouseVO.setModifiedby(CreatedBy);
					warehouseVO.setWarehouseLocation(location.toUpperCase() + "-" + unit.toUpperCase());
					warehouseVO.setLocationName(location.toUpperCase());
					warehouseVO.setUnit(unit.toUpperCase());
					warehouseVO.setCity(city.toUpperCase());
					warehouseVO.setPincode(pincode.toUpperCase());
					warehouseVO.setGst(gst.toUpperCase());
					warehouseVO.setStockBranch(stockBranch.toUpperCase());

					WarehouseVOsToSave.add(warehouseVO);
					successfulUploads++; // Increment successfulUploads
				}
			} catch (IOException e) {
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}

		// Batch save all BranchVOs
		warehouseRepo.saveAll(WarehouseVOsToSave);
	}

	private boolean isRowEmpty1(Row row) {
		for (Cell cell : row) {
			if (cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getSuccessfulUploads() {
		return successfulUploads;
	}

}
