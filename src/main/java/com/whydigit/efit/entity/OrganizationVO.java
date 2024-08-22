package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orgseqgen")
	@SequenceGenerator(name = "orgseqgen", sequenceName = "orgseqname", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "organizationid")
	private Long id;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "createdby")
	private String createdby;

	@Column(name = "modifiedby")
	private String modifiedby;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phoneNumber;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "pincode")
	private String pinCode;

	@Column(name = "country")
	private String country;

	@Column(name = "logo")
	private String orgLogo;

	@Column(name = "code")
	private String code;

	private String email;
	
	private String address;
	
	private String password;
	
	@Column(name = "adminfirstname")
	private String adminFirstName;
	
	@Column(name = "active")
	private boolean active;

	@Column(name = "subscriptionType")
	private String subscriptionType;

	@Column(name = "cancelremarks")
	private String cancelRemarks;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
