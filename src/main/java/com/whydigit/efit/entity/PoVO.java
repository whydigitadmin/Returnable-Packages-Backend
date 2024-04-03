package com.whydigit.efit.entity;

import java.time.LocalDate;
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
@Table(name = "po")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pogen")
	@SequenceGenerator(name = "pogen", sequenceName = "poseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "poid")
	private Long poId;
	@Column(name = "scode", length = 10)
	private String sCode;
	@Column(name="orgid")
	private Long orgId;
	@Column(name = "company", length = 50)
	private String company;
	@Column(name = "address", length = 200)
	private String address;
	@Column(name = "selfgst", length = 25)
	private String selfGst;
	@Column(name = "pono", length = 25)
	private String poNo;
	private LocalDate date;
	@Column(name = "apid", length = 20)
	private String apId;
	@Column(name = "apaddress", length = 200)
	private String apAddress;
	@Column(name = "apgst", length = 23)
	private String apGst;
	@Column(name = "shipto", length = 100)
	private String shipTo;
	@Column(name = "shiptoremarks", length = 100)
	private String shipToRemarks;
	@Column(name = "gsttype", length = 25)
	private String gstType;
	@Column(name = "igst", precision = 2, scale = 1)
	private Float iGst;
	@Column(name = "cgst", precision = 2, scale = 1)
	private Float cGst;
	@Column(name = "sgst", precision = 2, scale = 1)
	private Float sGst;
	@Column(name = "terms", length = 1000)
	private String terms;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modified", length = 25)
	private String modifiedBy;
	private boolean cancel;
	private boolean active;
	
	
	@OneToMany(mappedBy ="poVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<PoVO1> poVO1;
	
	
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
