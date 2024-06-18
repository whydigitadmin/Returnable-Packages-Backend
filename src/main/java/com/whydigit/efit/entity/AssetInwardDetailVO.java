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
@Table(name = "assetinwarddetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetInwardDetailVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "assetinwarddetailgen")
	@SequenceGenerator(name = "assetinwarddetailgen", sequenceName = "assetinwarddetailseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name ="assetinwarddetailid")
	private long assetInwardDetailId;
	
	
	@Column(name="skudetail",length=40)
	private String skuDetail;
	@Column(name="skucode",length=20)
	private String skucode;
	@Column(name="skuqty",length=15)
	private int skuQty;
	@Column(name="stockvalue",precision = 2,scale =4)
	private Float stockValue;
	@Column(name="binlocation",length=20)
	private String binLocation;
	@Column(name="stocklocation",length=40)
	private String stockLocation;
	@Column(name="tagcode",length=40)
	private String tagCode;
	@Column(name="rfid",length=100)
	private String rfId;
	
	@ManyToOne
    @JoinColumn(name ="assetinwardid")
	@JsonBackReference
	private AssetInwardVO assetInwardVO;
	
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
}
