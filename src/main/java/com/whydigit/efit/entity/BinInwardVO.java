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
@Table(name = "bininward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinInwardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bininwardgen")
	@SequenceGenerator(name = "bininwardgen", sequenceName = "bininwardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "bininwardid")
	private Long binInwardId;

	@Column(name = "orgid")
	private Long orgId;

	private String scode = "BNINW";

	@Column(name = "active")
	private boolean active;

	@Column(name = "docid", length = 25)
	private String docid;

	@Column(name = "docdate")
	private LocalDate docDate;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;

	@Column(name = "cancelremarks", length = 25)
	private String cancelRemarks;
	@Column(name = "allotmentno", length = 25)
	private String allotmentNo;
	@Column(name = "reqno", length = 25)
	private String reqNo;
	@Column(name = "flow", length = 120)
	private String flow;
	@Column(name = "finyr", length = 25)
	private String finYr;
	@Column(name = "kitcode", length = 25)
	private String kitCode;
	@Column(name = "allotedqty", length = 25)
	private int allotedQty;
	@Column(name = "emitterid", length = 25)
	private Long emitterId;
	@Column(name = "allotdate")
	private LocalDate allotDate;
	@Column(name = "binreqdate")
	private LocalDate binReqDate;
	
	@Column(name = "reqkitqty")
	private int reqKitQty;

	@OneToMany(mappedBy = "binInwardVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<BinInwardDetailsVO> binInwardDetailsVO;

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
