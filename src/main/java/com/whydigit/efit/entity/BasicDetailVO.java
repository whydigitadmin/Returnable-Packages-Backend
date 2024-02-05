
package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID partStudyId;
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
	
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private PackingDetailVO packingDetailVO;

	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private LogisticsVO logisticsVO;

	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private StockDetailVO stockDetailVO;

}
