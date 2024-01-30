package com.whydigit.efit.service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.dto.KitAssetDTO;
import com.whydigit.efit.dto.KitDTO;
import com.whydigit.efit.entity.AddressVO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.FlowDetailVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.KitAssetVO;
import com.whydigit.efit.entity.KitVO;
import com.whydigit.efit.entity.ManufacturerProductVO;
import com.whydigit.efit.entity.ManufacturerVO;
import com.whydigit.efit.entity.UnitVO;
import com.whydigit.efit.entity.VenderAddressVO;
import com.whydigit.efit.entity.VenderVO;
import com.whydigit.efit.entity.WarehouseLocationVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AddressRepo;
import com.whydigit.efit.repo.AssetCategoryRepo;
import com.whydigit.efit.repo.AssetGroupRepo;
import com.whydigit.efit.repo.AssetRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.KitRepo;
import com.whydigit.efit.repo.ManufacturerProductRepo;
import com.whydigit.efit.repo.ManufacturerRepo;
import com.whydigit.efit.repo.UnitRepo;
import com.whydigit.efit.repo.VenderAddressRepo;
import com.whydigit.efit.repo.VenderRepo;
import com.whydigit.efit.repo.WarehouseLocationRepo;

@Service
public class MasterServiceImpl implements MasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	AssetRepo assetRepo;
	@Autowired
	AssetGroupRepo assetGroupRepo;
	@Autowired
	CustomersRepo customersRepo;
	@Autowired
	FlowRepo flowRepo;
	@Autowired
	VenderRepo venderRepo;
	@Autowired
	AddressRepo addressRepo;
	@Autowired
	ManufacturerRepo manufacturerRepo;
	@Autowired
	ManufacturerProductRepo manufacturerProductRepo;
	@Autowired
	AssetCategoryRepo assetCategoryRepo;
	@Autowired
	UnitRepo unitRepo;
	@Autowired
	WarehouseLocationRepo warehouseLocationRepo;
	@Autowired
	VenderAddressRepo venderAddressRepo;
	@Autowired
	KitRepo kitRepo;

	@Override
	public List<AssetVO> getAllAsset(Long orgId) {
		List<AssetVO> assetVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  AssetInformation BY OrgId : {}", orgId);
			assetVO = assetRepo.getAllAssetByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  AssetInformation For All OrgId.");
			assetVO = assetRepo.findAll();
		}
		return assetVO;
	}

	@Override
	public Optional<AssetVO> getAssetById(int id) {
		return assetRepo.findById(id);
	}

	@Override
	public AssetVO createAsset(AssetVO assetVO) {
		return assetRepo.save(assetVO);
	}

	@Override
	public Optional<AssetVO> updateAsset(AssetVO assetVO) {
		if (assetRepo.existsById(assetVO.getId())) {
			return Optional.of(assetRepo.save(assetVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteAsset(int id) {
		assetRepo.deleteById(id);

	}

	@Override
	public List<AssetGroupVO> getAllAssetGroup(Long orgId) {
		List<AssetGroupVO> assetGroupVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  AssetGroupInformation BY OrgId : {}", orgId);
			assetGroupVO = assetGroupRepo.getAllAssetGroupByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  AssetGroupInformation For All OrgId.");
			assetGroupVO = assetGroupRepo.findAll();
		}
		return assetGroupVO;
	}

	@Override
	public Optional<AssetGroupVO> getAssetGroupById(String id) {
		return assetGroupRepo.findById(id);
	}

	@Override
	public AssetGroupVO createAssetGroup(AssetGroupVO assetGroupVO) throws ApplicationException {
		if (ObjectUtils.isNotEmpty(assetGroupVO) && StringUtils.isNotBlank(assetGroupVO.getId())) {
			if (assetGroupRepo.existsById(assetGroupVO.getId())) {
				throw new ApplicationException("AssetGroup already exist. Please try another one.");
			}
			return assetGroupRepo.save(assetGroupVO);
		} else {
			throw new ApplicationException("Invalid AssetGoup Information.");
		}
	}

	@Override
	public Optional<AssetGroupVO> updateAssetGroup(AssetGroupVO assetGroupVO) {
		if (assetGroupRepo.existsById(assetGroupVO.getId())) {
			return Optional.of(assetGroupRepo.save(assetGroupVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteAssetGroup(int id) {
		assetRepo.deleteById(id);

	}

	@Override
	public List<CustomersVO> getAllCustomers(Long orgId) {
		List<CustomersVO> customersVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  CustomerInformation BY OrgId : {}", orgId);
			customersVO = customersRepo.getAllCustomersByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  CustomerInformation For All OrgId.");
			customersVO = customersRepo.findAll();
		}
		return customersVO;
	}

	@Override
	public Optional<CustomersVO> getCustomersById(int id) {
		return customersRepo.findById(id);
	}

	@Override
	public CustomersVO createCustomers(CustomersVO customersVO) {
		return customersRepo.save(customersVO);
	}

	@Override
	public AddressVO createAddress(AddressVO addressVO) {
		return addressRepo.save(addressVO);
	}

	@Override
	public Optional<CustomersVO> updateCustomers(CustomersVO customersVO) {
		if (customersRepo.existsById(customersVO.getId())) {
			return Optional.of(customersRepo.save(customersVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCustomers(int id) {
		customersRepo.deleteById(id);

	}

	@Override
	public List<FlowVO> getAllFlow(Long orgId) {
		List<FlowVO> flowVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  FlowInformation BY OrgId : {}", orgId);
			flowVO = flowRepo.getAllFlowByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  FlowInformation For All OrgId.");
			flowVO = flowRepo.findAll();
		}
		return flowVO;
	}

	@Override
	public Optional<FlowVO> getFlowById(int id) {
		return flowRepo.findById(id);
	}

	@Override
	public FlowVO createFlow(FlowDTO flowDTO) {
		FlowVO flowVO = createFlowVOByFlowDTO(flowDTO);
		return flowRepo.save(flowVO);
	}

	private FlowVO createFlowVOByFlowDTO(FlowDTO flowDTO) {
		List<FlowDetailVO> flowDetailVOList = new ArrayList<>();
//		FlowVO flowVO = FlowVO.builder().active(flowDTO.isActive()).orgin(flowDTO.getOrgin())
//				.flowInfo(flowDTO.getFlowInfo()).flowName(flowDTO.getFlowName()).flowType(flowDTO.getFlowType()).orgId(flowDTO.getOrgId())
//				.flowDetailVO(flowDetailVOList).build();
//		flowDetailVOList = flowDTO.getFlowDetailDTO().stream()
//				.map(fdDTO -> FlowDetailVO.builder().active(fdDTO.isActive()).cycleTime(fdDTO.getCycleTime())
//						.dhr(fdDTO.getDhr()).fixedRentalCharge(fdDTO.getFixedRentalCharge()).flowVO(flowVO)
//						.itemGroup(fdDTO.getItemGroup()).issueCharge(fdDTO.getIssueCharge())
//						.productToPack(fdDTO.getProductToPack()).quantity(fdDTO.getQuantity())
//						.rentalTerm(fdDTO.getRentalTerm()).returnCharge(fdDTO.getReturnCharge()).build())
//				.collect(Collectors.toList());

		FlowVO flowVO = FlowVO.builder().active(flowDTO.isActive()).orgin(flowDTO.getOrgin())
				.receiver(flowDTO.getReceiver()).flowName(flowDTO.getFlowName()).emitter(flowDTO.getEmitter())
				.destination(flowDTO.getDestination()).orgId(flowDTO.getOrgId()).flowDetailVO(flowDetailVOList).build();

		flowDetailVOList = flowDTO.getFlowDetailDTO().stream()
				.map(fdDTO -> FlowDetailVO.builder().active(fdDTO.isActive()).cycleTime(fdDTO.getCycleTime())
						.partName(fdDTO.getPartName()).kitName(fdDTO.getKitName()).emitter(flowDTO.getEmitter())
						.subReceiver(fdDTO.getSubReceiver()).partNumber(fdDTO.getPartNumber()).build())
				.collect(Collectors.toList());
		flowVO.setFlowDetailVO(flowDetailVOList);
		return flowVO;
	}

	@Override
	public Optional<FlowVO> updateFlow(FlowVO flowVO) {
		if (flowRepo.existsById(flowVO.getId())) {
			return Optional.of(flowRepo.save(flowVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteFlow(int id) {
		flowRepo.deleteById(id);

	}

	@Override
	public List<VenderVO> getAllVender(Long orgId) {
		List<VenderVO> venderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  VenderInformation BY OrgId : {}", orgId);
			venderVO = venderRepo.getAllVenderByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  VenderInformation For All OrgId.");
			venderVO = venderRepo.findAll();
		}
		return venderVO;
	}

	@Override
	public Optional<VenderVO> getVenderById(int id) {
		return venderRepo.findById(id);
	}

	@Override
	public VenderVO createVender(VenderVO venderVO) {
		return venderRepo.save(venderVO);
	}

	@Override
	public Optional<VenderVO> updateVender(VenderVO venderVO) {
		if (venderRepo.existsById(venderVO.getId())) {
			return Optional.of(venderRepo.save(venderVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteVender(int id) {
		venderRepo.deleteById(id);

	}

	@Override
	public List<ManufacturerVO> getAllManufacturer(Long orgId) {
		List<ManufacturerVO> manufacturerVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ManufacturerInformation BY OrgId : {}", orgId);
			manufacturerVO = manufacturerRepo.getAllManufacturerByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  VenderInformation For All OrgId.");
			manufacturerVO = manufacturerRepo.findAll();
		}
		return manufacturerVO;
	}

	@Override
	public Optional<ManufacturerVO> getManufacturerById(int id) {
		return manufacturerRepo.findById(id);
	}

	@Override
	public ManufacturerVO createManufacturer(ManufacturerVO manufacturerVO) {
		return manufacturerRepo.save(manufacturerVO);
	}

	@Override
	public Optional<ManufacturerVO> updateManufacturer(ManufacturerVO manufacturerVO) {
		if (manufacturerRepo.existsById(manufacturerVO.getId())) {
			return Optional.of(manufacturerRepo.save(manufacturerVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteManufacturer(int id) {
		manufacturerRepo.deleteById(id);

	}

	@Override
	public List<ManufacturerProductVO> getAllManufacturerProduct(Long orgId) {
		List<ManufacturerProductVO> manufacturerProductVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received   Manufacturer Product Information BY OrgId : {}", orgId);
			manufacturerProductVO = manufacturerProductRepo.getAllManufacturerProduct(orgId);
		} else {
			LOGGER.info("Successfully Received  Manufacturer Product Information For All OrgId.");
			manufacturerProductVO = manufacturerProductRepo.findAll();
		}
		return manufacturerProductVO;
	}

	@Override
	public ManufacturerProductVO createManufacturerProduct(ManufacturerProductVO manufacturerProductVO) {
		return manufacturerProductRepo.save(manufacturerProductVO);
	}

	@Override
	public List<AssetCategoryVO> getAllAssetCategory(Long orgId) {
		List<AssetCategoryVO> assetCategoryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received AssetCategory BY OrgId : {}", orgId);
			assetCategoryVO = assetCategoryRepo.getAllAssetCategory(orgId);
		} else {
			LOGGER.info("Successfully Received AssetCategory Information For All OrgId.");
			assetCategoryVO = assetCategoryRepo.findAll();
		}
		return assetCategoryVO;
	}

	@Override
	public AssetCategoryVO createAssetCategory(AssetCategoryVO assetCategoryVO) {
		return assetCategoryRepo.save(assetCategoryVO);
	}

	// Unit

	@Override
	public List<UnitVO> getAllUnit(Long orgId) {
		List<UnitVO> unitVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Unit BY OrgId : {}", orgId);
			unitVO = unitRepo.getAllUnit(orgId);
		} else {
			LOGGER.info("Successfully Received Unit Information For All OrgId.");
			unitVO = unitRepo.findAll();
		}
		return unitVO;
	}

	@Override
	public Optional<UnitVO> getUnitById(int id) {
		return unitRepo.findById(id);
	}

	@Override
	public UnitVO createUnit(UnitVO unitVO) {
		return unitRepo.save(unitVO);
	}

	@Override
	public Optional<UnitVO> updateUnit(UnitVO unitVO) {
		if (unitRepo.existsById(unitVO.getId())) {
			return Optional.of(unitRepo.save(unitVO));
		} else {
			return Optional.empty();
		}
	}

	// Warehouse Location

	@Override
	public List<WarehouseLocationVO> getAllWarehouseLocation(Long orgId) {
		List<WarehouseLocationVO> warehouseLocationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received WarehouseLocation BY OrgId : {}", orgId);
			warehouseLocationVO = warehouseLocationRepo.getAllWarehouseLocation(orgId);
		} else {
			LOGGER.info("Successfully Received WarehouseLocation Information For All OrgId.");
			warehouseLocationVO = warehouseLocationRepo.findAll();
		}
		return warehouseLocationVO;
	}

	@Override
	public Optional<WarehouseLocationVO> getWarehouseLocationById(int id) {
		// TODO Auto-generated method stub
		return warehouseLocationRepo.findById(id);
	}

	@Override
	public WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouselocationVO) {
		// TODO Auto-generated method stub
		return warehouseLocationRepo.save(warehouselocationVO);
	}

	@Override
	public Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouselocationVO) {
		// TODO Auto-generated method stub
		if (warehouseLocationRepo.existsById(warehouselocationVO.getId())) {
			return Optional.of(warehouseLocationRepo.save(warehouselocationVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteWarehouseLocation(int id) {
		// TODO Auto-generated method stub
		warehouseLocationRepo.deleteById(id);

	}

	@Override
	public void deleteUnit(int id) {
		// TODO Auto-generated method stub
		unitRepo.deleteById(id);
	}

	@Override
	public List<AssetGroupVO> createAssetGroupByCSV(MultipartFile assetFile) throws ApplicationException {
		if (assetFile.isEmpty()) {
			throw new ApplicationException("Invalid CSV File.");
		}
		List<AssetGroupVO> assetGroups = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new InputStreamReader(assetFile.getInputStream()))) {
			List<String[]> lines = reader.readAll();
			assetGroups = lines.stream().skip(1) // Skip header
					.map(this::createAssetGroupFromCsvLine).filter(Objects::nonNull) // Filter out null values if
																						// validation fails
					.collect(Collectors.toList());
			System.out.println(lines);
		} catch (Exception e) {
			LOGGER.error(CommonConstant.ERR_MSG_FORMAT, "createAssetGroupByCSV()", e.getMessage());
			throw new ApplicationException("Failed to read CSV file.");
		}
		return assetGroupRepo.saveAll(assetGroups);
	}

	private AssetGroupVO createAssetGroupFromCsvLine(String[] csvLine) {
		try {
			if (StringUtils.isEmpty(csvLine[0])) {
				LOGGER.warn("Validation failed for CSV line: {}", Arrays.toString(csvLine));
				return null;
			}
			if (assetGroupRepo.existsById(csvLine[0])) {
				return null;
			}
			return AssetGroupVO.builder().id(csvLine[0]).assetName(csvLine[1]).assetCategory(csvLine[2])
					.active(Boolean.parseBoolean(csvLine[3])).length(Float.parseFloat(csvLine[4]))
					.breath(Float.parseFloat(csvLine[5])).height(Float.parseFloat(csvLine[6])).dimUnit(csvLine[7])
					.build();
		} catch (Exception e) {
			LOGGER.error("Error processing CSV line: {}", Arrays.toString(csvLine), e);
			return null;
		}
	}

	// venderAddress

	@Override
	public List<VenderAddressVO> getAllVenderAddress() {
		return venderAddressRepo.findAll();
	}

	@Override
	public Optional<VenderAddressVO> getVenderAddressById(int id) {
		return venderAddressRepo.findById(id);
	}

	@Override
	public VenderAddressVO createVenderAddress(VenderAddressVO venderAddressVO) {
		return venderAddressRepo.save(venderAddressVO);
	}

	@Override
	public Optional<VenderAddressVO> updateVenderAddress(VenderAddressVO venderAddressVO) {
		if (venderAddressRepo.existsById(venderAddressVO.getId())) {
			return Optional.of(venderAddressRepo.save(venderAddressVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteVenderAddress(int id) {
		venderAddressRepo.deleteById(id);
	}

// create kit
	@Override
	public List<KitVO> getAllKit() {
		// TODO Auto-generated method stub
		return kitRepo.findAll();
	}

	@Override
	public Optional<KitVO> getKitById(String id) {
		// TODO Auto-generated method stub
		return kitRepo.findById(id);
	}

	@Override
	public KitVO createkit(KitDTO kitDTO) {
		List<KitAssetVO> kitAssetVO = new ArrayList<>();
		KitVO kitVO = KitVO.builder().id(kitDTO.getId()).orgId(kitDTO.getOrgId()).partId(kitDTO.getPartId())
				.partQty(kitDTO.getPartQty()).kitAssetVO(kitAssetVO).build();
		for (KitAssetDTO kitAsset : kitDTO.getKitAssetDTO()) {
			kitAssetVO.add(KitAssetVO.builder().assetCategory(kitAsset.getAssetCategory())
					.assetName(kitAsset.getAssetName()).quantity(kitAsset.getQuantity()).kitVO(kitVO).build());
		}
		return kitRepo.save(kitVO);
	}

	@Override
	public Optional<KitVO> updatedKit(KitVO kitVO) {
		if (kitRepo.existsById(kitVO.getId())) {
			return Optional.of(kitRepo.save(kitVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteKit(String id) {
		kitRepo.deleteById(id);

	}
}
