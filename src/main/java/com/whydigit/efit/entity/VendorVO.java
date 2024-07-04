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

import com.fasterxml.jackson.annotation.JsonGetter;
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
	@Column(name = "vendorid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;

	private boolean cancel;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;

	@Column(name = "cancelremarks")
	private String cancelremarks;

	@Column(name = "type")
	private String venderType;

	@Column(name = "displayname")
	private String displyName;

	@Column(name = "phone")
	private String phoneNumber;

	@Column(name = "legalname")
	private String entityLegalName;

	@Column(name = "email")
	private String email;
	
	private String country;

	@Column(name = "activeportal")
	private boolean venderActivePortal;

	private boolean active;

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<VendorBankDetailsVO> vendorBankDetailsVO;

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<VendorAddressVO> vendorAddressVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
