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
@Table(name = "customers_address")
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
	private Long phoneNumber;
	private String gstNumber;
	private String city;
	private String contactName;
	private boolean isDefault;

	@ManyToOne
	@JoinColumn(name = "customers_id")
	@JsonBackReference
	private CustomersVO customersVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
