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
@Table(name = "warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long warehouseId;
	private long orgId;
	private String warehouseLocation;
	private String locationName;
	private String name;
	private String address;
	private String state;
	private String pincode;
	private String unit;
	private long code;
	private String city;
	private String country;
	private String gst;
	private boolean active;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
