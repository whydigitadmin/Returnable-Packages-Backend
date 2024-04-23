package com.whydigit.efit.dto;

import javax.persistence.Column;

import com.whydigit.efit.entity.BranchVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
	
	private Long id;
	private String branchName;
	private String branchCode;
    private String address1;
	private String address2;
    private Long orgId;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String phone;
    private String GST;
	private String pan;
	private String currency;
	private String createdBy;

}
