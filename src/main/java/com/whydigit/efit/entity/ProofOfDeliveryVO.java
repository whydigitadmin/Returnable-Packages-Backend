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
	@Column(name = "orgid",length = 15)
	private long orgId;
	@Column(name = "docid",length = 15)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "rfno",length =25 )
	private String rfNo;
	@Column(name = "rfdate")
	private LocalDate rfDate;
	@Column(name = "kitcode",length =25)
	private String kitCode;
	@Column(name = "kitqty",length =15)
	private int kitQty;
	@Column(name = "kitrqty",length =15)
	private int kitRQty;
	@Column(name = "uploadreceipt",length =240 )
	private String uploadReceipt;
	@Column(name = "createdby",length =25 )
	private String createdBy;
	@Column(name = "modifiedby",length =25 )
	private String modifiedBy;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


	
	
}
