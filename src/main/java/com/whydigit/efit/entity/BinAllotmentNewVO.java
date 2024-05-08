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
@Table(name = "binallotment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinAllotmentNewVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "binallotmentseq")
	@SequenceGenerator(name = "binallotmentseq", sequenceName = "binallotmentgen", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "binallotmentid")
	private Long id;

	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;

	private boolean cancel;

	@Column(name = "cancelremarks", length = 50)
	private String cancelRemarks;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "scode", length = 10)
	private String scode = "BNALT";

	@Column(name = "docid", length = 50)
	private String docId;

	@Column(name = "docdate")
	private LocalDate docDate;

	@Column(name = "binreqno", length = 50)
	private String binReqNo;

	@Column(name = "binreqdate")
	private LocalDate binReqDate;

	@Column(name = "emitterid")
	private Long emitterId;

	@Column(name = "emitter", length = 100)
	private String emitter;

	@Column(name = "part", length = 50)
	private String partName;

	@Column(name = "partcode", length = 25)
	private String partCode;

	@Column(name = "kitcode", length = 25)
	private String kitCode;

	@Column(name = "reqkitqty", length = 25)
	private int reqKitQty;

	@Column(name = "avlqty", length = 25)
	private int avlKitQty;

	@Column(name = "allotkitqty", length = 25)
	private int allotkKitQty;
	
	private String finyr;

	@Column(name = "stockbranch", length = 50)
	private String stockBranch;
	
	@Column(name = "flow", length =100)
	private String flow;

	@Column(name = "flowId")
	private Long flowId;
	
	
	@OneToMany(mappedBy = "binAllotmentNewVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<BinAllotmentDetailsVO> binAllotmentDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

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
