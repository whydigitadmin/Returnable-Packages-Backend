package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "binretrieval")
public class BinRetrievalVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "binretrievalgen")
	@SequenceGenerator(name = "binretrievalgen", sequenceName = "binretrievalseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "binretrievalid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "retrievalwarehouse")
	private String retrievalWarehouse;
	@Column(name = "fromstockbranch")
	private String fromStockBranch;
	@Column(name = "tostockbranch")
	private String toStockBranch;
	@Column(name = "transportdocno")
	private String transPortDocNo;
	@Column(name = "transporter")
	private String transPorter;
	@Column(name = "drivername")
	private String driverName;
	@Column(name = "handoverby")
	private String handOverBy;
	@Column(name = "driverphoneno")
	private String driverPhoneNo;
	@Column(name = "vechicleno")
	private String vechicleNo;
	@Column(name = "pickupdocid")
	private String pickupDocId;
	@Column(name = "pickupdate")
	private LocalDate pickupDate;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String modifiedby;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "finyr")
	private String finYr;
	private String scode = "BNRET";
	private String screen = "Bin Retrieval";

	@OneToMany(mappedBy = "binRetrievalVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<BinRetrievalDetailsVO> binRetrievalDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@PrePersist
	private void setDefaultFinyr() {
		// Execute the logic to set the default value for finyr
		String fyFull = calculateFinyr();
		this.finYr = fyFull;
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
