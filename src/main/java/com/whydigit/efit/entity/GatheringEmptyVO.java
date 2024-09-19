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
@Table(name="gatheringempty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatheringEmptyVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "gatheringgen")
	@SequenceGenerator(name = "gatheringgen", sequenceName = "gatheringseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatheringemptyid")
	private Long id;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "stockBranch")
	private String stockBranch;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedby;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "finyr")
	private String finyr;
	private String scode = "GEMTY";
	private String screen="Gathering Empty";
	private String retreival;
	
	@Column(name="receiverid")
	private Long receiverId;
	

	@OneToMany(mappedBy = "gatheringEmptyVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<GathereingEmptyDetailsVO> gathereingEmptyDetailsVO;

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
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}

