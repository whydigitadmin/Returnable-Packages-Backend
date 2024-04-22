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
@Table(name = "assetinward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetInwardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "assetinwardgen")
	@SequenceGenerator(name = "assetinwardgen", sequenceName = "assetinwardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "assetinwardid")
	private long assetInwardId;
	@Column(name = "scode", length = 10)
	private String sCode = "AINWD";
	@Column(name = "docid", length = 10)
	private String docId;
	@Column(name = "docdate")    
	private LocalDate docDate = LocalDate.now();
	@Column(name="sourcefrom",length = 25)
	private String sourceFrom;
	@Column(name = "stockbranch", length = 40)
	private String stockBranch;
	private boolean cancel;
	@Column(name = "cancelremarks", length = 40)
	private String cancelRemarks;
	@Column(name = "createdby", length = 40)
	private String createdBy;
	@Column(name = "modifiedby", length = 40)
	private String modifiedBy;
	@Column(name="orgid")
	private Long orgId;
	
	
	
	@OneToMany(mappedBy ="assetInwardVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<AssetInwardDetailVO> assetInwardDetailVO;
	
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
