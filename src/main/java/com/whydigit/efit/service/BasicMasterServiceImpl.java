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
	public Optional<CountryVO> getCountryById(int id) {
		return countryRepo.findById(id);
	}
	
	@Override
	public List<CountryVO> getAllCountryByOrgId(Long orgId) {
		return countryRepo.findAllByOrgId(orgId);
	}

	@Override
	public CountryVO createCountry(CountryVO countryVO) {
		countryVO.setDupchk(countryVO.getOrgId()+countryVO.getCountry()+countryVO.getCountryCode());
		return countryRepo.save(countryVO);
	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryRepo.existsById(countryVO.getId())) {
			countryVO.setDupchk(countryVO.getOrgId()+countryVO.getCountry()+countryVO.getCountryCode());
			return Optional.of(countryRepo.save(countryVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCountry(int id) {
		countryRepo.deleteById(id);
	}


//	state

	@Override
	public List<StateVO> getAllgetAllStates() {
		return stateRepo.findAll();
	}
	
	@Override
	public List<StateVO> getAllStatesByCountry(String Country) {
		return stateRepo.findAllStateByCountry(Country);
	}

	@Override
	public Optional<StateVO> getStateById(Long id) {
		return stateRepo.findById(id);
	}

	@Override
	public StateVO createState(StateVO stateVO) {
		stateVO.setDupchk(stateVO.getOrgId()+ stateVO.getStateName()+stateVO.getStateCode());
		return stateRepo.save(stateVO);
	}

	@Override
	public Optional<StateVO> updateState(StateVO stateVO) {
		if (stateRepo.existsById(stateVO.getId())) {
			stateVO.setDupchk(stateVO.getOrgId()+ stateVO.getStateName()+stateVO.getStateCode());
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
	public CityVO createCity(CityVO cityVO) {
		return cityRepo.save(cityVO);
	}

	@Override
	public Optional<CityVO> updateCity(CityVO cityVO) {
		if (cityRepo.existsById(cityVO.getCityid())) {
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
