package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flow1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "flowdetailgen")
	@SequenceGenerator(name = "flowdetailgen", sequenceName = "flowdetailseqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name="flow1id")
	private Long id;
	@Column(name = "flow2rowid")
	private Long flow2RowId;
	@Column(name="orgid")
	private Long orgId;
	@Column(name="kitcode",length = 25)
	private String kitName;
	@Column(name="partno",length = 25)
	private String partNumber;
	@Column(name="emitter",length = 25)
	private String emitter;
	@Column(name="emitterid")
	private Long emitterId;
	@Column(name="partqty")
	private int partQty;
	@Column(name="partname",length = 25)
	private String partName;
	@Column(name="cycletime",length = 25)
	private String cycleTime;
	@Column(name="subreceiver",length = 25)
	private String subReceiver;
	private boolean active;

	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;
	@Column(name = "cancelRemarks", length = 25)
	private String cancelRemarks;

	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "flowid")
	private FlowVO flowVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	
}