package com.whydigit.efit.service;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.OrganizationDTO;
import com.whydigit.efit.entity.OrganizationVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface CompanyService {

	OrganizationVO createCompany(OrganizationDTO organizationDTO) throws ApplicationException;
	

}
