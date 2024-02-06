
package com.whydigit.efit.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_basic_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long partStudyId;
	private long orgId;
	private LocalDate partStudyDate;
	private String emitterId;
	private String receiverId;
	private String partName;
	private String partNumber;
	private float weight;
	private String weightUnit;
	private String partVolume;
	private String highestVolume;
	private String lowestVolume;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonManagedReference
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private PackingDetailVO packingDetailVO;

	@JsonManagedReference
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private LogisticsVO logisticsVO;

	@JsonManagedReference
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private StockDetailVO stockDetailVO;

}
