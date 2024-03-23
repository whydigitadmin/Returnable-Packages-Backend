package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	private long id;
	@Column(name = "docid", length = 25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "orgid")
	private long orgId;
	@Column(name = "customerid")
	private int customerId;
	@Column(name = "whli")
	private long warehouseLocationId;
	@Column(name = "warehouselocation", length = 50)
	private String warehouseLocation;
	private long emitterId;
	@Column(name = "reqaddressid")
	private long reqAddressId;
	@Column(name = "flowid")
	private Long flowTo;
	@Column(name = "flow", length = 25)
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
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedy", length = 25)
	private String modifiedBy;
	private String remark;
	private boolean active;
	private boolean cancel;

	@OneToMany(mappedBy = "issueRequestVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<IssueItemVO> issueItemVO = new ArrayList<>();

}
