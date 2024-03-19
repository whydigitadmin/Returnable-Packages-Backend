package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersBankDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CustomersBankDetailsVO")
	@SequenceGenerator(name = "CustomersBankDetailsVO", sequenceName = "CustomersBankDetailsVo", initialValue = 1000000001, allocationSize = 1)
	@Column(name="customer2id")
	private Long id;
	
	@Column(name="bank",length = 50)
	private String bank;
	
	@Column(name="accountname",length =30 )
	private String accountName;
	
	@Column(name="ifsc",length =10 )
	private String ifscCode;
	
	@Column(name="branch",length =30 )
	private String branch;
	
	@Column(name="accountno",length =30 )
	private Long accountNo;
	
	@Column(name="defaultaccount")
	private boolean isDefault;

	@ManyToOne
	@JoinColumn(name = "customers_id")
	@JsonBackReference
	private CustomersVO customersVO;

	
}
