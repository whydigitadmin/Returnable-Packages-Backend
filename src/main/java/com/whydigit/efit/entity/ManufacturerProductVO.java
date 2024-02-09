
package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manufacturer_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerProductVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
//	private String assetCodeId;
	private String assetCategory;
	private String assetName;
	private String brand;
	private String warranty;
	private String sellingPrice;
	private String leadTime;
	private String maintananceFrequency;
	private String notes;
	private boolean active;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="manufacturer_id")
	private ManufacturerVO manufacturerVO;
	
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
