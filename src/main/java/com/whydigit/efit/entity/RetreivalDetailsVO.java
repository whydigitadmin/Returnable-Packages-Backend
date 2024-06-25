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

@Entity
@Table(name="retreivaldetails")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RetreivalDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "retreivaldetailsgen")
	@SequenceGenerator(name = "retreivaldetailsgen", sequenceName = "retreivaldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "retreivaldetailsid")
	private Long id;
	
	@Column(name = "outwarddocid")
	private String outwardDocId;
	
	@Column(name = "outwarddocDate")
	private String outwardDocDate;
	
	@Column(name = "outwardstockbranch")
	private String outwardStockBranch;
	
	@ManyToOne
	@JoinColumn(name = "retreivalid")
	@JsonBackReference
	private RetreivalVO retreivalVO;

}
