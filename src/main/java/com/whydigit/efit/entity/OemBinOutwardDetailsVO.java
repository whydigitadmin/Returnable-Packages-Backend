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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "oembinoutwarddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinOutwardDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "oembinoutwarddetailsgen")
	@SequenceGenerator(name = "oembinoutwarddetailsgen", sequenceName = "oembinoutwarddetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "oembinoutwarddetailsid")
	private Long id;
	@Column(name = "assetname")
	private String asset;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "category")
	private String category;
	@Column(name = "availqty")
	private int availqty;
	@Column(name = "outqty")
	private int outQty;

	@ManyToOne
	@JoinColumn(name = "oembinoutwardid")
	@JsonBackReference
	private OemBinOutwardVO oemBinOutwardVO;
}
