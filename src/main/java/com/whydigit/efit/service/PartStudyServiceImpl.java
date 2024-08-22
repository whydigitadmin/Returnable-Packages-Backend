
package com.whydigit.efit.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.dto.BasicDetailDTO;
import com.whydigit.efit.dto.LogisticsDTO;
import com.whydigit.efit.dto.PDAttachmentType;
import com.whydigit.efit.dto.PackingDetailDTO;
import com.whydigit.efit.dto.StockDetailDTO;
import com.whydigit.efit.entity.ApprovedPackageDrawingVO;
import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PDAttachmentVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.BasicDetailRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.LogisticsRepo;
import com.whydigit.efit.repo.PDAttachmentRepo;
import com.whydigit.efit.repo.PackingDetailRepo;
import com.whydigit.efit.repo.StockDetailRepo;
import com.whydigit.efit.util.CommonUtils;

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
	@Autowired
	Environment env;
	@Autowired
	PDAttachmentRepo pdAttachmentRepo;
	@Autowired
	CustomersRepo customersRepo;

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
	public List<BasicDetailVO> getAllActiveBasicDetail(Long orgId) {
		List<BasicDetailVO> basicDetailVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  BasicDetail Information BY OrgId : {}", orgId);
			basicDetailVO = basicDetailRepo.getAllActiveBasicDetailByOrgId(orgId);
		}
		return basicDetailVO;
	}

	@Override
	public BasicDetailVO getBasicDetailById(Long id) throws ApplicationException {
		BasicDetailVO basicDetailVO = basicDetailRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("BasicDetail not found"));
		PackingDetailVO packingDetailVO = basicDetailVO.getPackingDetailVO();
		List<PDAttachmentVO> pdAttachmentVO = getPDAttachment(id);
		// setAttachmentToPackageDetailVO(packingDetailVO, pdAttachmentVO);
		basicDetailVO.setPackingDetailVO(packingDetailVO);
		return basicDetailVO;
	}

	@Override
	public BasicDetailVO createBasicDetail(BasicDetailDTO basicDetailDTO) {

		String partstudy = "PS" + basicDetailRepo.finddocid();

		BasicDetailVO basicDetailVO = new BasicDetailVO();
		getBasicDetailVOFromBasicDetailDTO(basicDetailDTO, basicDetailVO);
		basicDetailVO.setDocid(partstudy);
		basicDetailRepo.save(basicDetailVO);
		PackingDetailVO packingDetailVO = new PackingDetailVO();
		StockDetailVO stockdetailVO = new StockDetailVO();
		LogisticsVO logisticsVO = new LogisticsVO();
		packingDetailVO.setRefPsId(basicDetailVO.getRefPsId());
		stockdetailVO.setRefPsId(basicDetailVO.getRefPsId());
		logisticsVO.setRefPsId(basicDetailVO.getRefPsId());
		packingDetailVO.setBasicDetailVO(basicDetailVO);
		stockdetailVO.setBasicDetailVO(basicDetailVO);
		logisticsVO.setBasicDetailVO(basicDetailVO);
		basicDetailVO.setPackingDetailVO(packingDetailVO);
		basicDetailVO.setStockDetailVO(stockdetailVO);
		basicDetailVO.setLogisticsVO(logisticsVO);
		basicDetailRepo.updatesequence();
		return basicDetailRepo.save(basicDetailVO);
	}

	private void getBasicDetailVOFromBasicDetailDTO(BasicDetailDTO basicDetailDTO, BasicDetailVO basicDetailVO) {
		basicDetailVO.setOrgId(basicDetailDTO.getOrgId());
		basicDetailVO.setPartStudyDate(LocalDate.now());
		basicDetailVO.setEmitterId(basicDetailDTO.getEmitterId());
//		basicDetailVO.setReceiverId(basicDetailDTO.getReceiverId());
		basicDetailVO.setPartName(basicDetailDTO.getPartName());
		basicDetailVO.setActive(basicDetailDTO.isActive());
		basicDetailVO.setPartNumber(basicDetailDTO.getPartNumber());
		basicDetailVO.setWeight(basicDetailDTO.getWeight());
		CustomersVO emitter = customersRepo.findById(basicDetailDTO.getEmitterId()).get();
		basicDetailVO.setEmitterDisplayName(emitter.getDisplayName());
//		CustomersVO receiver=customersRepo.findById(basicDetailDTO.getReceiverId()).get();
//		basicDetailVO.setReceiverDisplayName(receiver.getDisplayName());
		basicDetailVO.setWeightUnit(basicDetailDTO.getWeightUnit());
		basicDetailVO.setPartVolume(basicDetailDTO.getPartVolume());
		basicDetailVO.setHighestVolume(basicDetailDTO.getHighestVolume());
		basicDetailVO.setLowestVolume(basicDetailDTO.getLowestVolume());
		basicDetailVO.setCreatedBy(basicDetailDTO.getCreatedBy());
		basicDetailVO.setModifiedBy(basicDetailDTO.getModifiedBy());
	}

	@Override
	public BasicDetailVO updateBasicDetail(BasicDetailDTO basicDetailDTO) throws ApplicationException {
		BasicDetailVO basicDetailVO = new BasicDetailVO();
		basicDetailVO = basicDetailRepo.findById(basicDetailDTO.getRefPsId())
				.orElseThrow(() -> new ApplicationException("Invalid  basicDetail details"));
		getBasicDetailVOFromBasicDetailDTO(basicDetailDTO, basicDetailVO);
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
	public PackingDetailVO getPackingDetailById(Long id) throws ApplicationException {
		PackingDetailVO packingDetailVO = packingDetailRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("PackingDetail not found."));
		List<PDAttachmentVO> pdAttachmentVO = getPDAttachment(id);
		// setAttachmentToPackageDetailVO(packingDetailVO, pdAttachmentVO);
		return packingDetailVO;
	}

