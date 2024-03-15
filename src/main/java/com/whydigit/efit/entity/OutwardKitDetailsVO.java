package com.whydigit.efit.entity;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="outward_kit_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutwardKitDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String kitNO;
	private int kitQty;
	private LocalDateTime receivedDate=LocalDateTime.now();

	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@ManyToOne
	@JoinColumn(name="emitter_outward_id")
	@JsonBackReference
	private EmitterOutwardVO emitterOutwardVO;
	
	
	
	
	
	//	many to one
//	outrward id
//	emitter outward voi
//	UpdateOutwardKitQty
//	kitQty
//	kitname
//	outwardId
//	oiutwardid emitteroutwardrepo.findByid()
//	else thorow
	
	
	

}
