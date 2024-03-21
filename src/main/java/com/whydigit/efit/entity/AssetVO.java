
package com.whydigit.efit.entity;

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
@Table(name = "asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "assetgen")
	@SequenceGenerator(name = "assetgen", sequenceName = "assetseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="assetid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Column(name="assetcode",length = 25)
	private String assetCodeId;
	
	@Column(name="asset",length = 25)
	private String assetName;
		
	@Column(name="length",length = 5)
	private float length;
	
	@Column(name="breath",length = 5)
	private float breath;
	
	@Column(name="height",length = 5)
	private float height;
	
	@Column(name="weight",length = 5)
	private float weight;
	
	@Column(name="quantity",length = 20)
	private int quantity;   
	
	@Column(name="dimunit",length = 25)
	private String dimUnit;
	
	@Column(name="manufacturer",length = 25)
	private String manufacturer;
	
	@Column(name="chargableweight",length = 25)
	private String chargableWeight;
	
	@Column(name="brand",length = 25)
	private String brand;
	
	@Column(name="eanupc",length = 25)
	private String eanUpc;
	
	@Column(name="category",length = 25)
	private String assetCategory;
	
	@Column(name="expectedlife",length = 25)
	private String expectedLife;
	
	@Column(name="maintanenceperiod",length = 25)
	private String maintanencePeriod;
	
	@Column(name="expectedtrips",length = 25)
	private String expectedTrips;
	
	@Column(name="hsncode",length = 25)
	private String hsnCode;
	
	@Column(name="taxrate",length = 25)
	private String taxRate;
	
	@Column(name="skufrom",length = 25)
	private long skuFrom;
	
	@Column(name="skuto",length = 25)
	private long skuTo;
	
	@Column(name="costprice",length = 25)
	private String costPrice;
	
	@Column(name="sellprice",length = 25)
	private String sellPrice;
	
	@Column(name="scrapvalue",length = 25)
	private String scrapValue;
	
	private boolean cancel;
	
	@Column(name="createdby",length = 25)
	private String createdby;
	
	@Column(name="modifiedby",length = 25)
	private String modifiedby;
	
	@Column(name="cancelremarks",length = 50)
	private String cancelremarks;
	
	private boolean active;
	

	@OneToMany(mappedBy = "assetVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<AssetItemVO> assetItemVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
