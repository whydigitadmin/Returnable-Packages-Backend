package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
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
	@Column(name = "warehouseid")
	private Long warehouseId;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "whlocation")
	private String warehouseLocation;
	@Column(name = "location")
	private String locationName;
	@Column(name = "address")
	private String address;
	@Column(name = "state")
	private String state;
	@Column(name = "pincode")
	private String pincode;
	@Column(name = "unit")
	private String unit;
	@Column(name = "code")
	private String code;
	@Column(name = "city")
	private String city;
	@Column(name = "country")
	private String country;
	@Column(name = "gst")
	private String gst;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelremarks;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String modifiedby;

	@Column(name = "stockbranch")
	private String stockBranch;

	private boolean eflag;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
