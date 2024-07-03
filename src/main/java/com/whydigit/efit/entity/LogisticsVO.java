
package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partstudy3")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "partstudyloggen")
	@SequenceGenerator(name = "partstudyloggen", sequenceName = "partstudylogseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name ="partstudy3id")
	private Long id;
	
	@Column(name ="refpsid")
	private Long refPsId;
	
	@Column(name ="orgid")
	private Long orgId;
	
	@Column(name ="avglotsize")
	private int avgLotSize;
	
	@Column(name ="dispatchfrequency")
	private int dispatchFrequency;
	
	@Column(name ="diapatchto")
	private String diapatchTo;
	
	
	
	
	@JsonBackReference
	@OneToOne
	@MapsId
	@JoinColumn(name = "partstudyid")
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
