package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pod")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PodVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "podgen")
	@SequenceGenerator(name = "podgen", sequenceName = "podseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "podid")
	private Long podId;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "scode", length = 10)
	private String sCode = "POD";
	@Column(name = "docid", length = 30)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate; 
	@Column(name = "refno", length = 30)
	private String refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "kitcode", length = 30)
	private String kitCode;
	@Column(name = "kitqty", length = 10)
	private int kitQty;
	@Column(name = "kitrqty", length = 10)
	private int kitRqty;
	@Column(name = "stockbranchfrom", length = 10)
	private String stockBranchFrom;
	@Column(name = "stockbranchto", length = 10)
	private String stockBranchTo;
	private boolean cancel;
	private boolean active;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;

	@OneToMany(mappedBy = "podVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Pod1VO> pod1VO;

	@OneToMany(mappedBy = "podVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Pod2VO> pod2VO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
