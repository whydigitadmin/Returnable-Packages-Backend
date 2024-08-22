package com.whydigit.efit.entity;

import java.time.LocalDate;

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
@Table(name = "cnote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnoteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cnoteegen")
	@SequenceGenerator(name = "cnoteegen", sequenceName = "cnoteseqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "cnoteid")
	private Long cnoteId;
	@Column(name = "scode")
	private String scode = "CNOTE";
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "code")
	private String code;
	@Column(name = "description")
	private String description;
	@Column(name = "servicecode")
	private String serviceCode;
	@Column(name = "rcode")
	private String rcode;
	@Column(name = "ccode")
	private String ccode;
	@Column(name = "rledger")
	private String rlEdger;
	@Column(name = "cledger")
	private String clEdger;
	@Column(name = "fromdate")
	private LocalDate fromDate = LocalDate.now();
	@Column(name = "todate")
	private LocalDate toDate = LocalDate.now();
	@Column(name = "", precision = 2, scale = 2)
	private float gst;
	private boolean gstapplicable;
	private boolean cancel;
	private boolean active;
	private boolean block;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
