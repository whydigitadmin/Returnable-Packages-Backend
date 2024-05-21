package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="taggingdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTaggingDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taggingdetailsgen")
	@SequenceGenerator(name = "taggingdetailsgen", sequenceName = "taggingdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="taggingdetailsid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Column(name="rfid",unique = true)
	private String rfId;
	
	
	@Column(name="taggingdocid")
	private String taggingDocDd;


	@Column(name = "assetcode")
	private String assetCode;
	
	@Column(name = "asset")
	private String asset;
	
	
	@Column(name = "tagcode",unique = true)
	private String tagCode;
	
	private String category;

	@ManyToOne
	@JoinColumn(name = "taggingid")
	@JsonBackReference
	private AssetTaggingVO taggingVO;
}
