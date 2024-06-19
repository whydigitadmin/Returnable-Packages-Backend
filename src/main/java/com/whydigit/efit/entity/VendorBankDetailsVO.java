package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Vendor3")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorBankDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vendorbankgen")
	@SequenceGenerator(name = "vendorbankgen", sequenceName = "vendorbankseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="vendor3")
	private Long id;
	
	@Column(name="bank")
	private String bank;
	
	@Column(name="accountname")
	private String accountName;
	
	@Column(name="ifsccode")
	private String ifscCode;
	
	@Column(name="accountno")
	private String accountNo;
	
	@Column(name="branch")
	private String branch;
	
	@ManyToOne
	@JoinColumn(name="vendorid")
	@JsonBackReference
	VendorVO vendorVO;


}
