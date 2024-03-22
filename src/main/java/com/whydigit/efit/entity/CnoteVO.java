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
	@Column(name = "scode", length = 5)
	private String scode = "CNOTE";
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;
	@Column(name = "cancelremarks", length = 25)
	private String cancelRemarks;
	@Column(name = "code", length = 10)
	private String code;
	@Column(name = "description", length = 40)
	private String description;
	@Column(name = "servicecode", length = 2)
	private String serviceCode;
	@Column(name = "rcode", length = 10)
	private String rcode;
	@Column(name = "ccode", length = 10)
	private String ccode;
	@Column(name = "rledger", length = 25)
	private String rlEdger;
	@Column(name = "cledger", length = 25)
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
