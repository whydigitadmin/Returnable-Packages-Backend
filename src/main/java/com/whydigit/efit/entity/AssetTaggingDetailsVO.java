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
	
	@Column(name="rfid",length = 50,unique = true)
	private String rfId;
	
	
	@Column(name="taggingdocid",length = 25)
	private String taggingDocDd;


	@Column(name = "assetcode",length = 25)
	private String assetCode;
	
	@Column(name = "asset",length = 25)
	private String asset;
	
	
	@Column(name = "tagcode",length = 30,unique = true)
	private String tagCode;

	@ManyToOne
	@JoinColumn(name = "taggingid")
	@JsonBackReference
	private AssetTaggingVO taggingVO;
}
