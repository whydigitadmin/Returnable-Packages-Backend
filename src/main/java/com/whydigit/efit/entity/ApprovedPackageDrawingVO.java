package com.whydigit.efit.entity;

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
@Table(name = "ps_aprove_pkg_drawing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedPackageDrawingVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fileName;
	private Boolean status;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pkg_ref_ps_id")
	private PackingDetailVO packingDetailVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
