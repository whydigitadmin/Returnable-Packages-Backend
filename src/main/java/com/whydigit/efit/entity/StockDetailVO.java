
package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_stock_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailVO {
	@Id
	private Long refPsId;
	private String partStudyId;
	private Long orgId;
	private Integer emitterStoreDays;
	private Integer emitterLineDays;
	private Integer inTransitDays;
	private Integer receiverLineStorageDays;
	private Integer receiverManufacturingLineDays;
	private Integer otherStorageDays;
	private Integer reverseLogisticsDay;
	private Integer totalCycleTime;
	@JsonBackReference
	@OneToOne
	@MapsId
	@JoinColumn(name = "refPsId")
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
