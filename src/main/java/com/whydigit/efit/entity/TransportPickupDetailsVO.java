package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="transportpickupdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportPickupDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transportpickupdetailsgen")
	@SequenceGenerator(name = "transportpickupdetailsgen", sequenceName = "transportpickupdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "transportpickupdetailsid")
	private Long id;
	@Column(name = "category")
	private String category;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "asset")
	private String asset;
	@Column(name = "pickqty")
	private int pickQty;
	
	
	@ManyToOne
	@JoinColumn(name = "transportpickupid")
	@JsonBackReference
	private TransportPickupVO pickupVO;

}
