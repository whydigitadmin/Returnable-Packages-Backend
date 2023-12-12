package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.entity.AssetVO;

public interface MasterService {

	List<AssetVO>getAllAsset();
	
	Optional<AssetVO>getById(int id);
	
	AssetVO createAssetVO(AssetVO assetVO);
	
	Optional<AssetVO>updateAssetVO(AssetVO assetVO);
	
	void deleteAsset(int id);
}
