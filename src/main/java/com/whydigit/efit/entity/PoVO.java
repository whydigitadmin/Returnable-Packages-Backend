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
	@Column(name = "scode")
	private String sCode;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "company")
	private String company;
	@Column(name = "address")
	private String address;
	@Column(name = "selfgst")
	private String selfGst;
	@Column(name = "pono")
	private String poNo;
	private LocalDate date;
	@Column(name = "apid")
	private String apId;
	@Column(name = "apaddress")
	private String apAddress;
	@Column(name = "apgst")
	private String apGst;
	@Column(name = "shipto")
	private String shipTo;
	@Column(name = "shiptoremarks")
	private String shipToRemarks;
	@Column(name = "gsttype")
	private String gstType;
	@Column(name = "igst", precision = 2, scale = 1)
	private Float iGst;
	@Column(name = "cgst", precision = 2, scale = 1)
	private Float cGst;
	@Column(name = "sgst", precision = 2, scale = 1)
	private Float sGst;
	@Column(name = "terms")
	private String terms;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modified")
	private String modifiedBy;
	@Column(name = "ap")
	private String ap;
	@Column(name = "total")
	private int total;

	private boolean cancel;
	private boolean active;

	@OneToMany(mappedBy = "poVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PoVO1> poVO1;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
