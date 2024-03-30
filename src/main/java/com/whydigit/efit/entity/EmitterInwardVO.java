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
@Table(name="emitter_inward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmitterInwardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String location;
	private String netQtyReceived; 	 
	private String returnQty;
	private String reasonForReturn;
	private String rmNo;
	private String rmDate;
	private String demandDate;
	private String imNo;
	private String imDate;
	private String kitNo;
	private String flow;
	private String reqQty;
	private String IssuedQty;
	private String tat;
	private String parts;
	private String status;
	private boolean active;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
