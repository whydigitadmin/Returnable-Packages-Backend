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
@Table(name = "customers_bank_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersBankDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bank;
	private String accountName;
	private String ifscCode;
	private String branch;
	private Long accountNo;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
