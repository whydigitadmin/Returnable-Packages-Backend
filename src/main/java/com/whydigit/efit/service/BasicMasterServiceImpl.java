package com.whydigit.efit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
import com.whydigit.efit.entity.BranchVO;
import com.whydigit.efit.entity.CityVO;
import com.whydigit.efit.entity.CountryVO;
import com.whydigit.efit.entity.CurrencyMasterVO;
import com.whydigit.efit.entity.EmployeeVO;
import com.whydigit.efit.entity.FinancialYearVO;
import com.whydigit.efit.entity.LocalCurrencyVO;
import com.whydigit.efit.entity.StateVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.BranchRepo;
import com.whydigit.efit.repo.CityRepo;
import com.whydigit.efit.repo.CountryRepo;
import com.whydigit.efit.repo.CurrencyMasterRepo;
import com.whydigit.efit.repo.EmployeeRepo;
import com.whydigit.efit.repo.FinancialRepo;
import com.whydigit.efit.repo.LocalCurrencyRepo;
import com.whydigit.efit.repo.StateRepo;

@Service
public class BasicMasterServiceImpl implements BasicMasterService {

	@Autowired
	private LocalCurrencyRepo localCurrencyRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private FinancialRepo financialRepo;

	@Autowired
	private CurrencyMasterRepo currencyMasterRepo;
	
	@Autowired
	private BranchRepo branchRepo;
	
	@Override
	public List<LocalCurrencyVO> getAllLocalCurrencies() {
		return localCurrencyRepo.findAll();
	}

	@Override
	public Optional<LocalCurrencyVO> getLocalCurrencyById(int id) {
		return localCurrencyRepo.findById(id);
	}

	@Override
	public LocalCurrencyVO createLocalCurrency(LocalCurrencyVO localCurrencyVO) {
		return localCurrencyRepo.save(localCurrencyVO);
	}

