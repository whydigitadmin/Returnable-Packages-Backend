package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.repo.AssetRepo;

@Service
public class MasterServiceImpl implements MasterService {
	
	@Autowired
	AssetRepo assetRepo;

	@Override
	public List<AssetVO> getAllAsset() {
		
		return assetRepo.findAll();
	}

	@Override
	public Optional<AssetVO> getById(int id) {
		
		return assetRepo.findById(id);
	}

	@Override
	public AssetVO createAssetVO(AssetVO assetVO) {
		return assetRepo.save(assetVO);
	}

	@Override
	public Optional<AssetVO> updateAssetVO(AssetVO assetVO) {
		if(assetRepo.existsById(assetVO.getId()))
		{
			return Optional.of(assetRepo.save(assetVO));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteAsset(int id) {
		assetRepo.deleteById(id);
		
	}

}
