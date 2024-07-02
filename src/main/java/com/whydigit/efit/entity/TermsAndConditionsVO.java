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
@Table(name = "terms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsAndConditionsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "termsgen")
	@SequenceGenerator(name = "termsgen", sequenceName = "termsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "termsid")
	private Long termsId;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "scode")
	private String sCode;
	@Column(name = "termscode")
	private String termsCode;
	@Column(name = "details")
	private String details;
	@Column(name = "printremarks")
	private String printRemarks;
	@Column(name = "effectivefrom")
	private LocalDate effectiveFrom;
	@Column(name = "effectiveto")
	private LocalDate effectiveTo;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiebBy")
	private String modifiedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	private boolean cancel;
	private boolean active;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
