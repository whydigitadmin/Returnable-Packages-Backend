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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "oembinoutward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinOutwardVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "oembinoutwardgen")
	@SequenceGenerator(name = "oembinoutwardgen", sequenceName = "oembinoutwardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "oembinoutwardid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "stockbranch")
	private String stockBranch;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String modifiedby;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "finyr")
	private String finYr;
	@Column(name = "receiverid")
	private Long receiverId;
	@Builder.Default
	private String scode = "OEMBO";
	@Builder.Default
	private String screen = "Bin Outward";
	
	private String retreival;

	@OneToMany(mappedBy = "oemBinOutwardVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OemBinOutwardDetailsVO> oemBinOutwardDetails;

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
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
