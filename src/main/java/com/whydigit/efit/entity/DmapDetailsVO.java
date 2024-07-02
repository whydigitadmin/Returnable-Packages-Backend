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
@Table(name="dmap1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DmapDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dmap1gen")
	@SequenceGenerator(name = "dmap1gen", sequenceName = "dmap1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="dmap1id")
	private Long id;
	
	@Column(name="scode")
	private String scode;
	
	@Column(name="prefx")
	private String prefix;
	
	@Column(name="sequnce")
	private int sequence;
	
	@Column(name="suffix")
	private String sufix;
	
	@Column(name="type")
	private String type;
	
	@Column(name="docidtype")
	private String docIdType;
	
	@Column(name="finyear")
	private String finYear;
	
	@ManyToOne
	@JoinColumn(name = "dmapid")
	@JsonBackReference
	private DmapVO dmapVO;
	

}
