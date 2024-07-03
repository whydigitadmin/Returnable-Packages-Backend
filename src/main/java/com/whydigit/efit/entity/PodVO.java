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
	@Column(name = "scode")
	private String sCode = "POD";
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "kitcode")
	private String kitCode;
	@Column(name = "kitqty")
	private int kitQty;
	@Column(name = "kitrqty")
	private int kitRqty;
	@Column(name = "stockbranchfrom")
	private String stockBranchFrom;
	@Column(name = "stockbranchto")
	private String stockBranchTo;
	private boolean cancel;
	private boolean active;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
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
