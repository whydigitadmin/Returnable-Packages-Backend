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
@Table(name = "vender_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenderAddressVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private long orgId;
	private int venderId;
	private String gstRegistrationStatus;
	private long gstNumber;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private long pincode;
	private String contactName;
	private String phone;
	private boolean markPrimary;
	private String createdBy;
	private String updatedBy;
	private boolean active;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
