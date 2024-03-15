package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long orgId;
	private String venderType;
	private String displyName;
	private String phoneNumber;
	private String entityLegalName;
	private String email;
	private boolean venderActivePortal;
	private boolean active;

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<VendorBankDetailsVO> vendorBankDetailsVO;

	@OneToMany(mappedBy = "vendorVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<VendorAddressVO> vendorAddressVO;

	@Embedded
	private CreatedUpdatedDate commonDate;

}
