package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.whydigit.efit.dto.IssueRequestType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issuerequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "issuerequestgen")
	@SequenceGenerator(name = "issuerequestgen", sequenceName = "issuerequestseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "issuerequestid")
	private Long id;
	private String scode = "BNREQ";
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "orgid")
	private long orgId;
	@Column(name = "customerid")
	private Long customerId;
	@Column(name = "whlocationid")
	private long warehouseLocationId;
	@Column(name = "warehouselocation")
	private String warehouseLocation;
	@Column(name = "emitterid")
	private long emitterId;
	@Column(name = "customercode")
	private String emitterCode;
	@Column(name = "reqaddressid")
	private long reqAddressId;
	@Column(name = "flowid")
	private Long flowTo;
	@Column(name = "flow")
	private String flowName;
	@Column(name = "totalissueitem")
	private int totalIssueItem;
	@Column(name = "tat")
	private long tat;
	@Column(name = "irtype")
	private IssueRequestType irType;
	@Column(name = "requesteddate")
	private LocalDateTime requestedDate;
	@Column(name = "demanddate")
	private LocalDate demandDate;
	@Column(name = "issuestatus")
	private int issueStatus;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedy")
	private String modifiedBy;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	private String remark;
	private boolean active;
	private boolean cancel;
	private String finyr;
	private String emitter;

	@OneToMany(mappedBy = "issueRequestVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<IssueItemVO> issueItemVO = new ArrayList<>();

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
