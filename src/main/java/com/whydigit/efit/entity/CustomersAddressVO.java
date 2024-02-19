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
@Table(name = "customers_address_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersAddressVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String gstRegistrationStatus;
	private String street1;
	private String street2;
	private String pinCode;
	private String phoneNumber;
	private String gstNumber;
	private String city;
	private String contactName;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
