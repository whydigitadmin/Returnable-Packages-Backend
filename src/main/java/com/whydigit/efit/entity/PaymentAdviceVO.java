package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paymentadvice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdviceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "paymentadvicegen")
	@SequenceGenerator(name = "paymentadvicegen", sequenceName = "paymentadviceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "paymentadviceid")
	private Long id;
	
	@Column(name = "companyaddress", columnDefinition = "TEXT")
	private String companyAddress;
	
	@Column(name = "billername", columnDefinition = "TEXT")
	private String billerName;
	
	@Column(name = "billeraddress", columnDefinition = "TEXT")
	private String billerAddress;
	
	@Column(name = "date", columnDefinition = "TEXT")
	private String date;
	
	@Column(name = "paymentmode", columnDefinition = "TEXT")
	private String paymentMode;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "createdby", columnDefinition = "TEXT")
	private String createdBy;
	
	@Column(name = "modifiedby", columnDefinition = "TEXT")
	private String modifiedBy;
	
	@Column(name = "amount")
	private Long amount;
	
	@Column(name = "cancel")
	private boolean cancel = false;
	
	@OneToMany(mappedBy = "paymentAdviceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PaymentAdviceBillDetailsVO> paymentAdviceBillDetailsVO;
	
	@OneToMany(mappedBy = "paymentAdviceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PaymentAdviceDetailsVO> paymentAdviceDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
