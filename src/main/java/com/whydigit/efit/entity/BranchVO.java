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
@Table(name = "branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "branchgen")
	@SequenceGenerator(name = "branchgen", sequenceName = "branchseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "branchid")
	private Long id;
	@Column(name = "branch")
	private String branchName;
	@Column(name = "code")
	private String branchCode;
	@Column(name = "address1")
	private String address1;
	@Column(name = "address2")
	private String address2;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "pincode")
	private String pinCode;
	@Column(name = "phone")
	private String phone;
	@Column(name = "gst")
	private String GST;
	@Column(name = "pan")
	private String pan;

	private boolean eflag;

	@Column(name = "currency")
	private String currency;
	private boolean active;
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;
	@Column(name = "scode")
	private String sCode = "BRANC";

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
