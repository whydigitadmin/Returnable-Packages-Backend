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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="dispatch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dispatchgen")
	@SequenceGenerator(name = "dispatchgen", sequenceName = "dispatchseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dispatchid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name = "flow")
	private String flow;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "dispatchremarks")
	private String dispatchRemarks;
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
	private String finyr;
	private String scode = "DISPH";
	@Column(name="emitterid")
	private Long emitterId;	

	@OneToMany(mappedBy = "dispatchVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DispatchDetailsVO> dispatchDetailsVO;

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
