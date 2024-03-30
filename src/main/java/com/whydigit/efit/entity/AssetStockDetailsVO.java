package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "stockdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetStockDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stockdetailsgen")
	@SequenceGenerator(name = "stockdetailsgen", sequenceName = "stockdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockdetailsid")
	private Long stockDetailsId;
	private boolean active;
	private boolean cancel;
	@Column(name="cancelremarks",length=25)
	private String cancelRemarks;
	@Column(name="createdby",length=25)
	private String createdBy;
	@Column(name="modifiedby",length=25)
	private String modifiedBy;
	@Column(name="scode",length=5)
	private String sCode="";
	@Column(name="stockbranch",length=10)
	private String stockBranch;
	@Column(name="stocklocation",length=40)
	private String stockLocation;
	@Column(name="stockvalue",precision = 2,scale =4)
	private Float stockValue;
	@Column(name="stockref",length=10)
	private String stockRef;
	@Column(name="stockdate")
	private LocalDate stockDate=LocalDate.now();
	@Column(name="stockdatetime")
	private LocalDateTime stockDateAndTime=LocalDateTime.now();
	@Column(name="skucode",length=15)
	private String skuCode;
	@Column(name="skuqty",length=15)
	private int skuQty;
	@Column(name="sku",length=25)
	private String sku;
	@Column(name="pm",length=10)
	private String pm;
	@Column(name="screen",length=20)
	private String screen;
	@Column(name="stocksource",length=20) 
	private String stockSource;
	@Column(name="binlocation",length=20)
	private String binLocation;
	
	
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
