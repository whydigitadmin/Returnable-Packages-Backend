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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customerseqgen")
	@SequenceGenerator(name = "customerseqgen", sequenceName = "customerseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customerid")
	private Long id;

	@Column(name = "orgid")
	private long orgId;

	@Column(name = "type")
	private int customerType;

	@Column(name = "legalname", length = 100)
	private String entityLegalName;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "code", length = 25)
	private String customerCode;

	@Column(name = "displayname", length = 50)
	private String displayName;

	@Column(name = "phone", length = 25)
	private String phoneNumber;

	@Column(name = "activateportal", length = 25)
	private boolean customerActivatePortal;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;

	@Column(name = "sop")
	private String sop;
	
	@Column(name = "document")
	private String document;

	@OneToMany(mappedBy = "customersVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<CustomersAddressVO> customersAddressVO;

	@OneToMany(mappedBy = "customersVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<CustomersBankDetailsVO> customersBankDetailsVO;
	
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
