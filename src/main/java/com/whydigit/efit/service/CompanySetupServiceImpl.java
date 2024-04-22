package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.BranchDTO;
import com.whydigit.efit.entity.BranchVO;
import com.whydigit.efit.entity.CompanySetVO;
import com.whydigit.efit.entity.CompanySetupVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.BranchRepo;
import com.whydigit.efit.repo.CompanySetRepo;
import com.whydigit.efit.repo.CompanySetupRepo;


@Service
public class CompanySetupServiceImpl implements CompanySetupService {

	@Autowired
	private CompanySetupRepo companySetupRepo;
	
	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private CompanySetRepo companySetRepo;

	@Override
	
	public List<CompanySetupVO> getAllcompanies() {
		return companySetupRepo.findAll();
	}

	@Override
	public Optional<CompanySetupVO> getCompanyById(int id) {
		return companySetupRepo.findById(id);
	}

	@Override
	public CompanySetupVO createCompany(CompanySetupVO companySetupVO) {
		return companySetupRepo.save(companySetupVO);
	}

	@Override
	public Optional<CompanySetupVO> updateCompany(CompanySetupVO companySetupVO) {
		if (companySetupRepo.existsById(companySetupVO.getId())) {
			return Optional.of(companySetupRepo.save(companySetupVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCompany(int id) {
		companySetupRepo.deleteById(id);
	}
	
	@Override	
	public List<BranchVO> getAllBranch() {
		return branchRepo.findAll();
	}

	@Override
	public Optional<BranchVO> getBranchById(Long id) {
		return branchRepo.findById(id);
	}

	@Override
	public BranchVO createBranch(BranchDTO branchDTO)throws ApplicationException {
		
		 // Check if a branch with the same branchName and orgId already exists
	    boolean branchAndBranchCodeExists = branchRepo.existsByBranchNameAndBranchCodeAndOrgId(branchDTO.getBranchName(),branchDTO.getBranchCode(),branchDTO.getOrgId());
	    boolean branchCodeExists = branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(),branchDTO.getOrgId());
	    boolean branchExists = branchRepo.existsByBranchNameAndOrgId(branchDTO.getBranchName(),branchDTO.getOrgId());
	    
	    if (branchAndBranchCodeExists) {
	        throw new ApplicationException("A branchName and BranchCode already exists for this Organization.");
	    }
	    
	    if (branchCodeExists) {
	        throw new ApplicationException("A BranchCode already exists for this Organization.");
	    }
	    
	    if (branchExists) {
	        throw new ApplicationException("A BranchName already exists for this Organization.");
	    }
	    
	    BranchVO branchVO = new BranchVO();

	    if (branchDTO.getId() != null) {
	        // Update existing branch
	        branchVO = branchRepo.findById(branchDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Invalid branch details"));
	        branchVO.setModifiedBy(branchDTO.getCreatedBy()); // Set modifiedBy only during update
	    } else {
	        // Create new branch
	        branchVO.setCreatedBy(branchDTO.getCreatedBy());
	        branchVO.setModifiedBy(branchDTO.getCreatedBy());
	    }

	    // Set specific properties
	    getBranchVOFromBranchDTO(branchDTO, branchVO);

	    return branchRepo.save(branchVO);
	}

	private void getBranchVOFromBranchDTO(BranchDTO branchDTO, BranchVO branchVO) {
	    branchVO.setBranchName(branchDTO.getBranchName());
	    branchVO.setBranchCode(branchDTO.getBranchCode());
	    branchVO.setAddress1(branchDTO.getAddress1());
	    branchVO.setAddress2(branchDTO.getAddress2());
	    branchVO.setCity(branchDTO.getCity());
	    branchVO.setState(branchDTO.getState());
	    branchVO.setCountry(branchDTO.getCountry());
	    branchVO.setPinCode(branchDTO.getPinCode());
	    branchVO.setPhone(branchDTO.getPhone());
	    branchVO.setGST(branchDTO.getGST());
	    branchVO.setPan(branchDTO.getPan());
	    branchVO.setOrgId(branchDTO.getOrgId());
	    branchVO.setCurrency(branchDTO.getCurrency());
	    // Add other properties as needed
	}
		
	@Override
	public void deleteBranch(Long id) {
		branchRepo.deleteById(id);
	}
	
	@Override	
	public List<CompanySetVO> getAllCompanySet() {
		return companySetRepo.findAll();
	}

	@Override
	public Optional<CompanySetVO> getCompanySetById(int id) {
		return companySetRepo.findById(id);
	}

	@Override
	public CompanySetVO createCompanySet(CompanySetVO companySetVO) {
		return companySetRepo.save(companySetVO);
	}

	@Override
	public Optional<CompanySetVO> updateCompanySet(CompanySetVO companySetVO) {
		if (companySetRepo.existsById(companySetVO.getId())) {
			return Optional.of(companySetRepo.save(companySetVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCompanySet(int id) {
		companySetRepo.deleteById(id);
	}

	


}
