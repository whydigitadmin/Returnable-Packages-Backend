package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.entity.CityVO;
import com.whydigit.efit.entity.CountryVO;
import com.whydigit.efit.entity.CurrencyMasterVO;
import com.whydigit.efit.entity.EmployeeVO;
import com.whydigit.efit.entity.FinancialYearVO;
import com.whydigit.efit.entity.LocalCurrencyVO;
import com.whydigit.efit.entity.StateVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface BasicMasterService {

	List<LocalCurrencyVO> getAllLocalCurrencies();

	Optional<LocalCurrencyVO> getLocalCurrencyById(int id);

	LocalCurrencyVO createLocalCurrency(LocalCurrencyVO localCurrencyVO);

	Optional<LocalCurrencyVO> updateLocalCurrency(LocalCurrencyVO localCurrencyVO);

	void deleteLocalCurrency(int id);

	List<CountryVO> getAllgetAllcountries();

	Optional<CountryVO> getCountryById(Long id);

	List<CountryVO> getAllCountryByOrgId(Long orgId);

	CountryVO createCountry(CountryVO countryVO) throws ApplicationException;

	Optional<CountryVO> updateCountry(CountryVO countryVO) throws ApplicationException;

	void deleteCountry(Long id);

	List<StateVO> getAllgetAllStates();

	Optional<StateVO> getStateById(Long id);

	List<StateVO> getAllStatesByCountry(String Country, Long orgId);

	StateVO createState(StateVO stateVO) throws ApplicationException;

	Optional<StateVO> updateState(StateVO stateVO) throws ApplicationException;

	void deleteState(Long id);

	List<CityVO> getAllgetAllCities();

	Optional<CityVO> getCityById(Long id);

	List<CityVO> getAllCitiesByStateAndCountry(String state, String country, Long orgId);

	CityVO createCity(CityVO cityVO) throws ApplicationException;

	Optional<CityVO> updateCity(CityVO cityVO) throws ApplicationException;

	void deleteCity(Long id);

	List<EmployeeVO> getAllgetAllEmployees();

	Optional<EmployeeVO> getEmployeeById(int id);

	EmployeeVO createEmployee(EmployeeVO employeeVO);

	Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO);

	void deleteEmployee(int id);

	List<FinancialYearVO> getAllgetAllFinancialYears();

	Optional<FinancialYearVO> getFinancialYearById(int id);

	FinancialYearVO createFinancialYear(FinancialYearVO financialYearVO);

	Optional<FinancialYearVO> updateFinancialYear(FinancialYearVO financialYearVO);

	void deleteFinancialYear(int id);

	List<CurrencyMasterVO> getAllCurrencyMaster();

	Optional<CurrencyMasterVO> getCurrencyMasterById(int id);

	CurrencyMasterVO createCurrencyMasterVO(CurrencyMasterVO currencyMasterVO);

	Optional<CurrencyMasterVO> updateCurrencyMaster(CurrencyMasterVO currencyMasterVO);

	void deleteCurrencyMaster(int id);

	int getTotalRows();

	int getSuccessfulUploads();

	void ExcelUploadForBranch(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String CreatedBy)
			throws ApplicationException;

	void ExcelUploadForCountry(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy)
			throws ApplicationException;

	void ExcelUploadForState(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy)
			throws ApplicationException;

	void ExcelUploadForCity(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy)
			throws ApplicationException;

}
