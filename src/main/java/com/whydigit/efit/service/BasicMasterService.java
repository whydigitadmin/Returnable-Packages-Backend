package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.CityVO;
import com.whydigit.efit.entity.CountryVO;
import com.whydigit.efit.entity.CurrencyMasterVO;
import com.whydigit.efit.entity.EmployeeVO;
import com.whydigit.efit.entity.FinancialYearVO;
import com.whydigit.efit.entity.LocalCurrencyVO;
import com.whydigit.efit.entity.StateVO;

@Service
public interface BasicMasterService {

	List<LocalCurrencyVO> getAllLocalCurrencies();

	Optional<LocalCurrencyVO> getLocalCurrencyById(int id);

	LocalCurrencyVO createLocalCurrency(LocalCurrencyVO localCurrencyVO);

	Optional<LocalCurrencyVO> updateLocalCurrency(LocalCurrencyVO localCurrencyVO);

	void deleteLocalCurrency(int id);
	
	

	List<CountryVO> getAllgetAllcountries();

	Optional<CountryVO> getCountryById(int id);
	
	List<CountryVO> getAllCountryByOrgId(Long orgId);

	CountryVO createCountry(CountryVO countryVO);

	Optional<CountryVO> updateCountry(CountryVO countryVO);

	void deleteCountry(int id);


	List<StateVO> getAllgetAllStates();

	Optional<StateVO> getStateById(Long id);
	
	List<StateVO> getAllStatesByCountry(String Country);

	StateVO createState(StateVO stateVO);

	Optional<StateVO> updateState(StateVO stateVO);

	void deleteState(Long id);


	List<CityVO> getAllgetAllCities();

	Optional<CityVO> getCityById(Long id);

	CityVO createCity(CityVO cityVO);

	Optional<CityVO> updateCity(CityVO cityVO);

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

	

	







}
