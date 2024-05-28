package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.CityVO;
import com.whydigit.efit.entity.CountryVO;
import com.whydigit.efit.entity.CurrencyMasterVO;
import com.whydigit.efit.entity.EmployeeVO;
import com.whydigit.efit.entity.FinancialYearVO;
import com.whydigit.efit.entity.LocalCurrencyVO;
import com.whydigit.efit.entity.StateVO;
import com.whydigit.efit.exception.ApplicationException;
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

}
