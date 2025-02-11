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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emitteroutward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmitterOutwardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "emitteroutwardgen")
	@SequenceGenerator(name = "emitteroutwardgen", sequenceName = "emitteroutwardseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="emitteroutwardid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Column(name="location")
	private String location;
	@Column(name="kitcode")
	private String kitNo;
	@Column(name="kitqty")
	private int kitQty;
	@Column(name="kitreturnqty")
	private int kitReturnqty;
	
	@Column(name="balancekit")
	private int balancedKit;
	
	@Column(name="invoiceno")
	private String invoiceNo;
	
	@Column(name="cycletime")
	private String cycleTime;
	
	@Column(name="previousdispatch")
	private String previousDispatch;
	
	@Column(name="o2otat")
	private String o2oTat;
	
	private boolean active;
	private LocalDate inwardConfirmDate;
	
	@Column(name="cancel")
	private boolean cancel;
	
	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;
	
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	
	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "issueItemId")
	private IssueItemVO issueItemVO;

	@OneToMany(mappedBy = "emitterOutwardVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OutwardKitDetailsVO> OutwardKitDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	

}
