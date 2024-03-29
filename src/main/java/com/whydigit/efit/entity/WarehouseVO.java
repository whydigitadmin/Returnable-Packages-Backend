package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "warehousegen")
	@SequenceGenerator(name = "warehousegen", sequenceName = "warehousegen", initialValue = 1000000001, allocationSize = 1)
	@Column(name="warehouseid")
	private Long warehouseId;
	@Column(name="orgid")
	private Long orgId;
	@Column(name="whlocation",length = 30)
	private String warehouseLocation;
	@Column(name="location",length = 30)
	private String locationName;
	@Column(name="address",length = 100)
	private String address;
	@Column(name="state",length = 25)
	private String state;
	@Column(name="pincode",length = 10)
	private String pincode;
	@Column(name="unit",length = 30)
	private String unit;
	@Column(name="code",length = 30)
	private String code;
	@Column(name="city",length = 30)
	private String city;
	@Column(name="country",length = 30)
	private String country;
	@Column(name="gst",length = 30)
	private String gst;
	@Column(name="active",length = 30)
	private boolean active;
	@Column(name="cancel",length = 30)
	private boolean cancel;
	@Column(name="cancelremarks",length = 30)
	private String cancelremarks;
	@Column(name="createdby",length = 30)
	private String createdby;
	@Column(name="modifiedby",length = 30)
	private String modifiedby;
	
	@Column(name="stockbranch",length = 30)
	private String stockBranch;
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
