package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "oembininward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinInwardVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "oembininwardgen")
	@SequenceGenerator(name = "oembininwardgen", sequenceName = "oembininwardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "oembininwardid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "kitno")
	private String kitNo;
	@Column(name = "recievedkitqty")
	private int recievedKitQty;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String modifiedby;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "finyr")
	private String finyr;
	@Builder.Default
	private String scode = "OEMBI";
	@Builder.Default
	private String screen = "Bin Inward";
	@Column(name = "outwarddocid")
	private String outwardDocId;
	

	@OneToMany(mappedBy = "oemBinInwardVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OemBinInwardDetailsVO> oemBinInwardDetails;

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
