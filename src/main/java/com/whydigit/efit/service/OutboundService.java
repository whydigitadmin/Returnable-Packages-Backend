package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.entity.DcVendorVO;

public interface OutboundService {
	
	List<DcVendorVO>getAllDcVendorVO();
	
	Optional<DcVendorVO> getDcVendorVOById(int id);
	
	DcVendorVO createDcVendorVO(DcVendorVO dcVendorVO);
	
	Optional<DcVendorVO>updateDcVendorVO(DcVendorVO dcVendorVO);
	
	void deleteDcVendorVO(int id);
	

}
