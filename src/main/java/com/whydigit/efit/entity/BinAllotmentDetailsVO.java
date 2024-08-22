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
@Table(name="binallotment1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinAllotmentDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "binallotment1seq")
	@SequenceGenerator(name = "binallotment1seq", sequenceName = "binallotment1gen", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "binallotment1id")
	private Long id;
	
	@Column(name = "tagcode")
	private String tagCode;
	
	@Column(name = "rfid")
	private String rfId;
	
	@Column(name = "asset")
	private String asset;
	
	@Column(name = "assetcode")
	private String assetCode;
	
	@Column(name = "skuqty")
	private int qty;
	
	@ManyToOne
	@JoinColumn(name = "binallotmentid")
	@JsonBackReference
	private BinAllotmentNewVO binAllotmentNewVO;

}
