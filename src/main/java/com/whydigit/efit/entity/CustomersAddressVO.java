package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersAddressVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CustomersAddressVO")
	@SequenceGenerator(name = "CustomersAddressVO", sequenceName = "CustomersAddressVo", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customer1id")
	private Long id;

	@Column(name = "gst")
	private String gstRegistrationStatus;

	@Column(name = "street1")
	private String street1;

	@Column(name = "street2")
	private String street2;

	@Column(name = "pincode")
	private String pinCode;

	@Column(name = "phone")
	private String phoneNumber;

	@Column(name = "gstin")
	private String gstNumber;

	@Column(name = "city")
	private String city;

	@Column(name = "contactperson")
	private String contactName;

	@Column(name = "state")
	private String state;

	@Column(name = "email")
	private String email;

	@Column(name = "designation")
	private String designation;

	@Column(name = "country")
	private String country;

	@ManyToOne
	@JoinColumn(name = "customerid")
	@JsonBackReference
	private CustomersVO customersVO;

}
