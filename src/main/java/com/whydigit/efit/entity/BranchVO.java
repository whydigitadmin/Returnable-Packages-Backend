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
    private String orgId;
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
	@Column(name = "currency")
	private String currency;
    private boolean active;
	private boolean cancel;
	@Column(name="cancelremarks",length=25)
	private String cancelRemarks;
	@Column(name="createdby",length=25)
	private String createdBy;
	@Column(name="modifiedby",length=25)
	private String modifiedBy;
	@Column(name="scode",length=5)
	private String sCode="BRANC";
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
