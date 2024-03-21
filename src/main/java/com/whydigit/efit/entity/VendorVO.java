package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vendorgen")
	@SequenceGenerator(name = "vendorgen", sequenceName = "vendorseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="vednorid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	private boolean cancel;
	
	@Column(name="createdby",length = 25)
	private String createdby;
	
	@Column(name="modifiedby",length = 25)
	private String modifiedby;
	
	@Column(name="cancelremarks",length = 50)
	private String cancelremarks;
	
	@Column(name="type",length = 25)
	private String venderType;
	
	@Column(name="displayname",length = 25)
	private String displyName;
	
	@Column(name="phone",length = 25)
	private String phoneNumber;
	
	@Column(name="legalname",length = 25)
	private String entityLegalName;
	
	@Column(name="email",length = 50)
	private String email;
	
	@Column(name="activeportal",length = 25)
	private boolean venderActivePortal;
	
	private boolean active;
	

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<VendorBankDetailsVO> vendorBankDetailsVO;

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<VendorAddressVO> vendorAddressVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
