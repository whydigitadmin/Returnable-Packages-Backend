
package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.BasicDetailDTO;
import com.whydigit.efit.dto.LogisticsDTO;
import com.whydigit.efit.dto.PackingDetailDTO;
import com.whydigit.efit.dto.StockDetailDTO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.BasicDetailRepo;
import com.whydigit.efit.repo.LogisticsRepo;
import com.whydigit.efit.repo.PackingDetailRepo;
import com.whydigit.efit.repo.StockDetailRepo;

@Service
public class PartStudyServiceImpl implements PartStudyService {

	@Autowired
	BasicDetailRepo basicDetailRepo;
	@Autowired
	PackingDetailRepo packingDetailRepo;
	@Autowired
	LogisticsRepo logisticRepo;
	@Autowired
	StockDetailRepo stockDetailRepo;
	public static final Logger LOGGER = LoggerFactory.getLogger(PartStudyServiceImpl.class);

	@Override
	public List<BasicDetailVO> getAllBasicDetail(Long orgId) {
		List<BasicDetailVO> basicDetailVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  BasicDetail Information BY OrgId : {}", orgId);
			basicDetailVO = basicDetailRepo.getAllBasicDetailByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  BasicDetail Information For All OrgId.");
			basicDetailVO = basicDetailRepo.findAll();
		}
		return basicDetailVO;
	}

	@Override
	public Optional<BasicDetailVO> getBasicDetailById(Long id) {
		return basicDetailRepo.findById(id);
	}

	@Override
	public BasicDetailVO createBasicDetail(BasicDetailVO basicDetailVO) {
		PackingDetailVO packingDetailVO = new PackingDetailVO();
		packingDetailVO.setBasicDetailVO(basicDetailVO);
		StockDetailVO stockdetailVO = new StockDetailVO();
		stockdetailVO.setBasicDetailVO(basicDetailVO);
		LogisticsVO logisticsVO = new LogisticsVO();
		logisticsVO.setBasicDetailVO(basicDetailVO);
		basicDetailVO.setPackingDetailVO(packingDetailVO);
		basicDetailVO.setStockDetailVO(stockdetailVO);
		basicDetailVO.setLogisticsVO(logisticsVO);
		return basicDetailRepo.save(basicDetailVO);

	}

	@Override
	public BasicDetailVO updateBasicDetail(BasicDetailDTO basicDetailDTO) throws ApplicationException {
		BasicDetailVO basicDetailVO = new BasicDetailVO();
		basicDetailVO = basicDetailRepo.findById(basicDetailDTO.getRefPsId())
				.orElseThrow(() -> new ApplicationException("Invalid  basicDetail details"));
		basicDetailVO.setRefPsId(basicDetailDTO.getRefPsId());
		basicDetailVO.setPartStudyId(basicDetailDTO.getPartStudyId());
		basicDetailVO.setOrgId(basicDetailDTO.getOrgId());
		basicDetailVO.setPartStudyDate(basicDetailDTO.getPartStudyDate());
		basicDetailVO.setEmitterId(basicDetailDTO.getEmitterId());
		basicDetailVO.setReceiverId(basicDetailDTO.getReceiverId());
		basicDetailVO.setPartName(basicDetailDTO.getPartName());
		basicDetailVO.setPartNumber(basicDetailDTO.getPartNumber());
		basicDetailVO.setWeight(basicDetailDTO.getWeight());
		basicDetailVO.setWeightUnit(basicDetailDTO.getWeightUnit());
		basicDetailVO.setPartVolume(basicDetailDTO.getPartVolume());
		basicDetailVO.setHighestVolume(basicDetailDTO.getHighestVolume());
		basicDetailVO.setLowestVolume(basicDetailDTO.getLowestVolume());

		return basicDetailRepo.save(basicDetailVO);

	}

	@Override
	public void deleteBasicDetail(Long id) {
		basicDetailRepo.deleteById(id);

	}

	@Override
	public List<PackingDetailVO> getAllpackingDetail(Long orgId) {
		List<PackingDetailVO> packingDetailVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Package Detail Information BY OrgId : {}", orgId);
			packingDetailVO = packingDetailRepo.getpackingDetailByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  Package Detail Information For All OrgId.");
			packingDetailVO = packingDetailRepo.findAll();
		}
		return packingDetailVO;
	}

	@Override
	public Optional<PackingDetailVO> getPackingDetailById(Long id) {
		return packingDetailRepo.findById(id);
	}

	@Override
	public PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO) {
		return packingDetailRepo.save(packingDetailVO);
	}

	@Override
	public PackingDetailVO updatePackingDetail(PackingDetailDTO packingDetailDTO) throws ApplicationException {
		PackingDetailVO packingDetailVO = new PackingDetailVO();
		packingDetailVO = packingDetailRepo.findById(packingDetailDTO.getRefPsId())
				.orElseThrow(() -> new ApplicationException("Invalid packing details"));
		packingDetailVO.setRefPsId(packingDetailDTO.getRefPsId());
		packingDetailVO.setOrgId(packingDetailDTO.getOrgId());
		packingDetailVO.setPartStudyId(packingDetailDTO.getPartStudyId());
		packingDetailVO.setPartDimension(packingDetailDTO.getPartDimension());
		packingDetailVO.setLength(packingDetailDTO.getLength());
		packingDetailVO.setBreath(packingDetailDTO.getBreath());
		packingDetailVO.setHeight(packingDetailDTO.getHeight());
		packingDetailVO.setPartUnit(packingDetailDTO.getPartUnit());
		packingDetailVO.setPartDrawing(packingDetailDTO.getPartDrawing());
		packingDetailVO.setExistingPart(packingDetailDTO.getExistingPart());
		packingDetailVO.setCurrentPackingStudy(packingDetailDTO.getCurrentPackingStudy());
		packingDetailVO.setCurrentPackingChallenges(packingDetailDTO.getCurrentPackingChallenges());
		packingDetailVO.setNoOfParts(packingDetailDTO.getNoOfParts());
		packingDetailVO.setPartSensitive(packingDetailDTO.getPartSensitive());
		packingDetailVO.setGreasy(packingDetailDTO.getGreasy());
		packingDetailVO.setPartOrientation(packingDetailDTO.getPartOrientation());
		packingDetailVO.setMultiPartInSingleUnit(packingDetailDTO.getMultiPartInSingleUnit());
		packingDetailVO.setStacking(packingDetailDTO.getStacking());
		packingDetailVO.setNesting(packingDetailDTO.getNesting());
		packingDetailVO.setRemarks(packingDetailDTO.getRemarks());
		packingDetailVO.setPartDrawing(packingDetailDTO.getPartDrawing());
		packingDetailVO.setApprovedPackingTechnicalDrawing(packingDetailDTO.getApprovedPackingTechnicalDrawing());
		packingDetailVO.setApprovedCommercialContract(packingDetailDTO.getApprovedCommercialContract());
		packingDetailVO.setActive(packingDetailDTO.getActive());
		packingDetailVO.setPartImage(packingDetailDTO.getPartImage());
		return packingDetailRepo.save(packingDetailVO);

	}

	@Override
	public void deletePackingDetail(Long id) {
		packingDetailRepo.deleteById(id);

	}

	@Override
	public List<LogisticsVO> getAllLogistics(Long orgId) {
		List<LogisticsVO> logisticsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Logistics Information BY OrgId : {}", orgId);
			logisticsVO = logisticRepo.getLogisticsByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  Logistics Information For All OrgId.");
			logisticsVO = logisticRepo.findAll();
		}
		return logisticsVO;
	}

	@Override
	public Optional<LogisticsVO> getLogisticsById(Long id) {
		return logisticRepo.findById(id);
	}

	@Override
	public LogisticsVO createLogistics(LogisticsVO logisticsVO) {
		return logisticRepo.save(logisticsVO);
	}

	@Override
	public LogisticsVO updateLogistics(LogisticsDTO logisticsDTO) throws ApplicationException {
		LogisticsVO logisticsVO = new LogisticsVO();
		logisticsVO = logisticRepo.findById(logisticsDTO.getRefPsId())
				.orElseThrow(() -> new ApplicationException("Invalid logistics details"));
		logisticsVO.setRefPsId(logisticsDTO.getRefPsId());
		logisticsVO.setPartStudyId(logisticsDTO.getPartStudyId());
		logisticsVO.setOrgId(logisticsDTO.getOrgId());
		logisticsVO.setAvgLotSize(logisticsDTO.getAvgLotSize());
		logisticsVO.setDispatchFrequency(logisticsDTO.getDispatchFrequency());
		logisticsVO.setDiapatchTo(logisticsDTO.getDiapatchTo());
		logisticsVO.setTranspotationTo(logisticsDTO.getTranspotationTo());
		return logisticRepo.save(logisticsVO);
	}

	@Override
	public void deleteLogistics(Long id) {
		logisticRepo.deleteById(id);
	}

	@Override
	public List<StockDetailVO> getAllStockDetail(Long orgId) {
		List<StockDetailVO> stockDetailVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received StockDetaill Information BY OrgId : {}", orgId);
			stockDetailVO = stockDetailRepo.getStockDetailByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  StockDetail Information For All OrgId.");
			stockDetailVO = stockDetailRepo.findAll();
		}
		return stockDetailVO;
	}

	@Override
	public Optional<StockDetailVO> getStockDetailById(Long id) {
		return stockDetailRepo.findById(id);
	}

	@Override
	public StockDetailVO createStockDetail(StockDetailVO stockDetailVO) {
		return stockDetailRepo.save(stockDetailVO);
	}

	@Override
	public StockDetailVO updateStockDetail(StockDetailDTO stockDetailDTO) throws ApplicationException {
		StockDetailVO stockDetailVO = new StockDetailVO();
		stockDetailVO = stockDetailRepo.findById(stockDetailDTO.getRefPsId())
				.orElseThrow(() -> new ApplicationException("Invalid stockdetails details"));
		stockDetailVO.setPartStudyId(stockDetailVO.getPartStudyId());
		stockDetailVO.setRefPsId(stockDetailVO.getRefPsId());
		stockDetailVO.setOrgId(stockDetailVO.getOrgId());
		stockDetailVO.setEmitterStoreDays(stockDetailVO.getEmitterStoreDays());
		stockDetailVO.setEmitterLineDays(stockDetailVO.getEmitterLineDays());
		stockDetailVO.setInTransitDays(stockDetailVO.getInTransitDays());
		stockDetailVO.setEndUserLineStorageDays(stockDetailVO.getEndUserLineStorageDays());
		stockDetailVO.setEndUserManufacturingLineDays(stockDetailVO.getEndUserManufacturingLineDays());
		stockDetailVO.setOtherStorageDays(stockDetailVO.getOtherStorageDays());
		stockDetailVO.setTotalCycleTime(stockDetailVO.getTotalCycleTime());
		stockDetailVO.setEmptyPackagingReverseDays(stockDetailVO.getEmptyPackagingReverseDays());
		return stockDetailRepo.save(stockDetailVO);
	}

	@Override
	public void deleteStockDetail(Long id) {
		stockDetailRepo.deleteById(id);
	}

	@Override
	public boolean generatePartStudyId(String refPsId) {
		boolean status=false;
		if (StringUtils.isNotBlank(refPsId)) {
			LOGGER.info("Successfully Received  Generate PartStudy Id Information BY RefPsId : {}", refPsId);
//			basicDetailVO = basicDetailRepo.generatePartStudyId(refPsId);
		} else {
			LOGGER.info("Successfully Received  BasicDetail Information For All OrgId.");
//			basicDetailVO = basicDetailRepo.findAll();
		}
		return status;
	}

	@Override
	public Map<String, Object> searchPartStudyId(Long emitterId, Long receiverId, Long orgId, Boolean completeStatus) {
//		Map<String, Object> assetGroup = new HashMap<>();
//		List<AssetGroupVO> assetGroupVO = assetGroupRepo.findAll(new Specification<AssetGroupVO>() {
//			@Override
//			public Predicate toPredicate(Root<AssetGroupVO> root, CriteriaQuery<?> query,
//					CriteriaBuilder criteriaBuilder) {
//				List<Predicate> predicates = new ArrayList<>();
//				if (ObjectUtils.isNotEmpty(orgId)) {
//					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
//				}
//				if (StringUtils.isNotBlank(completeStatus)) {
//					predicates
//							.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("completeStatus"), completeStatus)));
//				}
//				if (ObjectUtils.isNotEmpty(emitterId)) {
//					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("emitterId"), emitterId)));
//				}
//				if (ObjectUtils.isNotEmpty(receiverId)) {
//					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("receiverId"), receiverId)));
//				}
//				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//			}
//		});
		return null;
	}
	}


