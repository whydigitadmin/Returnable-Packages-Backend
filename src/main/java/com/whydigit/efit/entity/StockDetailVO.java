
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
@Table(name = "partstudy4")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "partstudystockgen")
	@SequenceGenerator(name = "partstudystockgen", sequenceName = "partstudystockseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name ="partstudy4id")
	private Long id;
	
	@Column(name ="refpsid")
	private Long refPsId;
	
	@Column(name ="orgId")
	private Long orgId;
	
	@Column(name ="esd",length =15)
	private int emitterStoreDays;
	
	@Column(name ="eld",length =15)
	private int emitterLineDays;
	
	@Column(name ="intransitdays",length =15)
	private int inTransitDays;
	
	@Column(name ="rld",length =15)
	private int receiverLineStorageDays;
	
	@Column(name ="rmld",length =15)
	private int receiverManufacturingLineDays;
	
	@Column(name ="osd",length =15)
	private int otherStorageDays;
	
	@Column(name ="rlsd",length =15)
	private int reverseLogisticsDay;
	
	@Column(name ="tct",length =15)
	private int totalCycleTime;
	
	
	@JsonBackReference
	@OneToOne
	@MapsId
	@JoinColumn(name = "partstudyid")
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
