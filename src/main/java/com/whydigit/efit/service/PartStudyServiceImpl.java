
package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;
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
	public Optional<BasicDetailVO> getBasicDetailById(UUID id) {
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
	public Optional<BasicDetailVO> updateBasicDetail(BasicDetailVO basicDetailVO) {
		if (basicDetailRepo.existsById(basicDetailVO.getPartStudyId())) {
			return Optional.of(basicDetailRepo.save(basicDetailVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteBasicDetail(UUID id) {
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
	public Optional<PackingDetailVO> getPackingDetailById(UUID id) {
		return packingDetailRepo.findById(id);
	}

	@Override
	public PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO) {
		return packingDetailRepo.save(packingDetailVO);
	}

	@Override
	public Optional<PackingDetailVO> updatePackingDetail(PackingDetailVO packingDetailVO) {
		if (packingDetailRepo.existsById(packingDetailVO.getPartStudyId())) {
			return Optional.of(packingDetailRepo.save(packingDetailVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deletePackingDetail(UUID id) {
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
	public Optional<LogisticsVO> getLogisticsById(UUID id) {
		return logisticRepo.findById(id);
	}

	@Override
	public LogisticsVO createLogistics(LogisticsVO logisticsVO) {
		return logisticRepo.save(logisticsVO);
	}

	@Override
	public Optional<LogisticsVO> updateLogistics(LogisticsVO logisticsVO) {
		if (logisticRepo.existsById(logisticsVO.getPartStudyId())) {
			return Optional.of(logisticRepo.save(logisticsVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteLogistics(UUID id) {
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
	public Optional<StockDetailVO> getStockDetailById(UUID id) {
		return stockDetailRepo.findById(id);
	}

	@Override
	public StockDetailVO createStockDetail(StockDetailVO stockDetailVO) {
		return stockDetailRepo.save(stockDetailVO);
	}

	@Override
	public Optional<StockDetailVO> updateStockDetail(StockDetailVO stockDetailVO) {
		if (stockDetailRepo.existsById(stockDetailVO.getPartStudyId())) {
			return Optional.of(stockDetailRepo.save(stockDetailVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteStockDetail(UUID id) {
		stockDetailRepo.deleteById(id);
	}

}
