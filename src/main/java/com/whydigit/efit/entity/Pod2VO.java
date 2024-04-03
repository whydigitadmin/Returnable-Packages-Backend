package com.whydigit.efit.entity;

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
@Table(name = "pod2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pod2VO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pod2gen")
	@SequenceGenerator(name = "pod2gen", sequenceName = "pod2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pod2id")
	private Long pod2Id;
	@Column(name = "assetcode", length = 30)
	private String assetCode;
	@Column(name = "description", length = 100)
	private String description;
	@Column(name = "rejectedqty", length = 10)
	private int rejectedQty;
	@Column(name = "returnqty", length = 10)
	private int returnQty;

	@ManyToOne
	@JsonBackReference           
	@JoinColumn(name = "podid")
	private PodVO podVO;


}
