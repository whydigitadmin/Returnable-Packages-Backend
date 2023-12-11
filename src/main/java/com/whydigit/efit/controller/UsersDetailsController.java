package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.service.UsersDetailsService;
import com.whydigit.efit.entity.UsersDetails;

@RestController
@CrossOrigin
@RequestMapping("api/usersdetails")
public class UsersDetailsController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);
	
	@Autowired
	UsersDetailsService usersDetailsService;
	
	@GetMapping("/view")
	public ResponseEntity<ResponseDTO>getAllUsersDetails(){
		String methodName = "getAllUsersDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UsersDetails> usersDetails = new ArrayList<>();
		try {
			usersDetails = usersDetailsService.getAllUsers();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Users Details information get successfully");
			responseObjectsMap.put("UsersDetails", usersDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Users Details information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
		
	}
	
	@PostMapping("/view")
	public ResponseEntity<ResponseDTO>createUsersDetails(@RequestBody UsersDetails usersDetails){
		String methodName = "createUsersDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			UsersDetails Usersdetails = usersDetailsService.createUsersDetails(usersDetails);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Users Details created successfully");
			responseObjectsMap.put("UsersDeatils", Usersdetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Users Details creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
		
	}
	
	@PutMapping("/view")
	public ResponseEntity<ResponseDTO> updateUsersDetails(@RequestBody UsersDetails usersDetails) {
		String methodName = "updateUsersDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			UsersDetails updateUsersdetails = usersDetailsService.updateUsersDetails(usersDetails).orElse(null);
			if (updateUsersdetails != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse updated successfully");
				responseObjectsMap.put("UsersDetails", updateUsersdetails);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Users Details not found for ID: " + usersDetails.getUserdetails_id();
				responseDTO = createServiceResponseError(responseObjectsMap, "Users Details update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Users Deatils update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@DeleteMapping("/view/{id}")
	public ResponseEntity<ResponseDTO> deleteUsersDetails(@PathVariable int id) {
		String methodName = "deleteUsersDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			usersDetailsService.deleteUsersDetails(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Users Details deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Users Details deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	

}
