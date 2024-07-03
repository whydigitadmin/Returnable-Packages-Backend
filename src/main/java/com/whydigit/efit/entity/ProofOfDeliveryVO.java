package com.whydigit.efit.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proofofdelivery")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProofOfDeliveryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "proofofdeliverygen")
	@SequenceGenerator(name = "proofofdeliverygen", sequenceName = "proofofdeliveryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "proofofdeliveryid")
	private long ProofOfDeliveryId;
	@Column(name = "orgid")
	private long orgId;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "rfno")
	private String rfNo;
	@Column(name = "rfdate")
	private LocalDate rfDate;
	@Column(name = "kitcode")
	private String kitCode;
	@Column(name = "kitqty")
	private int kitQty;
	@Column(name = "kitrqty")
	private int kitRQty;
	@Column(name = "uploadreceipt")
	private String uploadReceipt;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
