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
@Table(name = "binoutwarddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinOutwardDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "binoutwarddetailsid")
	private Long id;
	@Column(name = "asset")
	private String asset;
	@Column(name = "assetcode")
	private String assetcode;
	@Column(name = "qty")
	private int qty;

	@ManyToOne
	@JoinColumn(name = "binoutwardid")
	@JsonBackReference
	private BinOutwardVO binoutwardVO;
}
