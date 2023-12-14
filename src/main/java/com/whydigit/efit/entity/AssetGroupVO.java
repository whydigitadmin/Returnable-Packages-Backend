package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="asset_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetGroupVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String sku;
	private String productUnit;
	private boolean active;
	private String associateItem;
	private String assQuantity;
	private float length;
	private float breath;
	private float height;
	private String dimUnit;
	private float weight;
	private String weightUnit;
	private String assetCategory;
	private String expectedLife;
	private String maintanencePeriod;
	private String expectedTrips;
	private String hsnCode;
	private String taxRate;
	private String costPrice;
	private String sellPrice;
	private String scrapValue;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
