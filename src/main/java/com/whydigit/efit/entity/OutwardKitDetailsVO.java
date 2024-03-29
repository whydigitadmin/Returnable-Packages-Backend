package com.whydigit.efit.entity;

import java.time.LocalDateTime;

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
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="outwardkitdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutwardKitDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "outwardkitgen")
	@SequenceGenerator(name = "outwardkitgen", sequenceName = "outwardkitseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="outwardkitdetailsid")
	private long id;
	@Column(name="kitcode")
	private String kitNO;
	@Column(name="kitqty")
	private int kitQty;
	private LocalDateTime receivedDate=LocalDateTime.now();

	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@ManyToOne
	@JoinColumn(name="emitteroutwardid")
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
