package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.DcVendorVO;
import com.whydigit.efit.repo.DcVendorRepository;

@Service
public class OutboundServiceImpl implements OutboundService {
	
	@Autowired
	DcVendorRepository dcVendorRepo;

	@Override
	public List<DcVendorVO> getAllDcVendorVO() {
		
		return dcVendorRepo.findAll();
	}

	@Override
	public Optional<DcVendorVO> getDcVendorVOById(int id) {
		
		return dcVendorRepo.findById(id);
	}

	@Override
	public DcVendorVO createDcVendorVO(DcVendorVO dcVendorVO) {
		
		return dcVendorRepo.save(dcVendorVO);
	}

	@Override
	public Optional<DcVendorVO> updateDcVendorVO(DcVendorVO dcVendorVO) {
		if(dcVendorRepo.existsById(dcVendorVO.getId()))
		{
			return Optional.of(dcVendorRepo.save(dcVendorVO));
		}
		else
		{
		return Optional.empty();
		}
	}

	@Override
	public void deleteDcVendorVO(int id) {
		
		dcVendorRepo.deleteById(id);
		
	}

}
