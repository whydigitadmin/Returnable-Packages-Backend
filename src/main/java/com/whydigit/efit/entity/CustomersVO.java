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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long orgId;
	private int customerType;
	private String entityLegalName;
	private String email;
	private String customerCode;
	private String displayName;
	private long phoneNumber;
	private String sop;
	private String document;
	private boolean customerActivatePortal;
	private boolean active;

	@OneToMany(mappedBy = "customersVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<CustomersAddressVO> customersAddressVO;

	@OneToMany(mappedBy = "customersVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<CustomersBankDetailsVO> customersBankDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
