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

@Entity
@Table(name="transportpickup")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportPickupVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transportpickupgen")
	@SequenceGenerator(name = "transportpickupgen", sequenceName = "transportpickupseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "transportpickupid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "fromstockbranch")
	private String fromStockBranch;
	@Column(name = "tostockbranch")
	private String toStockBranch;
	@Column(name = "transportdocno")
	private String transPortDocNo;
	@Column(name = "transporterid")
	private Long transPorterId;
	@Column(name = "transporter")
	private String transPorter;
	@Column(name = "handoverby")
	private String handoverby;
	@Column(name = "drivername")
	private String driverName;
	@Column(name = "driverphoneno")
	private String driverPhoneNo;
	@Column(name = "vechicleno")
	private String vechicleNo;
	@Column(name = "rmno")
	private String rmNo;
	@Column(name = "rmdate")
	private LocalDate rmDate;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String modifiedby;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "finyr")
	private String finYr;
	@Column(name = "receiverid")
	private Long receiverId;
	private String scode = "PIKUP";
	private String screen = "Pickup";
	
	@OneToMany(mappedBy = "pickupVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<TransportPickupDetailsVO> transportPickupDetailsVO;
	
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