//	private void setAttachmentToPackageDetailVO(PackingDetailVO packingDetailVO, List<PDAttachmentVO> pdAttachmentVO) {
//		packingDetailVO.setPartImage(
//				pdAttachmentVO.stream().filter(pa -> pa.getType().equalsIgnoreCase(PDAttachmentType.PART_IMAGE.name()))
//						.collect(Collectors.toList()));
//		packingDetailVO.setExistingPackingImage(pdAttachmentVO.stream()
//				.filter(pa -> pa.getType().equalsIgnoreCase(PDAttachmentType.EXISTING_PACKING_IMAGE.name()))
//				.collect(Collectors.toList()));
//		packingDetailVO.setPartDrawing(pdAttachmentVO.stream()
//				.filter(pa -> pa.getType().equalsIgnoreCase(PDAttachmentType.PART_DRAWING.name()))
//				.collect(Collectors.toList()));
//		packingDetailVO.setApprovedCommercialContract(pdAttachmentVO.stream()
//				.filter(pa -> pa.getType().equalsIgnoreCase(PDAttachmentType.APPROVED_COMMERCIAL_CONTRACT.name()))
//				.collect(Collectors.toList()));
//	}

	@Override
	public PackingDetailVO createPackingDetail(PackingDetailVO packingDetailVO) {
		return packingDetailRepo.save(packingDetailVO);
	}

	@Override
	public PackingDetailVO updatePackingDetail(PackingDetailDTO packingDetailDTO) throws ApplicationException {
		PackingDetailVO packingDetailVO = new PackingDetailVO();
		packingDetailVO = packingDetailRepo.findById(packingDetailDTO.getRefPsId())
				.orElseThrow(() -> new ApplicationException("Invalid packing details"));
		getPackingDetailVOFromPackingDetailDTO(packingDetailDTO, packingDetailVO);
		packingDetailVO.setApprovedCommercialContract(packingDetailDTO.getApprovedCommercialContract());
		packingDetailVO.setComercial(packingDetailDTO.getComercial());
		packingDetailVO.setPartDrawing(packingDetailDTO.getPartDrawing());
		packingDetailVO.setPartImage(packingDetailDTO.getPartImage());
		packingDetailVO.setExistingPackingImage(packingDetailDTO.getExistingPackingImage());
		ApprovedPackageDrawingVO approvedPackageDrawingVO = new ApprovedPackageDrawingVO();
		packingDetailVO.getApprovedPackageDrawingVO().add(null);
		return packingDetailRepo.save(packingDetailVO);
	}

	private void getPackingDetailVOFromPackingDetailDTO(PackingDetailDTO packingDetailDTO,
			PackingDetailVO packingDetailVO) {
		packingDetailVO.setOrgId(packingDetailDTO.getOrgId());
		packingDetailVO.setLength(packingDetailDTO.getLength());
		packingDetailVO.setBreath(packingDetailDTO.getBreath());
		packingDetailVO.setHeight(packingDetailDTO.getHeight());
		packingDetailVO.setApprovedCommercialContract(packingDetailDTO.getApprovedCommercialContract());
		packingDetailVO.setComercial(packingDetailDTO.getComercial());
		packingDetailVO.setPartDrawing(packingDetailDTO.getPartDrawing());
		packingDetailVO.setPartImage(packingDetailDTO.getPartImage());
		packingDetailVO.setExistingPackingImage(packingDetailDTO.getExistingPackingImage());

		packingDetailVO.setPartUnit(packingDetailDTO.getPartUnit());
		packingDetailVO.setExistingPart(packingDetailDTO.getExistingPart());
		packingDetailVO.setCurrentPackingChallenges(packingDetailDTO.getCurrentPackingChallenges());
		packingDetailVO.setPartsPerPackaging(packingDetailDTO.getPartsPerPackaging());
		packingDetailVO.setPartSensitive(packingDetailDTO.getPartSensitive());
		packingDetailVO.setPartGreasy(packingDetailDTO.getPartGreasy());
		packingDetailVO.setPartOrientation(packingDetailDTO.getPartOrientation());
		packingDetailVO.setMultiPartInSingleUnit(packingDetailDTO.getMultiPartInSingleUnit());
		packingDetailVO.setStacking(packingDetailDTO.getStacking());
		packingDetailVO.setNesting(packingDetailDTO.getNesting());
		packingDetailVO.setRemarks(packingDetailDTO.getRemarks());
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
		getLogisticsVOFromLogisticsDTO(logisticsDTO, logisticsVO);
		return logisticRepo.save(logisticsVO);
	}

	private void getLogisticsVOFromLogisticsDTO(LogisticsDTO logisticsDTO, LogisticsVO logisticsVO) {
		logisticsVO.setOrgId(logisticsDTO.getOrgId());
		logisticsVO.setAvgLotSize(logisticsDTO.getAvgLotSize());
		logisticsVO.setDispatchFrequency(logisticsDTO.getDispatchFrequency());
		logisticsVO.setDiapatchTo(logisticsDTO.getDiapatchTo());
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
		getStockDetailVOFromStockDetailDTO(stockDetailDTO, stockDetailVO);
		return stockDetailRepo.save(stockDetailVO);
	}

	private void getStockDetailVOFromStockDetailDTO(StockDetailDTO stockDetailDTO, StockDetailVO stockDetailVO) {
		stockDetailVO.setOrgId(stockDetailDTO.getOrgId());
		stockDetailVO.setEmitterStoreDays(stockDetailDTO.getEmitterStoreDays());
		stockDetailVO.setEmitterLineDays(stockDetailDTO.getEmitterLineDays());
		stockDetailVO.setInTransitDays(stockDetailDTO.getInTransitDays());
		stockDetailVO.setReceiverLineStorageDays(stockDetailDTO.getReceiverLineStorageDays());
		stockDetailVO.setReceiverManufacturingLineDays(stockDetailDTO.getReceiverManufacturingLineDays());
		stockDetailVO.setOtherStorageDays(stockDetailDTO.getOtherStorageDays());
		stockDetailVO.setReverseLogisticsDay(stockDetailDTO.getReverseLogisticsDay());
		stockDetailVO.setTotalCycleTime(stockDetailDTO.getTotalCycleTime());
	}

	@Override
	public void deleteStockDetail(Long id) {
		stockDetailRepo.deleteById(id);
	}

	@Override
	public boolean generatePartStudyId(String refPsId) {
		boolean status = false;
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
	public Map<String, Object> searchPartStudy(Long emitterId, Long refPsId, Long orgId, String partName,
			String partNumber) {
		Map<String, Object> basicDetail = new HashMap<>();
		List<BasicDetailVO> basicDetailVO = basicDetailRepo.findAll(new Specification<BasicDetailVO>() {
			@Override
			public Predicate toPredicate(Root<BasicDetailVO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(orgId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
				}
				if (ObjectUtils.isNotEmpty(emitterId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("emitterId"), emitterId)));
				}
				if (StringUtils.isNotBlank(partName)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("partName"), partName)));
				}
				if (StringUtils.isNotBlank(partNumber)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("partNumber"), partNumber)));
				}
				if (ObjectUtils.isNotEmpty(refPsId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("refPsId"), refPsId)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		List<Long> emitterIds = basicDetailVO.stream().map(BasicDetailVO::getEmitterId).distinct()
				.collect(Collectors.toList());
		basicDetail.put("basicDetailVO", basicDetailVO);
		basicDetail.put("emitter", customersRepo.findAllById(emitterIds).stream()
				.collect(Collectors.toMap(CustomersVO::getDisplayName, CustomersVO::getId)));
		basicDetail.put("partName",
				basicDetailVO.stream().map(BasicDetailVO::getPartName).distinct().collect(Collectors.toList()));
		basicDetail.put("partNumber",
				basicDetailVO.stream().map(BasicDetailVO::getPartNumber).distinct().collect(Collectors.toList()));
		basicDetail.put("partStudyId",
				basicDetailVO.stream().map(BasicDetailVO::getRefPsId).distinct().collect(Collectors.toList()));
		return basicDetail;
	}

	@Override
	public void saveAttachments(MultipartFile[] files, PDAttachmentType type, Long refPsId)
			throws ApplicationException {
		if (files == null || files.length == 0 || StringUtils.isEmpty(type.name()) || ObjectUtils.isEmpty(refPsId)) {
			throw new ApplicationException("Invalid Attachment Information.");
		}
		String psDirPath = env.getProperty("part.study.attachment.dir");
		String uploadDirPath = new StringBuilder(psDirPath).append("/").append(refPsId).toString();
		File uploadDir = new File(uploadDirPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_YYYY_HH_mm_ss"));
		int fileCount = 0;
		StringBuilder approvedPDFileName = new StringBuilder();
		PackingDetailVO packingDetailVO = packingDetailRepo.findById(refPsId)
				.orElseThrow(() -> new ApplicationException("PackingDetail not found."));
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					String fileName = CommonUtils.constructUniqueFileName(file.getOriginalFilename(), type.name(),
							fileCount, date);
					Path savePath = Paths.get(uploadDirPath, fileName);
					file.transferTo(savePath);
					String attFileName = new StringBuilder(CommonConstant.FORWARD_SLASH).append(type)
							.append(CommonConstant.FORWARD_SLASH).append(fileName).toString();
					if (type.name().equalsIgnoreCase(PDAttachmentType.APPROVED_PACKAGE_DRAWING.name())) {
						approvedPDFileName.append(attFileName).append(",");
					} else {
						pdAttachmentRepo.save(PDAttachmentVO.builder().fileName(attFileName).type(type.name())
								.refPsId(refPsId).build());
					}
				} catch (Exception e) {
					LOGGER.error("Failed to save the file: {} Error : {}", file.getOriginalFilename(), e.getMessage());
				}
				fileCount++;
			}
			if (type.name().equalsIgnoreCase(PDAttachmentType.APPROVED_PACKAGE_DRAWING.name())) {
				List<ApprovedPackageDrawingVO> approvedPackageDrawingVO = packingDetailVO.getApprovedPackageDrawingVO();
				ApprovedPackageDrawingVO approvedPackageDrawing = new ApprovedPackageDrawingVO();
				approvedPackageDrawing.setFileName(CommonUtils.trimLastCharacter(approvedPDFileName.toString()));
				approvedPackageDrawing.setPackingDetailVO(packingDetailVO);
				approvedPackageDrawing.setRejectStatus(false);
				approvedPackageDrawingVO.add(approvedPackageDrawing);
				packingDetailVO.setApprovedPackageDrawingVO(approvedPackageDrawingVO);
				packingDetailRepo.save(packingDetailVO);
			}
		}
	}

	private String constructUniqueFileName(String originalFilename, String type, int fileCount, String date) {
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		return new StringBuilder(type).append(CommonConstant.UNDERSCORE).append(date).append(CommonConstant.UNDERSCORE)
				.append(fileCount).append(extension).toString();
	}

	private List<PDAttachmentVO> getPDAttachment(long refPsId) {
		return pdAttachmentRepo.findByRefPsId(refPsId);
	}

