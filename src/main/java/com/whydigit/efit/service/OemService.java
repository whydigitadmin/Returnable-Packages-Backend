package com.whydigit.efit.service;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.entity.OemBinInwardVO;

@Service
public interface OemService {

//	OEM Bin Inward
	OemBinInwardVO createOemBinInward(OemBinInwardDTO oemBinInwardDTO);

//	OEM Bin Outward
	
}
