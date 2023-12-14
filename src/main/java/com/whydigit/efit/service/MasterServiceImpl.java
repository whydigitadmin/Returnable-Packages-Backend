package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.entity.AddressVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.FlowDetailVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.VenderVO;
import com.whydigit.efit.repo.AddressRepo;
import com.whydigit.efit.repo.AssetGroupRepo;
import com.whydigit.efit.repo.AssetRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.VenderRepo;

@Service
public class MasterServiceImpl implements MasterService {

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

	@Override
	public List<AssetVO> getAllAsset() {
		return assetRepo.findAll();
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
	public List<AssetGroupVO> getAllAssetGroup() {
		return assetGroupRepo.findAll();
	}

	@Override
	public Optional<AssetGroupVO> getAssetGroupById(int id) {
		return assetGroupRepo.findById(id);
	}

	@Override
	public AssetGroupVO createAssetGroup(AssetGroupVO assetGroupVO) {
		return assetGroupRepo.save(assetGroupVO);
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
	public List<CustomersVO> getAllCustomers() {
		return customersRepo.findAll();
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
	public List<FlowVO> getAllFlow() {
		return flowRepo.findAll();
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
		FlowVO flowVO = FlowVO.builder().active(flowDTO.isActive()).orgin(flowDTO.getOrgin())
				.flowInfo(flowDTO.getFlowInfo()).flowName(flowDTO.getFlowName()).flowType(flowDTO.getFlowType())
				.flowDetailVO(flowDetailVOList).build();
		flowDetailVOList = flowDTO.getFlowDetailDTO().stream()
				.map(fdDTO -> FlowDetailVO.builder().active(fdDTO.isActive()).cycleTime(fdDTO.getCycleTime())
						.dhr(fdDTO.getDhr()).fixedRentalCharge(fdDTO.getFixedRentalCharge()).flowVO(flowVO)
						.itemGroup(fdDTO.getItemGroup()).issueCharge(fdDTO.getIssueCharge())
						.productToPack(fdDTO.getProductToPack()).quantity(fdDTO.getQuantity())
						.rentalTerm(fdDTO.getRentalTerm()).returnCharge(fdDTO.getReturnCharge()).build())
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
	public List<VenderVO> getAllVender() {
		return venderRepo.findAll();
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

}