// Part Image
	@Value("${part.study.attachment.dir}")
	private String partimage;

	@Override
	public String uploadPartImage(Long id, MultipartFile file) {
		String uploadResult = uploadPartImageFile(id, file); // Call uploadFile method with docId and refNo
		// Here you can do further processing or return both results combined
		return uploadResult;
	}

	private String uploadPartImageFile(Long id, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionDocument(originalFileName);
			// Customize the filename
			String customizedFileName = id + "-PartImage" + fileExtension;
			String uploadDirPath = new StringBuilder(partimage).append("/").append(id).toString();
			// Create the directory if it doesn't exist
			File directory = new File(uploadDirPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(uploadDirPath, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			PackingDetailVO vo = packingDetailRepo.findById(id).orElse(null);
			// vo.setPartImg(filePath.toString().replace("\\", "/"));
			packingDetailRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	private String getFileExtensionDocument(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return "";
	}

	@Override
	public String uploadPartDrawing(Long id, MultipartFile file) {
		String uploadResult = uploadPartDrawingImageFile(id, file); // Call uploadFile method with docId and refNo
		return uploadResult;
	}

	private String uploadPartDrawingImageFile(Long id, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionDocument(originalFileName);
			// Customize the filename
			String customizedFileName = id + "-PartDrawing" + fileExtension;
			// Create the directory if it doesn't exist
			String uploadDirPath = new StringBuilder(partimage).append("/").append(id).toString();
			File directory = new File(uploadDirPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(uploadDirPath, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			PackingDetailVO vo = packingDetailRepo.findById(id).orElse(null);
			// vo.setPDrawing(filePath.toString().replace("\\", "/"));
			packingDetailRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	@Override
	public String uploadExPackageImage(Long id, MultipartFile file) {
		String uploadResult = uploadExPackageImageFile(id, file); // Call uploadFile method with docId and refNo
		return uploadResult;
	}

	private String uploadExPackageImageFile(Long id, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionDocument(originalFileName);
			// Customize the filename
			String customizedFileName = id + "-ExistingPackagingImage" + fileExtension;
			// Create the directory if it doesn't exist
			String uploadDirPath = new StringBuilder(partimage).append("/").append(id).toString();
			File directory = new File(uploadDirPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(uploadDirPath, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			PackingDetailVO vo = packingDetailRepo.findById(id).orElse(null);
			// vo.setExistingImage(filePath.toString().replace("\\", "/"));
			packingDetailRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	@Override
	public String uploadApprovedCommercial(Long id, MultipartFile file) {
		String uploadResult = uploadApprovedCommercialImageFile(id, file); // Call uploadFile method with docId and
																			// refNo
		return uploadResult;
	}

	private String uploadApprovedCommercialImageFile(Long id, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionDocument(originalFileName);
			// Customize the filename
			String customizedFileName = id + "-ApprovedCommercialImage" + fileExtension;
			// Create the directory if it doesn't exist
			String uploadDirPath = new StringBuilder(partimage).append("/").append(id).toString();
			File directory = new File(uploadDirPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(uploadDirPath, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			PackingDetailVO vo = packingDetailRepo.findById(id).orElse(null);
			// vo.setComercial(filePath.toString().replace("\\", "/"));
			packingDetailRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	@Override
	public String uploadApprovedTechnicalDrawing(Long id, MultipartFile file) {
		String uploadResult = uploadApprovedTechnicalDrawingImageFile(id, file); // Call uploadFile method with docId
																					// and refNo
		return uploadResult;
	}

	private String uploadApprovedTechnicalDrawingImageFile(Long id, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionDocument(originalFileName);
			// Customize the filename
			String customizedFileName = id + "-ApprovedTechnicalDrawing" + fileExtension;
			// Create the directory if it doesn't exist
			String uploadDirPath = new StringBuilder(partimage).append("/").append(id).toString();
			File directory = new File(uploadDirPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(uploadDirPath, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			PackingDetailVO vo = packingDetailRepo.findById(id).orElse(null);
			// vo.setApprovedDrawing(filePath.toString().replace("\\", "/"));
			packingDetailRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	@Override
	public PackingDetailVO uploadPartImageInBloob(MultipartFile file, Long refPsId) throws IOException {
		PackingDetailVO PackingDetailVO = packingDetailRepo.findById(refPsId).get();
		PackingDetailVO.setPartImage(file.getBytes());
		return packingDetailRepo.save(PackingDetailVO);
	}

	@Override
	public PackingDetailVO uploadExistingPackingImageInBloob(MultipartFile file, Long refPsId) throws IOException {
		PackingDetailVO PackingDetailVO = packingDetailRepo.findById(refPsId).get();
		PackingDetailVO.setExistingPackingImage(file.getBytes());
		return packingDetailRepo.save(PackingDetailVO);
	}

	@Override
	public PackingDetailVO uploadpartdrawingInBloob(MultipartFile file, Long refPsId) throws IOException {
		PackingDetailVO PackingDetailVO = packingDetailRepo.findById(refPsId).get();
		PackingDetailVO.setPartDrawing(file.getBytes());
		return packingDetailRepo.save(PackingDetailVO);
	}

	@Override
	public PackingDetailVO uploadApprovedCommercialContractInBloob(MultipartFile file, Long refPsId)
			throws IOException {
		PackingDetailVO PackingDetailVO = packingDetailRepo.findById(refPsId).get();
		PackingDetailVO.setApprovedCommercialContract(file.getBytes());
		return packingDetailRepo.save(PackingDetailVO);
	}

	@Override
	public PackingDetailVO uploadCommercialInBloob(MultipartFile file, Long refPsId) throws IOException {
		PackingDetailVO PackingDetailVO = packingDetailRepo.findById(refPsId).get();
		PackingDetailVO.setComercial(file.getBytes());
		return packingDetailRepo.save(PackingDetailVO);
	}
//
//	  @Override
//	    public PackingDetailVO uploadCommercialInBlob(MultipartFile file, Long refPsId) throws IOException {
//	        LOGGER.debug("Uploading commercial image for refPsId: {}", refPsId);
//	        PackingDetailVO packingDetailVO = packingDetailRepo.findById(refPsId).orElseThrow(() -> {
//	            LOGGER.error("PackingDetail not found for refPsId: {}", refPsId);
//	            return new RuntimeException("PackingDetail not found");
//	        });
//	        packingDetailVO.setComercial(file.getBytes());
//	        LOGGER.debug("Commercial image uploaded successfully for refPsId: {}", refPsId);
//	        return packingDetailRepo.save(packingDetailVO);
//	    }

//	    @Override
//	    public byte[] getCommercialImageById(Long id) {
//	        LOGGER.debug("Retrieving commercial image for id: {}", id);
//	        PackingDetailVO packingDetailVO = packingDetailRepo.findById(id).orElseThrow(() -> {
//	            LOGGER.error("PackingDetail not found for id: {}", id);
//	            return new RuntimeException("PackingDetail not found");
//	        });
//	        byte[] image = packingDetailVO.getComercial();
//	        LOGGER.debug("Commercial image retrieved successfully for id: {}", id);
//	        return image;
//	    }
}
