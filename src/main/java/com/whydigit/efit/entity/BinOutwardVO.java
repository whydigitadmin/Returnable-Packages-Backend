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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "binoutward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinOutwardVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "binoutwardid")
	private Long id;
	@Column(name = "docid")
	private String docid;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "flow")
	private String flow;
	@Column(name = "kit")
	private String kit;
	@Column(name = "outwardkitqty")
	private int outwardkitqty;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedy")
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
	private String finyr;
	@Builder.Default
	private String scode = "BNOUT";
	@Builder.Default
	private String pm = "M";
	@Builder.Default
	private String screen = "BIN OUTWARD";
	private String sourceid;

	@OneToMany(mappedBy = "binoutwardVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<BinOutwardDetailsVO> binOutwardDetails;

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
