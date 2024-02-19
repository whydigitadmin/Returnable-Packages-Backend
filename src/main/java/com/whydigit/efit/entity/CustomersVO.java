package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String firstName;
	private int customerType;
	private String lastName;
	private String customerOrgName;
	private String customerCode;
	private String displayName;
	private String Email;
	private String phone;
	private String sop;
	private String document;
	private boolean customerActivatePortal;
	private boolean active;
	private String bankName;
	private String accountNO;
	private String accountName;
	private String branch;
	private String ifscCode;
	private String gstRegStatus;
	private String gstNo;
	private String street1;
	private String street2;
	private String state;
	private String city;
	private String pincode;
	private String contactName;
	private String phoneNumber;
	private String unit;
	private boolean isPrimary;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
