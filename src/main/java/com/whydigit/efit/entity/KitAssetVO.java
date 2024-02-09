package com.whydigit.efit.entity;

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

@Entity
@Table(name = "kit_asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitAssetVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String assetCategory;
	private long assetCodeId;
	private String assetName;
	private String quantity;
	@ManyToOne
	@JoinColumn(name = "kit_id")
	@JsonBackReference
	private KitVO kitVO;

}
