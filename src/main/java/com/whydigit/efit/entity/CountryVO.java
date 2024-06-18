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
@Table(name = "country")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countryseqgen")
    @SequenceGenerator(name = "countryseqgen", sequenceName = "seqname", initialValue = 1000000001, allocationSize = 1)
	@Column(name="countryid")
	private Long id;
	
	@Column(name="code")
	private String countryCode;
	
	@Column(name="country")
	private String country;
	
	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;

	@Column(name = "cancelremarks")
	private String cancelRemarks;
	
	@Column(name="orgid")
	private Long orgId;
	
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
