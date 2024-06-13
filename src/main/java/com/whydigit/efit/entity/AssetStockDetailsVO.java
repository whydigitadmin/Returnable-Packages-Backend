package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
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
	@Column(name="cancelremarks")
	private String cancelRemarks;
	@Column(name="createdby")
	private String createdBy;
	@Column(name="modifiedby")
	private String modifiedBy;
	@Column(name="scode")
	private String sCode="";
	@Column(name="stockbranch")
	private String stockBranch;
	@Column(name="stocklocation")
	private String stockLocation;
	@Column(name="stockvalue")
	private Float stockValue;
	@Column(name="stockref")
	private String stockRef;
	@Column(name="stockdate")
	private LocalDate stockDate;
	@Column(name="stockdatetime")
	private LocalDateTime stockDateAndTime=LocalDateTime.now();
	@Column(name="skucode")
	private String skuCode;
	@Column(name="skuqty")
	private int skuQty;
	@Column(name="sku")
	private String sku;
	@Column(name="pm")
	private String pm;
	@Column(name="screen")
	private String screen;
	@Column(name="tagcode")
	private String tagCode;
	@Column(name="stocksource") 
	private String stockSource;
	@Column(name="binlocation")
	private String binLocation;
	@Column(name="rfid")
	private String rfId;
	@Column(name="sourceid")
	private Long sourceId;
	@Column(name="finyr")
	private String finyr;
	@Column (name = "orgid")
	private long orgId;
	@Column(name="status")
	private String status;
	private String category;
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	

	@PrePersist
	private void setDefaultFinyr() {
		// Execute the logic to set the default value for finyr
		String fyFull = calculateFinyr();
		this.finyr = fyFull;
	}

	private String calculateFinyr() {
		// Logic to calculate finyr based on the provided SQL query
		String currentMonthDay = LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"));
		String fyFull = (currentMonthDay.compareTo("0331") > 0)
				? LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"))
				: LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy"));
		return fyFull;
	}

}
