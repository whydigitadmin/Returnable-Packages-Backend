package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.entity.AddressVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.VenderVO;

public interface MasterService {

	List<AssetVO>getAllAsset();
	
	Optional<AssetVO>getAssetById(int id);
	
	AssetVO createAsset(AssetVO assetVO);
	
	Optional<AssetVO>updateAsset(AssetVO assetVO);
	
	void deleteAsset(int id);
	
	
    List<AssetGroupVO>getAllAssetGroup();
	
	Optional<AssetGroupVO>getAssetGroupById(int id);
	
	AssetGroupVO createAssetGroup(AssetGroupVO assetGroupVO);
	
	Optional<AssetGroupVO>updateAssetGroup(AssetGroupVO assetGroupVO);
	
	void deleteAssetGroup(int id);
	
	
    List<CustomersVO>getAllCustomers();
	
	Optional<CustomersVO>getCustomersById(int id);
	
	CustomersVO createCustomers(CustomersVO customersVO);
	
	AddressVO createAddress(AddressVO addressVO);
	
	Optional<CustomersVO>updateCustomers(CustomersVO customersVO);
	
	void deleteCustomers(int id);
	
	
    List<FlowVO>getAllFlow();
	
	Optional<FlowVO>getFlowById(int id);
	
	FlowVO createFlow(FlowDTO flowDTO);
	
	Optional<FlowVO>updateFlow(FlowVO flowVO);
	
	void deleteFlow(int id);
	
	
    List<VenderVO>getAllVender();
	
	Optional<VenderVO>getVenderById(int id);
	
	VenderVO createVender(VenderVO venderVO);
	
	Optional<VenderVO>updateVender(VenderVO venderVO);
	
	void deleteVender(int id);
}
