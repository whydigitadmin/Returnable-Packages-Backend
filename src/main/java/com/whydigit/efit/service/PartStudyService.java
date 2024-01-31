package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;

public interface PartStudyService {

	List<BasicDetailVO>getAllBasicDetail(Long orgId);
	
	Optional<BasicDetailVO>getBasicDetailById(int id);
	
	BasicDetailVO createBasicDetail(BasicDetailVO basicDetailVO);
	
	Optional<BasicDetailVO>updateBasicDetail(BasicDetailVO basicDetailVO);
	
	void deleteBasicDetail(int id);
	

	List<PackingDetailVO>getAllPackingDetail();
	
	Optional<PackingDetailVO>getPackingDetailById(int id);
	
	PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO);
	
	Optional<PackingDetailVO>updatePackingDetail(PackingDetailVO packingDetailVO);
	
	void deletePackingDetail(int id);

	List<PackingDetailVO> getAllpackingDetail();
	
	
	List<LogisticsVO>getAllLogistics();
	
	Optional<LogisticsVO>getLogisticsById(int id);
	
	LogisticsVO createLogistics(LogisticsVO logisticsVO);
	
	Optional<LogisticsVO>updateLogistics(LogisticsVO logisticsVO);
	
	void deleteLogistics(int id);
	
	
    List<StockDetailVO>getAllStockDetail();
	
	Optional<StockDetailVO>getStockDetailById(int id);
	
	StockDetailVO createStockDetail(StockDetailVO stockDetailVO);
	
	Optional<StockDetailVO>updateStockDetail(StockDetailVO stockDetailVO);
	
	void deleteStockDetail(int id);



	
}

