package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

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
@Table(name = "dmap1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DmapDetailsVO {
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dmapdetailsgen")
	@SequenceGenerator(name = "dmapdetailsgen", sequenceName = "dmapdetailsseqge", initialValue = 1000000001, allocationSize = 1)
	@Id
	@Column(name = "damp1id")
	private Long id;
	@Column(name = "scode", length = 5)
	private String sCode;
	@Column(name = "prefix", length = 7)
	private String prefix;
	@Column(name = "sequence", length = 5)
	private int sequence;
	@Column(name = "sufix", length = 3)
	private String sufix;
	@Column(name = "type", length = 2)
	private String type;
	
	
	@ManyToOne
	@JoinColumn(name="dampid")
	@JsonBackReference
	private DmapVO dmapVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
