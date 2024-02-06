package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;

public interface PartStudyService {

	List<BasicDetailVO> getAllBasicDetail(Long orgId);

	Optional<BasicDetailVO> getBasicDetailById(Long id);

	BasicDetailVO createBasicDetail(BasicDetailVO basicDetailVO);

	Optional<BasicDetailVO> updateBasicDetail(BasicDetailVO basicDetailVO);

	void deleteBasicDetail(Long id);

	List<PackingDetailVO> getAllpackingDetail(Long orgId);

	Optional<PackingDetailVO> getPackingDetailById(Long id);

	PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO);

	Optional<PackingDetailVO> updatePackingDetail(PackingDetailVO packingDetailVO);

	void deletePackingDetail(Long id);

	List<LogisticsVO> getAllLogistics(Long orgId);

	Optional<LogisticsVO> getLogisticsById(Long id);

	LogisticsVO createLogistics(LogisticsVO logisticsVO);

	Optional<LogisticsVO> updateLogistics(LogisticsVO logisticsVO);

	void deleteLogistics(Long id);

	List<StockDetailVO> getAllStockDetail(Long orgId);

	Optional<StockDetailVO> getStockDetailById(Long id);

	StockDetailVO createStockDetail(StockDetailVO stockDetailVO);

	Optional<StockDetailVO> updateStockDetail(StockDetailVO stockDetailVO);

	void deleteStockDetail(Long id);

}
