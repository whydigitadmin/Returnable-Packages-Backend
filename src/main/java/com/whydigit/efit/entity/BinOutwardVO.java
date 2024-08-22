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
@Table(name = "binoutward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinOutwardVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "binoutwardgen")
	@SequenceGenerator(name = "binoutwardgen", sequenceName = "binoutwardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "binoutwardid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "flowid")
	private Long flowId;
	@Column(name = "flow")
	private String flow;
	@Column(name = "kitno")
	private String kitNo;
	@Column(name = "outwardkitqty")
	private int outwardKitQty;
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
	private String finyr;
	private String scode = "BNOUT";
	@Column(name="emitterid")
	private Long emitterId;
	private String destination;
	private String receiver;
	private String orgin;
	private String emitter;
	@Column(name ="partcode")
	private String partCode;
	@Column(name ="partname")
	private String partName;
	
	private String invoiceno;
	

	@OneToMany(mappedBy = "binOutwardVO", cascade = CascadeType.ALL)
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
