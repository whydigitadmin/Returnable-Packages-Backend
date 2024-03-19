package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor_bank_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorBankDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long orgId;
	private String bank;
	private String displayName;
	private String ifscCode;
	private Long accountNum;
	private String branch;
	
	@ManyToOne
	@JoinColumn(name="vendor_id")
	@JsonBackReference
	VendorVO vendorVO;

	@Embedded
	private CreatedUpdatedDate commonDate;
}
