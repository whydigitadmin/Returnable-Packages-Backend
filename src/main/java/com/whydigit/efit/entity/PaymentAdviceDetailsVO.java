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
@Table(name = "paymentadvicedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdviceDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "paymentadvicedetailsgen")
	@SequenceGenerator(name = "paymentadvicedetailsgen", sequenceName = "paymentadvicedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentadvicedetailsid")
	private Long id;

	@Column(name = "paymentmode")
	private String paymentMode;

	@Column(name = "accountno")
	private String accountNo;

	@Column(name = "bank")
	private String bank;

	@Column(name = "utrno")
	private String utrNo;

	@Column(name = "date")
	private String paymentDate;

	@Column(name = "issuedfrom")
	private String issuedFrom;

	@Column(name = "paymentamount")
	private Long paymentAmount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "paymentadviceid")
	private PaymentAdviceVO paymentAdviceVO;
}
