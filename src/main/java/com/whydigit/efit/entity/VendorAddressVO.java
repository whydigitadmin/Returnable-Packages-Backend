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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorAddressVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long orgId;
	private String gstRegistrationStatus;
	private String street1;
	private String street2;
	private String state;
	private String city;
	private String pincode;
	private String contactName;
	private String phone;
	private String destination;
	private long gstNumber;
	private String eMail;
	private boolean markPrimary;
	private String createdBy;
	private String updatedBy;
	private boolean active;

	@ManyToOne
	@JoinColumn(name="vendor_id")
	@JsonBackReference
	VendorVO vendorVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
