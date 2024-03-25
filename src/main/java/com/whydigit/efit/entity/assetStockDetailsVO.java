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
public class assetStockDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stockdetailsgen")
	@SequenceGenerator(name = "stockdetailsgen", sequenceName = "stockdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockdetailsid")
	private String stockDetailsId;
	private boolean active;
	private boolean cancel;
	@Column(name="cancelremarks",length=25)
	private String cancelRemarks;
	@Column(name="createdby",length=25)
	private String createdBy;
	@Column(name="modifiedby",length=25)
	private String modifiedBy;
	@Column(name="scode",length=5)
	private String sCode;
	@Column(name="stockbranch",length=10)
	private String stockBranch;
	@Column(name="stocklocation",length=40)
	private String stockLocation;
	@Column(name="stock",length=25)
	private String stock;
	@Column(name="stockid",length=2)
	private String stockId;
	@Column(name="stockdate")
	private LocalDate stockDate=LocalDate.now();
	@Column(name="stockDateAndTime")
	private LocalDateTime stockDateAndTime=LocalDateTime.now();
	@Column(name="skuCode",length=15)
	private String skuCode;
	@Column(name="skudetails",length=40)
	private String skuDetails;
	@Column(name="skyqty",length=15)
	private int skyQty;
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
