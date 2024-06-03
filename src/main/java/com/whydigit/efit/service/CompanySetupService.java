package com.whydigit.efit.service;
import java.util.List;
import java.util.Optional;

import com.whydigit.efit.entity.CompanySetVO;
import com.whydigit.efit.entity.CompanySetupVO;

public interface CompanySetupService {

	Optional<CompanySetupVO> getCompanyById(int id);

	CompanySetupVO createCompany(CompanySetupVO companySetupVO);

	Optional<CompanySetupVO> updateCompany(CompanySetupVO companySetupVO);

	void deleteCompany(int id);

	List<CompanySetupVO> getAllcompanies();
	
	
	
	
	
	Optional<CompanySetVO> getCompanySetById(int id);

	CompanySetVO createCompanySet(CompanySetVO companySetVO);

	Optional<CompanySetVO> updateCompanySet(CompanySetVO companySetVO);

	void deleteCompanySet(int id);

	List<CompanySetVO> getAllCompanySet();

	

}
