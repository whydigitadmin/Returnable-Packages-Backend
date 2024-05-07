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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flow")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "flowgen")
	@SequenceGenerator(name = "flowgen", sequenceName = "flowseqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "flowid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "receiverid", length = 25)
	private Long receiverId;
	@Column(name = "flow", length = 100)
	private String flowName;
	@Column(name = "emitter")
	private String emitter;
	@Column(name = "emitterid")
	private Long emitterId;
	@Column(name = "receiver", length = 25)
	private String receiver;
	@Column(name = "orgin", length = 25)
	private String orgin;
	@Column(name = "destination", length = 25)
	private String destination;
	private boolean active;
	@Column(name = "whlocation", length = 25)
	private String warehouseLocation;
	@Column(name = "warehouseid")
	private Long warehouseId;
	private boolean cancel;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "docid", length = 16)
	private String docId;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;
	@Column(name = "cancelRemarks", length = 25)
	private String cancelRemarks;

	@JsonManagedReference
	@OneToMany(mappedBy = "flowVO", cascade = CascadeType.ALL)
	private List<FlowDetailVO> flowDetailVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}