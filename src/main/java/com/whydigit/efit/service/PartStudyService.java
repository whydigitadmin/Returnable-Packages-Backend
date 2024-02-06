package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;

public interface PartStudyService {

	List<BasicDetailVO> getAllBasicDetail(Long orgId);

	Optional<BasicDetailVO> getBasicDetailById(UUID id);

	BasicDetailVO createBasicDetail(BasicDetailVO basicDetailVO);

	Optional<BasicDetailVO> updateBasicDetail(BasicDetailVO basicDetailVO);

	void deleteBasicDetail(UUID id);

	List<PackingDetailVO> getAllpackingDetail(Long orgId);

	Optional<PackingDetailVO> getPackingDetailById(UUID id);

	PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO);

	Optional<PackingDetailVO> updatePackingDetail(PackingDetailVO packingDetailVO);

	void deletePackingDetail(UUID id);

	List<LogisticsVO> getAllLogistics(Long orgId);

	Optional<LogisticsVO> getLogisticsById(UUID id);

	LogisticsVO createLogistics(LogisticsVO logisticsVO);

	Optional<LogisticsVO> updateLogistics(LogisticsVO logisticsVO);

	void deleteLogistics(UUID id);

	List<StockDetailVO> getAllStockDetail(Long orgId);

	Optional<StockDetailVO> getStockDetailById(UUID id);

	StockDetailVO createStockDetail(StockDetailVO stockDetailVO);

	Optional<StockDetailVO> updateStockDetail(StockDetailVO stockDetailVO);

	void deleteStockDetail(UUID id);

}
