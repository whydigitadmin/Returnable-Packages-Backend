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
	
	@Column(name="bank",length = 25)
	private String bank;
	
	@Column(name="accountname",length = 25)
	private String accountname;
	
	@Column(name="ifsccode",length = 25)
	private String ifscCode;
	
	@Column(name="accountno",length = 25)
	private String accountNo;
	
	@Column(name="branch",length = 25)
	private String branch;
	
	@ManyToOne
	@JoinColumn(name="vendorid")
	@JsonBackReference
	VendorVO vendorVO;


}
