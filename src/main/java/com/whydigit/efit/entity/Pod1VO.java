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
@Table(name = "pod1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pod1VO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pod1gen")
	@SequenceGenerator(name = "pod1gen", sequenceName = "pod1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pod1id")
	private Long pod1Id;
	@Column(name = "assetcode", length = 30)
	private String assetCode;
	@Column(name = "description", length = 100)
	private String description;
	@Column(name = "allotqty", length = 10)
	private int allotQty;
	@Column(name = "acceptqty", length = 10)
	private int acceptQty;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "podid")
	private PodVO podVO;


}
