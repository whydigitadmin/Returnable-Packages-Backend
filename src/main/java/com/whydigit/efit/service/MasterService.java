package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.entity.AddressVO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.ManufacturerProductVO;
import com.whydigit.efit.entity.ManufacturerVO;
import com.whydigit.efit.entity.UnitVO;
import com.whydigit.efit.entity.VenderVO;
import com.whydigit.efit.entity.WarehouseLocationVO;

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
	
	
	 List<ManufacturerVO>getAllManufacturer();
		
		Optional<ManufacturerVO>getManufacturerById(int id);
		
		ManufacturerVO createManufacturer(ManufacturerVO manufacturerVO);
		
		Optional<ManufacturerVO>updateManufacturer(ManufacturerVO manufacturerVO);
		
		void deleteManufacturer(int id);
		
		
		List<ManufacturerProductVO>getAllManufacturerProduct();
		
		ManufacturerProductVO createManufacturerProduct(ManufacturerProductVO manufacturerProductVO);
		
List<AssetCategoryVO>getAllAssetCategory();
		
AssetCategoryVO createAssetCategory(AssetCategoryVO assetCategoryVO);



//unit
	
 List<UnitVO>getAllUnit();
	
	Optional<UnitVO>getUnitById(int id);
	
	UnitVO createUnit(UnitVO unitVO);
	
	Optional<UnitVO>updateUnit(UnitVO unitVO);
	
	void deleteUnit(int id);

//warehouse location

 List<WarehouseLocationVO>getAllWarehouseLocation();
	
	Optional<WarehouseLocationVO>getWarehouseLocationById(int id);
	
	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouselocationVO);
	
	Optional<WarehouseLocationVO>updateWarehouseLocation(WarehouseLocationVO warehouselocationVO);
	
	void deleteWarehouseLocation(int id);
}

