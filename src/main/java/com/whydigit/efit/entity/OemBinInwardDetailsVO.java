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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "oembininwarddetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinInwardDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "oembinwarddetailsgen")
	@SequenceGenerator(name = "oembinwarddetailsgen", sequenceName = "oembinwarddetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "oembinwarddetailsid")
	private Long id;
	@Column(name = "outwarddocid")
	private String outwardDocId;
	@Column(name = "outwarddocdate")
	private String outwardDocDate;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "allotedqty")
	private int allotedQty;
	@Column(name = "kitno")
	private String kitNo;
	@Column(name = "receivedkitqty")
	private int receivedKitQty;
	
	

	@ManyToOne
	@JoinColumn(name = "oembininwardid")
	@JsonBackReference
	private OemBinInwardVO oemBinInwardVO;
}
