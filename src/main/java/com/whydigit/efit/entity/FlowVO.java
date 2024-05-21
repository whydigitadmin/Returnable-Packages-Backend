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

import com.fasterxml.jackson.annotation.JsonGetter;
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
	@Column(name = "receiverid")
	private Long receiverId;
	@Column(name = "flow")
	private String flowName;
	@Column(name = "emitter")
	private String emitter;
	@Column(name = "emitterid")
	private Long emitterId;
	@Column(name = "receiver")
	private String receiver;
	@Column(name = "orgin")
	private String orgin;
	@Column(name = "destination")
	private String destination;
	private boolean active;
	@Column(name = "whlocation")
	private String warehouseLocation;
	
	@Column(name = "warehouseid")
	private Long warehouseId;
	
	@Column(name = "retrievalwhlocation")
	private String retrievalWarehouseLocation;
	@Column(name = "retrievalwarehouseid")
	private Long retrievalWarehouseId;
	
	private boolean cancel;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "docid")
	private String docId;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;
	@Column(name = "cancelRemarks")
	private String cancelRemarks;
	
	@JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    // Optionally, if you want to control serialization for 'cancel' field similarly
    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }

	@JsonManagedReference
	@OneToMany(mappedBy = "flowVO", cascade = CascadeType.ALL)
	private List<FlowDetailVO> flowDetailVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}