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
@Table(name="gatheringemptydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GathereingEmptyDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "gatheringdetailsgen")
	@SequenceGenerator(name = "gatheringdetailsgen", sequenceName = "gatheringdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatheringdetailsid")
	private Long id;
	@Column(name = "assettype")
	private String assetType;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "expqty")
	private int expQty;
	@Column(name = "emptyqty")
	private int emptyQty;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "gatheringemptyid")
	private GatheringEmptyVO gatheringEmptyVO;
}
