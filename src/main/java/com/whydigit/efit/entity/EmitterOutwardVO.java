package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emitter_outward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmitterOutwardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String location;
	private String issuedTo;
	private String partNo;
	private String partName;
	private String partQty;
	private String kitNo;
	private String kitQty;
	private String balancedKit;
	private String invoiceNo;
	private String cycleTime;
	private String previousDispatch;
	private String o2oTat;
	private boolean active;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}