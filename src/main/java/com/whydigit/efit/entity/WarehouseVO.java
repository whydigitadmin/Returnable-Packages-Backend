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
@Table(name="warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int warehouseCode;
	private long orgId;
	private String warehouseName;
	private String storageMapping;
	private String country;
	private String state;
	private String city;
	private String pincode;
	private String address;
	private String gst;
	private String locationType;
	private String warehouseLocation;
	private String document;
	private boolean active;
		
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	

}
