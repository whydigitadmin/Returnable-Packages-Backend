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
@Table(name = "partstudy6")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedPackageDrawingVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "partstudy6gen")
	@SequenceGenerator(name = "partstudy6gen", sequenceName = "partstudy6eqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partstudy6id")
	private Long id;
	@Column(name = "filename", length = 50)
	private String fileName;
	@Column(name = "rejectstatus")
	private boolean rejectStatus;
	@Column(name = "partstudy6rowid", length = 15)
	private int partstudy6Id;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "partstudyid")
	private PackingDetailVO packingDetailVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
