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
@Table(name="tagging")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTaggingVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tagginggen")
	@SequenceGenerator(name = "tagginggen", sequenceName = "taggingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="taggingid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	private String scode="ASTAG";
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="docid",length = 25)
	private String docid;
	
	@Column(name="docdate")
	private LocalDate docDate;
	
	@Column(name="cancel")
	private boolean cancel;
	
	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby",length = 25)
	private String modifiedBy;
	
	@Column(name = "cancelremarks",length = 25)
	private String cancelRemarks;

	@Column(name = "assetcode",length = 25)
	private String assetCode;
	
	@Column(name = "asset",length = 25)
	private String asset;
	
	@Column(name = "seqfrom",length = 4)
	private int seqFrom;
	
	@Column(name = "seqto",length = 4)
	private int seqTo;
	
	@OneToMany(mappedBy = "taggingVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<AssetTaggingDetailsVO> taggingDetails;
	
	

	@Embedded
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();

}