	@Override
	public Optional<LocalCurrencyVO> updateLocalCurrency(LocalCurrencyVO localCurrencyVO) {
		if (localCurrencyRepo.existsById(localCurrencyVO.getId())) {
			return Optional.of(localCurrencyRepo.save(localCurrencyVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteLocalCurrency(int id) {
		localCurrencyRepo.deleteById(id);
	}

	@Override
	public List<CountryVO> getAllgetAllcountries() {
		return countryRepo.findAll();
	}

	@Override
	public Optional<CountryVO> getCountryById(Long id) {
		return countryRepo.findById(id);
	}

	@Override
	public List<CountryVO> getAllCountryByOrgId(Long orgId) {
		return countryRepo.findAllByOrgId(orgId);
	}

	@Override
	public CountryVO createCountry(CountryVO countryVO) throws ApplicationException {

		if (countryRepo.existsByCountryAndCountryCodeAndOrgId(countryVO.getCountry(), countryVO.getCountryCode(),
				countryVO.getOrgId())) {
			throw new ApplicationException("A Country And CountryCode already exists for this organization.");
		}
		// Check if a country with the same name or code already exists for the same
		// orgId
		if (countryRepo.existsByCountryAndOrgId(countryVO.getCountry(), countryVO.getOrgId())) {
			throw new ApplicationException("A Country already exists for this organization");
		}

		if (countryRepo.existsByCountryCodeAndOrgId(countryVO.getCountryCode(), countryVO.getOrgId())) {
			throw new ApplicationException("A CountryCode already exists for this organization.");
		}
		return countryRepo.save(countryVO);
	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) throws ApplicationException {
		if (countryRepo.existsById(countryVO.getId())) {
			
			 if (countryRepo.existsByCountryAndCountryCodeAndOrgIdAndIdNot(countryVO.getCountry(),countryVO.getCountryCode(), countryVO.getOrgId(), countryVO.getId())) {
		            throw new ApplicationException("A Country and CountryCode already exists for this organization.");
		        }
	        // Check if a country with the same name already exists for this organization
	        if (countryRepo.existsByCountryAndOrgIdAndIdNot(countryVO.getCountry(), countryVO.getOrgId(), countryVO.getId())) {
	            throw new ApplicationException("A Country already exists for this organization.");
	        }
	        // Check if a country with the same code already exists for this organization
	        if (countryRepo.existsByCountryCodeAndOrgIdAndIdNot(countryVO.getCountryCode(), countryVO.getOrgId(), countryVO.getId())) {
	            throw new ApplicationException("A CountryCode already exists for this organization.");
	        }
	        // Update the country
	        CountryVO existingCountryVO = countryRepo.findById(countryVO.getId()).orElseThrow(() -> new ApplicationException("Country not found"));
	        countryVO.setCommonDate(existingCountryVO.getCommonDate());
	        return Optional.of(countryRepo.save(countryVO));
	    } else {
	        return Optional.empty(); // Country not found
	    }
	}


	@Override
	public void deleteCountry(Long id) {
		countryRepo.deleteById(id);
	}

//	state

	@Override
	public List<StateVO> getAllgetAllStates() {
		return stateRepo.findAll();
	}

	@Override
	public List<StateVO> getAllStatesByCountry(String Country, Long orgId) {
		return stateRepo.findAllStateByCountryAndOrgId(Country, orgId);
	}

	@Override
	public Optional<StateVO> getStateById(Long id) {
		return stateRepo.findById(id);
	}

	@Override
	public StateVO createState(StateVO stateVO) throws ApplicationException {
		// Check if a state with the same name or code already exists for the same
		// country and region
		if (stateRepo.existsByStateNameAndStateCodeAndCountryAndOrgId(stateVO.getStateName(), stateVO.getStateCode(),
				stateVO.getCountry(), stateVO.getOrgId())) {
			throw new ApplicationException("A StateName and StateCode  already exists for this country");
		}
		if (stateRepo.existsByStateNameAndCountryAndOrgId(stateVO.getStateName(), stateVO.getCountry(),
				stateVO.getOrgId())) {
			throw new ApplicationException("A StateName already exists for this country and region.");
		}

		if (stateRepo.existsByStateCodeAndCountryAndOrgId(stateVO.getStateCode(), stateVO.getCountry(),
				stateVO.getOrgId())) {
			throw new ApplicationException("A StateCode already exists for this country and region.");
		}
		return stateRepo.save(stateVO);
	}

	@Override
	public Optional<StateVO> updateState(StateVO stateVO) throws ApplicationException {
	    if (stateRepo.existsById(stateVO.getId())) {
	        // Check if a state with the same name and code already exists for this country
	        if (stateRepo.existsByStateNameAndStateCodeAndCountryAndOrgIdAndIdNot(
	                stateVO.getStateName(), stateVO.getStateCode(), stateVO.getCountry(),
	                stateVO.getOrgId(), stateVO.getId())) {
	            throw new ApplicationException("A StateName and StateCode already exists for this country.");
	        }
	        // Check if a state with the same name already exists for this country
	        if (stateRepo.existsByStateNameAndCountryAndOrgIdAndIdNot(
	                stateVO.getStateName(), stateVO.getCountry(), stateVO.getOrgId(), stateVO.getId())) {
	            throw new ApplicationException("A StateName already exists for this country.");
	        }
	        // Check if a state with the same code already exists for this country
	        if (stateRepo.existsByStateCodeAndCountryAndOrgIdAndIdNot(
	                stateVO.getStateCode(), stateVO.getCountry(), stateVO.getOrgId(), stateVO.getId())) {
	            throw new ApplicationException("A StateCode already exists for this country.");
	        }
	        // Update the state
	        StateVO existingStateVO = stateRepo.findById(stateVO.getId()).orElseThrow(() -> new ApplicationException("State not found"));
	        stateVO.setCommonDate(existingStateVO.getCommonDate());
	        return Optional.of(stateRepo.save(stateVO));
	    } else {
	        return Optional.empty();
	    }
	}



	@Override
	public void deleteState(Long id) {
		stateRepo.deleteById(id);
	}

//	city

	@Override
	public List<CityVO> getAllgetAllCities() {
		return cityRepo.findAll();
	}

	@Override
	public Optional<CityVO> getCityById(Long id) {
		return cityRepo.findById(id);
	}

	@Override
	public List<CityVO> getAllCitiesByStateAndCountry(String state, String country, Long orgId) {

		return cityRepo.findAllByStateAndCountryAndOrgId(state, country, orgId);
	}

	@Override
	public CityVO createCity(CityVO cityVO) throws ApplicationException {

		// Check if a city with the same name or code already exists for the same
		// country and state
		if (cityRepo.existsByCityNameAndCityCodeAndCountryAndStateAndOrgId(cityVO.getCityName(), cityVO.getCityCode(),cityVO.getCountry(), cityVO.getState(),cityVO.getOrgId())) {
			throw new ApplicationException("A CityName and CityCode already exists for this country and state.");
		}
		if (cityRepo.existsByCityNameAndCountryAndStateAndOrgId(cityVO.getCityName(), cityVO.getCountry(), cityVO.getState(),cityVO.getOrgId())) {
			throw new ApplicationException("A CityName already exists for this country and state.");
		}

		if (cityRepo.existsByCityCodeAndCountryAndStateAndOrgId(cityVO.getCityCode(), cityVO.getCountry(), cityVO.getState(),cityVO.getOrgId())) {
			throw new ApplicationException("A CityCode already exists for this country and state.");
		}
		return cityRepo.save(cityVO);
	}

	@Override
	public Optional<CityVO> updateCity(CityVO cityVO) throws ApplicationException {
	    if (cityRepo.existsById(cityVO.getCityid())) {
	        // Check if a city with the same name and code already exists for the same country and state
	        if (cityRepo.existsByCityNameAndCityCodeAndCountryAndStateAndOrgIdAndCityidNot(
	                cityVO.getCityName(), cityVO.getCityCode(), cityVO.getCountry(), cityVO.getState(),
	                cityVO.getOrgId(), cityVO.getCityid())) {
	            throw new ApplicationException("A city and CityCode already exists for this country and state.");
	        }
	        // Check if a city with the same code already exists for this country and state
	        if (cityRepo.existsByCityCodeAndCountryAndStateAndOrgIdAndCityidNot(
	                cityVO.getCityCode(), cityVO.getCountry(), cityVO.getState(),
	                cityVO.getOrgId(), cityVO.getCityid())) {
	            throw new ApplicationException("A CityCode already exists for this country and state.");
	        }
	        // Check if a city with the same name already exists for this country and state
	        if (cityRepo.existsByCityNameAndCountryAndStateAndOrgIdAndCityidNot(
	                cityVO.getCityName(), cityVO.getCountry(), cityVO.getState(),
	                cityVO.getOrgId(), cityVO.getCityid())) {
	            throw new ApplicationException("A CityName already exists for this country and state.");
	        }
	        // Update the city
	        CityVO existingCityVO = cityRepo.findById(cityVO.getCityid()).orElseThrow(() -> new ApplicationException("City not found"));
	        cityVO.setCommonDate(existingCityVO.getCommonDate());
	        return Optional.of(cityRepo.save(cityVO));
	    } else {
	        return Optional.empty();
	    }
	}


	@Override
	public void deleteCity(Long id) {
		cityRepo.deleteById(id);
	}

//	employee

	@Override
	public List<EmployeeVO> getAllgetAllEmployees() {
		return employeeRepo.findAll();
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(int id) {
		return employeeRepo.findById(id);
	}

	@Override
	public EmployeeVO createEmployee(EmployeeVO employeeVO) {
		return employeeRepo.save(employeeVO);
	}

	@Override
	public Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO) {
		if (employeeRepo.existsById(employeeVO.getId())) {
			return Optional.of(employeeRepo.save(employeeVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteEmployee(int id) {
		employeeRepo.deleteById(id);
	}

//	financial Year

	@Override
	public List<FinancialYearVO> getAllgetAllFinancialYears() {
		return financialRepo.findAll();
	}

	@Override
	public Optional<FinancialYearVO> getFinancialYearById(int id) {
		return financialRepo.findById(id);
	}

	@Override
	public FinancialYearVO createFinancialYear(FinancialYearVO financialYearVO) {
		return financialRepo.save(financialYearVO);
	}

	@Override
	public Optional<FinancialYearVO> updateFinancialYear(FinancialYearVO financialYearVO) {
		if (financialRepo.existsById(financialYearVO.getId())) {
			return Optional.of(financialRepo.save(financialYearVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteFinancialYear(int id) {
		financialRepo.deleteById(id);
	}

//	currencyMaster

	@Override
	public List<CurrencyMasterVO> getAllCurrencyMaster() {
		return currencyMasterRepo.findAll();
	}

	@Override
	public Optional<CurrencyMasterVO> getCurrencyMasterById(int id) {
		return currencyMasterRepo.findById(id);
	}

	@Override
	public CurrencyMasterVO createCurrencyMasterVO(CurrencyMasterVO currencyMasterVO) {
		return currencyMasterRepo.save(currencyMasterVO);
	}

	@Override
	public Optional<CurrencyMasterVO> updateCurrencyMaster(CurrencyMasterVO currencyMasterVO) {
		if (currencyMasterRepo.existsById(currencyMasterVO.getId())) {
			return Optional.of(currencyMasterRepo.save(currencyMasterVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCurrencyMaster(int id) {
		currencyMasterRepo.deleteById(id);
	}

	

	
	  private int totalRows = 0; // Initialize totalRows
	    private int successfulUploads = 0; // Initialize successfulUploads
	    @Override
	    @Transactional
	    public void ExcelUploadForBranch(MultipartFile[] files, CustomerAttachmentType type, Long orgId,String CreatedBy)
	            throws ApplicationException {
	        List<BranchVO> branchVOsToSave = new ArrayList<>();
	        totalRows = 0; // Reset totalRows for each execution
	        successfulUploads = 0; // Reset successfulUploads for each execution

	        for (MultipartFile file : files) {
	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
	                List<String> errorMessages = new ArrayList<>();
	                System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
	                
	                // Validate the header row
	                Row headerRow = sheet.getRow(0);
	                if (!isHeaderValidBranch(headerRow)) {
	                    throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
	                }

	                // Check all rows for validity first
	                for (Row row : sheet) {   
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    totalRows++; // Increment totalRows
	                    System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

	                    // Retrieve cell values and handle different types
	                    String branch = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String code = getStringCellValue(row.getCell(1));
	                    String address1 = getStringCellValue(row.getCell(2));
	                    String address2 = getStringCellValue(row.getCell(3));
	                    String country = getStringCellValue(row.getCell(4));
	                    String state = getStringCellValue(row.getCell(5));
	                    String city = getStringCellValue(row.getCell(6));
	                    String pincode = getStringCellValue(row.getCell(7));
	                    String phoneNumber = getStringCellValue(row.getCell(8));
	                    String gst = getStringCellValue(row.getCell(9));
	                    String pan = getStringCellValue(row.getCell(10));

	                    // Validate each row
						try {
							if (branchRepo.existsByBranchNameAndOrgId(branch, orgId)) {
								errorMessages.add("Branch " + branch.toUpperCase()
										+ " already exists for this organization. Row: " + (row.getRowNum() + 1));
							}
							if (branchRepo.existsByBranchCodeAndOrgId(code, orgId)) {
								errorMessages.add("Code " + code.toUpperCase()+ " already exists for this organization. Row: " + (row.getRowNum() + 1));
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
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    System.out.println("Saving row: " + (row.getRowNum() + 1)); // Debug statement

	                    String branch = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String code = getStringCellValue(row.getCell(1));
	                    String address1 = getStringCellValue(row.getCell(2));
	                    String address2 = getStringCellValue(row.getCell(3));
	                    String country = getStringCellValue(row.getCell(4));
	                    String state = getStringCellValue(row.getCell(5));
	                    String city = getStringCellValue(row.getCell(6));
	                    String pincode = getStringCellValue(row.getCell(7));
	                    String phoneNumber = getStringCellValue(row.getCell(8));
	                    String gst = getStringCellValue(row.getCell(9));
	                    String pan = getStringCellValue(row.getCell(10));

	                    // Create BranchVO and add to list for batch saving
	                    BranchVO branchVO = new BranchVO();
	                    branchVO.setOrgId(orgId);
	                    branchVO.setBranchName(branch.toUpperCase());
	                    branchVO.setBranchCode(code.toUpperCase());
	                    branchVO.setAddress1(address1);
	                    branchVO.setAddress2(address2);
	                    branchVO.setState(state.toUpperCase());
	                    branchVO.setCountry(state.toUpperCase());
	                    branchVO.setCity(city.toUpperCase());
	                    branchVO.setPinCode(pincode);
	                    branchVO.setPhone(phoneNumber);
	                    branchVO.setGST(gst);
	                    branchVO.setPan(pan);
	                    branchVO.setActive(true);
	                    branchVO.setCreatedBy(CreatedBy);
	                    branchVO.setModifiedBy(CreatedBy);

	                    branchVOsToSave.add(branchVO);
	                    successfulUploads++; // Increment successfulUploads
	                }
	            } catch (IOException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	            }
	        }

	        // Batch save all BranchVOs
	        branchRepo.saveAll(branchVOsToSave);
	    }

	    private boolean isHeaderValidBranch(Row headerRow) {
	        if (headerRow == null) {
	            return false;
	        }
	        int expectedColumnCount = 11;
	        if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
	            return false;
	        }
	        return "branch".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0))) &&
	               "code".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1))) &&
	               "address1".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2))) &&
	               "address2".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3)))&&
	               "country".equalsIgnoreCase(getStringCellValue(headerRow.getCell(4)))&&
	               "state".equalsIgnoreCase(getStringCellValue(headerRow.getCell(5)))&&
	               "city".equalsIgnoreCase(getStringCellValue(headerRow.getCell(6)))&&
	               "pincode".equalsIgnoreCase(getStringCellValue(headerRow.getCell(7)))&&
	               "phoneNumber".equalsIgnoreCase(getStringCellValue(headerRow.getCell(8)))&&
	               "gst".equalsIgnoreCase(getStringCellValue(headerRow.getCell(9)))&&
	               "pan".equalsIgnoreCase(getStringCellValue(headerRow.getCell(10)));
	    }

		private boolean isRowEmpty(Row row) {
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
	    
	    //COUNTRY EXCEL UPLOAD

	    private int totalRows1 = 0; // Initialize totalRows
	    private int successfulUploads1 = 0; // Initialize successfulUploads
	    @Override
	    @Transactional
	    public void ExcelUploadForCountry(MultipartFile[] files, CustomerAttachmentType type, Long orgId,String CreatedBy)
	            throws ApplicationException {
	        List<CountryVO> countryVOsToSave = new ArrayList<>();
	        totalRows = 0; // Reset totalRows for each execution
	        successfulUploads = 0; // Reset successfulUploads for each execution

	        for (MultipartFile file : files) {
	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
	                List<String> errorMessages = new ArrayList<>();
	                System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
	                
	                // Validate the header row
	                Row headerRow = sheet.getRow(0);
	                if (!isHeaderValidCountry(headerRow)) {
	                    throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
	                }

	                // Check all rows for validity first
	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    totalRows++; // Increment totalRows
	                    System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

	                    // Retrieve cell values and handle different types
	                    String country = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String code = getStringCellValue(row.getCell(1));
	                   
	                    // Validate each row
	                    try {
	                    	 if (countryRepo.existsByCountryAndCountryCodeAndOrgId(country,code, orgId)) {
		                            errorMessages.add("country " + country.toUpperCase()+ " already exists for this organization. Row: "
		                                    + (row.getRowNum() + 1));
		                        }
	                    	
	                    	
	                        if (countryRepo.existsByCountryAndOrgId(country, orgId)) {
	                            errorMessages.add("country " + country.toUpperCase() + " already exists for this organization. Row: "
	                                    + (row.getRowNum() + 1));
	                        }
	                        if (countryRepo.existsByCountryCodeAndOrgId(code, orgId)) {
	                            errorMessages.add("Code " + code.toUpperCase()+ " already exists for this organization. Row: " + (row.getRowNum() + 1));
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
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    System.out.println("Saving row: " + (row.getRowNum() + 1)); // Debug statement

	                    String country = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String code = getStringCellValue(row.getCell(1));

	                    // Create BranchVO and add to list for batch saving
	                    CountryVO countryVO = new CountryVO();
	                    countryVO.setOrgId(orgId);
	                    countryVO.setCountry(country.toUpperCase());
	                    countryVO.setCountryCode(code.toUpperCase());
	                    countryVO.setActive(true);
	                    countryVO.setCreatedBy(CreatedBy);
	                    countryVO.setModifiedBy(CreatedBy);

	                    countryVOsToSave.add(countryVO);
	                    successfulUploads++; // Increment successfulUploads
	                }
	            } catch (IOException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	            }
	        }

	        // Batch save all BranchVOs
	        countryRepo.saveAll(countryVOsToSave);
	    }

	    private boolean isRowEmpty1(Row row) {
	        for (Cell cell : row) {
	            if (cell.getCellType() != CellType.BLANK) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private String getStringCellValue1(Cell cell) {
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
	    
	    private boolean isHeaderValidCountry(Row headerRow) {
	        if (headerRow == null) {
	            return false;
	        }
	        int expectedColumnCount = 2;
	        if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
	            return false;
	        }
	        return "Country".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0))) &&
	               "code".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1))) ;
	    }


	    public int getTotalRows1() {
	        return totalRows;
	    }

	    public int getSuccessfulUploads1() {
	        return successfulUploads;
	    }

	    //COUNTRY EXCEL UPLOAD

	    private int totalRows2 = 0; // Initialize totalRows
	    private int successfulUploads2 = 0; // Initialize successfulUploads
	    @Override
	    @Transactional
	    public void ExcelUploadForState(MultipartFile[] files, CustomerAttachmentType type, Long orgId,String CreatedBy)
	            throws ApplicationException {
	        List<StateVO> stateVOsToSave = new ArrayList<>();
	        totalRows = 0; // Reset totalRows for each execution
	        successfulUploads = 0; // Reset successfulUploads for each execution

	        for (MultipartFile file : files) {
	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
	                List<String> errorMessages = new ArrayList<>();
	                System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
	                
	                // Validate the header row
	                Row headerRow = sheet.getRow(0);
	                if (!isHeaderValidState(headerRow)) {
	                    throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
	                }

	                // Check all rows for validity first
	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    totalRows++; // Increment totalRows
	                    System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

	                    // Retrieve cell values and handle different types
	                    String code = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String no = getStringCellValue(row.getCell(1));
	                    String state = getStringCellValue(row.getCell(2));
	                    String country = getStringCellValue(row.getCell(3));
	                   
	                    // Validate each row
	                    try {
	                    	if (stateRepo.existsByStateNameAndStateCodeAndStateNoAndCountryAndOrgId(state,code,country,no,orgId)) {
	                			errorMessages.add("A StateName and StateCode "+ state.toUpperCase() + code.toUpperCase()+ no.toUpperCase()+  " already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
	                		}
	                    	
	                    	//throw new ApplicationException("A StateName and StateCode  already exists for this country");
	                		if (stateRepo.existsByStateNameAndCountryAndOrgId(state,country,orgId)) {
	                			errorMessages.add("A StateName " + state.toUpperCase()+" already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
	                		}

	                		if (stateRepo.existsByStateCodeAndCountryAndOrgId(code,country,orgId)) {
	                			errorMessages.add("A StateCode "+ code.toUpperCase()+ " already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
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
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    System.out.println("Saving row: " + (row.getRowNum() + 1)); // Debug statement

	                    String code = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String no = getStringCellValue(row.getCell(1));
	                    String state = getStringCellValue(row.getCell(2));
	                    String country = getStringCellValue(row.getCell(3));
	                   

	                    // Create BranchVO and add to list for batch saving
	                    StateVO stateVO = new StateVO();
	                    stateVO.setOrgId(orgId);
	                    stateVO.setCountry(country.toUpperCase());
	                    stateVO.setStateCode(code.toUpperCase());
	                    stateVO.setActive(true);
	                    stateVO.setStateNo(no.toUpperCase());
	                    stateVO.setStateName(state.toUpperCase());
	                    stateVO.setCreatedBy(CreatedBy);
	                    stateVO.setModifiedBy(CreatedBy);

	                    stateVOsToSave.add(stateVO);
	                    successfulUploads++; // Increment successfulUploads
	                }
	            } catch (IOException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	            }
	        }

	        // Batch save all BranchVOs
	        stateRepo.saveAll(stateVOsToSave);
	    }

	    private boolean isRowEmpty2(Row row) {
	        for (Cell cell : row) {
	            if (cell.getCellType() != CellType.BLANK) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private String getStringCellValue2(Cell cell) {
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

	    private boolean isHeaderValidState(Row headerRow) {
	        if (headerRow == null) {
	            return false;
	        }
	        int expectedColumnCount = 4;
	        if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
	            return false;
	        }
	        return  "code".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))&&
	        		 "no".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1))) &&
		               "state".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2))) &&
		               "country".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3)));
           
	    }
	    
	    public int getTotalRows2() {
	        return totalRows;
	    }

	    public int getSuccessfulUploads2() {
	        return successfulUploads;
	    }

	    //CITY EXCEL UPLOAD

	    private int totalRows3 = 0; // Initialize totalRows
	    private int successfulUploads3 = 0; // Initialize successfulUploads

	    @Override
	    @Transactional
	    public void ExcelUploadForCity(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy)
	            throws ApplicationException {
	        List<CityVO> cityVOsToSave = new ArrayList<>();
	        totalRows = 0; // Reset totalRows for each execution
	        successfulUploads = 0; // Reset successfulUploads for each execution

	        for (MultipartFile file : files) {
	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
	                List<String> errorMessages = new ArrayList<>();
	                System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement

	                // Validate the header row
	                Row headerRow = sheet.getRow(0);
	                if (!isHeaderValidCity(headerRow)) {
	                    throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
	                }

	                // Check all rows for validity first
	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    totalRows++; // Increment totalRows
	                    System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

	                    // Retrieve cell values and handle different types
	                    String country = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String state = getStringCellValue(row.getCell(1));
	                    String city = getStringCellValue(row.getCell(2));
	                    String code = getStringCellValue(row.getCell(3));

	                    // Validate each row
	                    try {
	                        if (cityRepo.existsByCityNameAndCityCodeAndStateAndCountryAndOrgId(city, code, state, country, orgId)) {
	                            errorMessages.add("A CityName and cityCode " + state + code + state + " already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
	                        }

	                        if (cityRepo.existsByCityNameAndCountryAndOrgId(city, country, orgId)) {
	                            errorMessages.add("A CityName " + city.toUpperCase() + " already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
	                        }

	                        if (cityRepo.existsByStateAndCountryAndOrgId(state, country, orgId)) {
	                            errorMessages.add("A State " + state.toUpperCase() + " already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
	                        }

	                        if (cityRepo.existsByCityCodeAndCountryAndOrgId(code, country, orgId)) {
	                            errorMessages.add("A CityCode " + code.toUpperCase() + " already exists for this country. Row: "
	                                    + (row.getRowNum() + 1));
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
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue; // Skip header row and empty rows
	                    }

	                    System.out.println("Saving row: " + (row.getRowNum() + 1)); // Debug statement

	                    String country = getStringCellValue(row.getCell(0)); // Adjusted to match column position
	                    String state = getStringCellValue(row.getCell(1));
	                    String city = getStringCellValue(row.getCell(2));
	                    String code = getStringCellValue(row.getCell(3));

	                    // Create BranchVO and add to list for batch saving
	                    CityVO cityVO = new CityVO();
	                    cityVO.setOrgId(orgId);
	                    cityVO.setCountry(country.toUpperCase());
	                    cityVO.setCityCode(code.toUpperCase());
	                    cityVO.setActive(true);
	                    cityVO.setState(state.toUpperCase());
	                    cityVO.setCityName(city.toUpperCase());
	                    cityVO.setCreatedBy(createdBy);
	                    cityVO.setModifiedBy(createdBy);

	                    cityVOsToSave.add(cityVO);
	                    successfulUploads++; // Increment successfulUploads
	                }
	            } catch (IOException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	            }
	        }

	        // Batch save all BranchVOs
	        cityRepo.saveAll(cityVOsToSave);
	    }

	    private boolean isRowEmpty3(Row row) {
	        for (Cell cell : row) {
	            if (cell.getCellType() != CellType.BLANK) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private String getStringCellValue3(Cell cell) {
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

	    private boolean isHeaderValidCity(Row headerRow) {
	        if (headerRow == null) {
	            return false;
	        }
	        int expectedColumnCount = 4;
	        if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
	            return false;
	        }
	        return "Country".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0))) &&
	               "State".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1))) &&
	               "City".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2))) &&
	               "Code".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3)));
	    }

	    public int getTotalRows3() {
	        return totalRows;
	    }

	    public int getSuccessfulUploads3() {
	        return successfulUploads;
	    }

}
