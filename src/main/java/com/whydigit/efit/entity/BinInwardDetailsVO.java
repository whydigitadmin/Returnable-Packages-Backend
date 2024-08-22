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
@Table(name = "bininwarddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinInwardDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bininwarddetailsgen")
	@SequenceGenerator(name = "bininwarddetailsgen", sequenceName = "bininwarddetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bininwarddetailsid")
	private Long binInwardDetailsId;
	@Column(name = "tagcode")
	private String tagCode;
	@Column(name = "asset")
	private String asset;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "allotqty")
	private int allotQty;
	@Column(name = "recqty")
	private int recQty;
	@Column(name = "rfid")
	private String rfId;              
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "bininwardid")
	private BinInwardVO binInwardVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
