
package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String assetCodeId;
	private String assetName;
	private String AssetId;
	private boolean active;
	private float length;
	private float breath;
	private float height;
	private String dimUnit;
	private String manufacturer;
	private float weight;
	private String chargableWeight;
	private String Brand;
	private String eanUpc;
	private String assetCategory;
	private String expectedLife;
	private String maintanencePeriod;
	private String expectedTrips;
	private String hsnCode;
	private String taxRate;
	private long skuFrom;
	private long skuTo;
	private String costPrice;
	private String sellPrice;
	private String scrapValue;

	@OneToMany(mappedBy = "assetVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<AssetItemVO> assetItemVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
