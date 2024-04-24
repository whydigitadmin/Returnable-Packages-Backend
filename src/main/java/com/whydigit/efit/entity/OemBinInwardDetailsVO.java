package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "oembinwarddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinInwardDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oembinoutwarddetailsid")
	private Long id;
	@Column(name = "asset")
	private String asset;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "recievedqty")
	private int recievedQty;

	@ManyToOne
	@JoinColumn(name = "oembininwardid")
	@JsonBackReference
	private OemBinInwardVO oemBinInwardVO;
}