
package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

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
	public Optional<BasicDetailVO> getBasicDetailById(int id) {
		return basicDetailRepo.findById(id);
	}

	@Override
	public BasicDetailVO createBasicDetail(BasicDetailVO basicDetailVO) {
		return basicDetailRepo.save(basicDetailVO);
	}

	@Override
	public Optional<BasicDetailVO> updateBasicDetail(BasicDetailVO basicDetailVO) {
		if (basicDetailRepo.existsById(basicDetailVO.getId())) {
			return Optional.of(basicDetailRepo.save(basicDetailVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteBasicDetail(int id) {
		basicDetailRepo.deleteById(id);

	}

	@Override
	public List<PackingDetailVO> getAllpackingDetail() {
		return packingDetailRepo.findAll();
	}

	@Override
	public Optional<PackingDetailVO> getPackingDetailById(int id) {
		return packingDetailRepo.findById(id);
	}

	@Override
	public PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO) {
		return packingDetailRepo.save(packingDetailVO);
	}

	@Override
	public Optional<PackingDetailVO> updatePackingDetail(PackingDetailVO packingDetailVO) {
		if (packingDetailRepo.existsById(packingDetailVO.getId())) {
			return Optional.of(packingDetailRepo.save(packingDetailVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deletePackingDetail(int id) {
		packingDetailRepo.deleteById(id);

	}

	@Override
	public List<PackingDetailVO> getAllPackingDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogisticsVO> getAllLogistics() {
		return logisticRepo.findAll();
	}

	@Override
	public Optional<LogisticsVO> getLogisticsById(int id) {
		return logisticRepo.findById(id);
	}

	@Override
	public LogisticsVO createLogistics(LogisticsVO logisticsVO) {
		return logisticRepo.save(logisticsVO);
	}

	@Override
	public Optional<LogisticsVO> updateLogistics(LogisticsVO logisticsVO) {
		if (logisticRepo.existsById(logisticsVO.getId())) {
			return Optional.of(logisticRepo.save(logisticsVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteLogistics(int id) {
		logisticRepo.deleteById(id);
	}

	@Override
	public List<StockDetailVO> getAllStockDetail() {
		return stockDetailRepo.findAll();
	}

	@Override
	public Optional<StockDetailVO> getStockDetailById(int id) {
		return stockDetailRepo.findById(id);
	}

	@Override
	public StockDetailVO createStockDetail(StockDetailVO stockDetailVO) {
		return stockDetailRepo.save(stockDetailVO);
	}

	@Override
	public Optional<StockDetailVO> updateStockDetail(StockDetailVO stockDetailVO) {
		if (stockDetailRepo.existsById(stockDetailVO.getId())) {
			return Optional.of(stockDetailRepo.save(stockDetailVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteStockDetail(int id) {
		stockDetailRepo.deleteById(id);
	}

}
