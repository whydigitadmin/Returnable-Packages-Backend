package com.whydigit.efit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.BasicDetailDTO;
import com.whydigit.efit.dto.LogisticsDTO;
import com.whydigit.efit.dto.PDAttachmentType;
import com.whydigit.efit.dto.PackingDetailDTO;
import com.whydigit.efit.dto.StockDetailDTO;
import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;
import com.whydigit.efit.exception.ApplicationException;

public interface PartStudyService {

	List<BasicDetailVO> getAllBasicDetail(Long orgId);

	BasicDetailVO getBasicDetailById(Long id) throws ApplicationException;

	BasicDetailVO createBasicDetail(BasicDetailDTO basicDetailDTO);

	BasicDetailVO updateBasicDetail(BasicDetailDTO basicDetailDTO) throws ApplicationException;

	void deleteBasicDetail(Long id);

	List<PackingDetailVO> getAllpackingDetail(Long orgId);

	PackingDetailVO getPackingDetailById(Long id) throws ApplicationException;

	PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO);

	PackingDetailVO updatePackingDetail(PackingDetailDTO packingDetailDTO) throws ApplicationException;

	void deletePackingDetail(Long id);

	List<LogisticsVO> getAllLogistics(Long orgId);

	Optional<LogisticsVO> getLogisticsById(Long id);

	LogisticsVO createLogistics(LogisticsVO logisticsVO);

	LogisticsVO updateLogistics(LogisticsDTO logisticsDTO) throws ApplicationException;

	void deleteLogistics(Long id);

	List<StockDetailVO> getAllStockDetail(Long orgId);

	Optional<StockDetailVO> getStockDetailById(Long id);

	StockDetailVO createStockDetail(StockDetailVO stockDetailVO);

	StockDetailVO updateStockDetail(StockDetailDTO stockDetailDTO) throws ApplicationException;

	void deleteStockDetail(Long id);

	boolean generatePartStudyId(String refPsId);

	 Map<String, Object> searchPartStudy(Long emitterId, Long refPsId, Long orgId, String partName,String partNumber);

	void saveAttachments(MultipartFile[] files, PDAttachmentType type, Long refPsId) throws ApplicationException;
	
	String uploadPartImage(Long id, MultipartFile file);
	

}
