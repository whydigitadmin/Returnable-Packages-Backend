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
@Table(name = "paymentadvicebilldetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdviceBillDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "paymentadvicebilldetailsgen")
	@SequenceGenerator(name = "paymentadvicebilldetailsgen", sequenceName = "paymentadvicebilldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentadvicebilldetailsid")
	private Long id;

	@Column(name = "billreference")
	private String billReference;

	@Column(name = "date")
	private String date;

	@Column(name = "amount")
	private Long amount;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "paymentadviceid")
	private PaymentAdviceVO paymentAdviceVO;
}
